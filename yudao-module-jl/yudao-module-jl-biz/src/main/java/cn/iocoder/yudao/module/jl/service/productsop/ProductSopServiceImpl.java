package cn.iocoder.yudao.module.jl.service.productsop;

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
import cn.iocoder.yudao.module.jl.controller.admin.productsop.vo.*;
import cn.iocoder.yudao.module.jl.entity.productsop.ProductSop;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.productsop.ProductSopMapper;
import cn.iocoder.yudao.module.jl.repository.productsop.ProductSopRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 产品sop Service 实现类
 *
 */
@Service
@Validated
public class ProductSopServiceImpl implements ProductSopService {

    @Resource
    private ProductSopRepository productSopRepository;

    @Resource
    private ProductSopMapper productSopMapper;

    @Override
    public Long createProductSop(ProductSopCreateReqVO createReqVO) {
        // 插入
        ProductSop productSop = productSopMapper.toEntity(createReqVO);
        productSopRepository.save(productSop);
        // 返回
        return productSop.getId();
    }

    @Override
    public void updateProductSop(ProductSopUpdateReqVO updateReqVO) {
        // 校验存在
        validateProductSopExists(updateReqVO.getId());
        // 更新
        ProductSop updateObj = productSopMapper.toEntity(updateReqVO);
        productSopRepository.save(updateObj);
    }

    @Override
    public void deleteProductSop(Long id) {
        // 校验存在
        validateProductSopExists(id);
        // 删除
        productSopRepository.deleteById(id);
    }

    private void validateProductSopExists(Long id) {
        productSopRepository.findById(id).orElseThrow(() -> exception(PRODUCT_SOP_NOT_EXISTS));
    }

    @Override
    public Optional<ProductSop> getProductSop(Long id) {
        return productSopRepository.findById(id);
    }

    @Override
    public List<ProductSop> getProductSopList(Collection<Long> ids) {
        return StreamSupport.stream(productSopRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProductSop> getProductSopPage(ProductSopPageReqVO pageReqVO, ProductSopPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProductSop> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), pageReqVO.getProductId()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProductSop> page = productSopRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProductSop> getProductSopList(ProductSopExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProductSop> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), exportReqVO.getProductId()));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return productSopRepository.findAll(spec);
    }

    private Sort createSort(ProductSopPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProductId() != null) {
            orders.add(new Sort.Order(order.getProductId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productId"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}