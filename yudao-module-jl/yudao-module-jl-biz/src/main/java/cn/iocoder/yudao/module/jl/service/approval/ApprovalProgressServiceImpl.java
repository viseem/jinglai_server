package cn.iocoder.yudao.module.jl.service.approval;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectApprovalUpdateReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.ProjectCategoryApprovalUpdateReqVO;
import cn.iocoder.yudao.module.jl.enums.ApprovalStageEnums;
import cn.iocoder.yudao.module.jl.enums.ApprovalTypeEnums;
import cn.iocoder.yudao.module.jl.repository.approval.ApprovalRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectApprovalServiceImpl;
import cn.iocoder.yudao.module.jl.service.projectcategory.ProjectCategoryApprovalServiceImpl;
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

import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.approval.vo.*;
import cn.iocoder.yudao.module.jl.entity.approval.ApprovalProgress;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.approval.ApprovalProgressMapper;
import cn.iocoder.yudao.module.jl.repository.approval.ApprovalProgressRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 审批流程 Service 实现类
 *
 */
@Service
@Validated
public class ApprovalProgressServiceImpl implements ApprovalProgressService {

    @Resource
    private ProjectApprovalServiceImpl projectApprovalService;

    @Resource
    private ProjectCategoryApprovalServiceImpl projectCategoryApprovalService;

    @Resource
    private ApprovalProgressRepository approvalProgressRepository;

    @Resource
    private ApprovalProgressMapper approvalProgressMapper;
    private final ApprovalRepository approvalRepository;

    public ApprovalProgressServiceImpl(ApprovalRepository approvalRepository) {
        this.approvalRepository = approvalRepository;
    }

    @Override
    public Long createApprovalProgress(ApprovalProgressCreateReqVO createReqVO) {
        // 插入
        ApprovalProgress approvalProgress = approvalProgressMapper.toEntity(createReqVO);
        approvalProgressRepository.save(approvalProgress);
        // 返回
        return approvalProgress.getId();
    }

    @Override
    @Transactional
    public void updateApprovalProgress(ApprovalProgressUpdateReqVO updateReqVO) {
        // 校验存在
        ApprovalProgress approvalProgress = validateApprovalProgressExists(updateReqVO.getId());
        approvalProgress.setApprovalMark(updateReqVO.getApprovalMark());
        approvalProgress.setApprovalStage(updateReqVO.getApprovalStage());

        //检验approvalProgress是否为最后一步
        if(approvalProgress.getIsLast()|| Objects.equals(approvalProgress.getApprovalStage(), ApprovalStageEnums.APPROVAL_FAIL.getStatus())){
            //如果是项目状态变更
            if(Objects.equals(approvalProgress.getApprovalType(), ApprovalTypeEnums.PROJECT_STATUS_CHANGE.getStatus())){
                //获取ProjectApprovalUpdateReqVO
                ProjectApprovalUpdateReqVO projectApprovalUpdateReqVO = new ProjectApprovalUpdateReqVO();
                projectApprovalUpdateReqVO.setId(updateReqVO.getRefId());
                projectApprovalUpdateReqVO.setApprovalMark(approvalProgress.getApprovalMark());
                projectApprovalUpdateReqVO.setApprovalStage(approvalProgress.getApprovalStage());
                projectApprovalUpdateReqVO.setApprovalUserId(approvalProgress.getToUserId());
                // 更新项目状态
                projectApprovalService.updateProjectApproval(
                        projectApprovalUpdateReqVO
                );
            }

            //如果是实验审核推进
            if(Objects.equals(approvalProgress.getApprovalType(), ApprovalTypeEnums.EXP_PROGRESS.getStatus())){
                //获取ProjectCategoryApprovalUpdateReqVO
                ProjectCategoryApprovalUpdateReqVO projectCategoryApprovalUpdateReqVO = new ProjectCategoryApprovalUpdateReqVO();
                projectCategoryApprovalUpdateReqVO.setId(updateReqVO.getRefId());
                projectCategoryApprovalUpdateReqVO.setApprovalMark(approvalProgress.getApprovalMark());
                projectCategoryApprovalUpdateReqVO.setApprovalStage(approvalProgress.getApprovalStage());
                projectCategoryApprovalUpdateReqVO.setApprovalUserId(approvalProgress.getToUserId());

// 更新项目状态
                projectCategoryApprovalService.updateProjectCategoryApproval(
                        projectCategoryApprovalUpdateReqVO
                );
            }

            approvalRepository.updateStatusById(approvalProgress.getApprovalStage(),approvalProgress.getApprovalId());
        }



        // 更新
//        ApprovalProgress updateObj = approvalProgressMapper.toEntity(updateReqVO);
        approvalProgressRepository.save(approvalProgress);
    }

    @Override
    public void deleteApprovalProgress(Long id) {
        // 校验存在
        validateApprovalProgressExists(id);
        // 删除
        approvalProgressRepository.deleteById(id);
    }

    private ApprovalProgress validateApprovalProgressExists(Long id) {
        Optional<ApprovalProgress> byId = approvalProgressRepository.findById(id);
        if (byId.isEmpty()) {
            throw exception(APPROVAL_PROGRESS_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<ApprovalProgress> getApprovalProgress(Long id) {
        return approvalProgressRepository.findById(id);
    }

    @Override
    public List<ApprovalProgress> getApprovalProgressList(Collection<Long> ids) {
        return StreamSupport.stream(approvalProgressRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ApprovalProgress> getApprovalProgressPage(ApprovalProgressPageReqVO pageReqVO, ApprovalProgressPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ApprovalProgress> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getCreator()==null||pageReqVO.getCreator() != -1) {
                predicates.add(cb.equal(root.get("toUserId"), getLoginUserId()));
            }

            if(pageReqVO.getApprovalId() != null) {
                predicates.add(cb.equal(root.get("approvalId"), pageReqVO.getApprovalId()));
            }

            if(pageReqVO.getToUserId() != null) {
                predicates.add(cb.equal(root.get("toUserId"), pageReqVO.getToUserId()));
            }

            if(pageReqVO.getApprovalStage() != null) {
                predicates.add(cb.equal(root.get("approvalStage"), pageReqVO.getApprovalStage()));
            }

            if(pageReqVO.getApprovalMark() != null) {
                predicates.add(cb.equal(root.get("approvalMark"), pageReqVO.getApprovalMark()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ApprovalProgress> page = approvalProgressRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ApprovalProgress> getApprovalProgressList(ApprovalProgressExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ApprovalProgress> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getApprovalId() != null) {
                predicates.add(cb.equal(root.get("approvalId"), exportReqVO.getApprovalId()));
            }

            if(exportReqVO.getToUserId() != null) {
                predicates.add(cb.equal(root.get("toUserId"), exportReqVO.getToUserId()));
            }

            if(exportReqVO.getApprovalStage() != null) {
                predicates.add(cb.equal(root.get("approvalStage"), exportReqVO.getApprovalStage()));
            }

            if(exportReqVO.getApprovalMark() != null) {
                predicates.add(cb.equal(root.get("approvalMark"), exportReqVO.getApprovalMark()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return approvalProgressRepository.findAll(spec);
    }

    private Sort createSort(ApprovalProgressPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getApprovalId() != null) {
            orders.add(new Sort.Order(order.getApprovalId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "approvalId"));
        }

        if (order.getToUserId() != null) {
            orders.add(new Sort.Order(order.getToUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "toUserId"));
        }

        if (order.getApprovalStage() != null) {
            orders.add(new Sort.Order(order.getApprovalStage().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "approvalStage"));
        }

        if (order.getApprovalMark() != null) {
            orders.add(new Sort.Order(order.getApprovalMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "approvalMark"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}