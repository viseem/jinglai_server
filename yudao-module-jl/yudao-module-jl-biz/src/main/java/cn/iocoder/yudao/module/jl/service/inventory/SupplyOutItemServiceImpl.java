package cn.iocoder.yudao.module.jl.service.inventory;

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
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOutItem;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.inventory.SupplyOutItemMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.SupplyOutItemRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 出库申请明细 Service 实现类
 *
 */
@Service
@Validated
public class SupplyOutItemServiceImpl implements SupplyOutItemService {

    @Resource
    private SupplyOutItemRepository supplyOutItemRepository;

    @Resource
    private SupplyOutItemMapper supplyOutItemMapper;

    @Override
    public Long createSupplyOutItem(SupplyOutItemCreateReqVO createReqVO) {
        // 插入
        SupplyOutItem supplyOutItem = supplyOutItemMapper.toEntity(createReqVO);
        supplyOutItemRepository.save(supplyOutItem);
        // 返回
        return supplyOutItem.getId();
    }

    @Override
    public void updateSupplyOutItem(SupplyOutItemUpdateReqVO updateReqVO) {
        // 校验存在
        validateSupplyOutItemExists(updateReqVO.getId());
        // 更新
        SupplyOutItem updateObj = supplyOutItemMapper.toEntity(updateReqVO);
        supplyOutItemRepository.save(updateObj);
    }

    @Override
    public void deleteSupplyOutItem(Long id) {
        // 校验存在
        validateSupplyOutItemExists(id);
        // 删除
        supplyOutItemRepository.deleteById(id);
    }

    private void validateSupplyOutItemExists(Long id) {
        supplyOutItemRepository.findById(id).orElseThrow(() -> exception(SUPPLY_OUT_ITEM_NOT_EXISTS));
    }

    @Override
    public Optional<SupplyOutItem> getSupplyOutItem(Long id) {
        return supplyOutItemRepository.findById(id);
    }

    @Override
    public List<SupplyOutItem> getSupplyOutItemList(Collection<Long> ids) {
        return StreamSupport.stream(supplyOutItemRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<SupplyOutItem> getSupplyOutItemPage(SupplyOutItemPageReqVO pageReqVO, SupplyOutItemPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<SupplyOutItem> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getSupplyOutId() != null) {
                predicates.add(cb.equal(root.get("supplyOutId"), pageReqVO.getSupplyOutId()));
            }

            if(pageReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), pageReqVO.getProjectSupplyId()));
            }

            if(pageReqVO.getSupplyId() != null) {
                predicates.add(cb.equal(root.get("supplyId"), pageReqVO.getSupplyId()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getFeeStandard() != null) {
                predicates.add(cb.equal(root.get("feeStandard"), pageReqVO.getFeeStandard()));
            }

            if(pageReqVO.getUnitFee() != null) {
                predicates.add(cb.equal(root.get("unitFee"), pageReqVO.getUnitFee()));
            }

            if(pageReqVO.getUnitAmount() != null) {
                predicates.add(cb.equal(root.get("unitAmount"), pageReqVO.getUnitAmount()));
            }

            if(pageReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), pageReqVO.getQuantity()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<SupplyOutItem> page = supplyOutItemRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<SupplyOutItem> getSupplyOutItemList(SupplyOutItemExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<SupplyOutItem> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getSupplyOutId() != null) {
                predicates.add(cb.equal(root.get("supplyOutId"), exportReqVO.getSupplyOutId()));
            }

            if(exportReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), exportReqVO.getProjectSupplyId()));
            }

            if(exportReqVO.getSupplyId() != null) {
                predicates.add(cb.equal(root.get("supplyId"), exportReqVO.getSupplyId()));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getFeeStandard() != null) {
                predicates.add(cb.equal(root.get("feeStandard"), exportReqVO.getFeeStandard()));
            }

            if(exportReqVO.getUnitFee() != null) {
                predicates.add(cb.equal(root.get("unitFee"), exportReqVO.getUnitFee()));
            }

            if(exportReqVO.getUnitAmount() != null) {
                predicates.add(cb.equal(root.get("unitAmount"), exportReqVO.getUnitAmount()));
            }

            if(exportReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), exportReqVO.getQuantity()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return supplyOutItemRepository.findAll(spec);
    }

    private Sort createSort(SupplyOutItemPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getSupplyOutId() != null) {
            orders.add(new Sort.Order(order.getSupplyOutId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplyOutId"));
        }

        if (order.getProjectSupplyId() != null) {
            orders.add(new Sort.Order(order.getProjectSupplyId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectSupplyId"));
        }

        if (order.getSupplyId() != null) {
            orders.add(new Sort.Order(order.getSupplyId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplyId"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getFeeStandard() != null) {
            orders.add(new Sort.Order(order.getFeeStandard().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "feeStandard"));
        }

        if (order.getUnitFee() != null) {
            orders.add(new Sort.Order(order.getUnitFee().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "unitFee"));
        }

        if (order.getUnitAmount() != null) {
            orders.add(new Sort.Order(order.getUnitAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "unitAmount"));
        }

        if (order.getQuantity() != null) {
            orders.add(new Sort.Order(order.getQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quantity"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}