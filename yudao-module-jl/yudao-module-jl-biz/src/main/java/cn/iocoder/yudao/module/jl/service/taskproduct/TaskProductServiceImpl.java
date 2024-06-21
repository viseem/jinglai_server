package cn.iocoder.yudao.module.jl.service.taskproduct;

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
import cn.iocoder.yudao.module.jl.controller.admin.taskproduct.vo.*;
import cn.iocoder.yudao.module.jl.entity.taskproduct.TaskProduct;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.taskproduct.TaskProductMapper;
import cn.iocoder.yudao.module.jl.repository.taskproduct.TaskProductRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 任务产品中间 Service 实现类
 *
 */
@Service
@Validated
public class TaskProductServiceImpl implements TaskProductService {

    @Resource
    private TaskProductRepository taskProductRepository;

    @Resource
    private TaskProductMapper taskProductMapper;

    @Override
    public Long createTaskProduct(TaskProductCreateReqVO createReqVO) {
        // 插入
        TaskProduct taskProduct = taskProductMapper.toEntity(createReqVO);
        taskProductRepository.save(taskProduct);
        // 返回
        return taskProduct.getId();
    }

    @Override
    public void updateTaskProduct(TaskProductUpdateReqVO updateReqVO) {
        // 校验存在
        validateTaskProductExists(updateReqVO.getId());
        // 更新
        TaskProduct updateObj = taskProductMapper.toEntity(updateReqVO);
        taskProductRepository.save(updateObj);
    }

    @Override
    public void deleteTaskProduct(Long id) {
        // 校验存在
        validateTaskProductExists(id);
        // 删除
        taskProductRepository.deleteById(id);
    }

    private void validateTaskProductExists(Long id) {
        taskProductRepository.findById(id).orElseThrow(() -> exception(TASK_PRODUCT_NOT_EXISTS));
    }

    @Override
    public Optional<TaskProduct> getTaskProduct(Long id) {
        return taskProductRepository.findById(id);
    }

    @Override
    public List<TaskProduct> getTaskProductList(Collection<Long> ids) {
        return StreamSupport.stream(taskProductRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<TaskProduct> getTaskProductPage(TaskProductPageReqVO pageReqVO, TaskProductPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<TaskProduct> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getTaskId() != null) {
                predicates.add(cb.equal(root.get("taskId"), pageReqVO.getTaskId()));
            }

            if(pageReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), pageReqVO.getProductId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<TaskProduct> page = taskProductRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<TaskProduct> getTaskProductList(TaskProductExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<TaskProduct> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getTaskId() != null) {
                predicates.add(cb.equal(root.get("taskId"), exportReqVO.getTaskId()));
            }

            if(exportReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), exportReqVO.getProductId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return taskProductRepository.findAll(spec);
    }

    private Sort createSort(TaskProductPageOrder order) {
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

        if (order.getProductId() != null) {
            orders.add(new Sort.Order(order.getProductId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}