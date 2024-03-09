package cn.iocoder.yudao.module.jl.service.salesgroup;

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
import cn.iocoder.yudao.module.jl.controller.admin.salesgroup.vo.*;
import cn.iocoder.yudao.module.jl.entity.salesgroup.SalesGroup;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.salesgroup.SalesGroupMapper;
import cn.iocoder.yudao.module.jl.repository.salesgroup.SalesGroupRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 销售分组 Service 实现类
 *
 */
@Service
@Validated
public class SalesGroupServiceImpl implements SalesGroupService {

    @Resource
    private SalesGroupRepository salesGroupRepository;

    @Resource
    private SalesGroupMapper salesGroupMapper;

    @Override
    public Long createSalesGroup(SalesGroupCreateReqVO createReqVO) {
        // 插入
        SalesGroup salesGroup = salesGroupMapper.toEntity(createReqVO);
        salesGroupRepository.save(salesGroup);
        // 返回
        return salesGroup.getId();
    }

    @Override
    public void updateSalesGroup(SalesGroupUpdateReqVO updateReqVO) {
        // 校验存在
        validateSalesGroupExists(updateReqVO.getId());
        // 更新
        SalesGroup updateObj = salesGroupMapper.toEntity(updateReqVO);
        salesGroupRepository.save(updateObj);
    }

    @Override
    public void deleteSalesGroup(Long id) {
        // 校验存在
        validateSalesGroupExists(id);
        // 删除
        salesGroupRepository.deleteById(id);
    }

    private void validateSalesGroupExists(Long id) {
        salesGroupRepository.findById(id).orElseThrow(() -> exception(SALES_GROUP_NOT_EXISTS));
    }

    @Override
    public Optional<SalesGroup> getSalesGroup(Long id) {
        return salesGroupRepository.findById(id);
    }

    @Override
    public List<SalesGroup> getSalesGroupList(Collection<Long> ids) {
        return StreamSupport.stream(salesGroupRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<SalesGroup> getSalesGroupPage(SalesGroupPageReqVO pageReqVO, SalesGroupPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<SalesGroup> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<SalesGroup> page = salesGroupRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<SalesGroup> getSalesGroupList(SalesGroupExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<SalesGroup> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return salesGroupRepository.findAll(spec);
    }

    private Sort createSort(SalesGroupPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}