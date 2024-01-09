package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.appcustomer.CustomerProjectPageReqVO;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.project.*;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectStageEnums;
import cn.iocoder.yudao.module.jl.enums.SalesLeadStatusEnums;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectScheduleMapper;
import cn.iocoder.yudao.module.jl.repository.crm.CustomerSimpleRepository;
import cn.iocoder.yudao.module.jl.repository.crmsubjectgroup.CrmSubjectGroupRepository;
import cn.iocoder.yudao.module.jl.repository.project.*;
import cn.iocoder.yudao.module.jl.repository.projectperson.ProjectPersonRepository;
import cn.iocoder.yudao.module.jl.repository.projectquotation.ProjectQuotationRepository;
import cn.iocoder.yudao.module.jl.repository.user.UserRepository;
import cn.iocoder.yudao.module.jl.utils.DateAttributeGenerator;
import cn.iocoder.yudao.module.jl.utils.UniqCodeGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Predicate;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.jl.utils.JLSqlUtils.*;
import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.*;

/**
 * 项目管理 Service 实现类
 *
 */
@Service
@Validated
public class ProjectServiceImpl implements ProjectService {
    private final String uniqCodeKey = AUTO_INCREMENT_KEY_PROJECT_CODE.getKeyTemplate();
    private final String uniqCodePrefixKey = PREFIX_PROJECT_CODE.getKeyTemplate();

    @Resource
    private DateAttributeGenerator dateAttributeGenerator;

    @Resource
    private UniqCodeGenerator uniqCodeGenerator;

    @Resource
    private ProjectPersonRepository projectPersonRepository;

    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private ProjectSimpleRepository projectSimpleRepository;

    public static final String PROCESS_KEY = "PROJECT_OUTBOUND_APPLY";
    @Resource
    private BpmProcessInstanceApi processInstanceApi;

    @Resource
    private ProjectQuotationRepository projectQuotationRepository;

    @PostConstruct
    public void ProjectServiceImpl(){
        Project last = projectRepository.findFirstByOrderByIdDesc();
        uniqCodeGenerator.setInitUniqUid(last!=null?last.getCode():"",uniqCodeKey);
    }


    public String generateCode() {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        long count = projectRepository.countByCodeStartsWith(String.format("%s%s", PROJECT_CODE_DEFAULT_PREFIX, dateStr));
        if (count == 0) {
            uniqCodeGenerator.setUniqUid(0L);
        }
        return String.format("%s%s%04d", PROJECT_CODE_DEFAULT_PREFIX, dateStr, uniqCodeGenerator.generateUniqUid());
    }



    public ProjectServiceImpl(UserRepository userRepository,
                              ProjectConstractRepository projectConstractRepository,
                              ProjectCategoryRepository projectCategoryRepository,
                              ProjectSupplyRepository projectSupplyRepository,
                              ProjectChargeitemRepository projectChargeitemRepository) {
        this.userRepository = userRepository;
        this.projectConstractRepository = projectConstractRepository;
        this.projectCategoryRepository = projectCategoryRepository;
        this.projectSupplyRepository = projectSupplyRepository;
        this.projectChargeitemRepository = projectChargeitemRepository;
    }



    @Resource
    private ProjectScheduleRepository projectScheduleRepository;

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ProjectScheduleMapper projectScheduleMapper;
    private final UserRepository userRepository;
    private final ProjectConstractRepository projectConstractRepository;
    private final ProjectCategoryRepository projectCategoryRepository;
    private final ProjectSupplyRepository projectSupplyRepository;
    private final ProjectChargeitemRepository projectChargeitemRepository;


    @Override
    public Long createProject(ProjectCreateReqVO createReqVO) {
        // 插入
        //如果状态，注意这里的状态是销售线索的状态，不是项目的状态
        if(!Objects.equals(SalesLeadStatusEnums.QUOTATION.getStatus(),createReqVO.getStatus())){
            createReqVO.setCode(generateCode());
        }

        //如果项目负责人和销售负责人不存在于关注人中，则添加进去
//        createReqVO.getManagerId(),createReqVO.getSalesId(),createReqVO.getPreManagerId(),getLoginUserId(),createReqVO.getAfterManagerId(),createReqVO.getExperId())
        createReqVO.setFocusIds(processProjectFocusIds(createReqVO.getFocusIds(),null));

        Project project = projectMapper.toEntity(createReqVO);
        Project saveProject = projectRepository.save(project);

        Long projectId = project.getId();

        //创建一个默认的报价
        List<ProjectQuotation> quotations = projectQuotationRepository.findByProjectId(projectId);
        ProjectQuotation quotation;
        if (quotations==null|| quotations.isEmpty() || quotations.get(0)==null) {
            ProjectQuotation projectQuotation = new ProjectQuotation();
            projectQuotation.setProjectId(projectId);
            projectQuotation.setCustomerId(saveProject.getCustomerId());
            projectQuotation.setCode("v1");
            projectQuotation.setMark("默认报价");
            quotation=projectQuotationRepository.save(projectQuotation);
        }else{
            quotation = quotations.get(0);
        }
        projectRepository.updateCurrentQuotationIdById(quotation.getId(), projectId);

        // 创建默认的安排单
//        ProjectScheduleSaveReqVO saveScheduleReqVO = new ProjectScheduleSaveReqVO();
//        saveScheduleReqVO.setProjectId(projectId);
//        saveScheduleReqVO.setName(project.getName() + "的默认安排单");
//        ProjectSchedule projectSchedule = projectScheduleMapper.toEntity(saveScheduleReqVO);
//        projectScheduleRepository.save(projectSchedule);

        // 设置项目当前安排单
//        setProjectCurrentSchedule(projectId, projectSchedule.getId());




        // 返回
        return project.getId();
    }

    public String processProjectFocusIds(String _focusIds,List<Long> ids) {
        List<Long> focusIds = new ArrayList<>();
        if(_focusIds!=null&& !_focusIds.isEmpty()) {
            focusIds = Arrays.stream(_focusIds.split(","))
                    .filter(s -> s.matches("\\d+")) // Only include if s is a number
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }
        if(ids!=null&&!ids.isEmpty()){
            for (Long id : ids) {
                if (!focusIds.contains(id)) {
                    focusIds.add(id);
                }
            }
        }
        /*if (!focusIds.contains(managerId)) {
            focusIds.add(managerId);
        }
        if (!focusIds.contains(salesId)) {
            focusIds.add(salesId);
        }*/

        return focusIds.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @Override
    @Transactional
    public void projectOutboundApply(ProjectOutboundApplyReqVO outboundApplyReqVO){
        // 校验存在
        ProjectSimple project = validateProjectExists(outboundApplyReqVO.getProjectId());

        //加入审批流
        Map<String, Object> processInstanceVariables = new HashMap<>();
        String processInstanceId = processInstanceApi.createProcessInstance(getLoginUserId(),
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(outboundApplyReqVO.getProjectId())).setVariables(new HashMap<>() {{
                            put("projectSalesId", project.getSalesId());
                        }}));

        //更新出库状态、流程实例id、申请人
        projectRepository.updateStageAndProcessInstanceIdAndApplyUserById(outboundApplyReqVO.getProjectId(),ProjectStageEnums.OUTING.getStatus(),processInstanceId,getLoginUserId());

    }

    @Override
    @Transactional
    public void updateProject(ProjectUpdateReqVO updateReqVO) {
        // 校验存在
        ProjectSimple project = validateProjectExists(updateReqVO.getId());
/*        if(project.getCode()==null|| project.getCode().equals("")){
            updateReqVO.setCode(generateCode());
        }*/

        //删除旧的人员
//        projectPersonRepository.deleteByProjectId(updateReqVO.getId());
        //处理persons，添加新的人员
//        projectPersonRepository.saveAll(updateReqVO.getPersons());

        //如果项目负责人和销售负责人不存在于关注人中，则添加进去
//        Arrays.asList(updateReqVO.getManagerId(),updateReqVO.getSalesId(),updateReqVO.getPreManagerId(),getLoginUserId(),updateReqVO.getAfterManagerId(),updateReqVO.getExperId())
        updateReqVO.setFocusIds(processProjectFocusIds(updateReqVO.getFocusIds(),null));
        // 更新
        Project updateObj = projectMapper.toEntity(updateReqVO);
        projectRepository.save(updateObj);
    }

    @Override
    public void setProjectCurrentSchedule(Long projectId, Long scheduleId) {
        // 校验存在
        validateProjectExists(projectId);

        projectRepository.updateCurrentScheduleIdById(projectId, scheduleId);
    }

    @Override
    public void deleteProject(Long id) {
        // 校验存在
        validateProjectExists(id);
        // 删除
        projectRepository.deleteById(id);
    }

    public ProjectSimple validateProjectExists(Long id) {
        Optional<ProjectSimple> byId = projectSimpleRepository.findById(id);
        if(byId.isEmpty()){
            throw exception(PROJECT_NOT_EXISTS);
        }
        return byId.orElse(null);
    }

    @Override
    public Optional<Project> getProject(Long id) {
        Optional<Project> byId = projectRepository.findById(id);
        byId.ifPresent(item->{
            processProjectItem(item,true);
        });
        return byId;
    }

    @Override
    public List<Project> getProjectList(Collection<Long> ids) {
        return StreamSupport.stream(projectRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectStatsRespVO getProjectStats(ProjectPageReqVO pageReqVO){
        ProjectStatsRespVO respVO = new ProjectStatsRespVO();
        Map<String,Integer> map = new HashMap<>();


        if(pageReqVO.getSalesId()!=null){
            //项目预开展个数
            respVO.setPreCount(projectRepository.countByStageAndSalesId(ProjectStageEnums.PRE.getStatus(),getLoginUserId()));
            map.put(ProjectStageEnums.PRE.getStatus(),respVO.getPreCount());
            //项目开展个数
            respVO.setDoingCount(projectRepository.countByStageAndSalesId(ProjectStageEnums.DOING.getStatus(),getLoginUserId()));
            map.put(ProjectStageEnums.DOING.getStatus(),respVO.getDoingCount());
            //项目已出库个数
            respVO.setOutedCount(projectRepository.countByStageAndSalesId(ProjectStageEnums.OUTED.getStatus(),getLoginUserId()));
            map.put(ProjectStageEnums.OUTED.getStatus(),respVO.getOutedCount());
            //项目出库中个数
            respVO.setOutingCount(projectRepository.countByStageAndSalesId(ProjectStageEnums.OUTING.getStatus(),getLoginUserId()));
            map.put(ProjectStageEnums.OUTING.getStatus(),respVO.getOutingCount());
            //项目暂停个数
            respVO.setPauseCount(projectRepository.countByStageAndSalesId(ProjectStageEnums.PAUSE.getStatus(),getLoginUserId()));
            map.put(ProjectStageEnums.PAUSE.getStatus(),respVO.getPauseCount());
        }else{

            if(pageReqVO.getAttribute()== null){
                //项目预开展个数
                respVO.setPreCount(projectRepository.countByStage(ProjectStageEnums.PRE.getStatus()));
                map.put(ProjectStageEnums.PRE.getStatus(),respVO.getPreCount());
                //项目开展个数
                respVO.setDoingCount(projectRepository.countByStage(ProjectStageEnums.DOING.getStatus()));
                map.put(ProjectStageEnums.DOING.getStatus(),respVO.getDoingCount());
                //项目已出库个数
                respVO.setOutedCount(projectRepository.countByStage(ProjectStageEnums.OUTED.getStatus()));
                map.put(ProjectStageEnums.OUTED.getStatus(),respVO.getOutedCount());
                //项目出库中个数
                respVO.setOutingCount(projectRepository.countByStage(ProjectStageEnums.OUTING.getStatus()));
                map.put(ProjectStageEnums.OUTING.getStatus(),respVO.getOutingCount());
                //项目暂停个数
                respVO.setPauseCount(projectRepository.countByStage(ProjectStageEnums.PAUSE.getStatus()));
                map.put(ProjectStageEnums.PAUSE.getStatus(),respVO.getPauseCount());
            } else if (pageReqVO.getAttribute().equals(DataAttributeTypeEnums.MY.getStatus())){
                //项目预开展个数
                respVO.setPreCount(projectRepository.countByStageAndManagerId(ProjectStageEnums.PRE.getStatus(),getLoginUserId()));
                map.put(ProjectStageEnums.PRE.getStatus(),respVO.getPreCount());
                //项目开展个数
                respVO.setDoingCount(projectRepository.countByStageAndManagerId(ProjectStageEnums.DOING.getStatus(),getLoginUserId()));
                map.put(ProjectStageEnums.DOING.getStatus(),respVO.getDoingCount());
                //项目已出库个数
                respVO.setOutedCount(projectRepository.countByStageAndManagerId(ProjectStageEnums.OUTED.getStatus(),getLoginUserId()));
                map.put(ProjectStageEnums.OUTED.getStatus(),respVO.getOutedCount());
                //项目出库中个数
                respVO.setOutingCount(projectRepository.countByStageAndManagerId(ProjectStageEnums.OUTING.getStatus(),getLoginUserId()));
                map.put(ProjectStageEnums.OUTING.getStatus(),respVO.getOutingCount());
                //项目暂停个数
                respVO.setPauseCount(projectRepository.countByStageAndManagerId(ProjectStageEnums.PAUSE.getStatus(),getLoginUserId()));
                map.put(ProjectStageEnums.PAUSE.getStatus(),respVO.getPauseCount());
            }
        }

        respVO.setCountMap(map);

        //赋值给resp的map
        return respVO;
    }

    @Override
    public ProjectSupplyAndChargeRespVO getProjectSupplyAndCharge(ProjectSupplyAndChargeReqVO reqVO){
        ProjectSupplyAndChargeRespVO respVO = new ProjectSupplyAndChargeRespVO();
        List<ProjectSupply> byQuotationId = projectSupplyRepository.findByQuotationId(reqVO.getQuotationId());
        respVO.setSupplyList(byQuotationId);
        List<ProjectChargeitem> byQuotationId1 = projectChargeitemRepository.findByQuotationId(reqVO.getQuotationId());
        respVO.setChargeList(byQuotationId1);
        return respVO;
    }

    @Override
    public PageResult<Project> getProjectPage(ProjectPageReqVO pageReqVO, ProjectPageOrder orderV0) {

        Long[] users = dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
        pageReqVO.setManagers(users);

        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Project> spec = getProjectCommonSpecification(pageReqVO);

        // 执行查询
        Page<Project> page = projectRepository.findAll(spec, pageable);
        page.forEach(item->{
            processProjectItem(item,false);
        });

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @NotNull
    private <T>Specification<T> getProjectCommonSpecification(ProjectPageReqVO pageReqVO) {
        Specification<T> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getIsSale()!=null&&!pageReqVO.getIsSale()){
                if(pageReqVO.getAttribute()!=null&&!Objects.equals(pageReqVO.getAttribute(), DataAttributeTypeEnums.ANY.getStatus())){
                    if(Objects.equals(pageReqVO.getAttribute(),DataAttributeTypeEnums.FOCUS.getStatus())) {
                        mysqlFindInSet(getLoginUserId(),"focusIds", root, cb, predicates);
                    }else{
                        predicates.add(root.get("managerId").in(Arrays.stream(pageReqVO.getManagers()).toArray()));
                    }
                }
            }else{

                Long[] users = pageReqVO.getSalesId()!=null?dateAttributeGenerator.processAttributeUsersWithUserId(pageReqVO.getAttribute(), pageReqVO.getSalesId()):dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
                pageReqVO.setCreators(users);
                if(pageReqVO.getAttribute()!=null&&!Objects.equals(pageReqVO.getAttribute(),DataAttributeTypeEnums.ANY.getStatus())){
                    predicates.add(root.get("salesId").in(Arrays.stream(pageReqVO.getCreators()).toArray()));
                }

            }



            //默认查询code不为空的
            predicates.add(cb.isNotNull(root.get("code")));

            if(pageReqVO.getFocusId() != null) {
                mysqlFindInSet(pageReqVO.getFocusId(),"focusIds", root, cb, predicates);
            }

            // 课题组
            if(pageReqVO.getSubjectGroupId() != null) {
                predicates.add(cb.equal(root.get("subjectGroupId"), pageReqVO.getSubjectGroupId()));
            }


/*            if(pageReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), pageReqVO.getSalesId()));
            }*/

            if(pageReqVO.getStageArr() != null&& !pageReqVO.getStageArr().isEmpty()) {
                predicates.add(root.get("stage").in(pageReqVO.getStageArr()));
            }

            if(pageReqVO.getSalesleadId() != null) {
                predicates.add(cb.equal(root.get("salesleadId"), pageReqVO.getSalesleadId()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }
            if(pageReqVO.getCode() != null) {
                predicates.add(cb.like(root.get("code"), "%" + pageReqVO.getCode() + "%"));
            }

            if(pageReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), pageReqVO.getStage()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), pageReqVO.getStartDate()[0], pageReqVO.getStartDate()[1]));
            }
            if(pageReqVO.getEndDate() != null) {
                predicates.add(cb.between(root.get("endDate"), pageReqVO.getEndDate()[0], pageReqVO.getEndDate()[1]));
            }
            if(pageReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), pageReqVO.getManagerId()));
            }

            if(pageReqVO.getParticipants() != null) {
                predicates.add(cb.equal(root.get("participants"), pageReqVO.getParticipants()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getProcurementerId() != null) {
                predicates.add(cb.equal(root.get("procurementerId"), pageReqVO.getProcurementerId()));
            }
            if(pageReqVO.getInventorierId() != null) {
                predicates.add(cb.equal(root.get("inventorierId"), pageReqVO.getInventorierId()));
            }
            if(pageReqVO.getExperId() != null) {
                predicates.add(cb.equal(root.get("experId"), pageReqVO.getExperId()));
            }
/*            if(pageReqVO.getExpersId() != null) {
                predicates.add(cb.in(root.get("experIds"), pageReqVO.getExpersId()));
            }*/
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return spec;
    }

    @Override
    public PageResult<ProjectSimple> getCustomerProjectPage(CustomerProjectPageReqVO pageReqVO, ProjectPageOrder orderV0) {


        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectSimple> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), getLoginUserId()));
            }

            if(pageReqVO.getStageArr() != null&& !pageReqVO.getStageArr().isEmpty()) {
                predicates.add(root.get("stage").in(pageReqVO.getStageArr()));
            }

            if(pageReqVO.getSalesleadId() != null) {
                predicates.add(cb.equal(root.get("salesleadId"), pageReqVO.getSalesleadId()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), pageReqVO.getStage()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), pageReqVO.getStartDate()[0], pageReqVO.getStartDate()[1]));
            }
            if(pageReqVO.getEndDate() != null) {
                predicates.add(cb.between(root.get("endDate"), pageReqVO.getEndDate()[0], pageReqVO.getEndDate()[1]));
            }
            if(pageReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), pageReqVO.getManagerId()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getProcurementerId() != null) {
                predicates.add(cb.equal(root.get("procurementerId"), pageReqVO.getProcurementerId()));
            }
            if(pageReqVO.getInventorierId() != null) {
                predicates.add(cb.equal(root.get("inventorierId"), pageReqVO.getInventorierId()));
            }
            if(pageReqVO.getExperId() != null) {
                predicates.add(cb.equal(root.get("experId"), pageReqVO.getExperId()));
            }
/*            if(pageReqVO.getExpersId() != null) {
                predicates.add(cb.in(root.get("experIds"), pageReqVO.getExpersId()));
            }*/
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectSimple> page = projectSimpleRepository.findAll(spec, pageable);
//        page.forEach(this::processProjectSimpleItem);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    private void processProjectSimpleItem(ProjectSimple project) {
        long completeCount = projectCategoryRepository.countByProjectIdAndStageAndType(
                project.getId(), ProjectCategoryStatusEnums.COMPLETE.getStatus()
        );
        long allCount = projectCategoryRepository.countByProjectIdAndType(project.getId());
        project.setAllCount((int) allCount);
        project.setCompleteCount((int) completeCount);
        //计算百分比
        if(allCount>0){
            project.setCompletePercent((int) (completeCount*100/allCount));
        }

    }
    private void processProjectItem(Project project,Boolean isDetail) {
        long completeCount = projectCategoryRepository.countByProjectIdAndStageAndType(
                project.getId(), ProjectCategoryStatusEnums.COMPLETE.getStatus()
        );
        long waitDoCount = projectCategoryRepository.countByProjectIdAndStageAndType(
                project.getId(), ProjectCategoryStatusEnums.WAIT_DO.getStatus()
        );
        long pauseCount = projectCategoryRepository.countByProjectIdAndStageAndType(
                project.getId(), ProjectCategoryStatusEnums.PAUSE.getStatus()
        );
        long doingCount = projectCategoryRepository.countByProjectIdAndStageAndType(
                project.getId(), ProjectCategoryStatusEnums.DOING.getStatus()
        );
        long allCount = projectCategoryRepository.countByProjectIdAndType(project.getId());
        project.setAllCount(allCount);
        project.setCompleteCount(completeCount);
        project.setDoingCount(doingCount);
        project.setPauseCount(pauseCount);
        project.setWaitDoCount(waitDoCount);
        //计算百分比
        if(allCount>0){
            project.setCompletePercent((int) (completeCount*100/allCount));
        }
        //处理参与者
        if(project.getFocusIds()!=null&&!project.getFocusIds().isEmpty()){
            project.setFocusList(idsString2QueryList(project.getFocusIds(),userRepository));
        }

    }

    @Override
    public PageResult<ProjectSimple> getProjectSimplePage(ProjectPageReqVO pageReqVO, ProjectPageOrder orderV0) {

        Long[] users = dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
        pageReqVO.setManagers(users);

        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectSimple> spec = getProjectCommonSpecification(pageReqVO);

        // 执行查询
        Page<ProjectSimple> page = projectSimpleRepository.findAll(spec, pageable);
//        page.forEach(this::processProjectItem);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }


    @Override
    public List<Project> getProjectList(ProjectExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Project> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getSalesleadId() != null) {
                predicates.add(cb.equal(root.get("salesleadId"), exportReqVO.getSalesleadId()));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), exportReqVO.getStage()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), exportReqVO.getStartDate()[0], exportReqVO.getStartDate()[1]));
            } 
            if(exportReqVO.getEndDate() != null) {
                predicates.add(cb.between(root.get("endDate"), exportReqVO.getEndDate()[0], exportReqVO.getEndDate()[1]));
            } 
            if(exportReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), exportReqVO.getManagerId()));
            }

            if(exportReqVO.getParticipants() != null) {
                predicates.add(cb.equal(root.get("participants"), exportReqVO.getParticipants()));
            }

            if(exportReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), exportReqVO.getSalesId()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectRepository.findAll(spec);
    }

    private Sort createSort(ProjectPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getSalesleadId() != null) {
            orders.add(new Sort.Order(order.getSalesleadId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salesleadId"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getStage() != null) {
            orders.add(new Sort.Order(order.getStage().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "stage"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getStartDate() != null) {
            orders.add(new Sort.Order(order.getStartDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "startDate"));
        }

        if (order.getEndDate() != null) {
            orders.add(new Sort.Order(order.getEndDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "endDate"));
        }

        if (order.getManagerId() != null) {
            orders.add(new Sort.Order(order.getManagerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "managerId"));
        }

        if (order.getParticipants() != null) {
            orders.add(new Sort.Order(order.getParticipants().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "participants"));
        }

        if (order.getSalesId() != null) {
            orders.add(new Sort.Order(order.getSalesId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salesId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}