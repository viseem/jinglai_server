package cn.iocoder.yudao.module.jl.service.projectpushlog;

import cn.iocoder.yudao.module.jl.service.commonattachment.CommonAttachmentServiceImpl;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectpushlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectpushlog.ProjectPushLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectpushlog.ProjectPushLogMapper;
import cn.iocoder.yudao.module.jl.repository.projectpushlog.ProjectPushLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目推进记录 Service 实现类
 *
 */
@Service
@Validated
public class ProjectPushLogServiceImpl implements ProjectPushLogService {

    @Resource
    private ProjectPushLogRepository projectPushLogRepository;

    @Resource
    private ProjectPushLogMapper projectPushLogMapper;

    @Resource
    private CommonAttachmentServiceImpl commonAttachmentService;

    @Override
    @Transactional
    public Long createProjectPushLog(ProjectPushLogCreateReqVO createReqVO) {
        // 插入
        ProjectPushLog projectPushLog = projectPushLogMapper.toEntity(createReqVO);
        projectPushLogRepository.save(projectPushLog);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(projectPushLog.getId(),"PROJECT_PUSH_LOG",createReqVO.getAttachmentList());
        // 返回
        return projectPushLog.getId();
    }

    @Override
    @Transactional
    public void updateProjectPushLog(ProjectPushLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectPushLogExists(updateReqVO.getId());
        // 更新
        ProjectPushLog updateObj = projectPushLogMapper.toEntity(updateReqVO);
        projectPushLogRepository.save(updateObj);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(updateReqVO.getId(),"PROJECT_PUSH_LOG",updateReqVO.getAttachmentList());
    }

    @Override
    public void deleteProjectPushLog(Long id) {
        // 校验存在
        validateProjectPushLogExists(id);
        // 删除
        projectPushLogRepository.deleteById(id);
    }

    private void validateProjectPushLogExists(Long id) {
        projectPushLogRepository.findById(id).orElseThrow(() -> exception(PROJECT_PUSH_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectPushLog> getProjectPushLog(Long id) {
        return projectPushLogRepository.findById(id);
    }

    @Override
    public List<ProjectPushLog> getProjectPushLogList(Collection<Long> ids) {
        return StreamSupport.stream(projectPushLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectPushLog> getProjectPushLogPage(ProjectPushLogPageReqVO pageReqVO, ProjectPushLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectPushLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getRecordTime() != null) {
                predicates.add(cb.between(root.get("recordTime"), pageReqVO.getRecordTime()[0], pageReqVO.getRecordTime()[1]));
            } 
            if(pageReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), pageReqVO.getStage()));
            }

            if(pageReqVO.getProgress() != null) {
                predicates.add(cb.equal(root.get("progress"), pageReqVO.getProgress()));
            }

            if(pageReqVO.getExpectedProgress() != null) {
                predicates.add(cb.equal(root.get("expectedProgress"), pageReqVO.getExpectedProgress()));
            }

            if(pageReqVO.getRisk() != null) {
                predicates.add(cb.equal(root.get("risk"), pageReqVO.getRisk()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectPushLog> page = projectPushLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectPushLog> getProjectPushLogList(ProjectPushLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectPushLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getRecordTime() != null) {
                predicates.add(cb.between(root.get("recordTime"), exportReqVO.getRecordTime()[0], exportReqVO.getRecordTime()[1]));
            } 
            if(exportReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), exportReqVO.getStage()));
            }

            if(exportReqVO.getProgress() != null) {
                predicates.add(cb.equal(root.get("progress"), exportReqVO.getProgress()));
            }

            if(exportReqVO.getExpectedProgress() != null) {
                predicates.add(cb.equal(root.get("expectedProgress"), exportReqVO.getExpectedProgress()));
            }

            if(exportReqVO.getRisk() != null) {
                predicates.add(cb.equal(root.get("risk"), exportReqVO.getRisk()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectPushLogRepository.findAll(spec);
    }

    private Sort createSort(ProjectPushLogPageOrder order) {
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

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getRecordTime() != null) {
            orders.add(new Sort.Order(order.getRecordTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "recordTime"));
        }

        if (order.getStage() != null) {
            orders.add(new Sort.Order(order.getStage().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "stage"));
        }

        if (order.getProgress() != null) {
            orders.add(new Sort.Order(order.getProgress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "progress"));
        }

        if (order.getExpectedProgress() != null) {
            orders.add(new Sort.Order(order.getExpectedProgress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "expectedProgress"));
        }

        if (order.getRisk() != null) {
            orders.add(new Sort.Order(order.getRisk().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "risk"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}