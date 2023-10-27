package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.module.bpm.enums.message.BpmMessageEnum;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.module.jl.entity.projectfeedback.ProjectFeedbackFocus;
import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectFeedbackEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.repository.projectfeedback.ProjectFeedbackFocusRepository;
import cn.iocoder.yudao.module.jl.utils.DateAttributeGenerator;
import cn.iocoder.yudao.module.system.api.mail.dto.MailSendSingleToUserReqDTO;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
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

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFeedback;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectFeedbackMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectFeedbackRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目反馈 Service 实现类
 */
@Service
@Validated
public class ProjectFeedbackServiceImpl implements ProjectFeedbackService {
    @Resource
    private DateAttributeGenerator dateAttributeGenerator;
    @Resource
    private ProjectFeedbackRepository projectFeedbackRepository;

    @Resource
    private ProjectFeedbackFocusRepository projectFeedbackFocusRepository;

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private ProjectFeedbackMapper projectFeedbackMapper;
    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;
    @Override
    @Transactional
    public Long createProjectFeedback(ProjectFeedbackCreateReqVO createReqVO) {
        //查询项目是否存在
        Project project = projectRepository.findById(createReqVO.getProjectId()).orElseThrow(() -> exception(PROJECT_NOT_EXISTS));
        // 校验是否已经反馈过
        // 插入
        ProjectFeedback projectFeedback = projectFeedbackMapper.toEntity(createReqVO);
        projectFeedback.setStatus(ProjectFeedbackEnums.NOT_PROCESS.getStatus());
        projectFeedback.setCustomerId(project.getCustomerId());
        projectFeedbackRepository.save(projectFeedback);

        // 如果是projectCategory的反馈，则更新projectCategory的字段 TODO 可能会更新失败
        if (createReqVO.getProjectCategoryId() != null && createReqVO.getProjectCategoryId() > 0) {
            projectCategoryRepository.updateHasFeedbackById(createReqVO.getProjectCategoryId(), (byte) 1);
        }

        //保存关注人列表,使用projectFeedbackFocusRepository.saveAll()方法
/*        projectFeedbackFocusRepository.saveAll(createReqVO.getFocusUserIds().stream().map(focus -> {
            //发送站内通知
            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("processInstanceName", reqDTO.getProcessInstanceName());
            templateParams.put("detailUrl", getProcessInstanceDetailUrl(reqDTO.getProcessInstanceId()));
            //发送通知
            notifyMessageSendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO(
                    reqDTO.getStartUserId(),
                    BpmMessageEnum.NOTIFY_WHEN_APPROVAL.getTemplateCode(), templateParams
            ));

            return new ProjectFeedbackFocus().setProjectFeedbackId(projectFeedback.getId()).setUserId(focus);
        }).collect(Collectors.toList()));*/

        // 返回
        return projectFeedback.getId();
    }

/*    private String getProcessInstanceDetailUrl(String taskId) {
        return webProperties.getAdminUi().getUrl() + "/link/transfer/taskInstance?id=" + taskId;
    }*/

    @Override
    public void updateProjectFeedback(ProjectFeedbackUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectFeedbackExists(updateReqVO.getId());
        // 更新
        ProjectFeedback updateObj = projectFeedbackMapper.toEntity(updateReqVO);
        projectFeedbackRepository.save(updateObj);
    }

    @Override
    public void deleteProjectFeedback(Long id) {
        // 校验存在
        validateProjectFeedbackExists(id);
        // 删除
        projectFeedbackRepository.deleteById(id);
    }

    private void validateProjectFeedbackExists(Long id) {
        projectFeedbackRepository.findById(id).orElseThrow(() -> exception(PROJECT_FEEDBACK_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectFeedback> getProjectFeedback(Long id) {
        return projectFeedbackRepository.findById(id);
    }

    @Override
    public List<ProjectFeedback> getProjectFeedbackList(Collection<Long> ids) {
        return StreamSupport.stream(projectFeedbackRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectFeedback> getProjectFeedbackPage(ProjectFeedbackPageReqVO pageReqVO, ProjectFeedbackPageOrder orderV0) {

        //获取attribute
        Long[] users = dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
        //这里注意 是起错名字了 这个是责任人 不是创建人 逻辑没错
        pageReqVO.setCreators(users);


        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectFeedback> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            //如果是看自己的
            if(!pageReqVO.getAttribute().equals(DataAttributeTypeEnums.ANY.getStatus())){
                if(pageReqVO.getCreator()!=null&& pageReqVO.getCreator()==1){
                    predicates.add(cb.equal(root.get("creator"), getLoginUserId()));
                }else{
                    //不是看自己的 默认查由自己处理的
                    predicates.add(root.get("userId").in(Arrays.stream(pageReqVO.getCreators()).toArray()));
                }
            }

            if (pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if (pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if (pageReqVO.getProjectStage() != null) {
                predicates.add(cb.equal(root.get("projectStage"), pageReqVO.getProjectStage()));
            }

            if (pageReqVO.getFeedType() != null) {
                predicates.add(cb.equal(root.get("feedType"), pageReqVO.getFeedType()));
            }

            if (pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }

            if (pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if (pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if (pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            //content!=null  like content
            if (pageReqVO.getContent() != null) {
                predicates.add(cb.like(root.get("content"), "%" + pageReqVO.getContent() + "%"));
            }


            if (pageReqVO.getResult() != null) {
                predicates.add(cb.equal(root.get("result"), pageReqVO.getResult()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectFeedback> page = projectFeedbackRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectFeedback> getProjectFeedbackList(ProjectFeedbackExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectFeedback> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if (exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if (exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }

            if (exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if (exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if (exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if (exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if (exportReqVO.getResult() != null) {
                predicates.add(cb.equal(root.get("result"), exportReqVO.getResult()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectFeedbackRepository.findAll(spec);
    }

    /**
     * @param replyReqVO
     */
    @Override
    public void replyFeedback(ProjectFeedbackReplyReqVO replyReqVO) {
        // 校验存在
        validateProjectFeedbackExists(replyReqVO.getId());
        replyReqVO.setStatus(ProjectFeedbackEnums.PROCESSED.getStatus());
        // 更新
        projectFeedbackRepository.replyFeedback(replyReqVO.getResult(), replyReqVO.getResultUserId(), LocalDateTime.now(), replyReqVO.getStatus(), replyReqVO.getId());
    }

    private Sort createSort(ProjectFeedbackPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getUserId() != null) {
            orders.add(new Sort.Order(order.getUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "userId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getResult() != null) {
            orders.add(new Sort.Order(order.getResult().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "result"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}