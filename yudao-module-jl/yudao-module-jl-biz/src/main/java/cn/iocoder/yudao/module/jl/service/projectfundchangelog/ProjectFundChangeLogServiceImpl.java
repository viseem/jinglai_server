package cn.iocoder.yudao.module.jl.service.projectfundchangelog;

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
import cn.iocoder.yudao.module.jl.controller.admin.projectfundchangelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectfundchangelog.ProjectFundChangeLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectfundchangelog.ProjectFundChangeLogMapper;
import cn.iocoder.yudao.module.jl.repository.projectfundchangelog.ProjectFundChangeLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 款项计划变更日志 Service 实现类
 *
 */
@Service
@Validated
public class ProjectFundChangeLogServiceImpl implements ProjectFundChangeLogService {

    @Resource
    private ProjectFundChangeLogRepository projectFundChangeLogRepository;

    @Resource
    private ProjectFundChangeLogMapper projectFundChangeLogMapper;

    @Override
    public Long createProjectFundChangeLog(ProjectFundChangeLogCreateReqVO createReqVO) {
        // 插入
        ProjectFundChangeLog projectFundChangeLog = projectFundChangeLogMapper.toEntity(createReqVO);
        projectFundChangeLogRepository.save(projectFundChangeLog);
        // 返回
        return projectFundChangeLog.getId();
    }

    @Override
    public void updateProjectFundChangeLog(ProjectFundChangeLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectFundChangeLogExists(updateReqVO.getId());
        // 更新
        ProjectFundChangeLog updateObj = projectFundChangeLogMapper.toEntity(updateReqVO);
        projectFundChangeLogRepository.save(updateObj);
    }

    @Override
    public void deleteProjectFundChangeLog(Long id) {
        // 校验存在
        validateProjectFundChangeLogExists(id);
        // 删除
        projectFundChangeLogRepository.deleteById(id);
    }

    private void validateProjectFundChangeLogExists(Long id) {
        projectFundChangeLogRepository.findById(id).orElseThrow(() -> exception(PROJECT_FUND_CHANGE_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectFundChangeLog> getProjectFundChangeLog(Long id) {
        return projectFundChangeLogRepository.findById(id);
    }

    @Override
    public List<ProjectFundChangeLog> getProjectFundChangeLogList(Collection<Long> ids) {
        return StreamSupport.stream(projectFundChangeLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectFundChangeLog> getProjectFundChangeLogPage(ProjectFundChangeLogPageReqVO pageReqVO, ProjectFundChangeLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectFundChangeLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectFundId() != null) {
                predicates.add(cb.equal(root.get("projectFundId"), pageReqVO.getProjectFundId()));
            }

            if(pageReqVO.getOriginStatus() != null) {
                predicates.add(cb.equal(root.get("originStatus"), pageReqVO.getOriginStatus()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), pageReqVO.getSalesId()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getContractId() != null) {
                predicates.add(cb.equal(root.get("contractId"), pageReqVO.getContractId()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), pageReqVO.getPrice()));
            }

            if(pageReqVO.getDeadline() != null) {
                predicates.add(cb.equal(root.get("deadline"), pageReqVO.getDeadline()));
            }

            if(pageReqVO.getChangeType() != null) {
                predicates.add(cb.equal(root.get("changeType"), pageReqVO.getChangeType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectFundChangeLog> page = projectFundChangeLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectFundChangeLog> getProjectFundChangeLogList(ProjectFundChangeLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectFundChangeLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectFundId() != null) {
                predicates.add(cb.equal(root.get("projectFundId"), exportReqVO.getProjectFundId()));
            }

            if(exportReqVO.getOriginStatus() != null) {
                predicates.add(cb.equal(root.get("originStatus"), exportReqVO.getOriginStatus()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), exportReqVO.getSalesId()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getContractId() != null) {
                predicates.add(cb.equal(root.get("contractId"), exportReqVO.getContractId()));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), exportReqVO.getPrice()));
            }

            if(exportReqVO.getDeadline() != null) {
                predicates.add(cb.equal(root.get("deadline"), exportReqVO.getDeadline()));
            }

            if(exportReqVO.getChangeType() != null) {
                predicates.add(cb.equal(root.get("changeType"), exportReqVO.getChangeType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectFundChangeLogRepository.findAll(spec);
    }

    private Sort createSort(ProjectFundChangeLogPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProjectFundId() != null) {
            orders.add(new Sort.Order(order.getProjectFundId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectFundId"));
        }

        if (order.getOriginStatus() != null) {
            orders.add(new Sort.Order(order.getOriginStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "originStatus"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getSalesId() != null) {
            orders.add(new Sort.Order(order.getSalesId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salesId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getContractId() != null) {
            orders.add(new Sort.Order(order.getContractId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "contractId"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getPrice() != null) {
            orders.add(new Sort.Order(order.getPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "price"));
        }

        if (order.getDeadline() != null) {
            orders.add(new Sort.Order(order.getDeadline().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "deadline"));
        }

        if (order.getChangeType() != null) {
            orders.add(new Sort.Order(order.getChangeType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "changeType"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}