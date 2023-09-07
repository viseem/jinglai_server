package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.module.jl.repository.project.ProjectScheduleRepository;
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
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectPlan;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectPlanMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectPlanRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目实验方案 Service 实现类
 *
 */
@Service
@Validated
public class ProjectPlanServiceImpl implements ProjectPlanService {

    @Resource
    private ProjectPlanRepository projectPlanRepository;

    @Resource
    private ProjectPlanMapper projectPlanMapper;

    @Resource
    private ProjectScheduleRepository projectScheduleRepository;

    @Override
    public Long createProjectPlan(ProjectPlanCreateReqVO createReqVO) {
        // 插入
        ProjectPlan projectPlan = projectPlanMapper.toEntity(createReqVO);
        projectPlanRepository.save(projectPlan);
        // 返回
        return projectPlan.getId();
    }

    @Override
    @Transactional
    public void updateProjectPlan(ProjectPlanUpdateReqVO updateReqVO) {
        // 校验存在
//        validateProjectPlanExists(updateReqVO.getId());
        // 更新
        //如果没有id，则设置一下version
        if(updateReqVO.getId() == null){
            updateReqVO.setVersion(String.valueOf((projectPlanRepository.countByScheduleId(updateReqVO.getScheduleId())+1)));
        }

        //更新安排单的当前方案id
        projectScheduleRepository.updateCurrentPlanIdById(updateReqVO.getId(),updateReqVO.getScheduleId());

        ProjectPlan updateObj = projectPlanMapper.toEntity(updateReqVO);
        projectPlanRepository.save(updateObj);
    }

    @Override
    public void deleteProjectPlan(Long id) {
        // 校验存在
        validateProjectPlanExists(id);
        // 删除
        projectPlanRepository.deleteById(id);
    }

    private void validateProjectPlanExists(Long id) {
        projectPlanRepository.findById(id).orElseThrow(() -> exception(PROJECT_PLAN_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectPlan> getProjectPlan(Long id) {
        return projectPlanRepository.findById(id);
    }

    @Override
    public List<ProjectPlan> getProjectPlanList(Collection<Long> ids) {
        return StreamSupport.stream(projectPlanRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectPlan> getProjectPlanPage(ProjectPlanPageReqVO pageReqVO, ProjectPlanPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectPlan> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getScheduleId() != null) {
                predicates.add(cb.equal(root.get("scheduleId"), pageReqVO.getScheduleId()));
            }

            if(pageReqVO.getPlanText() != null) {
                predicates.add(cb.equal(root.get("planText"), pageReqVO.getPlanText()));
            }

            if(pageReqVO.getVersion() != null) {
                predicates.add(cb.equal(root.get("version"), pageReqVO.getVersion()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectPlan> page = projectPlanRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectPlan> getProjectPlanList(ProjectPlanExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectPlan> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getScheduleId() != null) {
                predicates.add(cb.equal(root.get("scheduleId"), exportReqVO.getScheduleId()));
            }

            if(exportReqVO.getPlanText() != null) {
                predicates.add(cb.equal(root.get("planText"), exportReqVO.getPlanText()));
            }

            if(exportReqVO.getVersion() != null) {
                predicates.add(cb.equal(root.get("version"), exportReqVO.getVersion()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectPlanRepository.findAll(spec);
    }

    private Sort createSort(ProjectPlanPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getScheduleId() != null) {
            orders.add(new Sort.Order(order.getScheduleId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "scheduleId"));
        }

        if (order.getPlanText() != null) {
            orders.add(new Sort.Order(order.getPlanText().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "planText"));
        }

        if (order.getVersion() != null) {
            orders.add(new Sort.Order(order.getVersion().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "version"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}