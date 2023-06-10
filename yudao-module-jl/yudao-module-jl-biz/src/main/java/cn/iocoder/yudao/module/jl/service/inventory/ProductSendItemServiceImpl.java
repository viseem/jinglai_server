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
import cn.iocoder.yudao.module.jl.entity.inventory.ProductSendItem;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.inventory.ProductSendItemMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.ProductSendItemRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 实验产品寄送明细 Service 实现类
 *
 */
@Service
@Validated
public class ProductSendItemServiceImpl implements ProductSendItemService {

    @Resource
    private ProductSendItemRepository productSendItemRepository;

    @Resource
    private ProductSendItemMapper productSendItemMapper;

    @Override
    public Long createProductSendItem(ProductSendItemCreateReqVO createReqVO) {
        // 插入
        ProductSendItem productSendItem = productSendItemMapper.toEntity(createReqVO);
        productSendItemRepository.save(productSendItem);
        // 返回
        return productSendItem.getId();
    }

    @Override
    public void updateProductSendItem(ProductSendItemUpdateReqVO updateReqVO) {
        // 校验存在
        validateProductSendItemExists(updateReqVO.getId());
        // 更新
        ProductSendItem updateObj = productSendItemMapper.toEntity(updateReqVO);
        productSendItemRepository.save(updateObj);
    }

    @Override
    public void deleteProductSendItem(Long id) {
        // 校验存在
        validateProductSendItemExists(id);
        // 删除
        productSendItemRepository.deleteById(id);
    }

    private void validateProductSendItemExists(Long id) {
        productSendItemRepository.findById(id).orElseThrow(() -> exception(PRODUCT_SEND_ITEM_NOT_EXISTS));
    }

    @Override
    public Optional<ProductSendItem> getProductSendItem(Long id) {
        return productSendItemRepository.findById(id);
    }

    @Override
    public List<ProductSendItem> getProductSendItemList(Collection<Long> ids) {
        return StreamSupport.stream(productSendItemRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProductSendItem> getProductSendItemPage(ProductSendItemPageReqVO pageReqVO, ProductSendItemPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProductSendItem> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProductSendId() != null) {
                predicates.add(cb.equal(root.get("productSendId"), pageReqVO.getProductSendId()));
            }

            if(pageReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), pageReqVO.getProductId()));
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

            if(pageReqVO.getValidDate() != null) {
                predicates.add(cb.between(root.get("validDate"), pageReqVO.getValidDate()[0], pageReqVO.getValidDate()[1]));
            } 
            if(pageReqVO.getTemperature() != null) {
                predicates.add(cb.equal(root.get("temperature"), pageReqVO.getTemperature()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProductSendItem> page = productSendItemRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProductSendItem> getProductSendItemList(ProductSendItemExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProductSendItem> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProductSendId() != null) {
                predicates.add(cb.equal(root.get("productSendId"), exportReqVO.getProductSendId()));
            }

            if(exportReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), exportReqVO.getProductId()));
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

            if(exportReqVO.getValidDate() != null) {
                predicates.add(cb.between(root.get("validDate"), exportReqVO.getValidDate()[0], exportReqVO.getValidDate()[1]));
            } 
            if(exportReqVO.getTemperature() != null) {
                predicates.add(cb.equal(root.get("temperature"), exportReqVO.getTemperature()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return productSendItemRepository.findAll(spec);
    }

    private Sort createSort(ProductSendItemPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProductSendId() != null) {
            orders.add(new Sort.Order(order.getProductSendId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productSendId"));
        }

        if (order.getProductId() != null) {
            orders.add(new Sort.Order(order.getProductId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productId"));
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