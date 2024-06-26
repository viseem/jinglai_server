package cn.iocoder.yudao.module.jl.service.taskarrangerelation;

import cn.iocoder.yudao.module.jl.repository.commontask.CommonTaskRepository;
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

import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.taskarrangerelation.vo.*;
import cn.iocoder.yudao.module.jl.entity.taskarrangerelation.TaskArrangeRelation;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.taskarrangerelation.TaskArrangeRelationMapper;
import cn.iocoder.yudao.module.jl.repository.taskarrangerelation.TaskArrangeRelationRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 任务安排关系 Service 实现类
 *
 */
@Service
@Validated
public class TaskArrangeRelationServiceImpl implements TaskArrangeRelationService {

    @Resource
    private TaskArrangeRelationRepository taskArrangeRelationRepository;

    @Resource
    private TaskArrangeRelationMapper taskArrangeRelationMapper;

    @Resource
    private CommonTaskRepository commonTaskRepository;

    @Override
    public Long createTaskArrangeRelation(TaskArrangeRelationCreateReqVO createReqVO) {
        // 插入
        TaskArrangeRelation taskArrangeRelation = taskArrangeRelationMapper.toEntity(createReqVO);
        taskArrangeRelationRepository.save(taskArrangeRelation);
        // 返回
        return taskArrangeRelation.getId();
    }

    @Override
    public void updateTaskArrangeRelation(TaskArrangeRelationUpdateReqVO updateReqVO) {
        // 校验存在
        validateTaskArrangeRelationExists(updateReqVO.getId());
        // 更新
        TaskArrangeRelation updateObj = taskArrangeRelationMapper.toEntity(updateReqVO);
        taskArrangeRelationRepository.save(updateObj);
    }

    @Override
    @Transactional
    public void deleteTaskArrangeRelation(Long id) {
        // 校验存在
        TaskArrangeRelation relation = validateTaskArrangeRelationExists(id);
        // 删除
        taskArrangeRelationRepository.deleteById(id);
        // 删除任务
        if(relation.getTaskId()!=null){
                commonTaskRepository.deleteById(relation.getTaskId());
        }
    }

    private TaskArrangeRelation validateTaskArrangeRelationExists(Long id) {
        return taskArrangeRelationRepository.findById(id).orElseThrow(() -> exception(TASK_ARRANGE_RELATION_NOT_EXISTS));
    }

    @Override
    public Optional<TaskArrangeRelation> getTaskArrangeRelation(Long id) {
        return taskArrangeRelationRepository.findById(id);
    }

    @Override
    public List<TaskArrangeRelation> getTaskArrangeRelationList(Collection<Long> ids) {
        return StreamSupport.stream(taskArrangeRelationRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<TaskArrangeRelation> getTaskArrangeRelationPage(TaskArrangeRelationPageReqVO pageReqVO, TaskArrangeRelationPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<TaskArrangeRelation> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getTaskId() != null) {
                predicates.add(cb.equal(root.get("taskId"), pageReqVO.getTaskId()));
            }

            if(pageReqVO.getChargeItemId() != null) {
                predicates.add(cb.equal(root.get("chargeItemId"), pageReqVO.getChargeItemId()));
            }

            if(pageReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), pageReqVO.getProductId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<TaskArrangeRelation> page = taskArrangeRelationRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<TaskArrangeRelation> getTaskArrangeRelationList(TaskArrangeRelationExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<TaskArrangeRelation> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getTaskId() != null) {
                predicates.add(cb.equal(root.get("taskId"), exportReqVO.getTaskId()));
            }

            if(exportReqVO.getChargeItemId() != null) {
                predicates.add(cb.equal(root.get("chargeItemId"), exportReqVO.getChargeItemId()));
            }

            if(exportReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), exportReqVO.getProductId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return taskArrangeRelationRepository.findAll(spec);
    }

    private Sort createSort(TaskArrangeRelationPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getTaskId() != null) {
            orders.add(new Sort.Order(order.getTaskId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "taskId"));
        }

        if (order.getChargeItemId() != null) {
            orders.add(new Sort.Order(order.getChargeItemId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "chargeItemId"));
        }

        if (order.getProductId() != null) {
            orders.add(new Sort.Order(order.getProductId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}