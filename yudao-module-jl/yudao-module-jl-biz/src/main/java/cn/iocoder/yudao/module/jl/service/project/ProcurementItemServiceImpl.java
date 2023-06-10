package cn.iocoder.yudao.module.jl.service.project;

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
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProcurementItemMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementItemRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目采购单申请明细 Service 实现类
 *
 */
@Service
@Validated
public class ProcurementItemServiceImpl implements ProcurementItemService {

    @Resource
    private ProcurementItemRepository procurementItemRepository;

    @Resource
    private ProcurementItemMapper procurementItemMapper;

    @Override
    public Long createProcurementItem(ProcurementItemCreateReqVO createReqVO) {
        // 插入
        ProcurementItem procurementItem = procurementItemMapper.toEntity(createReqVO);
        procurementItemRepository.save(procurementItem);
        // 返回
        return procurementItem.getId();
    }

    @Override
    public void updateProcurementItem(ProcurementItemUpdateReqVO updateReqVO) {
        // 校验存在
        validateProcurementItemExists(updateReqVO.getId());
        // 更新
        ProcurementItem updateObj = procurementItemMapper.toEntity(updateReqVO);
        procurementItemRepository.save(updateObj);
    }

    @Override
    public void deleteProcurementItem(Long id) {
        // 校验存在
        validateProcurementItemExists(id);
        // 删除
        procurementItemRepository.deleteById(id);
    }

    private void validateProcurementItemExists(Long id) {
        procurementItemRepository.findById(id).orElseThrow(() -> exception(PROCUREMENT_ITEM_NOT_EXISTS));
    }

    @Override
    public Optional<ProcurementItem> getProcurementItem(Long id) {
        return procurementItemRepository.findById(id);
    }

    @Override
    public List<ProcurementItem> getProcurementItemList(Collection<Long> ids) {
        return StreamSupport.stream(procurementItemRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProcurementItem> getProcurementItemPage(ProcurementItemPageReqVO pageReqVO, ProcurementItemPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProcurementItem> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProcurementId() != null) {
                predicates.add(cb.equal(root.get("procurementId"), pageReqVO.getProcurementId()));
            }

            if(pageReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), pageReqVO.getProjectSupplyId()));
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

            if(pageReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), pageReqVO.getSupplierId()));
            }

            if(pageReqVO.getBuyPrice() != null) {
                predicates.add(cb.equal(root.get("buyPrice"), pageReqVO.getBuyPrice()));
            }

            if(pageReqVO.getSalePrice() != null) {
                predicates.add(cb.equal(root.get("salePrice"), pageReqVO.getSalePrice()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getValidDate() != null) {
                predicates.add(cb.between(root.get("validDate"), pageReqVO.getValidDate()[0], pageReqVO.getValidDate()[1]));
            } 
            if(pageReqVO.getBrand() != null) {
                predicates.add(cb.equal(root.get("brand"), pageReqVO.getBrand()));
            }

            if(pageReqVO.getCatalogNumber() != null) {
                predicates.add(cb.equal(root.get("catalogNumber"), pageReqVO.getCatalogNumber()));
            }

            if(pageReqVO.getDeliveryDate() != null) {
                predicates.add(cb.between(root.get("deliveryDate"), pageReqVO.getDeliveryDate()[0], pageReqVO.getDeliveryDate()[1]));
            } 
            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProcurementItem> page = procurementItemRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProcurementItem> getProcurementItemList(ProcurementItemExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProcurementItem> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProcurementId() != null) {
                predicates.add(cb.equal(root.get("procurementId"), exportReqVO.getProcurementId()));
            }

            if(exportReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), exportReqVO.getProjectSupplyId()));
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

            if(exportReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), exportReqVO.getSupplierId()));
            }

            if(exportReqVO.getBuyPrice() != null) {
                predicates.add(cb.equal(root.get("buyPrice"), exportReqVO.getBuyPrice()));
            }

            if(exportReqVO.getSalePrice() != null) {
                predicates.add(cb.equal(root.get("salePrice"), exportReqVO.getSalePrice()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getValidDate() != null) {
                predicates.add(cb.between(root.get("validDate"), exportReqVO.getValidDate()[0], exportReqVO.getValidDate()[1]));
            } 
            if(exportReqVO.getBrand() != null) {
                predicates.add(cb.equal(root.get("brand"), exportReqVO.getBrand()));
            }

            if(exportReqVO.getCatalogNumber() != null) {
                predicates.add(cb.equal(root.get("catalogNumber"), exportReqVO.getCatalogNumber()));
            }

            if(exportReqVO.getDeliveryDate() != null) {
                predicates.add(cb.between(root.get("deliveryDate"), exportReqVO.getDeliveryDate()[0], exportReqVO.getDeliveryDate()[1]));
            } 
            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return procurementItemRepository.findAll(spec);
    }

    private Sort createSort(ProcurementItemPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProcurementId() != null) {
            orders.add(new Sort.Order(order.getProcurementId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "procurementId"));
        }

        if (order.getProjectSupplyId() != null) {
            orders.add(new Sort.Order(order.getProjectSupplyId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectSupplyId"));
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

        if (order.getSupplierId() != null) {
            orders.add(new Sort.Order(order.getSupplierId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplierId"));
        }

        if (order.getBuyPrice() != null) {
            orders.add(new Sort.Order(order.getBuyPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "buyPrice"));
        }

        if (order.getSalePrice() != null) {
            orders.add(new Sort.Order(order.getSalePrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salePrice"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getValidDate() != null) {
            orders.add(new Sort.Order(order.getValidDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "validDate"));
        }

        if (order.getBrand() != null) {
            orders.add(new Sort.Order(order.getBrand().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "brand"));
        }

        if (order.getCatalogNumber() != null) {
            orders.add(new Sort.Order(order.getCatalogNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "catalogNumber"));
        }

        if (order.getDeliveryDate() != null) {
            orders.add(new Sort.Order(order.getDeliveryDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "deliveryDate"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}