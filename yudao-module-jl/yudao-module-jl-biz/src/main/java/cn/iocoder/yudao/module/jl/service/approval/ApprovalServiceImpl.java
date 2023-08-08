package cn.iocoder.yudao.module.jl.service.approval;

import cn.iocoder.yudao.module.jl.entity.approval.ApprovalProgress;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.enums.ApprovalStageEnums;
import cn.iocoder.yudao.module.jl.repository.approval.ApprovalProgressRepository;
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
import cn.iocoder.yudao.module.jl.controller.admin.approval.vo.*;
import cn.iocoder.yudao.module.jl.entity.approval.Approval;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.approval.ApprovalMapper;
import cn.iocoder.yudao.module.jl.repository.approval.ApprovalRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 审批 Service 实现类
 *
 */
@Service
@Validated
public class ApprovalServiceImpl implements ApprovalService {

    @Resource
    private ApprovalProgressRepository approvalProgressRepository;

    @Resource
    private ApprovalRepository approvalRepository;

    @Resource
    private ApprovalMapper approvalMapper;

    @Override
    public Long createApproval(ApprovalCreateReqVO createReqVO) {
        // 插入
        Approval approval = approvalMapper.toEntity(createReqVO);
        approvalRepository.save(approval);
        // 返回
        return approval.getId();
    }

    @Override
    public void updateApproval(ApprovalUpdateReqVO updateReqVO) {
        // 校验存在
        validateApprovalExists(updateReqVO.getId());
        // 更新
        Approval updateObj = approvalMapper.toEntity(updateReqVO);
        approvalRepository.save(updateObj);
    }

    public Approval processApproval(List<User> userList, String type, Long id, String mark) {
        Approval approval = new Approval();
        approval.setRefId(id);
        approval.setContent(mark);
        approval.setType(type);
        approval.setStatus(ApprovalStageEnums.DEFAULT.getStatus());
        //保存Approval
        approvalRepository.save(approval);

        //保存ApprovalProgress
        //获取审批人数组
        int totalUsers = userList.size();
        // 遍历审批人数组
        for (int i = 0; i < totalUsers; i++) {
            User user = userList.get(i);

            // 设置ApprovalProgress的属性
            ApprovalProgress approvalProgress = new ApprovalProgress();
            approvalProgress.setApprovalId(approval.getId());
            approvalProgress.setToUserId(user.getId());
            approvalProgress.setType("APPROVAL");
            approvalProgress.setApprovalType(type);
            approvalProgress.setApprovalStage(ApprovalStageEnums.DEFAULT.getStatus());

            // 判断是否为最后一个元素，并设置isLast的值
            boolean isLast = (i == totalUsers - 1);
            approvalProgress.setIsLast(isLast);

            // 保存ApprovalProgress
            approvalProgressRepository.save(approvalProgress);
        }
        return approval;
    }

    @Override
    public void deleteApproval(Long id) {
        // 校验存在
        validateApprovalExists(id);
        // 删除
        approvalRepository.deleteById(id);
    }

    private void validateApprovalExists(Long id) {
        approvalRepository.findById(id).orElseThrow(() -> exception(APPROVAL_NOT_EXISTS));
    }

    @Override
    public Optional<Approval> getApproval(Long id) {
        return approvalRepository.findById(id);
    }

    @Override
    public List<Approval> getApprovalList(Collection<Long> ids) {
        return StreamSupport.stream(approvalRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Approval> getApprovalPage(ApprovalPageReqVO pageReqVO, ApprovalPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Approval> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if(pageReqVO.getCreator()==null||pageReqVO.getCreator() != -1) {
                predicates.add(cb.equal(root.get("creator"), getLoginUserId()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), pageReqVO.getRefId()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getCurrentApprovalUser() != null) {
                predicates.add(cb.equal(root.get("currentApprovalUser"), pageReqVO.getCurrentApprovalUser()));
            }

            if(pageReqVO.getCurrentApprovalStage() != null) {
                predicates.add(cb.equal(root.get("currentApprovalStage"), pageReqVO.getCurrentApprovalStage()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<Approval> page = approvalRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<Approval> getApprovalList(ApprovalExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Approval> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), exportReqVO.getRefId()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getCurrentApprovalUser() != null) {
                predicates.add(cb.equal(root.get("currentApprovalUser"), exportReqVO.getCurrentApprovalUser()));
            }

            if(exportReqVO.getCurrentApprovalStage() != null) {
                predicates.add(cb.equal(root.get("currentApprovalStage"), exportReqVO.getCurrentApprovalStage()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return approvalRepository.findAll(spec);
    }

    private Sort createSort(ApprovalPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order(order.getCreateTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getRefId() != null) {
            orders.add(new Sort.Order(order.getRefId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refId"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getCurrentApprovalUser() != null) {
            orders.add(new Sort.Order(order.getCurrentApprovalUser().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "currentApprovalUser"));
        }

        if (order.getCurrentApprovalStage() != null) {
            orders.add(new Sort.Order(order.getCurrentApprovalStage().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "currentApprovalStage"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}