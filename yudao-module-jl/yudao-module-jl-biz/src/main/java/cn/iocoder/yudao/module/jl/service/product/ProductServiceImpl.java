package cn.iocoder.yudao.module.jl.service.product;

import cn.iocoder.yudao.module.jl.controller.admin.productuser.vo.ProductUserUpdateReqVO;
import cn.iocoder.yudao.module.jl.entity.product.ProductDetail;
import cn.iocoder.yudao.module.jl.repository.product.ProductDetailRepository;
import cn.iocoder.yudao.module.jl.service.commonattachment.CommonAttachmentServiceImpl;
import cn.iocoder.yudao.module.jl.service.productuser.ProductUserServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
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
import cn.iocoder.yudao.module.jl.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.jl.entity.product.Product;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.product.ProductMapper;
import cn.iocoder.yudao.module.jl.repository.product.ProductRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 产品库 Service 实现类
 *
 */
@Service
@Validated
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ProductDetailRepository productDetailRepository;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private CommonAttachmentServiceImpl commonAttachmentService;

    @Resource
    private ProductUserServiceImpl productUserService;

    @Override
    @Transactional
    public Long createProduct(ProductCreateReqVO createReqVO) {
        // 插入
        Product product = productMapper.toEntity(createReqVO);
        productRepository.save(product);
        if(createReqVO.getProductUserList()!=null){
            ProductUserUpdateReqVO productUserReq = new ProductUserUpdateReqVO();
            productUserReq.setId(0L);
            productUserReq.setProductId(product.getId());
            productUserReq.setUserId(0L);
            createReqVO.getProductUserList().forEach(item->{
                item.setProductId(product.getId());
            });
            productUserReq.setList(createReqVO.getProductUserList());
            productUserService.updateProductUser(productUserReq);
        }
        // 返回
        return product.getId();
    }

    @Override
    @Transactional
    public void updateProduct(ProductUpdateReqVO updateReqVO) {
        // 校验存在
        validateProductExists(updateReqVO.getId());
        // 更新
        ProductDetail updateObj = productMapper.toEntityDetail(updateReqVO);
        productDetailRepository.save(updateObj);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        if(updateReqVO.getAttachmentType()!=null){
            commonAttachmentService.saveAttachmentListWithSubType(updateObj.getId(),"JL_PRODUCT",updateReqVO.getAttachmentType(),updateReqVO.getAttachmentList());
        }

        if(updateReqVO.getProductUserList()!=null){
            updateReqVO.getProductUserList().forEach(item->{
                item.setProductId(updateObj.getId());
            });
            ProductUserUpdateReqVO productUserReq = new ProductUserUpdateReqVO();
            productUserReq.setId(0L);
            productUserReq.setProductId(updateReqVO.getId());
            productUserReq.setUserId(0L);
            productUserReq.setList(updateReqVO.getProductUserList());
            productUserService.updateProductUser(productUserReq);
        }
    }

    @Override
    public void deleteProduct(Long id) {
        // 校验存在
        validateProductExists(id);
        // 删除
        productRepository.deleteById(id);
    }

    private void validateProductExists(Long id) {
        productRepository.findById(id).orElseThrow(() -> exception(PRODUCT_NOT_EXISTS));
    }

    @Override
    public Optional<ProductDetail> getProduct(Long id) {
        return productDetailRepository.findById(id);
    }

    @Override
    public List<Product> getProductList(Collection<Long> ids) {
        return StreamSupport.stream(productRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Product> getProductPage(ProductPageReqVO pageReqVO, ProductPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Product> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getNameKey() != null) {
               // like name or like nameEn or like nameShort
                predicates.add(cb.or(cb.like(root.get("name"), "%" + pageReqVO.getNameKey() + "%"),
                        cb.like(root.get("nameEn"), "%" + pageReqVO.getNameKey() + "%"),
                        cb.like(root.get("nameShort"), "%" + pageReqVO.getNameKey() + "%")));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getCateId() != null) {
                predicates.add(cb.equal(root.get("cateId"), pageReqVO.getCateId()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getPiGroupId() != null) {
                predicates.add(cb.equal(root.get("piGroupId"), pageReqVO.getPiGroupId()));
            }

            if(pageReqVO.getExperId() != null) {
                predicates.add(cb.equal(root.get("experId"), pageReqVO.getExperId()));
            }

            if(pageReqVO.getInfoUserId() != null) {
                predicates.add(cb.equal(root.get("infoUserId"), pageReqVO.getInfoUserId()));
            }

            if(pageReqVO.getSubject() != null) {
                predicates.add(cb.equal(root.get("subject"), pageReqVO.getSubject()));
            }

            if(pageReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), pageReqVO.getSupplierId()));
            }

            if(pageReqVO.getStandardPrice() != null) {
                predicates.add(cb.equal(root.get("standardPrice"), pageReqVO.getStandardPrice()));
            }

            if(pageReqVO.getCostPrice() != null) {
                predicates.add(cb.equal(root.get("costPrice"), pageReqVO.getCostPrice()));
            }

            if(pageReqVO.getCompetePrice() != null) {
                predicates.add(cb.equal(root.get("competePrice"), pageReqVO.getCompetePrice()));
            }

            if(pageReqVO.getDiscountPrice() != null) {
                predicates.add(cb.equal(root.get("discountPrice"), pageReqVO.getDiscountPrice()));
            }

            if(pageReqVO.getSoldAmount() != null) {
                predicates.add(cb.equal(root.get("soldAmount"), pageReqVO.getSoldAmount()));
            }

            if(pageReqVO.getSoldCount() != null) {
                predicates.add(cb.equal(root.get("soldCount"), pageReqVO.getSoldCount()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<Product> page = productRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<Product> getProductList(ProductExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Product> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getCate() != null) {
                predicates.add(cb.equal(root.get("cate"), exportReqVO.getCate()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getPiGroupId() != null) {
                predicates.add(cb.equal(root.get("piGroupId"), exportReqVO.getPiGroupId()));
            }

            if(exportReqVO.getExperId() != null) {
                predicates.add(cb.equal(root.get("experId"), exportReqVO.getExperId()));
            }

            if(exportReqVO.getInfoUserId() != null) {
                predicates.add(cb.equal(root.get("infoUserId"), exportReqVO.getInfoUserId()));
            }

            if(exportReqVO.getSubject() != null) {
                predicates.add(cb.equal(root.get("subject"), exportReqVO.getSubject()));
            }

            if(exportReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), exportReqVO.getSupplierId()));
            }

            if(exportReqVO.getStandardPrice() != null) {
                predicates.add(cb.equal(root.get("standardPrice"), exportReqVO.getStandardPrice()));
            }

            if(exportReqVO.getCostPrice() != null) {
                predicates.add(cb.equal(root.get("costPrice"), exportReqVO.getCostPrice()));
            }

            if(exportReqVO.getCompetePrice() != null) {
                predicates.add(cb.equal(root.get("competePrice"), exportReqVO.getCompetePrice()));
            }

            if(exportReqVO.getDiscountPrice() != null) {
                predicates.add(cb.equal(root.get("discountPrice"), exportReqVO.getDiscountPrice()));
            }

            if(exportReqVO.getSoldAmount() != null) {
                predicates.add(cb.equal(root.get("soldAmount"), exportReqVO.getSoldAmount()));
            }

            if(exportReqVO.getSoldCount() != null) {
                predicates.add(cb.equal(root.get("soldCount"), exportReqVO.getSoldCount()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return productRepository.findAll(spec);
    }

    private Sort createSort(ProductPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整
        orders.add(new Sort.Order(Sort.Direction.ASC, "sort"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getCate() != null) {
            orders.add(new Sort.Order(order.getCate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "cate"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getPiGroupId() != null) {
            orders.add(new Sort.Order(order.getPiGroupId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "piGroupId"));
        }

        if (order.getExperId() != null) {
            orders.add(new Sort.Order(order.getExperId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "experId"));
        }

        if (order.getInfoUserId() != null) {
            orders.add(new Sort.Order(order.getInfoUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "infoUserId"));
        }

        if (order.getSubject() != null) {
            orders.add(new Sort.Order(order.getSubject().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "subject"));
        }

        if (order.getSupplierId() != null) {
            orders.add(new Sort.Order(order.getSupplierId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplierId"));
        }

        if (order.getStandardPrice() != null) {
            orders.add(new Sort.Order(order.getStandardPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "standardPrice"));
        }

        if (order.getCostPrice() != null) {
            orders.add(new Sort.Order(order.getCostPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "costPrice"));
        }

        if (order.getCompetePrice() != null) {
            orders.add(new Sort.Order(order.getCompetePrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "competePrice"));
        }

        if (order.getDiscountPrice() != null) {
            orders.add(new Sort.Order(order.getDiscountPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "discountPrice"));
        }

        if (order.getSoldAmount() != null) {
            orders.add(new Sort.Order(order.getSoldAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "soldAmount"));
        }

        if (order.getSoldCount() != null) {
            orders.add(new Sort.Order(order.getSoldCount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "soldCount"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}