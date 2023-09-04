package cn.iocoder.yudao.module.jl.service.feedback;

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
import cn.iocoder.yudao.module.jl.controller.admin.feedback.vo.*;
import cn.iocoder.yudao.module.jl.entity.feedback.Feedback;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.feedback.FeedbackMapper;
import cn.iocoder.yudao.module.jl.repository.feedback.FeedbackRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 晶莱反馈 Service 实现类
 *
 */
@Service
@Validated
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackRepository feedbackRepository;

    @Resource
    private FeedbackMapper feedbackMapper;

    @Override
    public Long createFeedback(FeedbackCreateReqVO createReqVO) {
        // 插入
        Feedback feedback = feedbackMapper.toEntity(createReqVO);
        feedbackRepository.save(feedback);
        // 返回
        return feedback.getId();
    }

    @Override
    public void updateFeedback(FeedbackUpdateReqVO updateReqVO) {
        // 校验存在
        validateFeedbackExists(updateReqVO.getId());
        // 更新
        Feedback updateObj = feedbackMapper.toEntity(updateReqVO);
        feedbackRepository.save(updateObj);
    }

    @Override
    public void deleteFeedback(Long id) {
        // 校验存在
        validateFeedbackExists(id);
        // 删除
        feedbackRepository.deleteById(id);
    }

    private void validateFeedbackExists(Long id) {
        feedbackRepository.findById(id).orElseThrow(() -> exception(FEEDBACK_NOT_EXISTS));
    }

    @Override
    public Optional<Feedback> getFeedback(Long id) {
        return feedbackRepository.findById(id);
    }

    @Override
    public List<Feedback> getFeedbackList(Collection<Long> ids) {
        return StreamSupport.stream(feedbackRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Feedback> getFeedbackPage(FeedbackPageReqVO pageReqVO, FeedbackPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Feedback> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getImportance() != null) {
                predicates.add(cb.equal(root.get("importance"), pageReqVO.getImportance()));
            }

            if(pageReqVO.getDeadline() != null) {
                predicates.add(cb.equal(root.get("deadline"), pageReqVO.getDeadline()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<Feedback> page = feedbackRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<Feedback> getFeedbackList(FeedbackExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Feedback> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getImportance() != null) {
                predicates.add(cb.equal(root.get("importance"), exportReqVO.getImportance()));
            }

            if(exportReqVO.getDeadline() != null) {
                predicates.add(cb.equal(root.get("deadline"), exportReqVO.getDeadline()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), exportReqVO.getFileUrl()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return feedbackRepository.findAll(spec);
    }

    private Sort createSort(FeedbackPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getImportance() != null) {
            orders.add(new Sort.Order(order.getImportance().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "importance"));
        }

        if (order.getDeadline() != null) {
            orders.add(new Sort.Order(order.getDeadline().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "deadline"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getFileUrl() != null) {
            orders.add(new Sort.Order(order.getFileUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileUrl"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}