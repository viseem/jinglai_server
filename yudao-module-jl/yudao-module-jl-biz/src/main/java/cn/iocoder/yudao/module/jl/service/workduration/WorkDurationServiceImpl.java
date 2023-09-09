package cn.iocoder.yudao.module.jl.service.workduration;

import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
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
import cn.iocoder.yudao.module.jl.controller.admin.workduration.vo.*;
import cn.iocoder.yudao.module.jl.entity.workduration.WorkDuration;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.workduration.WorkDurationMapper;
import cn.iocoder.yudao.module.jl.repository.workduration.WorkDurationRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 工时 Service 实现类
 *
 */
@Service
@Validated
public class WorkDurationServiceImpl implements WorkDurationService {

    @Resource
    private WorkDurationRepository workDurationRepository;

    @Resource
    private WorkDurationMapper workDurationMapper;

    @Override
    public Long createWorkDuration(WorkDurationCreateReqVO createReqVO) {
        // 插入
        WorkDuration workDuration = workDurationMapper.toEntity(createReqVO);
        workDurationRepository.save(workDuration);
        // 返回
        return workDuration.getId();
    }

    @Override
    public void updateWorkDuration(WorkDurationUpdateReqVO updateReqVO) {
        // 校验存在
        validateWorkDurationExists(updateReqVO.getId());
        // 更新
        WorkDuration updateObj = workDurationMapper.toEntity(updateReqVO);
        workDurationRepository.save(updateObj);
    }

    @Override
    public void deleteWorkDuration(Long id) {
        // 校验存在
        validateWorkDurationExists(id);
        // 删除
        workDurationRepository.deleteById(id);
    }

    private void validateWorkDurationExists(Long id) {
        workDurationRepository.findById(id).orElseThrow(() -> exception(WORK_DURATION_NOT_EXISTS));
    }

    @Override
    public Optional<WorkDuration> getWorkDuration(Long id) {
        return workDurationRepository.findById(id);
    }

    @Override
    public List<WorkDuration> getWorkDurationList(Collection<Long> ids) {
        return StreamSupport.stream(workDurationRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<WorkDuration> getWorkDurationPage(WorkDurationPageReqVO pageReqVO, WorkDurationPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<WorkDuration> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getAttribute()!=null&&pageReqVO.getAttribute().equals(DataAttributeTypeEnums.MY.getStatus())){
                predicates.add(cb.equal(root.get("creator"), getLoginUserId()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getDuration() != null) {
                predicates.add(cb.equal(root.get("duration"), pageReqVO.getDuration()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getAuditStatus() != null) {
                predicates.add(cb.equal(root.get("auditStatus"), pageReqVO.getAuditStatus()));
            }

            if(pageReqVO.getAuditUserId() != null) {
                predicates.add(cb.equal(root.get("auditUserId"), pageReqVO.getAuditUserId()));
            }

            if(pageReqVO.getAuditMark() != null) {
                predicates.add(cb.equal(root.get("auditMark"), pageReqVO.getAuditMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<WorkDuration> page = workDurationRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<WorkDuration> getWorkDurationList(WorkDurationExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<WorkDuration> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getDuration() != null) {
                predicates.add(cb.equal(root.get("duration"), exportReqVO.getDuration()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getAuditStatus() != null) {
                predicates.add(cb.equal(root.get("auditStatus"), exportReqVO.getAuditStatus()));
            }

            if(exportReqVO.getAuditUserId() != null) {
                predicates.add(cb.equal(root.get("auditUserId"), exportReqVO.getAuditUserId()));
            }

            if(exportReqVO.getAuditMark() != null) {
                predicates.add(cb.equal(root.get("auditMark"), exportReqVO.getAuditMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return workDurationRepository.findAll(spec);
    }

    private Sort createSort(WorkDurationPageOrder order) {
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

        if (order.getDuration() != null) {
            orders.add(new Sort.Order(order.getDuration().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "duration"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getAuditStatus() != null) {
            orders.add(new Sort.Order(order.getAuditStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "auditStatus"));
        }

        if (order.getAuditUserId() != null) {
            orders.add(new Sort.Order(order.getAuditUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "auditUserId"));
        }

        if (order.getAuditMark() != null) {
            orders.add(new Sort.Order(order.getAuditMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "auditMark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}