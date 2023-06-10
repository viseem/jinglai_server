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
import cn.iocoder.yudao.module.jl.entity.project.SupplyLogAttachment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.SupplyLogAttachmentMapper;
import cn.iocoder.yudao.module.jl.repository.project.SupplyLogAttachmentRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 库管项目物资库存变更日志附件 Service 实现类
 *
 */
@Service
@Validated
public class SupplyLogAttachmentServiceImpl implements SupplyLogAttachmentService {

    @Resource
    private SupplyLogAttachmentRepository supplyLogAttachmentRepository;

    @Resource
    private SupplyLogAttachmentMapper supplyLogAttachmentMapper;

    @Override
    public Long createSupplyLogAttachment(SupplyLogAttachmentCreateReqVO createReqVO) {
        // 插入
        SupplyLogAttachment supplyLogAttachment = supplyLogAttachmentMapper.toEntity(createReqVO);
        supplyLogAttachmentRepository.save(supplyLogAttachment);
        // 返回
        return supplyLogAttachment.getId();
    }

    @Override
    public void updateSupplyLogAttachment(SupplyLogAttachmentUpdateReqVO updateReqVO) {
        // 校验存在
        validateSupplyLogAttachmentExists(updateReqVO.getId());
        // 更新
        SupplyLogAttachment updateObj = supplyLogAttachmentMapper.toEntity(updateReqVO);
        supplyLogAttachmentRepository.save(updateObj);
    }

    @Override
    public void deleteSupplyLogAttachment(Long id) {
        // 校验存在
        validateSupplyLogAttachmentExists(id);
        // 删除
        supplyLogAttachmentRepository.deleteById(id);
    }

    private void validateSupplyLogAttachmentExists(Long id) {
        supplyLogAttachmentRepository.findById(id).orElseThrow(() -> exception(SUPPLY_LOG_ATTACHMENT_NOT_EXISTS));
    }

    @Override
    public Optional<SupplyLogAttachment> getSupplyLogAttachment(Long id) {
        return supplyLogAttachmentRepository.findById(id);
    }

    @Override
    public List<SupplyLogAttachment> getSupplyLogAttachmentList(Collection<Long> ids) {
        return StreamSupport.stream(supplyLogAttachmentRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<SupplyLogAttachment> getSupplyLogAttachmentPage(SupplyLogAttachmentPageReqVO pageReqVO, SupplyLogAttachmentPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<SupplyLogAttachment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectSupplyLogId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyLogId"), pageReqVO.getProjectSupplyLogId()));
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
        Page<SupplyLogAttachment> page = supplyLogAttachmentRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<SupplyLogAttachment> getSupplyLogAttachmentList(SupplyLogAttachmentExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<SupplyLogAttachment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectSupplyLogId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyLogId"), exportReqVO.getProjectSupplyLogId()));
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
        return supplyLogAttachmentRepository.findAll(spec);
    }

    private Sort createSort(SupplyLogAttachmentPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProjectSupplyLogId() != null) {
            orders.add(new Sort.Order(order.getProjectSupplyLogId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectSupplyLogId"));
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