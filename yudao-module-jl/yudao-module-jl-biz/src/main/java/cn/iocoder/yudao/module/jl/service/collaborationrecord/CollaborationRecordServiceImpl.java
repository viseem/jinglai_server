package cn.iocoder.yudao.module.jl.service.collaborationrecord;

import cn.iocoder.yudao.module.jl.service.commonattachment.CommonAttachmentServiceImpl;
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
import cn.iocoder.yudao.module.jl.controller.admin.collaborationrecord.vo.*;
import cn.iocoder.yudao.module.jl.entity.collaborationrecord.CollaborationRecord;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.collaborationrecord.CollaborationRecordMapper;
import cn.iocoder.yudao.module.jl.repository.collaborationrecord.CollaborationRecordRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 通用协作记录 Service 实现类
 *
 */
@Service
@Validated
public class CollaborationRecordServiceImpl implements CollaborationRecordService {
    @Resource
    private CommonAttachmentServiceImpl commonAttachmentService;
    @Resource
    private CollaborationRecordRepository collaborationRecordRepository;

    @Resource
    private CollaborationRecordMapper collaborationRecordMapper;

    @Override
    @Transactional
    public Long createCollaborationRecord(CollaborationRecordCreateReqVO createReqVO) {
        // 插入
        CollaborationRecord collaborationRecord = collaborationRecordMapper.toEntity(createReqVO);
        collaborationRecordRepository.save(collaborationRecord);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(collaborationRecord.getId(),"COLLABORATION_RECORD",createReqVO.getAttachmentList());

        // 返回
        return collaborationRecord.getId();
    }

    @Override
    @Transactional
    public void updateCollaborationRecord(CollaborationRecordUpdateReqVO updateReqVO) {
        // 校验存在
        validateCollaborationRecordExists(updateReqVO.getId());
        // 更新
        CollaborationRecord updateObj = collaborationRecordMapper.toEntity(updateReqVO);
        collaborationRecordRepository.save(updateObj);
        commonAttachmentService.saveAttachmentList(updateReqVO.getId(),"COLLABORATION_RECORD",updateReqVO.getAttachmentList());

    }

    @Override
    public void deleteCollaborationRecord(Long id) {
        // 校验存在
        validateCollaborationRecordExists(id);
        // 删除
        collaborationRecordRepository.deleteById(id);
    }

    private void validateCollaborationRecordExists(Long id) {
        collaborationRecordRepository.findById(id).orElseThrow(() -> exception(COLLABORATION_RECORD_NOT_EXISTS));
    }

    @Override
    public Optional<CollaborationRecord> getCollaborationRecord(Long id) {
        return collaborationRecordRepository.findById(id);
    }

    @Override
    public List<CollaborationRecord> getCollaborationRecordList(Collection<Long> ids) {
        return StreamSupport.stream(collaborationRecordRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CollaborationRecord> getCollaborationRecordPage(CollaborationRecordPageReqVO pageReqVO, CollaborationRecordPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CollaborationRecord> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), pageReqVO.getRefId()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getPid() != null) {
                predicates.add(cb.equal(root.get("pid"), pageReqVO.getPid()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CollaborationRecord> page = collaborationRecordRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CollaborationRecord> getCollaborationRecordList(CollaborationRecordExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CollaborationRecord> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), exportReqVO.getRefId()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getPid() != null) {
                predicates.add(cb.equal(root.get("pid"), exportReqVO.getPid()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return collaborationRecordRepository.findAll(spec);
    }

    private Sort createSort(CollaborationRecordPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getRefId() != null) {
            orders.add(new Sort.Order(order.getRefId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refId"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getPid() != null) {
            orders.add(new Sort.Order(order.getPid().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "pid"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}