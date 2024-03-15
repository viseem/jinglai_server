package cn.iocoder.yudao.module.jl.service.worktodotagrelation;

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
import cn.iocoder.yudao.module.jl.controller.admin.worktodotagrelation.vo.*;
import cn.iocoder.yudao.module.jl.entity.worktodotagrelation.WorkTodoTagRelation;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.worktodotagrelation.WorkTodoTagRelationMapper;
import cn.iocoder.yudao.module.jl.repository.worktodotagrelation.WorkTodoTagRelationRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 工作任务 TODO 与标签的关联 Service 实现类
 *
 */
@Service
@Validated
public class WorkTodoTagRelationServiceImpl implements WorkTodoTagRelationService {

    @Resource
    private WorkTodoTagRelationRepository workTodoTagRelationRepository;

    @Resource
    private WorkTodoTagRelationMapper workTodoTagRelationMapper;

    @Override
    public Long createWorkTodoTagRelation(WorkTodoTagRelationCreateReqVO createReqVO) {
        // 插入
        WorkTodoTagRelation workTodoTagRelation = workTodoTagRelationMapper.toEntity(createReqVO);
        workTodoTagRelationRepository.save(workTodoTagRelation);
        // 返回
        return workTodoTagRelation.getId();
    }

    @Override
    public void updateWorkTodoTagRelation(WorkTodoTagRelationUpdateReqVO updateReqVO) {
        // 校验存在
        validateWorkTodoTagRelationExists(updateReqVO.getId());
        // 更新
        WorkTodoTagRelation updateObj = workTodoTagRelationMapper.toEntity(updateReqVO);
        workTodoTagRelationRepository.save(updateObj);
    }

    @Override
    public void deleteWorkTodoTagRelation(Long id) {
        // 校验存在
        validateWorkTodoTagRelationExists(id);
        // 删除
        workTodoTagRelationRepository.deleteById(id);
    }

    private void validateWorkTodoTagRelationExists(Long id) {
        workTodoTagRelationRepository.findById(id).orElseThrow(() -> exception(WORK_TODO_TAG_RELATION_NOT_EXISTS));
    }

    @Override
    public Optional<WorkTodoTagRelation> getWorkTodoTagRelation(Long id) {
        return workTodoTagRelationRepository.findById(id);
    }

    @Override
    public List<WorkTodoTagRelation> getWorkTodoTagRelationList(Collection<Long> ids) {
        return StreamSupport.stream(workTodoTagRelationRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<WorkTodoTagRelation> getWorkTodoTagRelationPage(WorkTodoTagRelationPageReqVO pageReqVO, WorkTodoTagRelationPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<WorkTodoTagRelation> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getTodoId() != null) {
                predicates.add(cb.equal(root.get("todoId"), pageReqVO.getTodoId()));
            }

            if(pageReqVO.getTagId() != null) {
                predicates.add(cb.equal(root.get("tagId"), pageReqVO.getTagId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<WorkTodoTagRelation> page = workTodoTagRelationRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<WorkTodoTagRelation> getWorkTodoTagRelationList(WorkTodoTagRelationExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<WorkTodoTagRelation> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getTodoId() != null) {
                predicates.add(cb.equal(root.get("todoId"), exportReqVO.getTodoId()));
            }

            if(exportReqVO.getTagId() != null) {
                predicates.add(cb.equal(root.get("tagId"), exportReqVO.getTagId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return workTodoTagRelationRepository.findAll(spec);
    }

    private Sort createSort(WorkTodoTagRelationPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getTodoId() != null) {
            orders.add(new Sort.Order(order.getTodoId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "todoId"));
        }

        if (order.getTagId() != null) {
            orders.add(new Sort.Order(order.getTagId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "tagId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}