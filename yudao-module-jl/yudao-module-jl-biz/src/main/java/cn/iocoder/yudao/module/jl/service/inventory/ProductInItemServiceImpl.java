package cn.iocoder.yudao.module.jl.service.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.ProductSendItem;
import cn.iocoder.yudao.module.jl.entity.project.SupplySendInItem;
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
import cn.iocoder.yudao.module.jl.entity.inventory.ProductInItem;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.inventory.ProductInItemMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.ProductInItemRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 实验产品入库明细 Service 实现类
 *
 */
@Service
@Validated
public class ProductInItemServiceImpl implements ProductInItemService {

    @Resource
    private ProductInItemRepository productInItemRepository;

    @Resource
    private ProductInItemMapper productInItemMapper;

    @Override
    public Long createProductInItem(ProductInItemCreateReqVO createReqVO) {
        // 插入
        ProductInItem productInItem = productInItemMapper.toEntity(createReqVO);
        productInItemRepository.save(productInItem);
        // 返回
        return productInItem.getId();
    }

    @Override
    public void updateProductInItem(ProductInItemUpdateReqVO updateReqVO) {
        // 校验存在
        validateProductInItemExists(updateReqVO.getId());
        // 更新
        ProductInItem updateObj = productInItemMapper.toEntity(updateReqVO);
        productInItemRepository.save(updateObj);
    }

    @Override
    public void deleteProductInItem(Long id) {
        // 校验存在
        validateProductInItemExists(id);
        // 删除
        productInItemRepository.deleteById(id);
    }

    private void validateProductInItemExists(Long id) {
        productInItemRepository.findById(id).orElseThrow(() -> exception(PRODUCT_IN_ITEM_NOT_EXISTS));
    }

    @Override
    public Optional<ProductInItem> getProductInItem(Long id) {
        return productInItemRepository.findById(id);
    }

    @Override
    public List<ProductInItem> getProductInItemList(Collection<Long> ids) {
        return StreamSupport.stream(productInItemRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProductInItem> getProductInItemPage(ProductInItemPageReqVO pageReqVO, ProductInItemPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProductInItem> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProductInId() != null) {
                predicates.add(cb.equal(root.get("productInId"), pageReqVO.getProductInId()));
            }

            if(pageReqVO.getSourceSupplyId() != null) {
                predicates.add(cb.equal(root.get("sourceSupplyId"), pageReqVO.getSourceSupplyId()));
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

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getRoomId() != null) {
                predicates.add(cb.equal(root.get("roomId"), pageReqVO.getRoomId()));
            }

            if(pageReqVO.getContainerId() != null) {
                predicates.add(cb.equal(root.get("containerId"), pageReqVO.getContainerId()));
            }

            if(pageReqVO.getZoomId() != null) {
                predicates.add(cb.equal(root.get("zoomId"), pageReqVO.getZoomId()));
            }

            if(pageReqVO.getValidDate() != null) {
                predicates.add(cb.between(root.get("validDate"), pageReqVO.getValidDate()[0], pageReqVO.getValidDate()[1]));
            } 
            if(pageReqVO.getTemperature() != null) {
                predicates.add(cb.equal(root.get("temperature"), pageReqVO.getTemperature()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProductInItem> page = productInItemRepository.findAll(spec, pageable);

        List<ProductInItem> productInItems = page.getContent();

        if (productInItems.size()>0){
            productInItems.forEach(item->{
                Integer sendedQuantity = 0; // 已入库数量
                if (item.getProductSendItems().size() > 0) {
                    sendedQuantity += item.getProductSendItems().stream()
                            .mapToInt(ProductSendItem::getQuantity)
                            .sum();
                }
                item.setSendedQuantity(sendedQuantity);
                if(item.getProductIn()!=null){
                    item.setInStatus(item.getProductIn().getStatus());
                }
            });
        }
        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProductInItem> getProductInItemList(ProductInItemExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProductInItem> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProductInId() != null) {
                predicates.add(cb.equal(root.get("productInId"), exportReqVO.getProductInId()));
            }

            if(exportReqVO.getSourceSupplyId() != null) {
                predicates.add(cb.equal(root.get("sourceSupplyId"), exportReqVO.getSourceSupplyId()));
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

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getRoomId() != null) {
                predicates.add(cb.equal(root.get("roomId"), exportReqVO.getRoomId()));
            }

            if(exportReqVO.getContainerId() != null) {
                predicates.add(cb.equal(root.get("containerId"), exportReqVO.getContainerId()));
            }

            if(exportReqVO.getZoomId() != null) {
                predicates.add(cb.equal(root.get("zoomId"), exportReqVO.getZoomId()));
            }

            if(exportReqVO.getValidDate() != null) {
                predicates.add(cb.between(root.get("validDate"), exportReqVO.getValidDate()[0], exportReqVO.getValidDate()[1]));
            } 
            if(exportReqVO.getTemperature() != null) {
                predicates.add(cb.equal(root.get("temperature"), exportReqVO.getTemperature()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return productInItemRepository.findAll(spec);
    }

    private Sort createSort(ProductInItemPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProductInId() != null) {
            orders.add(new Sort.Order(order.getProductInId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productInId"));
        }

        if (order.getSourceSupplyId() != null) {
            orders.add(new Sort.Order(order.getSourceSupplyId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sourceSupplyId"));
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

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getRoomId() != null) {
            orders.add(new Sort.Order(order.getRoomId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "roomId"));
        }

        if (order.getContainerId() != null) {
            orders.add(new Sort.Order(order.getContainerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "containerId"));
        }

        if (order.getZoomId() != null) {
            orders.add(new Sort.Order(order.getZoomId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "zoomId"));
        }

        if (order.getValidDate() != null) {
            orders.add(new Sort.Order(order.getValidDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "validDate"));
        }

        if (order.getTemperature() != null) {
            orders.add(new Sort.Order(order.getTemperature().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "temperature"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}