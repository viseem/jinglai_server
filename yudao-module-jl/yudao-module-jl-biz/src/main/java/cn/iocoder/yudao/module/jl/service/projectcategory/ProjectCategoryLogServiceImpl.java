package cn.iocoder.yudao.module.jl.service.projectcategory;

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
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategoryLogMapper;
import cn.iocoder.yudao.module.jl.repository.projectcategory.ProjectCategoryLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目实验名目的操作日志 Service 实现类
 *
 */
@Service
@Validated
public class ProjectCategoryLogServiceImpl implements ProjectCategoryLogService {

    @Resource
    private ProjectCategoryLogRepository projectCategoryLogRepository;

    @Resource
    private ProjectCategoryLogMapper projectCategoryLogMapper;

    @Override
    public Long createProjectCategoryLog(ProjectCategoryLogCreateReqVO createReqVO) {

        createReqVO.setOperatorId(getLoginUserId());

        // 插入
        ProjectCategoryLog projectCategoryLog = projectCategoryLogMapper.toEntity(createReqVO);
        projectCategoryLogRepository.save(projectCategoryLog);
        // 返回
        return projectCategoryLog.getId();
    }

    @Override
    public void updateProjectCategoryLog(ProjectCategoryLogUpdateReqVO updateReqVO) {

        updateReqVO.setOperatorId(getLoginUserId());

        // 校验存在
        validateProjectCategoryLogExists(updateReqVO.getId());
        // 更新
        ProjectCategoryLog updateObj = projectCategoryLogMapper.toEntity(updateReqVO);
        projectCategoryLogRepository.save(updateObj);
    }

    @Override
    public void deleteProjectCategoryLog(Long id) {
        // 校验存在
        validateProjectCategoryLogExists(id);
        // 删除
        projectCategoryLogRepository.deleteById(id);
    }

    private void validateProjectCategoryLogExists(Long id) {
        projectCategoryLogRepository.findById(id).orElseThrow(() -> exception(PROJECT_CATEGORY_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectCategoryLog> getProjectCategoryLog(Long id) {
        return projectCategoryLogRepository.findById(id);
    }

    @Override
    public List<ProjectCategoryLog> getProjectCategoryLogList(Collection<Long> ids) {
        return StreamSupport.stream(projectCategoryLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectCategoryLog> getProjectCategoryLogPage(ProjectCategoryLogPageReqVO pageReqVO, ProjectCategoryLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectCategoryLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if(pageReqVO.getOperatorId() != null) {
                predicates.add(cb.equal(root.get("operatorId"), pageReqVO.getOperatorId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectCategoryLog> page = projectCategoryLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectCategoryLog> getProjectCategoryLogList(ProjectCategoryLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectCategoryLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if(exportReqVO.getOperatorId() != null) {
                predicates.add(cb.equal(root.get("operatorId"), exportReqVO.getOperatorId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectCategoryLogRepository.findAll(spec);
    }

    private Sort createSort(ProjectCategoryLogPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getOperatorId() != null) {
            orders.add(new Sort.Order(order.getOperatorId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "operatorId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}