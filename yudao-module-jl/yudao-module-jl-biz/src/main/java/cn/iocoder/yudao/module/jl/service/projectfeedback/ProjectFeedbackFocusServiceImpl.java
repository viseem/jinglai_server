package cn.iocoder.yudao.module.jl.service.projectfeedback;

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
import cn.iocoder.yudao.module.jl.controller.admin.projectfeedback.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectfeedback.ProjectFeedbackFocus;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectfeedback.ProjectFeedbackFocusMapper;
import cn.iocoder.yudao.module.jl.repository.projectfeedback.ProjectFeedbackFocusRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 晶莱项目反馈关注人员 Service 实现类
 *
 */
@Service
@Validated
public class ProjectFeedbackFocusServiceImpl implements ProjectFeedbackFocusService {

    @Resource
    private ProjectFeedbackFocusRepository projectFeedbackFocusRepository;

    @Resource
    private ProjectFeedbackFocusMapper projectFeedbackFocusMapper;

    @Override
    public Long createProjectFeedbackFocus(ProjectFeedbackFocusCreateReqVO createReqVO) {
        // 插入
        ProjectFeedbackFocus projectFeedbackFocus = projectFeedbackFocusMapper.toEntity(createReqVO);
        projectFeedbackFocusRepository.save(projectFeedbackFocus);
        // 返回
        return projectFeedbackFocus.getId();
    }

    @Override
    public void updateProjectFeedbackFocus(ProjectFeedbackFocusUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectFeedbackFocusExists(updateReqVO.getId());
        // 更新
        ProjectFeedbackFocus updateObj = projectFeedbackFocusMapper.toEntity(updateReqVO);
        projectFeedbackFocusRepository.save(updateObj);
    }

    @Override
    public void deleteProjectFeedbackFocus(Long id) {
        // 校验存在
        validateProjectFeedbackFocusExists(id);
        // 删除
        projectFeedbackFocusRepository.deleteById(id);
    }

    private void validateProjectFeedbackFocusExists(Long id) {
        projectFeedbackFocusRepository.findById(id).orElseThrow(() -> exception(PROJECT_FEEDBACK_FOCUS_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectFeedbackFocus> getProjectFeedbackFocus(Long id) {
        return projectFeedbackFocusRepository.findById(id);
    }

    @Override
    public List<ProjectFeedbackFocus> getProjectFeedbackFocusList(Collection<Long> ids) {
        return StreamSupport.stream(projectFeedbackFocusRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectFeedbackFocus> getProjectFeedbackFocusPage(ProjectFeedbackFocusPageReqVO pageReqVO, ProjectFeedbackFocusPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectFeedbackFocus> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }

            if(pageReqVO.getProjectFeedbackId() != null) {
                predicates.add(cb.equal(root.get("projectFeedbackId"), pageReqVO.getProjectFeedbackId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectFeedbackFocus> page = projectFeedbackFocusRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectFeedbackFocus> getProjectFeedbackFocusList(ProjectFeedbackFocusExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectFeedbackFocus> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }

            if(exportReqVO.getProjectFeedbackId() != null) {
                predicates.add(cb.equal(root.get("projectFeedbackId"), exportReqVO.getProjectFeedbackId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectFeedbackFocusRepository.findAll(spec);
    }

    private Sort createSort(ProjectFeedbackFocusPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getUserId() != null) {
            orders.add(new Sort.Order(order.getUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "userId"));
        }

        if (order.getProjectFeedbackId() != null) {
            orders.add(new Sort.Order(order.getProjectFeedbackId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectFeedbackId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}