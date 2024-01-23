package cn.iocoder.yudao.module.jl.service.taskrelation;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
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
import cn.iocoder.yudao.module.jl.controller.admin.taskrelation.vo.*;
import cn.iocoder.yudao.module.jl.entity.taskrelation.TaskRelation;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.taskrelation.TaskRelationMapper;
import cn.iocoder.yudao.module.jl.repository.taskrelation.TaskRelationRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 任务关系依赖 Service 实现类
 *
 */
@Service
@Validated
public class TaskRelationServiceImpl implements TaskRelationService {

    @Resource
    private TaskRelationRepository taskRelationRepository;

    @Resource
    private TaskRelationMapper taskRelationMapper;

    @Override
    public Long createTaskRelation(TaskRelationCreateReqVO createReqVO) {
        // 插入
        TaskRelation taskRelation = taskRelationMapper.toEntity(createReqVO);
        taskRelationRepository.save(taskRelation);
        // 返回
        return taskRelation.getId();
    }

    @Override
    public void updateTaskRelation(TaskRelationUpdateReqVO updateReqVO) {
        // 校验存在
        validateTaskRelationExists(updateReqVO.getId());
        // 更新
        TaskRelation updateObj = taskRelationMapper.toEntity(updateReqVO);
        taskRelationRepository.save(updateObj);
    }

    @Override
    public void deleteTaskRelation(Long id) {
        // 校验存在
        validateTaskRelationExists(id);
        // 删除
        taskRelationRepository.deleteById(id);
    }

    private void validateTaskRelationExists(Long id) {
        taskRelationRepository.findById(id).orElseThrow(() -> exception(TASK_RELATION_NOT_EXISTS));
    }

    @Override
    public Optional<TaskRelation> getTaskRelation(Long id) {
        return taskRelationRepository.findById(id);
    }

    @Override
    public List<TaskRelation> getTaskRelationList(Collection<Long> ids) {
        return StreamSupport.stream(taskRelationRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<TaskRelation> getTaskRelationPage(TaskRelationPageReqVO pageReqVO, TaskRelationPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<TaskRelation> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getLevel() != null) {
                predicates.add(cb.equal(root.get("level"), pageReqVO.getLevel()));
            }

            if(pageReqVO.getTaskId() != null) {
                predicates.add(cb.equal(root.get("taskId"), pageReqVO.getTaskId()));
            }

            if(pageReqVO.getDependId() != null) {
                predicates.add(cb.equal(root.get("dependId"), pageReqVO.getDependId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<TaskRelation> page = taskRelationRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<TaskRelation> getTaskRelationList(TaskRelationExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<TaskRelation> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getLevel() != null) {
                predicates.add(cb.equal(root.get("level"), exportReqVO.getLevel()));
            }

            if(exportReqVO.getTaskId() != null) {
                predicates.add(cb.equal(root.get("taskId"), exportReqVO.getTaskId()));
            }

            if(exportReqVO.getDependId() != null) {
                predicates.add(cb.equal(root.get("dependId"), exportReqVO.getDependId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return taskRelationRepository.findAll(spec);
    }

    private Sort createSort(TaskRelationPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getLevel() != null) {
            orders.add(new Sort.Order(order.getLevel().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "level"));
        }

        if (order.getTaskId() != null) {
            orders.add(new Sort.Order(order.getTaskId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "taskId"));
        }

        if (order.getDependId() != null) {
            orders.add(new Sort.Order(order.getDependId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "dependId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}