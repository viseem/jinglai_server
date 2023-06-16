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
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryOptAttachment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.inventory.InventoryOptAttachmentMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.InventoryOptAttachmentRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 库管操作附件记录 Service 实现类
 *
 */
@Service
@Validated
public class InventoryOptAttachmentServiceImpl implements InventoryOptAttachmentService {

    @Resource
    private InventoryOptAttachmentRepository inventoryOptAttachmentRepository;

    @Resource
    private InventoryOptAttachmentMapper inventoryOptAttachmentMapper;

    @Override
    public Long createInventoryOptAttachment(InventoryOptAttachmentCreateReqVO createReqVO) {
        // 插入
        InventoryOptAttachment inventoryOptAttachment = inventoryOptAttachmentMapper.toEntity(createReqVO);
        inventoryOptAttachmentRepository.save(inventoryOptAttachment);
        // 返回
        return inventoryOptAttachment.getId();
    }

    @Override
    public void updateInventoryOptAttachment(InventoryOptAttachmentUpdateReqVO updateReqVO) {
        // 校验存在
        validateInventoryOptAttachmentExists(updateReqVO.getId());
        // 更新
        InventoryOptAttachment updateObj = inventoryOptAttachmentMapper.toEntity(updateReqVO);
        inventoryOptAttachmentRepository.save(updateObj);
    }

    @Override
    public void deleteInventoryOptAttachment(Long id) {
        // 校验存在
        validateInventoryOptAttachmentExists(id);
        // 删除
        inventoryOptAttachmentRepository.deleteById(id);
    }

    private void validateInventoryOptAttachmentExists(Long id) {
        inventoryOptAttachmentRepository.findById(id).orElseThrow(() -> exception(INVENTORY_OPT_ATTACHMENT_NOT_EXISTS));
    }

    @Override
    public Optional<InventoryOptAttachment> getInventoryOptAttachment(Long id) {
        return inventoryOptAttachmentRepository.findById(id);
    }

    @Override
    public List<InventoryOptAttachment> getInventoryOptAttachmentList(Collection<Long> ids) {
        return StreamSupport.stream(inventoryOptAttachmentRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<InventoryOptAttachment> getInventoryOptAttachmentPage(InventoryOptAttachmentPageReqVO pageReqVO, InventoryOptAttachmentPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<InventoryOptAttachment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), pageReqVO.getRefId()));
            }

            if(pageReqVO.getRefItemId() != null) {
                predicates.add(cb.equal(root.get("refItemId"), pageReqVO.getRefItemId()));
            }

            if(pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            if(pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<InventoryOptAttachment> page = inventoryOptAttachmentRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<InventoryOptAttachment> getInventoryOptAttachmentList(InventoryOptAttachmentExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<InventoryOptAttachment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), exportReqVO.getRefId()));
            }

            if(exportReqVO.getRefItemId() != null) {
                predicates.add(cb.equal(root.get("refItemId"), exportReqVO.getRefItemId()));
            }

            if(exportReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + exportReqVO.getFileName() + "%"));
            }

            if(exportReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), exportReqVO.getFileUrl()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return inventoryOptAttachmentRepository.findAll(spec);
    }

    private Sort createSort(InventoryOptAttachmentPageOrder order) {
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

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getRefId() != null) {
            orders.add(new Sort.Order(order.getRefId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refId"));
        }

        if (order.getRefItemId() != null) {
            orders.add(new Sort.Order(order.getRefItemId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refItemId"));
        }

        if (order.getFileName() != null) {
            orders.add(new Sort.Order(order.getFileName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileName"));
        }

        if (order.getFileUrl() != null) {
            orders.add(new Sort.Order(order.getFileUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileUrl"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}