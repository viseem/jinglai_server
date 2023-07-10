package cn.iocoder.yudao.module.jl.service.projectservice;

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
import cn.iocoder.yudao.module.jl.controller.admin.projectservice.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectservice.ProjectService;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectservice.ProjectServiceMapper;
import cn.iocoder.yudao.module.jl.repository.projectservice.ProjectServiceRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目售后 Service 实现类
 *
 */
@Service
@Validated
public class ProjectServiceServiceImpl implements ProjectServiceService {

    @Resource
    private ProjectServiceRepository projectServiceRepository;

    @Resource
    private ProjectServiceMapper projectServiceMapper;

    @Override
    public Long createProjectService(ProjectServiceCreateReqVO createReqVO) {
        // 插入
        ProjectService projectService = projectServiceMapper.toEntity(createReqVO);
        projectServiceRepository.save(projectService);
        // 返回
        return projectService.getId();
    }

    @Override
    public void updateProjectService(ProjectServiceUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectServiceExists(updateReqVO.getId());
        // 更新
        ProjectService updateObj = projectServiceMapper.toEntity(updateReqVO);
        projectServiceRepository.save(updateObj);
    }

    @Override
    public void deleteProjectService(Long id) {
        // 校验存在
        validateProjectServiceExists(id);
        // 删除
        projectServiceRepository.deleteById(id);
    }

    private void validateProjectServiceExists(Long id) {
        projectServiceRepository.findById(id).orElseThrow(() -> exception(PROJECT_SERVICE_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectService> getProjectService(Long id) {
        return projectServiceRepository.findById(id);
    }

    @Override
    public List<ProjectService> getProjectServiceList(Collection<Long> ids) {
        return StreamSupport.stream(projectServiceRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectService> getProjectServicePage(ProjectServicePageReqVO pageReqVO, ProjectServicePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectService> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if(pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getResult() != null) {
                predicates.add(cb.equal(root.get("result"), pageReqVO.getResult()));
            }

            if(pageReqVO.getResultUserId() != null) {
                predicates.add(cb.equal(root.get("resultUserId"), pageReqVO.getResultUserId()));
            }

            if(pageReqVO.getResultTime() != null) {
                predicates.add(cb.between(root.get("resultTime"), pageReqVO.getResultTime()[0], pageReqVO.getResultTime()[1]));
            } 

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectService> page = projectServiceRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectService> getProjectServiceList(ProjectServiceExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectService> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if(exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getResult() != null) {
                predicates.add(cb.equal(root.get("result"), exportReqVO.getResult()));
            }

            if(exportReqVO.getResultUserId() != null) {
                predicates.add(cb.equal(root.get("resultUserId"), exportReqVO.getResultUserId()));
            }

            if(exportReqVO.getResultTime() != null) {
                predicates.add(cb.between(root.get("resultTime"), exportReqVO.getResultTime()[0], exportReqVO.getResultTime()[1]));
            } 

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectServiceRepository.findAll(spec);
    }

    private Sort createSort(ProjectServicePageOrder order) {
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

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getUserId() != null) {
            orders.add(new Sort.Order(order.getUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "userId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getResult() != null) {
            orders.add(new Sort.Order(order.getResult().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "result"));
        }

        if (order.getResultUserId() != null) {
            orders.add(new Sort.Order(order.getResultUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "resultUserId"));
        }

        if (order.getResultTime() != null) {
            orders.add(new Sort.Order(order.getResultTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "resultTime"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}