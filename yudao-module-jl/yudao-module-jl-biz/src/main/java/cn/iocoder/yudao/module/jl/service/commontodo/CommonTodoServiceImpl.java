package cn.iocoder.yudao.module.jl.service.commontodo;

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
import cn.iocoder.yudao.module.jl.controller.admin.commontodo.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontodo.CommonTodo;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.commontodo.CommonTodoMapper;
import cn.iocoder.yudao.module.jl.repository.commontodo.CommonTodoRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 通用TODO Service 实现类
 *
 */
@Service
@Validated
public class CommonTodoServiceImpl implements CommonTodoService {

    @Resource
    private CommonTodoRepository commonTodoRepository;

    @Resource
    private CommonTodoMapper commonTodoMapper;

    @Override
    public Long createCommonTodo(CommonTodoCreateReqVO createReqVO) {
        // 插入
        CommonTodo commonTodo = commonTodoMapper.toEntity(createReqVO);
        commonTodoRepository.save(commonTodo);
        // 返回
        return commonTodo.getId();
    }

    @Override
    public void updateCommonTodo(CommonTodoUpdateReqVO updateReqVO) {
        // 校验存在
        validateCommonTodoExists(updateReqVO.getId());
        // 更新
        CommonTodo updateObj = commonTodoMapper.toEntity(updateReqVO);
        commonTodoRepository.save(updateObj);
    }

    @Override
    public void deleteCommonTodo(Long id) {
        // 校验存在
        validateCommonTodoExists(id);
        // 删除
        commonTodoRepository.deleteById(id);
    }

    private void validateCommonTodoExists(Long id) {
        commonTodoRepository.findById(id).orElseThrow(() -> exception(COMMON_TODO_NOT_EXISTS));
    }

    @Override
    public Optional<CommonTodo> getCommonTodo(Long id) {
        return commonTodoRepository.findById(id);
    }

    @Override
    public List<CommonTodo> getCommonTodoList(Collection<Long> ids) {
        return StreamSupport.stream(commonTodoRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CommonTodo> getCommonTodoPage(CommonTodoPageReqVO pageReqVO, CommonTodoPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CommonTodo> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CommonTodo> page = commonTodoRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CommonTodo> getCommonTodoList(CommonTodoExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CommonTodo> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return commonTodoRepository.findAll(spec);
    }

    private Sort createSort(CommonTodoPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}