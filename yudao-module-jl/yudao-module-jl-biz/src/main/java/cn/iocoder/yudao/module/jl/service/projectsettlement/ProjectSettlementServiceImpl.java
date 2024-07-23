package cn.iocoder.yudao.module.jl.service.projectsettlement;

import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.service.projectquotation.ProjectQuotationServiceImpl;
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
import cn.iocoder.yudao.module.jl.controller.admin.projectsettlement.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectsettlement.ProjectSettlement;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectsettlement.ProjectSettlementMapper;
import cn.iocoder.yudao.module.jl.repository.projectsettlement.ProjectSettlementRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目结算 Service 实现类
 *
 */
@Service
@Validated
public class ProjectSettlementServiceImpl implements ProjectSettlementService {

    @Resource
    private ProjectSettlementRepository projectSettlementRepository;

    @Resource
    private ProjectSettlementMapper projectSettlementMapper;

    @Resource
    private ProjectQuotationServiceImpl projectQuotationService;

    @Override
    public Long createProjectSettlement(ProjectSettlementCreateReqVO createReqVO) {

        ProjectQuotation quotation = projectQuotationService.validateProjectQuotationExists(createReqVO.getQuotationId());
        createReqVO.setProjectId(quotation.getProjectId());
        createReqVO.setCustomerId(quotation.getCustomerId());

        // 插入
        ProjectSettlement projectSettlement = projectSettlementMapper.toEntity(createReqVO);
        projectSettlementRepository.save(projectSettlement);
        // 返回
        return projectSettlement.getId();
    }

    @Override
    public void updateProjectSettlement(ProjectSettlementUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectSettlementExists(updateReqVO.getId());
        // 更新
        ProjectSettlement updateObj = projectSettlementMapper.toEntity(updateReqVO);
        projectSettlementRepository.save(updateObj);
    }

    @Override
    public void deleteProjectSettlement(Long id) {
        // 校验存在
        validateProjectSettlementExists(id);
        // 删除
        projectSettlementRepository.deleteById(id);
    }

    private void validateProjectSettlementExists(Long id) {
        projectSettlementRepository.findById(id).orElseThrow(() -> exception(PROJECT_SETTLEMENT_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectSettlement> getProjectSettlement(Long id) {
        return projectSettlementRepository.findById(id);
    }

    @Override
    public List<ProjectSettlement> getProjectSettlementList(Collection<Long> ids) {
        return StreamSupport.stream(projectSettlementRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectSettlement> getProjectSettlementPage(ProjectSettlementPageReqVO pageReqVO, ProjectSettlementPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectSettlement> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getPaidAmount() != null) {
                predicates.add(cb.equal(root.get("paidAmount"), pageReqVO.getPaidAmount()));
            }

            if(pageReqVO.getReminderDate() != null) {
                predicates.add(cb.between(root.get("reminderDate"), pageReqVO.getReminderDate()[0], pageReqVO.getReminderDate()[1]));
            } 
            if(pageReqVO.getAmount() != null) {
                predicates.add(cb.equal(root.get("amount"), pageReqVO.getAmount()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectSettlement> page = projectSettlementRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectSettlement> getProjectSettlementList(ProjectSettlementExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectSettlement> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getPaidAmount() != null) {
                predicates.add(cb.equal(root.get("paidAmount"), exportReqVO.getPaidAmount()));
            }

            if(exportReqVO.getReminderDate() != null) {
                predicates.add(cb.between(root.get("reminderDate"), exportReqVO.getReminderDate()[0], exportReqVO.getReminderDate()[1]));
            } 
            if(exportReqVO.getAmount() != null) {
                predicates.add(cb.equal(root.get("amount"), exportReqVO.getAmount()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectSettlementRepository.findAll(spec);
    }

    private Sort createSort(ProjectSettlementPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getPaidAmount() != null) {
            orders.add(new Sort.Order(order.getPaidAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "paidAmount"));
        }

        if (order.getReminderDate() != null) {
            orders.add(new Sort.Order(order.getReminderDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "reminderDate"));
        }

        if (order.getAmount() != null) {
            orders.add(new Sort.Order(order.getAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "amount"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}