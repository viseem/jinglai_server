package cn.iocoder.yudao.module.jl.service.commonattachment;

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
import cn.iocoder.yudao.module.jl.controller.admin.commonattachment.vo.*;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.commonattachment.CommonAttachmentMapper;
import cn.iocoder.yudao.module.jl.repository.commonattachment.CommonAttachmentRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 通用附件 Service 实现类
 *
 */
@Service
@Validated
public class CommonAttachmentServiceImpl implements CommonAttachmentService {

    @Resource
    private CommonAttachmentRepository commonAttachmentRepository;

    @Resource
    private CommonAttachmentMapper commonAttachmentMapper;

    @Override
    public Long createCommonAttachment(CommonAttachmentCreateReqVO createReqVO) {
        // 插入
        CommonAttachment commonAttachment = commonAttachmentMapper.toEntity(createReqVO);
        commonAttachmentRepository.save(commonAttachment);
        // 返回
        return commonAttachment.getId();
    }

    @Override
    public void updateCommonAttachment(CommonAttachmentUpdateReqVO updateReqVO) {
        // 校验存在
        validateCommonAttachmentExists(updateReqVO.getId());
        // 更新
        CommonAttachment updateObj = commonAttachmentMapper.toEntity(updateReqVO);
        commonAttachmentRepository.save(updateObj);
    }

    @Override
    public void deleteCommonAttachment(Long id) {
        // 校验存在
        validateCommonAttachmentExists(id);
        // 删除
        commonAttachmentRepository.deleteById(id);
    }

    private void validateCommonAttachmentExists(Long id) {
        commonAttachmentRepository.findById(id).orElseThrow(() -> exception(COMMON_ATTACHMENT_NOT_EXISTS));
    }

    @Override
    public Optional<CommonAttachment> getCommonAttachment(Long id) {
        return commonAttachmentRepository.findById(id);
    }

    @Override
    public List<CommonAttachment> getCommonAttachmentList(Collection<Long> ids) {
        return StreamSupport.stream(commonAttachmentRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CommonAttachment> getCommonAttachmentPage(CommonAttachmentPageReqVO pageReqVO, CommonAttachmentPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CommonAttachment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), pageReqVO.getRefId()));
            }

            if(pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            if(pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CommonAttachment> page = commonAttachmentRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CommonAttachment> getCommonAttachmentList(CommonAttachmentExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CommonAttachment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), exportReqVO.getRefId()));
            }

            if(exportReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + exportReqVO.getFileName() + "%"));
            }

            if(exportReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), exportReqVO.getFileUrl()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return commonAttachmentRepository.findAll(spec);
    }

    private Sort createSort(CommonAttachmentPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getRefId() != null) {
            orders.add(new Sort.Order(order.getRefId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refId"));
        }

        if (order.getFileName() != null) {
            orders.add(new Sort.Order(order.getFileName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileName"));
        }

        if (order.getFileUrl() != null) {
            orders.add(new Sort.Order(order.getFileUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileUrl"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}