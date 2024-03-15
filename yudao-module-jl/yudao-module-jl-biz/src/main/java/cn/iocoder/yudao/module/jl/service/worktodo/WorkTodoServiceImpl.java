package cn.iocoder.yudao.module.jl.service.worktodo;

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
import cn.iocoder.yudao.module.jl.controller.admin.worktodo.vo.*;
import cn.iocoder.yudao.module.jl.entity.worktodo.WorkTodo;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.worktodo.WorkTodoMapper;
import cn.iocoder.yudao.module.jl.repository.worktodo.WorkTodoRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 工作任务 TODO Service 实现类
 *
 */
@Service
@Validated
public class WorkTodoServiceImpl implements WorkTodoService {

    @Resource
    private WorkTodoRepository workTodoRepository;

    @Resource
    private WorkTodoMapper workTodoMapper;

    @Override
    public Long createWorkTodo(WorkTodoCreateReqVO createReqVO) {
        // 插入
        WorkTodo workTodo = workTodoMapper.toEntity(createReqVO);
        workTodoRepository.save(workTodo);
        // 返回
        return workTodo.getId();
    }

    @Override
    public void updateWorkTodo(WorkTodoUpdateReqVO updateReqVO) {
        // 校验存在
        validateWorkTodoExists(updateReqVO.getId());
        // 更新
        WorkTodo updateObj = workTodoMapper.toEntity(updateReqVO);
        workTodoRepository.save(updateObj);
    }

    @Override
    public void deleteWorkTodo(Long id) {
        // 校验存在
        validateWorkTodoExists(id);
        // 删除
        workTodoRepository.deleteById(id);
    }

    private void validateWorkTodoExists(Long id) {
        workTodoRepository.findById(id).orElseThrow(() -> exception(WORK_TODO_NOT_EXISTS));
    }

    @Override
    public Optional<WorkTodo> getWorkTodo(Long id) {
        return workTodoRepository.findById(id);
    }

    @Override
    public List<WorkTodo> getWorkTodoList(Collection<Long> ids) {
        return StreamSupport.stream(workTodoRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<WorkTodo> getWorkTodoPage(WorkTodoPageReqVO pageReqVO, WorkTodoPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<WorkTodo> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getTitle() != null) {
                predicates.add(cb.equal(root.get("title"), pageReqVO.getTitle()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getPriority() != null) {
                predicates.add(cb.equal(root.get("priority"), pageReqVO.getPriority()));
            }

            if(pageReqVO.getDeadline() != null) {
                predicates.add(cb.equal(root.get("deadline"), pageReqVO.getDeadline()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), pageReqVO.getRefId()));
            }

            if(pageReqVO.getRefDesc() != null) {
                predicates.add(cb.equal(root.get("refDesc"), pageReqVO.getRefDesc()));
            }

            if(pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<WorkTodo> page = workTodoRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<WorkTodo> getWorkTodoList(WorkTodoExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<WorkTodo> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getTitle() != null) {
                predicates.add(cb.equal(root.get("title"), exportReqVO.getTitle()));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getPriority() != null) {
                predicates.add(cb.equal(root.get("priority"), exportReqVO.getPriority()));
            }

            if(exportReqVO.getDeadline() != null) {
                predicates.add(cb.equal(root.get("deadline"), exportReqVO.getDeadline()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), exportReqVO.getRefId()));
            }

            if(exportReqVO.getRefDesc() != null) {
                predicates.add(cb.equal(root.get("refDesc"), exportReqVO.getRefDesc()));
            }

            if(exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return workTodoRepository.findAll(spec);
    }

    private Sort createSort(WorkTodoPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getTitle() != null) {
            orders.add(new Sort.Order(order.getTitle().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "title"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getPriority() != null) {
            orders.add(new Sort.Order(order.getPriority().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "priority"));
        }

        if (order.getDeadline() != null) {
            orders.add(new Sort.Order(order.getDeadline().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "deadline"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        // 创建 Sort 对象
        return Sort.by(orders);
    }
}