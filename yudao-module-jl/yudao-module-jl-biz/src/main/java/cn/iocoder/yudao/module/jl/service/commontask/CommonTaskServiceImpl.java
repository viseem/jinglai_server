package cn.iocoder.yudao.module.jl.service.commontask;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bpm.enums.message.BpmMessageEnum;
import cn.iocoder.yudao.module.jl.controller.admin.commontask.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontask.CommonTask;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.entity.taskarrangerelation.TaskArrangeRelation;
import cn.iocoder.yudao.module.jl.entity.taskproduct.TaskProduct;
import cn.iocoder.yudao.module.jl.enums.CommonTaskCreateTypeEnums;
import cn.iocoder.yudao.module.jl.enums.CommonTaskStatusEnums;
import cn.iocoder.yudao.module.jl.enums.CommonTaskTypeEnums;
import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import cn.iocoder.yudao.module.jl.mapper.commontask.CommonTaskMapper;
import cn.iocoder.yudao.module.jl.repository.commontask.CommonTaskRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectChargeitemRepository;
import cn.iocoder.yudao.module.jl.repository.projectquotation.ProjectQuotationRepository;
import cn.iocoder.yudao.module.jl.repository.taskarrangerelation.TaskArrangeRelationRepository;
import cn.iocoder.yudao.module.jl.repository.taskproduct.TaskProductRepository;
import cn.iocoder.yudao.module.jl.repository.user.UserRepository;
import cn.iocoder.yudao.module.jl.service.laboratory.ChargeItemServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProjectChargeitemServiceImpl;
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

    @Override
    @Transactional
    public Long createCommonTask(CommonTaskCreateReqVO createReqVO) {

        processCommonTaskSaveData(createReqVO);

        // 插入
        CommonTask commonTask = commonTaskMapper.toEntity(createReqVO);
        commonTaskRepository.save(commonTask);

        // 插入到 任务安排关系表中
        saveTaskArrangeRelation(createReqVO, commonTask.getId());

        // 返回
        return commonTask.getId();
    }

    private void saveTaskArrangeRelation(CommonTaskBaseVO reqVO, Long taskId) {

        // 如果是收费项指派的任务，这个叫做实验(产品)任务
        if(Objects.equals(reqVO.getCreateType(), CommonTaskCreateTypeEnums.PRODUCT.getStatus())){

            if(reqVO.getChargeitemId()==null){
                throw exception(CHARGE_ITEM_NOT_EXISTS);
            }
            ProjectChargeitem chargeItem = chargeItemService.validateProjectChargeitemExists(reqVO.getChargeitemId());
            TaskArrangeRelation relation = new TaskArrangeRelation();
            relation.setTaskId(taskId);
            relation.setChargeItemId(reqVO.getChargeitemId());
            relation.setProductId(reqVO.getProductId());
            relation.setProjectId(chargeItem.getProjectId());
            relation.setQuotationId(chargeItem.getQuotationId());
            taskArrangeRelationRepository.save(relation);
        }

        // 如果是管理任务
        if(Objects.equals(reqVO.getCreateType(), CommonTaskCreateTypeEnums.MANAGE.getStatus())){
            if(reqVO.getChargeList()!=null&& !reqVO.getChargeList().isEmpty()){
                List<TaskArrangeRelation> relationList = new ArrayList<>();
                for (ProjectChargeitem charge : reqVO.getChargeList()) {
                    TaskArrangeRelation relation = new TaskArrangeRelation();
                    relation.setTaskId(taskId);
                    relation.setChargeItemId(charge.getId());
                    relation.setProductId(charge.getProductId());
                    relation.setProjectId(charge.getProjectId());
                    relation.setQuotationId(charge.getQuotationId());
                    relationList.add(relation);
                }
                taskArrangeRelationRepository.saveAll(relationList);
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

        userRepository.findById(vo.getUserId()).ifPresentOrElse(user -> {
            vo.setUserNickname(user.getNickname());
        }, () -> {
            throw exception(USER_NOT_EXISTS);
        });


        if (vo.getAssignUserId() == null && getLoginUserId() != null) {
            userRepository.findById(getLoginUserId()).ifPresentOrElse(user -> {
                vo.setAssignUserId(user.getId());
                vo.setAssignUserName(user.getNickname());
            }, () -> {
                throw exception(USER_NOT_EXISTS);
            });
        }

        // 如果报价id不为空，则设置一下项目id,这里没有从前端传过来
        if(vo.getQuotationId()!=null){
            projectQuotationRepository.findById(vo.getQuotationId()).ifPresentOrElse(quotation->{
                vo.setProjectId(quotation.getProjectId());
            },()->{
                throw exception(PROJECT_QUOTATION_NOT_EXISTS);
            });
        }

/*        if (vo.getChargeitemId() != null || vo.getProjectCategoryId() != null) {
            vo.setType(CommonTaskTypeEnums.PROJECT.getStatus());
        }*/

        // 如果有chargeitemId，则查询chargeItem
/*        if (vo.getChargeitemId() != null) {

            Optional<ProjectChargeitem> byId = chargeitemRepository.findById(vo.getChargeitemId());
            if (byId.isPresent()) {
                ProjectChargeitem projectChargeitem = byId.get();
                vo.setChargeitemName(projectChargeitem.getName());
                vo.setQuotationId(projectChargeitem.getQuotationId());
                vo.setProductId(projectChargeitem.getProductId());

                if (projectChargeitem.getProjectCategoryId() != null) {
                    Long projectCategoryId = projectChargeitem.getProjectCategoryId();
                    processCategorySaveData(vo, projectCategoryId);
                }
            }
        }*/

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
    public void deleteCommonTask(Long id) {
        // 校验存在
        validateCommonTaskExists(id);
        // 删除
        commonTaskRepository.deleteById(id);
    }

    private void validateCommonTaskExists(Long id) {
        commonTaskRepository.findById(id).orElseThrow(() -> exception(COMMON_TASK_NOT_EXISTS));
    }

    @Override
    public Optional<CommonTask> getCommonTask(Long id) {
        Optional<CommonTask> byId = commonTaskRepository.findById(id);
        byId.ifPresent(task -> {
            List<TaskProduct> byTaskId = taskProductRepository.findByTaskId(task.getId());
            task.setProductList(byTaskId);
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
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CommonTask> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

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

            if (pageReqVO.getChargeitemId() == null && !pageReqVO.getAttribute().equals(DataAttributeTypeEnums.ANY.getStatus())) {
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

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
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
        orders.add(new Sort.Order("desc".equals(order.getCreateTime()) ? Sort.Direction.DESC : Sort.Direction.ASC, "status"));
        orders.add(new Sort.Order("asc".equals(order.getId()) ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));


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
}