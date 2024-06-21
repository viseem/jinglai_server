package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.bpm.enums.message.BpmMessageEnum;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.ProjectCategoryLogBaseVO;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.enums.CommonTaskStatusEnums;
import cn.iocoder.yudao.module.jl.enums.CommonTodoEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.repository.commontask.CommonTaskRepository;
import cn.iocoder.yudao.module.jl.repository.project.*;
import cn.iocoder.yudao.module.jl.repository.user.UserRepository;
import cn.iocoder.yudao.module.jl.service.commontask.CommonTaskServiceImpl;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import liquibase.pro.packaged.R;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
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
import cn.iocoder.yudao.module.jl.entity.project.ProjectSop;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectSopMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目中的实验名目的操作SOP Service 实现类
 *
 */
@Service
@Validated
public class ProjectSopServiceImpl implements ProjectSopService {

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @Resource
    private ProjectCategorySimpleRepository projectCategorySimpleRepository;

    @Resource
    private ProjectSopRepository projectSopRepository;

    @Resource
    private ProjectSopMapper projectSopMapper;

    @Resource
    private ProjectCategoryServiceImpl projectCategoryService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private ProjectOnlyRepository projectOnlyRepository;

    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;

    @Resource
    private ProjectCategoryOnlyRepository projectCategoryOnlyRepository;

    @Resource
    private CommonTaskRepository commonTaskRepository;

    @Resource
    private CommonTaskServiceImpl commonTaskService;

    @Override
    public Long createProjectSop(ProjectSopCreateReqVO createReqVO) {
        // 插入
        processSaveData(createReqVO);
        ProjectSop projectSop = projectSopMapper.toEntity(createReqVO);
        projectSopRepository.save(projectSop);
        // 返回
        return projectSop.getId();
    }

    private void processSaveData(ProjectSopBaseVO vo) {


        if(vo.getTaskId()!=null){
            commonTaskRepository.findById(vo.getTaskId()).ifPresentOrElse(task -> {
                vo.setProjectCategoryId(task.getProjectCategoryId());
            }, () -> {
                throw exception(COMMON_TASK_NOT_EXISTS);
            });
        }else{

        }



    }

    @Override
    public void updateProjectSopSort(ProjectSopUpdateSortReqVO reqVO) {

        if(reqVO.getList()!=null){
            for (int i = 0; i < reqVO.getList().size(); i++) {
                reqVO.getList().get(i).setStep(i+1);
//                System.out.println(reqVO.getList().get(i).getId()+"---"+(i+1));
//                projectSopRepository.updateStepById(i+1,reqVO.getList().get(i).getId());
            }
            projectSopRepository.saveAll(reqVO.getList());
        }

    }

    @Override
    @Transactional
    public void updateProjectSop(ProjectSopUpdateReqVO updateReqVO) {
        // 校验存在
        ProjectSop projectSop = validateProjectSopExists(updateReqVO.getId());

        processSaveData(updateReqVO);

        // 更新
        ProjectSop updateObj = projectSopMapper.toEntity(updateReqVO);
        projectSopRepository.save(updateObj);


        if(Objects.equals(updateReqVO.getStatus(),CommonTodoEnums.DONE.getStatus())&&projectSop.getProjectCategoryId()!=null){
            Optional<ProjectCategoryOnly> projectCategoryOnlyOptional = projectCategoryOnlyRepository.findById(updateReqVO.getProjectCategoryId());
            if(projectCategoryOnlyOptional.isPresent()){
                ProjectCategoryOnly category = projectCategoryOnlyOptional.get();
                Optional<ProjectOnly> projectOptional = projectOnlyRepository.findById(category.getProjectId());
                Optional<User> userOptional = userRepository.findById(getLoginUserId());

                if(projectOptional.isPresent()&&userOptional.isPresent()){
                    ProjectOnly project = projectOptional.get();
                    User user = userOptional.get();

                    //发送消息
                    Map<String, Object> templateParams = new HashMap<>();
                    templateParams.put("projectId",project.getId());
                    templateParams.put("content",projectSop.getContent());
                    templateParams.put("userName",user.getNickname());
                    templateParams.put("projectName", project.getName());
                    templateParams.put("categoryName", category.getName());
                    //查询PI组成员
                    List<Long> userIds = new ArrayList<>();

                    if(project.getManagerId()!=null){
                        userIds.add(project.getManagerId());
                    }
/*                if(project.getSalesId()!=null){
                    userIds.add(project.getSalesId());
                }*/
                    // 本来是通知项目的 改为通知这个实验的实验员
                    if(project.getFocusIds()!=null){
                        String[] split = project.getFocusIds().split(",");
                        for (String s : split) {
                            //判断s是否是字符串数字
                            if(s!=null&&s.matches("-?\\d+(\\.\\d+)?")){
                                userIds.add(Long.valueOf(s));
                            }
                        }
                    }

                    for (Long userId : userIds) {
                        if(userId.equals(getLoginUserId())){
                            continue;
                        }
                        notifyMessageSendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO(
                                userId,
                                BpmMessageEnum.NOTIFY_WHEN_SOP_DONE.getTemplateCode(), templateParams
                        ));
                    }
                } // if  projectOptional.isPresent()&&userOptional.isPresent() end

            }

        }

        updateProjectCategoryStageById(updateReqVO.getProjectCategoryId());

        updateCommonTaskStatusById(updateReqVO.getTaskId());

    }

    public void updateProjectCategoryStageById(Long projectCategoryId) {
        if(projectCategoryId!=null){
            List<ProjectSop> sopList = projectSopRepository.findByProjectCategoryId(projectCategoryId);
            int doneCount = 0;
            int totalCount = sopList.size();
            for(ProjectSop sop : sopList) {
                if(sop.getStatus()!=null&&sop.getStatus().equals(CommonTodoEnums.DONE.getStatus())) {
                    doneCount++;
                }
            }
            if(doneCount>0){
                if(doneCount==totalCount){
                    projectCategoryService.updateProjectCategoryStageById(projectCategoryId, ProjectCategoryStatusEnums.DONE.getStatus(),ProjectCategoryStatusEnums.COMPLETE.getStatus(),null);
                }else{
                    projectCategoryService.updateProjectCategoryStageById(projectCategoryId, ProjectCategoryStatusEnums.DOING.getStatus(),null,null);
                }
            }
        }
    }

    public void updateCommonTaskStatusById(Long taskId) {
        if(taskId!=null){
            List<ProjectSop> sopList = projectSopRepository.findByTaskId(taskId);
            int doneCount = 0;
            int totalCount = sopList.size();
            for(ProjectSop sop : sopList) {
                if(sop.getStatus()!=null&&sop.getStatus().equals(CommonTodoEnums.DONE.getStatus())) {
                    doneCount++;
                }
            }
            if(doneCount>0){
                if(doneCount==totalCount){
                    // 更新不是已出库的为已完成
                    commonTaskService.updateCommonTaskStatusById(taskId, CommonTaskStatusEnums.DONE.getStatus(),CommonTaskStatusEnums.DONE.getStatus());
                }else{
                    // 不管是什么状态 更新为进行中
                    commonTaskService.updateCommonTaskStatusById(taskId, CommonTaskStatusEnums.DOING.getStatus(),null);
                }
            }
        }
    }

    @Override
    public void saveProjectSop(List<ProjectSopBaseVO> sopList) {
        // 校验存在
//        validateProjectSopExists(updateReqVO.getId());
        // 更新
        List<Long> projectCategoryIds = sopList.stream().map(ProjectSopBaseVO::getProjectCategoryId).collect(Collectors.toList());
        projectSopRepository.deleteByProjectCategoryIdIn(projectCategoryIds);
        List<ProjectSop> sops = projectSopMapper.toEntity(sopList);
        projectSopRepository.saveAll(sops);
    }

    public void batchUpdateSopStatus(List<ProjectSopBaseVO> sopList) {
        // 校验存在
//        validateProjectSopExists(updateReqVO.getId());
        // 更新
        List<Long> projectCategoryIds = sopList.stream().map(ProjectSopBaseVO::getProjectCategoryId).collect(Collectors.toList());
        projectSopRepository.deleteByProjectCategoryIdIn(projectCategoryIds);
        List<ProjectSop> sops = projectSopMapper.toEntity(sopList);
        projectSopRepository.saveAll(sops);
    }

    @Override
    public void deleteProjectSop(Long id) {
        // 校验存在
        validateProjectSopExists(id);
        // 删除
        projectSopRepository.deleteById(id);
    }

    private ProjectSop validateProjectSopExists(Long id) {
        Optional<ProjectSop> byId = projectSopRepository.findById(id);
        if (byId.isEmpty()) {
            throw exception(PROJECT_SOP_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<ProjectSop> getProjectSop(Long id) {
        return projectSopRepository.findById(id);
    }

    @Override
    public List<ProjectSop> getProjectSopList(Collection<Long> ids) {
        return StreamSupport.stream(projectSopRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectSop> getProjectSopPage(ProjectSopPageReqVO pageReqVO, ProjectSopPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectSop> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getTaskId() != null) {
                predicates.add(cb.equal(root.get("taskId"), pageReqVO.getTaskId()));
            }

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if(pageReqVO.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("categoryId"), pageReqVO.getCategoryId()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getStep() != null) {
                predicates.add(cb.equal(root.get("step"), pageReqVO.getStep()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getDependIds() != null) {
                predicates.add(cb.equal(root.get("dependIds"), pageReqVO.getDependIds()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectSop> page = projectSopRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectSop> getProjectSopList(ProjectSopExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectSop> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if(exportReqVO.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("categoryId"), exportReqVO.getCategoryId()));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getStep() != null) {
                predicates.add(cb.equal(root.get("step"), exportReqVO.getStep()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getDependIds() != null) {
                predicates.add(cb.equal(root.get("dependIds"), exportReqVO.getDependIds()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectSopRepository.findAll(spec);
    }

    private Sort createSort(ProjectSopPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order(Sort.Direction.ASC, "step"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getCategoryId() != null) {
            orders.add(new Sort.Order(order.getCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "categoryId"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getStep() != null) {
            orders.add(new Sort.Order(order.getStep().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "step"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getDependIds() != null) {
            orders.add(new Sort.Order(order.getDependIds().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "dependIds"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}