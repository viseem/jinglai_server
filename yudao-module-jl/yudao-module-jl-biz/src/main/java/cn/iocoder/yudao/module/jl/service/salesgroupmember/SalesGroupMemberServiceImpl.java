package cn.iocoder.yudao.module.jl.service.salesgroupmember;

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
import cn.iocoder.yudao.module.jl.controller.admin.salesgroupmember.vo.*;
import cn.iocoder.yudao.module.jl.entity.salesgroupmember.SalesGroupMember;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.salesgroupmember.SalesGroupMemberMapper;
import cn.iocoder.yudao.module.jl.repository.salesgroupmember.SalesGroupMemberRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 销售分组成员 Service 实现类
 *
 */
@Service
@Validated
public class SalesGroupMemberServiceImpl implements SalesGroupMemberService {

    @Resource
    private SalesGroupMemberRepository salesGroupMemberRepository;

    @Resource
    private SalesGroupMemberMapper salesGroupMemberMapper;

    @Override
    public Long createSalesGroupMember(SalesGroupMemberCreateReqVO createReqVO) {
        // 插入
        SalesGroupMember salesGroupMember = salesGroupMemberMapper.toEntity(createReqVO);
        salesGroupMemberRepository.save(salesGroupMember);
        // 返回
        return salesGroupMember.getId();
    }

    @Override
    public void updateSalesGroupMember(SalesGroupMemberUpdateReqVO updateReqVO) {
        // 校验存在
        validateSalesGroupMemberExists(updateReqVO.getId());
        // 更新
        SalesGroupMember updateObj = salesGroupMemberMapper.toEntity(updateReqVO);
        salesGroupMemberRepository.save(updateObj);
    }

    @Override
    public void deleteSalesGroupMember(Long id) {
        // 校验存在
        validateSalesGroupMemberExists(id);
        // 删除
        salesGroupMemberRepository.deleteById(id);
    }

    private void validateSalesGroupMemberExists(Long id) {
        salesGroupMemberRepository.findById(id).orElseThrow(() -> exception(SALES_GROUP_MEMBER_NOT_EXISTS));
    }

    @Override
    public Optional<SalesGroupMember> getSalesGroupMember(Long id) {
        return salesGroupMemberRepository.findById(id);
    }

    @Override
    public List<SalesGroupMember> getSalesGroupMemberList(Collection<Long> ids) {
        return StreamSupport.stream(salesGroupMemberRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<SalesGroupMember> getSalesGroupMemberPage(SalesGroupMemberPageReqVO pageReqVO, SalesGroupMemberPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<SalesGroupMember> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getGroupId() != null) {
                predicates.add(cb.equal(root.get("groupId"), pageReqVO.getGroupId()));
            }

            if(pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }

            if(pageReqVO.getMonthRefundKpi() != null) {
                predicates.add(cb.equal(root.get("monthRefundKpi"), pageReqVO.getMonthRefundKpi()));
            }

            if(pageReqVO.getMonthOrderKpi() != null) {
                predicates.add(cb.equal(root.get("monthOrderKpi"), pageReqVO.getMonthOrderKpi()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<SalesGroupMember> page = salesGroupMemberRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<SalesGroupMember> getSalesGroupMemberList(SalesGroupMemberExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<SalesGroupMember> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getGroupId() != null) {
                predicates.add(cb.equal(root.get("groupId"), exportReqVO.getGroupId()));
            }

            if(exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }

            if(exportReqVO.getMonthRefundKpi() != null) {
                predicates.add(cb.equal(root.get("monthRefundKpi"), exportReqVO.getMonthRefundKpi()));
            }

            if(exportReqVO.getMonthOrderKpi() != null) {
                predicates.add(cb.equal(root.get("monthOrderKpi"), exportReqVO.getMonthOrderKpi()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return salesGroupMemberRepository.findAll(spec);
    }

    private Sort createSort(SalesGroupMemberPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getGroupId() != null) {
            orders.add(new Sort.Order(order.getGroupId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "groupId"));
        }

        if (order.getUserId() != null) {
            orders.add(new Sort.Order(order.getUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "userId"));
        }

        if (order.getMonthRefundKpi() != null) {
            orders.add(new Sort.Order(order.getMonthRefundKpi().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "monthRefundKpi"));
        }

        if (order.getMonthOrderKpi() != null) {
            orders.add(new Sort.Order(order.getMonthOrderKpi().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "monthOrderKpi"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}