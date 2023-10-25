package cn.iocoder.yudao.module.jl.service.reminder;

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
import cn.iocoder.yudao.module.jl.controller.admin.reminder.vo.*;
import cn.iocoder.yudao.module.jl.entity.reminder.Reminder;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.reminder.ReminderMapper;
import cn.iocoder.yudao.module.jl.repository.reminder.ReminderRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 晶莱提醒事项 Service 实现类
 *
 */
@Service
@Validated
public class ReminderServiceImpl implements ReminderService {

    @Resource
    private ReminderRepository reminderRepository;

    @Resource
    private ReminderMapper reminderMapper;

    @Override
    public Long createReminder(ReminderCreateReqVO createReqVO) {
        // 插入
        Reminder reminder = reminderMapper.toEntity(createReqVO);
        reminderRepository.save(reminder);
        // 返回
        return reminder.getId();
    }

    @Override
    public void updateReminder(ReminderUpdateReqVO updateReqVO) {
        // 校验存在
        validateReminderExists(updateReqVO.getId());
        // 更新
        Reminder updateObj = reminderMapper.toEntity(updateReqVO);
        reminderRepository.save(updateObj);
    }

    @Override
    public void deleteReminder(Long id) {
        // 校验存在
        validateReminderExists(id);
        // 删除
        reminderRepository.deleteById(id);
    }

    private void validateReminderExists(Long id) {
        reminderRepository.findById(id).orElseThrow(() -> exception(REMINDER_NOT_EXISTS));
    }

    @Override
    public Optional<Reminder> getReminder(Long id) {
        return reminderRepository.findById(id);
    }

    @Override
    public List<Reminder> getReminderList(Collection<Long> ids) {
        return StreamSupport.stream(reminderRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Reminder> getReminderPage(ReminderPageReqVO pageReqVO, ReminderPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Reminder> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getContractId() != null) {
                predicates.add(cb.equal(root.get("contractId"), pageReqVO.getContractId()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getDeadline() != null) {
                predicates.add(cb.equal(root.get("deadline"), pageReqVO.getDeadline()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<Reminder> page = reminderRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<Reminder> getReminderList(ReminderExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Reminder> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getContractId() != null) {
                predicates.add(cb.equal(root.get("contractId"), exportReqVO.getContractId()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getDeadline() != null) {
                predicates.add(cb.equal(root.get("deadline"), exportReqVO.getDeadline()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return reminderRepository.findAll(spec);
    }

    private Sort createSort(ReminderPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getContractId() != null) {
            orders.add(new Sort.Order(order.getContractId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "contractId"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getDeadline() != null) {
            orders.add(new Sort.Order(order.getDeadline().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "deadline"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}