package cn.iocoder.yudao.module.jl.service.inventory;

import cn.iocoder.yudao.module.jl.entity.project.SupplySendIn;
import cn.iocoder.yudao.module.jl.mapper.inventory.ProductInItemMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.ProductInItemRepository;
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
import cn.iocoder.yudao.module.jl.entity.inventory.ProductIn;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.inventory.ProductInMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.ProductInRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 实验产品入库 Service 实现类
 *
 */
@Service
@Validated
public class ProductInServiceImpl implements ProductInService {

    @Resource
    private ProductInRepository productInRepository;

    @Resource
    private ProductInMapper productInMapper;

    @Resource
    private ProductInItemRepository productInItemRepository;

    @Resource
    private ProductInItemMapper productInItemMapper;

    @Override
    public Long createProductIn(ProductInCreateReqVO createReqVO) {
        // 插入
        ProductIn productIn = productInMapper.toEntity(createReqVO);
        productInRepository.save(productIn);
        // 返回
        return productIn.getId();
    }

    @Override
    public void updateProductIn(ProductInUpdateReqVO updateReqVO) {
        // 校验存在
        validateProductInExists(updateReqVO.getId());
        // 更新
        ProductIn updateObj = productInMapper.toEntity(updateReqVO);
        productInRepository.save(updateObj);
    }

    @Override
    public void deleteProductIn(Long id) {
        // 校验存在
        validateProductInExists(id);
        // 删除
        productInRepository.deleteById(id);
    }

    private void validateProductInExists(Long id) {
        productInRepository.findById(id).orElseThrow(() -> exception(PRODUCT_IN_NOT_EXISTS));
    }

    @Override
    public Optional<ProductIn> getProductIn(Long id) {
        return productInRepository.findById(id);
    }

    @Override
    public List<ProductIn> getProductInList(Collection<Long> ids) {
        return StreamSupport.stream(productInRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProductIn> getProductInPage(ProductInPageReqVO pageReqVO, ProductInPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProductIn> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("categoryId"), pageReqVO.getCategoryId()));
            }

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
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
        Page<ProductIn> page = productInRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProductIn> getProductInList(ProductInExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProductIn> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("categoryId"), exportReqVO.getCategoryId()));
            }

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
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
        return productInRepository.findAll(spec);
    }

    /**
     * @param saveReqVO
     */
    @Override
    public void saveProductIn(ProductInSaveReqVO saveReqVO) {
        if(saveReqVO.getId() != null) {
            // 存在 id，更新操作
            Long id = saveReqVO.getId();
            // 校验存在
            validateProductInExists(saveReqVO.getId());
        }

        // 更新
        ProductIn updateObj = productInMapper.toEntity(saveReqVO);
        updateObj = productInRepository.save(updateObj);
        Long productInId = updateObj.getId();

        // 删除原有的
        productInItemRepository.deleteByProductInId(productInId);

        // 更新 items
        productInItemRepository.saveAll(saveReqVO.getItems().stream().map(item -> {
            item.setProductInId(productInId);
            return productInItemMapper.toEntity(item);
        }).collect(Collectors.toList()));

    }

    private Sort createSort(ProductInPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getCategoryId() != null) {
            orders.add(new Sort.Order(order.getCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "categoryId"));
        }

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
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