package cn.iocoder.yudao.module.jl.service.productuser;

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

import javax.persistence.criteria.Predicate;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.productuser.vo.*;
import cn.iocoder.yudao.module.jl.entity.productuser.ProductUser;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.productuser.ProductUserMapper;
import cn.iocoder.yudao.module.jl.repository.productuser.ProductUserRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 产品库人员 Service 实现类
 *
 */
@Service
@Validated
public class ProductUserServiceImpl implements ProductUserService {

    @Resource
    private ProductUserRepository productUserRepository;

    @Resource
    private ProductUserMapper productUserMapper;

    @Override
    public Long createProductUser(ProductUserCreateReqVO createReqVO) {
        // 插入
        ProductUser productUser = productUserMapper.toEntity(createReqVO);
        productUserRepository.save(productUser);
        // 返回
        return productUser.getId();
    }

    @Override
    public void updateProductUser(ProductUserUpdateReqVO updateReqVO) {
        if(updateReqVO.getId()==0L&&updateReqVO.getList()!=null&& !updateReqVO.getList().isEmpty()){
            productUserRepository.deleteByProductId(updateReqVO.getProductId());
            productUserRepository.saveAll(updateReqVO.getList());
        }

        if(updateReqVO.getId()==0L){
            return;
        }

        // 校验存在
        validateProductUserExists(updateReqVO.getId());
        // 更新
        ProductUser updateObj = productUserMapper.toEntity(updateReqVO);
        productUserRepository.save(updateObj);
    }

    @Override
    public void deleteProductUser(Long id) {
        // 校验存在
        validateProductUserExists(id);
        // 删除
        productUserRepository.deleteById(id);
    }

    private void validateProductUserExists(Long id) {
        productUserRepository.findById(id).orElseThrow(() -> exception(PRODUCT_USER_NOT_EXISTS));
    }

    @Override
    public Optional<ProductUser> getProductUser(Long id) {
        return productUserRepository.findById(id);
    }

    @Override
    public List<ProductUser> getProductUserList(Collection<Long> ids) {
        return StreamSupport.stream(productUserRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProductUser> getProductUserPage(ProductUserPageReqVO pageReqVO, ProductUserPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProductUser> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), pageReqVO.getProductId()));
            }

            if(pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProductUser> page = productUserRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProductUser> getProductUserList(ProductUserExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProductUser> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), exportReqVO.getProductId()));
            }

            if(exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return productUserRepository.findAll(spec);
    }

    private Sort createSort(ProductUserPageOrder order) {
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

        if (order.getUserId() != null) {
            orders.add(new Sort.Order(order.getUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "userId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}