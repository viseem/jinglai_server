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
import cn.iocoder.yudao.module.jl.entity.inventory.ProductSend;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.inventory.ProductSendMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.ProductSendRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 实验产品寄送 Service 实现类
 *
 */
@Service
@Validated
public class ProductSendServiceImpl implements ProductSendService {

    @Resource
    private ProductSendRepository productSendRepository;

    @Resource
    private ProductSendMapper productSendMapper;

    @Override
    public Long createProductSend(ProductSendCreateReqVO createReqVO) {
        // 插入
        ProductSend productSend = productSendMapper.toEntity(createReqVO);
        productSendRepository.save(productSend);
        // 返回
        return productSend.getId();
    }

    @Override
    public void updateProductSend(ProductSendUpdateReqVO updateReqVO) {
        // 校验存在
        validateProductSendExists(updateReqVO.getId());
        // 更新
        ProductSend updateObj = productSendMapper.toEntity(updateReqVO);
        productSendRepository.save(updateObj);
    }

    @Override
    public void deleteProductSend(Long id) {
        // 校验存在
        validateProductSendExists(id);
        // 删除
        productSendRepository.deleteById(id);
    }

    private void validateProductSendExists(Long id) {
        productSendRepository.findById(id).orElseThrow(() -> exception(PRODUCT_SEND_NOT_EXISTS));
    }

    @Override
    public Optional<ProductSend> getProductSend(Long id) {
        return productSendRepository.findById(id);
    }

    @Override
    public List<ProductSend> getProductSendList(Collection<Long> ids) {
        return StreamSupport.stream(productSendRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProductSend> getProductSendPage(ProductSendPageReqVO pageReqVO, ProductSendPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProductSend> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if(pageReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), pageReqVO.getCode()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getSendDate() != null) {
                predicates.add(cb.between(root.get("sendDate"), pageReqVO.getSendDate()[0], pageReqVO.getSendDate()[1]));
            } 
            if(pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }

            if(pageReqVO.getReceiverName() != null) {
                predicates.add(cb.like(root.get("receiverName"), "%" + pageReqVO.getReceiverName() + "%"));
            }

            if(pageReqVO.getReceiverPhone() != null) {
                predicates.add(cb.equal(root.get("receiverPhone"), pageReqVO.getReceiverPhone()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProductSend> page = productSendRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProductSend> getProductSendList(ProductSendExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProductSend> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if(exportReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), exportReqVO.getCode()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getSendDate() != null) {
                predicates.add(cb.between(root.get("sendDate"), exportReqVO.getSendDate()[0], exportReqVO.getSendDate()[1]));
            } 
            if(exportReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), exportReqVO.getAddress()));
            }

            if(exportReqVO.getReceiverName() != null) {
                predicates.add(cb.like(root.get("receiverName"), "%" + exportReqVO.getReceiverName() + "%"));
            }

            if(exportReqVO.getReceiverPhone() != null) {
                predicates.add(cb.equal(root.get("receiverPhone"), exportReqVO.getReceiverPhone()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return productSendRepository.findAll(spec);
    }

    private Sort createSort(ProductSendPageOrder order) {
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

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getCode() != null) {
            orders.add(new Sort.Order(order.getCode().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "code"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getSendDate() != null) {
            orders.add(new Sort.Order(order.getSendDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sendDate"));
        }

        if (order.getAddress() != null) {
            orders.add(new Sort.Order(order.getAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        }

        if (order.getReceiverName() != null) {
            orders.add(new Sort.Order(order.getReceiverName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receiverName"));
        }

        if (order.getReceiverPhone() != null) {
            orders.add(new Sort.Order(order.getReceiverPhone().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receiverPhone"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}