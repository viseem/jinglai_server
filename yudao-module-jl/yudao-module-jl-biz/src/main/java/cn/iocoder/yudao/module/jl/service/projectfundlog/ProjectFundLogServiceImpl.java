package cn.iocoder.yudao.module.jl.service.projectfundlog;

import cn.iocoder.yudao.module.jl.entity.project.ProjectFund;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFundOnly;
import cn.iocoder.yudao.module.jl.repository.project.ProjectFundRepository;
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
import cn.iocoder.yudao.module.jl.controller.admin.projectfundlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectfundlog.ProjectFundLogMapper;
import cn.iocoder.yudao.module.jl.repository.projectfundlog.ProjectFundLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目款项 Service 实现类
 *
 */
@Service
@Validated
public class ProjectFundLogServiceImpl implements ProjectFundLogService {

    @Resource
    private ProjectFundLogRepository projectFundLogRepository;

    @Resource
    private ProjectFundRepository projectFundRepository;

    @Resource
    private ProjectFundLogMapper projectFundLogMapper;

    @Override
    @Transactional
    public Long createProjectFundLog(ProjectFundLogCreateReqVO createReqVO) {

        ProjectFund projectFund = validateProjectFundExists(createReqVO.getProjectFundId());

        // 插入
        ProjectFundLog projectFundLog = projectFundLogMapper.toEntity(createReqVO);
        projectFundLog.setProjectId(projectFund.getProjectId());
        projectFundLog.setCustomerId(projectFund.getCustomerId());
        projectFundLog.setContractId(projectFund.getContractId());
        projectFundLogRepository.save(projectFundLog);

        //更新projectFund的已收金额
        processReceivedPrice(createReqVO.getProjectFundId());

        // 返回
        return projectFundLog.getId();
    }

    @Override
    @Transactional
    public void updateProjectFundLog(ProjectFundLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectFundLogExists(updateReqVO.getId());

        // 更新
        ProjectFundLog updateObj = projectFundLogMapper.toEntity(updateReqVO);
        projectFundLogRepository.save(updateObj);

        //更新projectFund的已收金额
        processReceivedPrice(updateReqVO.getProjectFundId());
    }

    private void processReceivedPrice(Long updateReqVO) {
        List<ProjectFundLog> allByProjectFundId = projectFundLogRepository.findAllByProjectFundId(updateReqVO);
        int priceSum = 0;
        for (ProjectFundLog log : allByProjectFundId) {
            priceSum += log.getPrice();
        }
        projectFundRepository.updateReceivedPriceById(priceSum, updateReqVO);
    }

    @Override
    public void deleteProjectFundLog(Long id) {
        // 校验存在
        validateProjectFundLogExists(id);
        // 删除
        projectFundLogRepository.deleteById(id);
    }

    private ProjectFund validateProjectFundExists(Long id) {
        Optional<ProjectFund> byId = projectFundRepository.findById(id);
        if (byId.isEmpty()){
            throw exception(PROJECT_FUND_NOT_EXISTS);
        }
        return byId.orElse(null);
    }

    private void validateProjectFundLogExists(Long id) {
        projectFundLogRepository.findById(id).orElseThrow(() -> exception(PROJECT_FUND_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectFundLog> getProjectFundLog(Long id) {
        return projectFundLogRepository.findById(id);
    }

    @Override
    public List<ProjectFundLog> getProjectFundLogList(Collection<Long> ids) {
        return StreamSupport.stream(projectFundLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectFundLog> getProjectFundLogPage(ProjectFundLogPageReqVO pageReqVO, ProjectFundLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectFundLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), pageReqVO.getPrice()));
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
            if(pageReqVO.getReceiptUrl() != null) {
                predicates.add(cb.equal(root.get("receiptUrl"), pageReqVO.getReceiptUrl()));
            }

            if(pageReqVO.getPaidTime() != null) {
                predicates.add(cb.between(root.get("paidTime"), pageReqVO.getPaidTime()[0], pageReqVO.getPaidTime()[1]));
            } 
            if(pageReqVO.getReceiptName() != null) {
                predicates.add(cb.like(root.get("receiptName"), "%" + pageReqVO.getReceiptName() + "%"));
            }

            if(pageReqVO.getPayer() != null) {
                predicates.add(cb.equal(root.get("payer"), pageReqVO.getPayer()));
            }

            if(pageReqVO.getPayee() != null) {
                predicates.add(cb.equal(root.get("payee"), pageReqVO.getPayee()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getProjectFundId() != null) {
                predicates.add(cb.equal(root.get("projectFundId"), pageReqVO.getProjectFundId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectFundLog> page = projectFundLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectFundLog> getProjectFundLogList(ProjectFundLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectFundLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), exportReqVO.getPrice()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getReceiptUrl() != null) {
                predicates.add(cb.equal(root.get("receiptUrl"), exportReqVO.getReceiptUrl()));
            }

            if(exportReqVO.getPaidTime() != null) {
                predicates.add(cb.between(root.get("paidTime"), exportReqVO.getPaidTime()[0], exportReqVO.getPaidTime()[1]));
            } 
            if(exportReqVO.getReceiptName() != null) {
                predicates.add(cb.like(root.get("receiptName"), "%" + exportReqVO.getReceiptName() + "%"));
            }

            if(exportReqVO.getPayer() != null) {
                predicates.add(cb.equal(root.get("payer"), exportReqVO.getPayer()));
            }

            if(exportReqVO.getPayee() != null) {
                predicates.add(cb.equal(root.get("payee"), exportReqVO.getPayee()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getProjectFundId() != null) {
                predicates.add(cb.equal(root.get("projectFundId"), exportReqVO.getProjectFundId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectFundLogRepository.findAll(spec);
    }

    private Sort createSort(ProjectFundLogPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getPrice() != null) {
            orders.add(new Sort.Order(order.getPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "price"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getReceiptUrl() != null) {
            orders.add(new Sort.Order(order.getReceiptUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receiptUrl"));
        }

        if (order.getPaidTime() != null) {
            orders.add(new Sort.Order(order.getPaidTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "paidTime"));
        }

        if (order.getReceiptName() != null) {
            orders.add(new Sort.Order(order.getReceiptName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receiptName"));
        }

        if (order.getPayer() != null) {
            orders.add(new Sort.Order(order.getPayer().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "payer"));
        }

        if (order.getPayee() != null) {
            orders.add(new Sort.Order(order.getPayee().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "payee"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getProjectFundId() != null) {
            orders.add(new Sort.Order(order.getProjectFundId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectFundId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}