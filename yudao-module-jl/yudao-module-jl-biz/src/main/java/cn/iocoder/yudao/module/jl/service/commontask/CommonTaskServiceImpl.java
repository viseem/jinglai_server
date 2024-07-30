package cn.iocoder.yudao.module.jl.service.commontask;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils;
import cn.iocoder.yudao.module.bpm.enums.message.BpmMessageEnum;
import cn.iocoder.yudao.module.bpm.framework.flowable.core.enums.BpmTaskStatustEnum;
import cn.iocoder.yudao.module.jl.controller.admin.commontask.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontask.CommonTask;
import cn.iocoder.yudao.module.jl.entity.product.ProductSelector;
import cn.iocoder.yudao.module.jl.entity.project.*;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.entity.taskarrangerelation.TaskArrangeRelation;
import cn.iocoder.yudao.module.jl.entity.taskproduct.TaskProduct;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.enums.CommonTaskCreateTypeEnums;
import cn.iocoder.yudao.module.jl.enums.CommonTaskStatusEnums;
import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectStageEnums;
import cn.iocoder.yudao.module.jl.mapper.commontask.CommonTaskMapper;
import cn.iocoder.yudao.module.jl.repository.commontask.CommonTaskRepository;
import cn.iocoder.yudao.module.jl.repository.product.ProductOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.product.ProductSelectorRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectChargeitemRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectSopRepository;
import cn.iocoder.yudao.module.jl.repository.projectquotation.ProjectQuotationRepository;
import cn.iocoder.yudao.module.jl.repository.taskarrangerelation.TaskArrangeRelationRepository;
import cn.iocoder.yudao.module.jl.repository.taskproduct.TaskProductRepository;
import cn.iocoder.yudao.module.jl.repository.user.UserRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectChargeitemServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProjectServiceImpl;
import cn.iocoder.yudao.module.jl.service.projectquotation.ProjectQuotationServiceImpl;
import cn.iocoder.yudao.module.jl.service.user.UserServiceImpl;
import cn.iocoder.yudao.module.jl.utils.DateAttributeGenerator;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 通用任务 Service 实现类
 */
@Service
@Validated
public class CommonTaskServiceImpl implements CommonTaskService {

    @Resource
    private CommonTaskRepository commonTaskRepository;

    @Resource
    private CommonTaskMapper commonTaskMapper;

    @Resource
    private UserRepository userRepository;

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @Resource
    private ProjectChargeitemRepository chargeitemRepository;

    @Resource
    private DateAttributeGenerator dateAttributeGenerator;

    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;

    @Resource
    private TaskProductRepository taskProductRepository;

    @Resource
    private TaskArrangeRelationRepository taskArrangeRelationRepository;

    @Resource
    private ProjectChargeitemServiceImpl chargeItemService;

    @Resource
    private ProjectQuotationRepository projectQuotationRepository;

    @Resource
    private ProjectQuotationServiceImpl projectQuotationService;

    @Resource
    private ProjectSopRepository projectSopRepository;

    @Resource
    private ProjectServiceImpl projectService;

    @Resource
    private ProjectChargeitemRepository projectChargeitemRepository;

    @Resource
    private ProductSelectorRepository productSelectorRepository;

    @Resource
    private ProjectOnlyRepository projectOnlyRepository;

    @Resource
    private UserServiceImpl userService;

    @Override
    @Transactional
    public Long createCommonTask(CommonTaskCreateReqVO createReqVO) {

        processCommonTaskSaveData(createReqVO);

        // 插入
        CommonTask commonTask = commonTaskMapper.toEntity(createReqVO);
        if(createReqVO.getManageTaskId()==null || createReqVO.getManageTaskId()==0){
            commonTaskRepository.save(commonTask);
        }else{
            commonTask.setId(createReqVO.getManageTaskId());
        }

        // 插入到 任务安排关系表中
        saveTaskArrangeRelation(createReqVO, commonTask.getId());

        // 更新父任务的实验员，目前先只更新管理任务的，所以如果新增的是产品任务
        saveParentTaskExperIds(createReqVO, commonTask.getId());

        // 发送通知
        if(Objects.equals(createReqVO.getNeedSendMsg(),true)){
            if(createReqVO.getUserId()!=null){
                Map<String, Object> templateParams = new HashMap<>();
                String content = String.format(
                        "收到来自项目(%s)的待办任务(%s)，点击查看",
                        createReqVO.getProjectSimple()!=null?createReqVO.getProjectSimple().getName():"",
                        createReqVO.getName()
                );
                templateParams.put("content",content);
                templateParams.put("id",commonTask.getId());
                notifyMessageSendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO(
                        createReqVO.getUserId(),
                        BpmMessageEnum.NOTIFY_WHEN_COMMON_TASK_WAIT_DO.getTemplateCode(), templateParams
                ));
            }
        }

        // 返回
        return commonTask.getId();
    }
    @Override
    @Transactional
    public void batchCreateCommonTask(CommonTaskBatchCreateReqVO vo){
        // 收费项、任务关系表
        List<TaskArrangeRelation> relationList = new ArrayList<>();

        // 当前登录人既是指派人
        User user = userService.validateUserExists(getLoginUserId());

        // 查询一下项目是否已经进行过开展前审批
        Long projectId = vo.getChargeItemList().get(0).getProjectId();
        ProjectSimple projectSimple = projectService.validateProjectExists(projectId);
        Boolean isDoAudit = projectService.validateProjectIsDoAuditSuccess(projectSimple);

        for (ProjectChargeitem item : vo.getChargeItemList()) {
            // 任务
            CommonTask task = new CommonTask();
            task.setName(item.getName());
            task.setStatus(isDoAudit?CommonTaskStatusEnums.WAIT_DO.getStatus() : CommonTaskStatusEnums.WAIT_SEND.getStatus());
            task.setCreateType(CommonTaskCreateTypeEnums.PRODUCT.getStatus());
            task.setAssignUserId(getLoginUserId());
            task.setAssignUserName(user.getNickname());
            task.setChargeitemId(item.getId());
            task.setQuotationId(item.getQuotationId());
            task.setProductId(item.getProductId());
            task.setProjectId(item.getProjectId());
            task.setProjectCategoryId(item.getProjectCategoryId());

            task.setUserId(vo.getUserId());
            task.setUserNickname(vo.getUserNickname());
//            taskList.add(task);
            commonTaskRepository.save(task);

            // 任务收费项关系表
            TaskArrangeRelation relation = new TaskArrangeRelation();
            relation.setProjectId(item.getProjectId());
            relation.setQuotationId(item.getQuotationId());
            relation.setChargeItemId(item.getId());
            relation.setProductId(item.getProductId());
            relation.setTaskId(task.getId());
            relationList.add(relation);
        }

        taskArrangeRelationRepository.saveAll(relationList);

    }

    private void saveParentTaskExperIds(CommonTaskBaseVO createReqVO, Long taskId) {
        //如果父级id不为空
        if(createReqVO.getParentId()!=null&&createReqVO.getParentId()>0){
            CommonTask commonTask = createReqVO.getParentTask();
            // 如果父级是管理任务，则新增的是产品任务，需要更新一下管理任务的experIds字段
            if(Objects.equals(commonTask.getCreateType(),CommonTaskCreateTypeEnums.MANAGE.getStatus())){
                List<CommonTask> byParentId = commonTaskRepository.findByParentId(createReqVO.getParentId());
                // 把实验员的id在管理任务中记录一下
                List<Long> experIds = new ArrayList<>();

                for (CommonTask task : byParentId) {
                    experIds.add(task.getUserId());
                }

                if(!experIds.isEmpty()){
                    // experIds去重一下

                    commonTaskRepository.updateExperIdsById(experIds.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(",")), createReqVO.getParentId());
                }
            }
        }

    }

    private void saveTaskArrangeRelation(CommonTaskBaseVO reqVO, Long taskId) {

        if(reqVO.getChargeitemId()!=null){
            ProjectChargeitem chargeItem = chargeItemService.validateProjectChargeitemExists(reqVO.getChargeitemId());
            reqVO.setQuotationId( chargeItem.getQuotationId());
            reqVO.setProjectId(chargeItem.getProjectId());


        }


        // 如果是产品(实验)任务，第三级的任务，这里也有两种途径创建：1、收费项的指派按钮 2、任务弹窗里面的新增子任务
        if(Objects.equals(reqVO.getCreateType(), CommonTaskCreateTypeEnums.PRODUCT.getStatus())){
            TaskArrangeRelation relation = new TaskArrangeRelation();
            relation.setTaskId(taskId);
            relation.setChargeItemId(reqVO.getChargeitemId());
            relation.setProductId(reqVO.getProductId());
            relation.setProjectId( reqVO.getProjectId());
            relation.setQuotationId( reqVO.getQuotationId());
            taskArrangeRelationRepository.save(relation);
        }

        System.out.println("---==---=="+reqVO.getQuotationId());

        // 如果是管理任务
        if(Objects.equals(reqVO.getCreateType(), CommonTaskCreateTypeEnums.MANAGE.getStatus())){
            if(reqVO.getChargeList()!=null&& !reqVO.getChargeList().isEmpty()){

//                List<TaskArrangeRelation> relationList = new ArrayList<>();
                // 不用存到relation里面
                for (ProjectChargeitem charge : reqVO.getChargeList()) {
                    if(charge.getTaskList()!=null&&!charge.getTaskList().isEmpty()){

                        charge.getTaskList().forEach(task-> {
                            task.setParentId(taskId);
                            task.setProjectId(reqVO.getProjectId());
                            task.setQuotationId(reqVO.getQuotationId());
                        });
                        commonTaskRepository.saveAll(charge.getTaskList());

                    }
                }
            }
        }

    }

    @Transactional
    public void updateCommonTaskStatusById(Long id, Integer stage, Integer stageNot) {

/*        Optional<ProjectCategory> byId = projectCategoryRepository.findById(id);
        if(byId.isPresent()&&!byId.get().getStage().equals(stage)){
        }*/

        if (stageNot != null) {
            commonTaskRepository.updateStatusByIdAndStatusNot(stage, id, stageNot);
        } else {
            commonTaskRepository.updateStatusById(stage, id);
        }


    }

    @Transactional
    public void processCommonTaskSaveData(CommonTaskBaseVO vo) {

        if(vo.getChargeitemId()!=null&&vo.getChargeitemId()>0){
            ProjectChargeitem chargeItem = chargeItemService.validateProjectChargeitemExists(vo.getChargeitemId());
            vo.setQuotationId(chargeItem.getQuotationId());
            vo.setProjectId(chargeItem.getProjectId());
        }else{
            if(vo.getQuotationId()!=null&&vo.getQuotationId()>0){
                ProjectQuotation quotation = projectQuotationService.validateProjectQuotationExists(vo.getQuotationId());
                vo.setProjectId(quotation.getProjectId());
            }
        }

        if(vo.getParentId()!=null&&vo.getParentId()>0){
            CommonTask commonTask = validateCommonTaskExists(vo.getParentId());
            vo.setQuotationId(commonTask.getQuotationId());
            vo.setProjectId(commonTask.getProjectId());
            vo.setParentTask(commonTask);
        }

        // 如果项目不为空，查询一下项目的状态，如果是开展前审批通过了，则该任务状态改为待开展
        // 这里是项目开展前审批后，单独追加的任务，所以需要发送通知
        if(vo.getProjectId()!=null){
            ProjectSimple projectSimple = projectService.validateProjectExists(vo.getProjectId());
            vo.setProjectSimple(projectSimple);
            //如果项目的开展前审批是同意的，并且客户已经签字确认
            if(Objects.equals(projectSimple.getDoApplyResult(), BpmTaskStatustEnum.APPROVE.getStatus().toString())&&projectSimple.getCustomerSignImgUrl()!=null&&projectSimple.getCustomerSignImgUrl().contains("http")){
                vo.setNeedSendMsg(true);
                vo.setStatus(CommonTaskStatusEnums.WAIT_DO.getStatus());
            }
        }

        if(vo.getUserId()!=null){
            userRepository.findById(vo.getUserId()).ifPresentOrElse(user -> {
                vo.setUserNickname(user.getNickname());
            }, () -> {
                throw exception(USER_NOT_EXISTS);
            });
        }

        if (vo.getAssignUserId() == null && getLoginUserId() != null) {
            userRepository.findById(getLoginUserId()).ifPresentOrElse(user -> {
                vo.setAssignUserId(user.getId());
                vo.setAssignUserName(user.getNickname());
            }, () -> {
                throw exception(USER_NOT_EXISTS);
            });
        }





    }

    private void processCategorySaveData(CommonTaskBaseVO vo, Long projectCategoryId) {
        Optional<ProjectCategory> byId1 = projectCategoryRepository.findById(projectCategoryId);
        if (byId1.isPresent()) {
            ProjectCategory projectCategory = byId1.get();
            vo.setProjectCategoryId(projectCategory.getId());
            vo.setProjectCategoryName(projectCategory.getName());
            vo.setProjectId(projectCategory.getProjectId());
            vo.setCustomerId(projectCategory.getCustomerId());
            if (projectCategory.getProject() != null) {
                vo.setProjectName(projectCategory.getProject().getName());
                if (projectCategory.getProject().getCustomer() != null) {
                    vo.setCustomerName(projectCategory.getProject().getCustomer().getName());
                }
            }
        }
    }

    @Override
    @Transactional
    public void updateCommonTask(CommonTaskUpdateReqVO updateReqVO) {
        // 校验存在
        validateCommonTaskExists(updateReqVO.getId());

        processCommonTaskSaveData(updateReqVO);

        // 更新
        CommonTask updateObj = commonTaskMapper.toEntity(updateReqVO);
        commonTaskRepository.save(updateObj);

        saveParentTaskExperIds(updateReqVO,updateObj.getId());
//        saveTaskArrangeRelation(updateReqVO, updateObj.getId());
    }

    @Override
    public void updateCommonTaskStatus(CommonTaskUpdateReqVO updateReqVO) {
        // 校验存在
        validateCommonTaskExists(updateReqVO.getId());
        commonTaskRepository.updateStatusById(updateReqVO.getStatus(), updateReqVO.getId());
    }

    @Override
    @Transactional
    public void sendCommonTask(CommonTaskSendReqVO reqVO) {
        if (reqVO.getQuotationId() != null) {
            List<CommonTask> byQuotationId = commonTaskRepository.findByQuotationId(reqVO.getQuotationId());
            byQuotationId.forEach(task -> {
                // 只有等于待下发状态的才下发
                if(Objects.equals(task.getStatus(),CommonTaskStatusEnums.WAIT_SEND.getStatus())){
                    //改状态为待接收
                    task.setStatus(CommonTaskStatusEnums.WAIT_RECEIVE.getStatus());
                    Map<String, Object> templateParams = new HashMap<>();
                    templateParams.put("content", processSendTaskContent(task));
                    templateParams.put("id",task.getId());
                    notifyMessageSendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO(
                            task.getUserId(),
                            BpmMessageEnum.NOTIFY_WHEN_COMMON_TASK_SEND.getTemplateCode(), templateParams
                    ));
                }
            });

        }
    }

    private String processSendTaskContent(CommonTask task) {
        return "您有一个任务待接收,任务名称：" + task.getName()+",请及时处理";
    }


    @Override
    @Transactional
    public void deleteCommonTask(Long id) {
        // 校验存在
        CommonTask commonTask = validateCommonTaskExists(id);
        // 删除
        commonTaskRepository.deleteById(id);

        // 删除中间表
        TaskArrangeRelation byTaskId = taskArrangeRelationRepository.findByTaskId(id);
        if(byTaskId!=null){
            taskArrangeRelationRepository.deleteById(byTaskId.getId());
        }

        // 删除子任务
        List<CommonTask> byParentId = commonTaskRepository.findByParentId(id);
        if(byParentId!=null&&!byParentId.isEmpty()){
            commonTaskRepository.deleteAllByIdInBatch(byParentId.stream().map(CommonTask::getId).collect(Collectors.toList()));
        }
    }

    public CommonTask validateCommonTaskExists(Long id) {
       return commonTaskRepository.findById(id).orElseThrow(() -> exception(COMMON_TASK_NOT_EXISTS));
    }

    @Override
    public Optional<CommonTask> getCommonTask(Long id) {
        Optional<CommonTask> byId = commonTaskRepository.findById(id);
        byId.ifPresent(task -> {
            // 查询收费项
            if(task.getChargeitemId()!=null){
                Optional<ProjectChargeitem> byId1 = projectChargeitemRepository.findById(task.getChargeitemId());
                byId1.ifPresent(task::setChargeItem);
            }
            // 查询产品
            if(task.getProductId()!=null){
                Optional<ProductSelector> byId1 = productSelectorRepository.findById(task.getProductId());
                byId1.ifPresent(task::setProduct);
            }
            // 查询项目
            if(task.getProjectId()!=null){
                Optional<ProjectOnly> byId1 = projectOnlyRepository.findById(task.getProjectId());
                byId1.ifPresent(task::setProject);
            }

        });
        return byId;
    }
    @Override
    public CommonTaskCountStatusRespVO getCommonTaskStatusCount() {
        CommonTaskCountStatusRespVO respVO = new CommonTaskCountStatusRespVO();
        Integer i = commonTaskRepository.countByUserIdAndStatusNotIn(getLoginUserId(), CommonTaskStatusEnums.getDoneStatus());
        respVO.setUndoneCount(i);
        return respVO;
    }

    @Override
    public List<CommonTask> getCommonTaskList(Collection<Long> ids) {
        return StreamSupport.stream(commonTaskRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CommonTask> getCommonTaskPage(CommonTaskPageReqVO pageReqVO, CommonTaskPageOrder orderV0) {

        // 这里处理一下 open的接口参数问题，后台接口用的是get请求，quotationId也会被orderVO读取，但是open接口是post请求，orderVO好像没有quotationID了，
        // 这就导致 createSort里面的一个排序sort的判断失效了
        orderV0.setQuotationId(pageReqVO.getQuotationId());

        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CommonTask> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 如果传递了 chargeItemId 或者quotationId,要默认查询包含未下发的任务
            if(pageReqVO.getChargeitemId()!=null || pageReqVO.getQuotationId()!=null || pageReqVO.getParentId()!=null){
                pageReqVO.setHasWaitSend(true);
            }


            //查询 除了未下发的
            if(Objects.equals(pageReqVO.getHasWaitSend(),false)){
                predicates.add(cb.notEqual(root.get("status"), CommonTaskStatusEnums.WAIT_SEND.getStatus()));
            }

            if (pageReqVO.getCreateType() != null) {
                predicates.add(cb.equal(root.get("createType"), pageReqVO.getCreateType()));
            }

            if (pageReqVO.getParentId() != null) {
                predicates.add(cb.equal(root.get("parentId"), pageReqVO.getParentId()));
            }

            // 这里的逻辑是：如果没有收费项id、没有报价id，则默认查询自己的任务
            if ((pageReqVO.getChargeitemId() == null && pageReqVO.getQuotationId()==null)&& !pageReqVO.getAttribute().equals(DataAttributeTypeEnums.ANY.getStatus())) {
                Long[] users = pageReqVO.getUserId() != null ? dateAttributeGenerator.processAttributeUsersWithUserId(pageReqVO.getAttribute(), pageReqVO.getUserId()) : dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
                predicates.add(root.get("userId").in(Arrays.stream(users).toArray()));
            }

            if (pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if (pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if (pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if (pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if (pageReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), pageReqVO.getStartDate()[0], pageReqVO.getStartDate()[1]));
            }
            if (pageReqVO.getEndDate() != null) {
                predicates.add(cb.between(root.get("endDate"), pageReqVO.getEndDate()[0], pageReqVO.getEndDate()[1]));
            }
            if (pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }

            if (pageReqVO.getFocusIds() != null) {
                predicates.add(cb.equal(root.get("focusIds"), pageReqVO.getFocusIds()));
            }

            if (pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if (pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if (pageReqVO.getChargeitemId() != null) {
                predicates.add(cb.equal(root.get("chargeitemId"), pageReqVO.getChargeitemId()));
            }

            if (pageReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), pageReqVO.getProductId()));
            }

            if (pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if (pageReqVO.getQuotationId() != null) {
                predicates.add(cb.equal(root.get("quotationId"), pageReqVO.getQuotationId()));
            }

            if (pageReqVO.getProjectName() != null) {
                predicates.add(cb.like(root.get("projectName"), "%" + pageReqVO.getProjectName() + "%"));
            }

            if (pageReqVO.getCustomerName() != null) {
                predicates.add(cb.like(root.get("customerName"), "%" + pageReqVO.getCustomerName() + "%"));
            }

            if (pageReqVO.getChargeitemName() != null) {
                predicates.add(cb.like(root.get("chargeitemName"), "%" + pageReqVO.getChargeitemName() + "%"));
            }

            if (pageReqVO.getProductName() != null) {
                predicates.add(cb.like(root.get("productName"), "%" + pageReqVO.getProductName() + "%"));
            }

            if (pageReqVO.getProjectCategoryName() != null) {
                predicates.add(cb.like(root.get("projectCategoryName"), "%" + pageReqVO.getProjectCategoryName() + "%"));
            }

            if (pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if (pageReqVO.getLevel() != null) {
                predicates.add(cb.equal(root.get("level"), pageReqVO.getLevel()));
            }

            if (pageReqVO.getAssignUserId() != null) {
                predicates.add(cb.equal(root.get("assignUserId"), pageReqVO.getAssignUserId()));
            }

            if (pageReqVO.getAssignUserName() != null) {
                predicates.add(cb.like(root.get("assignUserName"), "%" + pageReqVO.getAssignUserName() + "%"));
            }

            if (pageReqVO.getLabIds() != null) {
                predicates.add(cb.equal(root.get("labIds"), pageReqVO.getLabIds()));
            }

            if (pageReqVO.getSort() != null) {
                predicates.add(cb.equal(root.get("sort"), pageReqVO.getSort()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CommonTask> page = commonTaskRepository.findAll(spec, pageable);

        List<CommonTask> content = page.getContent();
        if(pageReqVO.getHasSopList()){
            List<Long> taskIds = content.stream()
                    .map(CommonTask::getId)
                    .collect(Collectors.toList());

            List<ProjectSop> byTaskIdIn = projectSopRepository.findByTaskIdIn(taskIds);

            if (byTaskIdIn != null) {
                Map<Long, List<ProjectSop>> sopMap = byTaskIdIn.stream()
                        .collect(Collectors.groupingBy(ProjectSop::getTaskId));

                content.forEach(commonTask ->
                        commonTask.setSopList(sopMap.getOrDefault(commonTask.getId(), new ArrayList<>()))
                );
            }
        }

        // 转换为 PageResult 并返回
        return new PageResult<>(content, page.getTotalElements());
    }

    @Override
    public List<CommonTask> getCommonTaskList(CommonTaskExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CommonTask> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if (exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if (exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if (exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if (exportReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), exportReqVO.getStartDate()[0], exportReqVO.getStartDate()[1]));
            }
            if (exportReqVO.getEndDate() != null) {
                predicates.add(cb.between(root.get("endDate"), exportReqVO.getEndDate()[0], exportReqVO.getEndDate()[1]));
            }
            if (exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }

            if (exportReqVO.getFocusIds() != null) {
                predicates.add(cb.equal(root.get("focusIds"), exportReqVO.getFocusIds()));
            }

            if (exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if (exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if (exportReqVO.getChargeitemId() != null) {
                predicates.add(cb.equal(root.get("chargeitemId"), exportReqVO.getChargeitemId()));
            }

            if (exportReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), exportReqVO.getProductId()));
            }

            if (exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if (exportReqVO.getQuotationId() != null) {
                predicates.add(cb.equal(root.get("quotationId"), exportReqVO.getQuotationId()));
            }

            if (exportReqVO.getProjectName() != null) {
                predicates.add(cb.like(root.get("projectName"), "%" + exportReqVO.getProjectName() + "%"));
            }

            if (exportReqVO.getCustomerName() != null) {
                predicates.add(cb.like(root.get("customerName"), "%" + exportReqVO.getCustomerName() + "%"));
            }

            if (exportReqVO.getChargeitemName() != null) {
                predicates.add(cb.like(root.get("chargeitemName"), "%" + exportReqVO.getChargeitemName() + "%"));
            }

            if (exportReqVO.getProductName() != null) {
                predicates.add(cb.like(root.get("productName"), "%" + exportReqVO.getProductName() + "%"));
            }

            if (exportReqVO.getProjectCategoryName() != null) {
                predicates.add(cb.like(root.get("projectCategoryName"), "%" + exportReqVO.getProjectCategoryName() + "%"));
            }

            if (exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if (exportReqVO.getLevel() != null) {
                predicates.add(cb.equal(root.get("level"), exportReqVO.getLevel()));
            }

            if (exportReqVO.getAssignUserId() != null) {
                predicates.add(cb.equal(root.get("assignUserId"), exportReqVO.getAssignUserId()));
            }

            if (exportReqVO.getAssignUserName() != null) {
                predicates.add(cb.like(root.get("assignUserName"), "%" + exportReqVO.getAssignUserName() + "%"));
            }

            if (exportReqVO.getLabIds() != null) {
                predicates.add(cb.equal(root.get("labIds"), exportReqVO.getLabIds()));
            }

            if (exportReqVO.getSort() != null) {
                predicates.add(cb.equal(root.get("sort"), exportReqVO.getSort()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return commonTaskRepository.findAll(spec);
    }

    private Sort createSort(CommonTaskPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if(order.getQuotationId()!=null){
//            orderV0.setCreateTime("asc");
            orders.add(new Sort.Order( Sort.Direction.ASC , "sort"));
        }

        orders.add(new Sort.Order("desc".equals(order.getCreateTime()) ? Sort.Direction.DESC : Sort.Direction.ASC, "status"));
        if(order.getQuotationId()!=null){
            orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
        }else{
            orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        }



        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getStartDate() != null) {
            orders.add(new Sort.Order(order.getStartDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "startDate"));
        }

        if (order.getEndDate() != null) {
            orders.add(new Sort.Order(order.getEndDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "endDate"));
        }

        if (order.getUserId() != null) {
            orders.add(new Sort.Order(order.getUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "userId"));
        }

        if (order.getFocusIds() != null) {
            orders.add(new Sort.Order(order.getFocusIds().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "focusIds"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getChargeitemId() != null) {
            orders.add(new Sort.Order(order.getChargeitemId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "chargeitemId"));
        }

        if (order.getProductId() != null) {
            orders.add(new Sort.Order(order.getProductId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productId"));
        }

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getQuotationId() != null) {
            orders.add(new Sort.Order(order.getQuotationId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quotationId"));
        }

        if (order.getProjectName() != null) {
            orders.add(new Sort.Order(order.getProjectName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectName"));
        }

        if (order.getCustomerName() != null) {
            orders.add(new Sort.Order(order.getCustomerName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerName"));
        }

        if (order.getChargeitemName() != null) {
            orders.add(new Sort.Order(order.getChargeitemName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "chargeitemName"));
        }

        if (order.getProductName() != null) {
            orders.add(new Sort.Order(order.getProductName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productName"));
        }

        if (order.getProjectCategoryName() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryName"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getLevel() != null) {
            orders.add(new Sort.Order(order.getLevel().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "level"));
        }

        if (order.getAssignUserId() != null) {
            orders.add(new Sort.Order(order.getAssignUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "assignUserId"));
        }

        if (order.getAssignUserName() != null) {
            orders.add(new Sort.Order(order.getAssignUserName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "assignUserName"));
        }

        if (order.getLabIds() != null) {
            orders.add(new Sort.Order(order.getLabIds().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "labIds"));
        }

        if (order.getSort() != null) {
            orders.add(new Sort.Order(order.getSort().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sort"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }

    @Transactional
    public void sendTaskAndMsg(Long currentQuotationId, String projectName, Long projectId) {

        // 发送通知：1、只发给任务状态是未下发的 2、排除当前登录人
        HashSet<Long> userIds = new HashSet<>();

        //查询任务
        List<CommonTask> byQuotationId = commonTaskRepository.findByQuotationId(currentQuotationId);
        if(byQuotationId!=null){
            for (CommonTask commonTask : byQuotationId) {
                if(Objects.equals(commonTask.getStatus(),CommonTaskStatusEnums.WAIT_SEND.getStatus())){
                    userIds.add(commonTask.getUserId());
                }
            }
        }

        Map<String, Object> templateParams = new HashMap<>();
        String content = String.format(
                "收到来自项目(%s)的待办任务，点击查看",
                projectName
        );
        templateParams.put("projectName", projectName);
        templateParams.put("content", content);
        templateParams.put("id", projectId);
        for (Long userId : userIds) {
            if (userId == null||userId.equals(WebFrameworkUtils.getLoginUserId())) {
                continue;
            }
            notifyMessageSendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO(
                    userId,
                    BpmMessageEnum.NOTIFY_WHEN_PROJECT_COMMON_TASK_WAIT_DO.getTemplateCode(), templateParams
            ));
        }

        commonTaskRepository.updateStatusByQuotationIdAndStatus(CommonTaskStatusEnums.WAIT_DO.getStatus(), currentQuotationId,CommonTaskStatusEnums.WAIT_SEND.getStatus());
    }
}