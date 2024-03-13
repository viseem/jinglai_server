package cn.iocoder.yudao.module.jl.service.crm;

import cn.iocoder.yudao.module.jl.entity.crm.*;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import cn.iocoder.yudao.module.jl.entity.project.ProjectDocument;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.enums.*;
import cn.iocoder.yudao.module.jl.mapper.crm.SalesleadCompetitorMapper;
import cn.iocoder.yudao.module.jl.mapper.crm.SalesleadCustomerPlanMapper;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectConstractMapper;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectMapper;
import cn.iocoder.yudao.module.jl.repository.crm.*;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectDocumentRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.repository.projectquotation.ProjectQuotationRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectConstractServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProjectServiceImpl;
import cn.iocoder.yudao.module.jl.service.statistic.StatisticUtils;
import cn.iocoder.yudao.module.jl.utils.CommonPageSortUtils;
import cn.iocoder.yudao.module.jl.utils.DateAttributeGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Predicate;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.crm.SalesleadMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 销售线索 Service 实现类
 *
 */
@Service
@Validated
public class SalesleadServiceImpl implements SalesleadService {

    @Resource
    private DateAttributeGenerator dateAttributeGenerator;

    @Resource
    private SalesleadRepository salesleadRepository;

    @Resource
    private SalesleadDetailRepository salesleadDetailRepository;

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private SalesleadMapper salesleadMapper;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ProjectConstractRepository projectConstractRepository;

    @Resource
    private ProjectConstractMapper projectConstractMapper;

    @Resource
    private SalesleadCustomerPlanRepository salesleadCustomerPlanRepository;

    @Resource
    private SalesleadCustomerPlanMapper salesleadCustomerPlanMapper;

    @Resource
    private SalesleadCompetitorRepository salesleadCompetitorRepository;

    @Resource
    private SalesleadCompetitorMapper salesleadCompetitorMapper;

    @Resource
    private ProjectServiceImpl projectService;

    @Resource
    private ProjectConstractServiceImpl contractServiceImpl;

    @Resource
    private ProjectDocumentRepository projectDocumentRepository;

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @Resource
    private ProjectQuotationRepository projectQuotationRepository;

    @Override
    public Long createSaleslead(SalesleadCreateReqVO createReqVO) {
        // 插入
        Saleslead saleslead = salesleadMapper.toEntity(createReqVO);
        salesleadRepository.save(saleslead);
        // 返回
        return saleslead.getId();
    }

    @Override
    public SalesleadCountStatsRespVO getSalesleadCountStats(){
        //获取未转化为项目的销售线索数量
        Integer notToProjectCount = salesleadRepository.countByStatusNot(Integer.valueOf(SalesLeadStatusEnums.NotToProject.getStatus()));
        SalesleadCountStatsRespVO salesleadCountStatsRespVO = new SalesleadCountStatsRespVO();
        salesleadCountStatsRespVO.setNotToProjectCount(notToProjectCount);
        return salesleadCountStatsRespVO;
    }

    @Override
    @Transactional
    public void salesleadToSeas(SalesleadSeasVO reqVO){
        // 校验存在
        Saleslead saleslead = validateSalesleadExists(reqVO.getId());
        // 更新
        saleslead.setCreator(null);
        saleslead.setManagerId(null);
        saleslead.setAssignMark(null);
//        saleslead.setCustomerId(0L);
        saleslead.setProjectId(null);
        saleslead.setStatus(Integer.valueOf(SalesLeadStatusEnums.PotentialConsultation.getStatus()));
        saleslead.setAssignMark(null);
        saleslead.setQuotationMark(null);
        saleslead.setJsonLog(reqVO.getJsonLog());
        salesleadRepository.save(saleslead);
        salesleadRepository.updateCreatorById(null,reqVO.getId());
    }

    @Override
    @Transactional
    public void salesleadToSale(SalesleadSeasVO reqVO){
        // 校验存在
         validateSalesleadExists(reqVO.getId());
        salesleadRepository.updateJsonLogById(reqVO.getJsonLog(),reqVO.getId());
        salesleadRepository.updateCreatorById(getLoginUserId(),reqVO.getId());
    }

    @Override
    public void updateSalesleadQuotationMark(SalesleadNoRequireBaseVO updateReqVO){
        salesleadRepository.updateQuotationMarkAndQuotationJsonFileById(updateReqVO.getQuotationMark(),updateReqVO.getQuotationJsonFile(),updateReqVO.getId());
    }

    @Override
    @Transactional
    public void updateSaleslead(SalesleadUpdateReqVO updateReqVO) {

        if(updateReqVO.getId() != null) {
            // 校验存在
            Saleslead saleslead = validateSalesleadExists(updateReqVO.getId());
            updateReqVO.setProjectId(saleslead.getProjectId());
        }

        // 更新线索
        Saleslead saleleadsObj = salesleadMapper.toEntity(updateReqVO);
        salesleadRepository.save(saleleadsObj);
        Long salesleadId = saleleadsObj.getId();

        salesleadCompetitorRepository.deleteBySalesleadId(salesleadId);
        // 再插入
        List<SalesleadCompetitorItemVO> competitorQuotations = updateReqVO.getCompetitorQuotations();
        if(competitorQuotations != null && competitorQuotations.size() > 0) {
            // 遍历 competitorQuotations，将它的 salesleadId 字段设置为 saleleadsObj.getId()
            competitorQuotations.forEach(competitorQuotation -> competitorQuotation.setSalesleadId(salesleadId));
            List<SalesleadCompetitor> quotations = salesleadCompetitorMapper.toEntityList(competitorQuotations);
            salesleadCompetitorRepository.saveAll(quotations);
        }

        // 更新客户方案
        // 删除原有的
        salesleadCustomerPlanRepository.deleteBySalesleadId(salesleadId);
        // 再插入
        List<SalesleadCustomerPlanItemVO> customerPlans = updateReqVO.getCustomerPlans();
        if(customerPlans != null && customerPlans.size() > 0) {
            // 过滤掉fileUrl为null的
            customerPlans = customerPlans.stream().filter(customerPlan -> customerPlan.getFileUrl() != null).collect(Collectors.toList());
            customerPlans.forEach(customerPlan -> {
                customerPlan.setSalesleadId(salesleadId);
                customerPlan.setCustomerId(updateReqVO.getCustomerId());
            });
            List<SalesleadCustomerPlan> plans = salesleadCustomerPlanMapper.toEntityList(customerPlans);
            salesleadCustomerPlanRepository.saveAll(plans);
        }

        //设置customer updateLastSalesleadIdById
        customerRepository.updateLastSalesleadIdById(updateReqVO.getCustomerId(),saleleadsObj.getId());

    }

    @Override
    @Transactional
    public Integer saveSaleslead(SalesleadUpdateReqVO updateReqVO) {

        if(updateReqVO.getId() != null) {
            // 校验存在
            Saleslead saleslead = validateSalesleadExists(updateReqVO.getId());
            updateReqVO.setProjectId(saleslead.getProjectId());
            // 如果线索已经是转项目状态，则不再修改状态
            if(Objects.equals(saleslead.getStatus().toString(),SalesLeadStatusEnums.ToProject.getStatus())){
                updateReqVO.setStatus(SalesLeadStatusEnums.ToProject.getStatus());
            }
        }

        // 更新线索
        Saleslead saleleadsObj = salesleadMapper.toEntity(updateReqVO);

        //如果是申请报价，则更新一下报价创建时间
        if(updateReqVO.getStatus().equals(SalesLeadStatusEnums.QUOTATION.getStatus())){
            // 当前localDateTime
            saleleadsObj.setQuotationCreateTime(LocalDateTime.now());
        }

        Saleslead saleslead = salesleadRepository.save(saleleadsObj);
        Long salesleadId = saleleadsObj.getId();

        salesleadCompetitorRepository.deleteBySalesleadId(salesleadId);
        // 再插入
        List<SalesleadCompetitorItemVO> competitorQuotations = updateReqVO.getCompetitorQuotations();
        if(competitorQuotations != null && competitorQuotations.size() > 0) {
            // 遍历 competitorQuotations，将它的 salesleadId 字段设置为 saleleadsObj.getId()
            competitorQuotations.forEach(competitorQuotation -> competitorQuotation.setSalesleadId(salesleadId));
            List<SalesleadCompetitor> quotations = salesleadCompetitorMapper.toEntityList(competitorQuotations);
            salesleadCompetitorRepository.saveAll(quotations);
        }




        if(updateReqVO.getStatus().equals(SalesLeadStatusEnums.ToProject.getStatus()) || updateReqVO.getStatus().equals(SalesLeadStatusEnums.QUOTATION.getStatus())){
            // 1. 创建项目
            Project project = new Project();
            // 注意这里----
            project.setId(updateReqVO.getProjectId());
            project.setSalesleadId(salesleadId);
            project.setCustomerId(updateReqVO.getCustomerId());
            project.setName(updateReqVO.getProjectName());
            project.setStage("1");
            project.setStatus(updateReqVO.getStatus());
            project.setType(updateReqVO.getType());
            project.setSalesId(getLoginUserId()); // 线索的销售人员 id
            project.setManagerId(updateReqVO.getProjectManagerId()==null?updateReqVO.getManagerId():updateReqVO.getProjectManagerId());

            if(updateReqVO.getProjectId()==null){
                //如果客户不存在，则抛出异常
                Customer customer = customerRepository.findById(updateReqVO.getCustomerId()).orElseThrow(() -> exception(CUSTOMER_NOT_EXISTS));
                String projectName = updateReqVO.getStatus().equals(SalesLeadStatusEnums.QUOTATION.getStatus()) ? customer.getName()+"的报价" : updateReqVO.getProjectName();
                project.setName(projectName);
                Project saveProject = projectRepository.save(project);
                saleleadsObj.setProjectId(saveProject.getId());
                updateReqVO.setProjectId(saveProject.getId());
                salesleadRepository.save(saleleadsObj);
                //创建一个默认的报价
                List<ProjectQuotation> quotations = projectQuotationRepository.findByProjectId(updateReqVO.getProjectId());
                ProjectQuotation quotation;
                if (quotations==null|| quotations.isEmpty() || quotations.get(0)==null) {
                    ProjectQuotation projectQuotation = new ProjectQuotation();
                    projectQuotation.setProjectId(updateReqVO.getProjectId());
                    projectQuotation.setCustomerId(updateReqVO.getCustomerId());
                    projectQuotation.setCode("v1");
                    projectQuotation.setMark("默认报价");
                    quotation=projectQuotationRepository.save(projectQuotation);
                }else{
                    quotation = quotations.get(0);
                }
                projectRepository.updateCurrentQuotationIdById(quotation.getId(), updateReqVO.getProjectId());

            }

            //如果是转项目
            if(updateReqVO.getStatus().equals(SalesLeadStatusEnums.ToProject.getStatus())){
                project = projectRepository.findById(saleslead.getProjectId()).orElseThrow(() -> exception(PROJECT_NOT_EXISTS));
                project.setCode(projectService.generateCode());
                project.setName(updateReqVO.getProjectName());
                project.setStatus(updateReqVO.getStatus());
                project.setManagerId(updateReqVO.getProjectManagerId());
                project.setPreManagerId(saleslead.getManagerId());
                if(updateReqVO.getType()!=null){
                    project.setType(updateReqVO.getType());
                }
//                Arrays.asList(project.getManagerId(),project.getSalesId(),project.getPreManagerId())
                project.setFocusIds(projectService.processProjectFocusIds(project.getFocusIds(),null));
                projectRepository.save(project);
                projectCategoryRepository.updateTypeByQuotationId(ProjectCategoryTypeEnums.SCHEDULE.getStatus(), project.getCurrentQuotationId());
            }
            if(Objects.equals(updateReqVO.getType(),ProjectTypeEnums.NormalProject.getStatus())){

                ProjectConstract bySn = projectConstractRepository.findBySn(updateReqVO.getContractSn());
                if (bySn != null) {
                    return 0;
                }

                ProjectConstract contract = new ProjectConstract();
                contract.setProjectId(saleleadsObj.getProjectId());
                contract.setCustomerId(saleleadsObj.getCustomerId());
                contract.setSalesId(project.getSalesId()==null?getLoginUserId():project.getSalesId()); // 线索的销售人员 id
                contract.setName(updateReqVO.getProjectName());
                contract.setStampFileName(updateReqVO.getContractStampFileName());
                contract.setStampFileUrl(updateReqVO.getContractStampFileUrl());
                contract.setPrice(updateReqVO.getContractPrice());
                contract.setPaperPrice(updateReqVO.getPaperPrice());
                contract.setStatus(ProjectContractStatusEnums.SIGNED.getStatus());
                contract.setSn(updateReqVO.getContractSn());
                contract.setCompanyName(updateReqVO.getContractCompanyName());
                contract.setSignedTime(updateReqVO.getSignedTime());
                projectConstractRepository.save(contract);
            }

/*           List<ProjectConstractItemVO> projectConstracts = updateReqVO.getProjectConstracts();
           if(projectConstracts != null && projectConstracts.size() > 0) {
               // 遍历 projectConstracts，将它的 projectId 字段设置为 project.getId()
               projectConstracts.forEach(projectConstract -> {
                   projectConstract.setProjectId(updateReqVO.getProjectId());
                   projectConstract.setCustomerId(updateReqVO.getCustomerId());
                   projectConstract.setSalesId(saleleadsObj.getCreator()); // 线索的销售人员 id
                   projectConstract.setName(updateReqVO.getProjectName());
               });
               List<ProjectConstract> contracts = projectConstractMapper.toEntityList(projectConstracts);
               projectConstractRepository.saveAll(contracts);
           }*/
        }
        // 更新客户方案
        // 删除原有的
        salesleadCustomerPlanRepository.deleteBySalesleadId(salesleadId);
        // 再插入
        List<SalesleadCustomerPlanItemVO> customerPlans = updateReqVO.getCustomerPlans();
        if(customerPlans != null && customerPlans.size() > 0) {
            // 遍历 customerPlans，将它的 salesleadId 字段设置为 saleleadsObj.getId()
            // 保存到projectDocument里面去,用saveAll一次性保存，先存到list，再saveAll
                List<ProjectDocument> projectDocuments = new ArrayList<>();
                customerPlans.forEach(customerPlan -> {
                    if(customerPlan.getFileUrl()!=null){
                        customerPlan.setSalesleadId(salesleadId);
                        customerPlan.setCustomerId(updateReqVO.getCustomerId());
                        ProjectDocument projectDocument = new ProjectDocument();
                        projectDocument.setProjectId(updateReqVO.getProjectId());
                        projectDocument.setType(ProjectDocumentTypeEnums.CUSTOMER_PLAN.getStatus());
                        projectDocument.setFileName(customerPlan.getFileName());
                        projectDocument.setFileUrl(customerPlan.getFileUrl());
                        projectDocuments.add(projectDocument);
                    }
                });
            if(updateReqVO.getProjectId()!=null){
                projectDocumentRepository.deleteByTypeAndProjectId(ProjectDocumentTypeEnums.CUSTOMER_PLAN.getStatus(),updateReqVO.getProjectId());
                projectDocumentRepository.saveAll(projectDocuments);
            }
            List<SalesleadCustomerPlan> plans = salesleadCustomerPlanMapper.toEntityList(customerPlans);
            salesleadCustomerPlanRepository.saveAll(plans);
        }


        //设置customer updateLastSalesleadIdById
        customerRepository.updateLastSalesleadIdById(updateReqVO.getCustomerId(),saleleadsObj.getId());
        return 1;
    }

    @Override
    public void deleteSaleslead(Long id) {
        // 校验存在
        validateSalesleadExists(id);
        // 删除
        salesleadRepository.deleteById(id);
    }

    private Saleslead validateSalesleadExists(Long id) {
        Optional<Saleslead> byId = salesleadRepository.findById(id);
        if(byId.isEmpty()) {
            throw exception(SALESLEAD_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<SalesleadDetail> getSaleslead(Long id) {
        return salesleadDetailRepository.findById(id);
    }

    @Override
    public List<Saleslead> getSalesleadList(Collection<Long> ids) {
        return StreamSupport.stream(salesleadRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Saleslead> getSalesleadPage(SalesleadPageReqVO pageReqVO, SalesleadPageOrder orderV0) {

//        Long[] users = dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());

        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Saleslead> spec = getSalesleadSpecification(pageReqVO);

        // 执行查询
        Page<Saleslead> page = salesleadRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @NotNull
    private Specification<Saleslead> getSalesleadSpecification(SalesleadPageReqVO pageReqVO) {
        Specification<Saleslead> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // price大于指定金额的
            if (pageReqVO.getQuotation() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("quotation"), pageReqVO.getQuotation()));
            }

            // price小于指定金额的
            if (pageReqVO.getQuotationBig() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("quotation"), pageReqVO.getQuotationBig()));
            }


            if (pageReqVO.getCreateTime() != null) {
                predicates.add(cb.between(root.get("createTime"), pageReqVO.getCreateTime()[0], pageReqVO.getCreateTime()[1]));
            }

            if(pageReqVO.getSource() != null) {
                predicates.add(cb.equal(root.get("source"), pageReqVO.getSource()));
            }

            if(pageReqVO.getRequirement() != null) {
                predicates.add(cb.equal(root.get("requirement"), pageReqVO.getRequirement()));
            }

            if(pageReqVO.getBudget() != null) {
                predicates.add(cb.equal(root.get("budget"), pageReqVO.getBudget()));
            }


            if(pageReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"),getLoginUserId()));
/*                if(pageReqVO.getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
                }*/
            }

            if(pageReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("creator"), pageReqVO.getSalesId()));
            }

            if(pageReqVO.getCreatorIds()==null){
                if(pageReqVO.getCustomerId() != null) {
                    predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
                }else{
                    if (pageReqVO.getManagerId() == null) {
                        if(pageReqVO.getAttribute()!=null){
                            if(Objects.equals(pageReqVO.getAttribute(),DataAttributeTypeEnums.SEAS.getStatus())){
                                predicates.add(root.get("creator").isNull());
                            }else if(!Objects.equals(pageReqVO.getAttribute(),DataAttributeTypeEnums.ANY.getStatus())){
                                Long[] users = pageReqVO.getSalesId()!=null?dateAttributeGenerator.processAttributeUsersWithUserId(pageReqVO.getAttribute(), pageReqVO.getSalesId()):dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
                                pageReqVO.setCreators(users);
                                predicates.add(root.get("creator").in(Arrays.stream(pageReqVO.getCreators()).toArray()));
                            }
                        }


                    }
                }
            }else{
                predicates.add(root.get("creator").in(Arrays.stream(pageReqVO.getCreatorIds()).toArray()));
            }

            if(pageReqVO.getTimeRange()!=null){
                predicates.add(cb.between(root.get("createTime"), StatisticUtils.getStartTimeByTimeRange(pageReqVO.getTimeRange()), LocalDateTime.now()));
            }


            if(pageReqVO.getStatus() != null) {
                //查询未转项目的
                if(pageReqVO.getStatus().toString().equals(SalesLeadStatusEnums.NotToProject.getStatus())){
                    predicates.add(cb.notEqual(root.get("status"), SalesLeadStatusEnums.ToProject.getStatus()));
                }else{

                    predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
                }

            }

            //如果statusArr不为空，则查询statusArr中的状态
            if(pageReqVO.getStatusArr() != null) {
                predicates.add(root.get("status").in(Arrays.stream(pageReqVO.getStatusArr()).toArray()));

                //判断statusArr是否包含未转项目的状态
                if(pageReqVO.getIsWorkstation()==null || !pageReqVO.getIsWorkstation()){
                    if(Arrays.asList(pageReqVO.getStatusArr()).contains(SalesLeadStatusEnums.QUOTATION.getStatus())){
                        predicates.add(cb.equal(root.get("managerId"),getLoginUserId()));
                    }
                }

            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getBusinessType() != null) {
                predicates.add(cb.equal(root.get("businessType"), pageReqVO.getBusinessType()));
            }

            if(pageReqVO.getLostNote() != null) {
                predicates.add(cb.equal(root.get("lostNote"), pageReqVO.getLostNote()));
            }




            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return spec;
    }


    @Override
    public List<Saleslead> getSalesleadList(SalesleadPageReqVO salesleadPageReqVO) {
        // 创建 Specification
        Specification<Saleslead> spec = getSalesleadSpecification(salesleadPageReqVO);
        List<Saleslead> salesleadList = salesleadRepository.findAll(spec);
        for (Saleslead saleslead : salesleadList) {
            if(saleslead.getCustomer()!=null){
                saleslead.setCustomerName(saleslead.getCustomer().getName());
                if(saleslead.getCustomer().getSales()!=null){
                    saleslead.setSalesName(saleslead.getCustomer().getSales().getNickname());
                }
            }

            if(saleslead.getLastFollowup()!=null){
                saleslead.setLastFollowContent(saleslead.getLastFollowup().getContent());
                saleslead.setLastFollowTime(saleslead.getLastFollowup().getCreateTime());
            }

        }
        List<Saleslead> salesleadList1 = salesleadRepository.findAll(spec);
        // 执行查询
        return salesleadList1;
    }

    private Sort createSort(SalesleadPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        // sortFields是createTime_desc,source_asc这种格式，需要先拆分，再排序
/*        if(order.getSortFields()!=null){
            String[] sortFields = order.getSortFields();
            for (String sortField : sortFields) {
                String[] split = sortField.split("_");
                if(split.length==2){
                    orders.add(new Sort.Order(split[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, split[0]));
                }
            }
        }*/

        CommonPageSortUtils.parseAndAddSort(orders, order.getSortFields());

        if (order.getLastFollowTimeSort() != null) {
            orders.add(new Sort.Order(order.getLastFollowTimeSort().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "lastFollowTime"));
        }

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));


        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getSource() != null) {
            orders.add(new Sort.Order(order.getSource().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "source"));
        }

        if (order.getRequirement() != null) {
            orders.add(new Sort.Order(order.getRequirement().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "requirement"));
        }

        if (order.getBudget() != null) {
            orders.add(new Sort.Order(order.getBudget().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "budget"));
        }

        if (order.getQuotation() != null) {
            orders.add(new Sort.Order(order.getQuotation().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quotation"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getBusinessType() != null) {
            orders.add(new Sort.Order(order.getBusinessType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "businessType"));
        }

        if (order.getLostNote() != null) {
            orders.add(new Sort.Order(order.getLostNote().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "lostNote"));
        }

        if (order.getManagerId() != null) {
            orders.add(new Sort.Order(order.getManagerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "managerId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}