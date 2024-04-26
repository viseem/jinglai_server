package cn.iocoder.yudao.module.jl.service.productcate;

import cn.iocoder.yudao.module.jl.entity.productcate.ProductCateOnly;
import cn.iocoder.yudao.module.jl.repository.product.ProductOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.productcate.ProductCateOnlyRepository;
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
import cn.iocoder.yudao.module.jl.controller.admin.productcate.vo.*;
import cn.iocoder.yudao.module.jl.entity.productcate.ProductCate;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.productcate.ProductCateMapper;
import cn.iocoder.yudao.module.jl.repository.productcate.ProductCateRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 产品库分类 Service 实现类
 *
 */
@Service
@Validated
public class ProductCateServiceImpl implements ProductCateService {

    @Resource
    private ProductCateRepository productCateRepository;

    @Resource
    private ProductCateMapper productCateMapper;

    @Resource
    private ProductCateOnlyRepository productCateOnlyRepository;

    @Resource
    private ProductOnlyRepository productOnlyRepository;

    @Override
    public Long createProductCate(ProductCateCreateReqVO createReqVO) {
        // 插入
        ProductCate productCate = productCateMapper.toEntity(createReqVO);
        productCateRepository.save(productCate);
        // 返回
        return productCate.getId();
    }

    @Override
    public void updateProductCate(ProductCateUpdateReqVO updateReqVO) {
        // 校验存在
        validateProductCateExists(updateReqVO.getId());
        // 更新
        ProductCate updateObj = productCateMapper.toEntity(updateReqVO);
        productCateRepository.save(updateObj);
    }

    @Override
    public void deleteProductCate(Long id) {

        //校验是否存在子项
        long l = productCateOnlyRepository.countByParentId(id);
        if(l > 0){
            throw exception(PRODUCT_CATE_HAS_CHILDREN);
        }

        long l1 = productOnlyRepository.countByCateId(id);
        if(l1 > 0){
            throw exception(PRODUCT_CATE_HAS_PRODUCT);
        }

        // 校验存在
        validateProductCateExists(id);
        // 删除
        productCateRepository.deleteById(id);
    }

    private void validateProductCateExists(Long id) {
        productCateRepository.findById(id).orElseThrow(() -> exception(PRODUCT_CATE_NOT_EXISTS));
    }

    @Override
    public Optional<ProductCate> getProductCate(Long id) {
        return productCateRepository.findById(id);
    }

    @Override
    public List<ProductCate> getProductCateList(Collection<Long> ids) {
        return StreamSupport.stream(productCateRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProductCate> getProductCatePage(ProductCatePageReqVO pageReqVO, ProductCatePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProductCate> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getParentId()!=null){
                predicates.add(cb.equal(root.get("parentId"), pageReqVO.getParentId()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProductCate> page = productCateRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProductCate> getProductCateList(ProductCateExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProductCate> spec = (root, query, cb) -> {
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
        return productCateRepository.findAll(spec);
    }

    private Sort createSort(ProductCatePageOrder order) {
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