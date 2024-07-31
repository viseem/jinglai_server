package cn.iocoder.yudao.module.jl.service.quotationchangelog;

import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupplyOnly;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectChargeitemRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectSupplyOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.projectquotation.ProjectQuotationRepository;
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

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.quotationchangelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.quotationchangelog.QuotationChangeLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.quotationchangelog.QuotationChangeLogMapper;
import cn.iocoder.yudao.module.jl.repository.quotationchangelog.QuotationChangeLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 报价变更日志 Service 实现类
 *
 */
@Service
@Validated
public class QuotationChangeLogServiceImpl implements QuotationChangeLogService {

    @Resource
    private QuotationChangeLogRepository quotationChangeLogRepository;

    @Resource
    private QuotationChangeLogMapper quotationChangeLogMapper;

    @Resource
    private ProjectQuotationServiceImpl projectQuotationService;

    @Resource
    private ProjectQuotationRepository projectQuotationRepository;

    @Resource
    private ProjectSupplyOnlyRepository projectSupplyOnlyRepository;

    @Resource
    private ProjectCategoryOnlyRepository projectCategoryOnlyRepository;

    @Resource
    private ProjectChargeitemRepository projectChargeitemRepository;

    @Override
    @Transactional
    public Long createQuotationChangeLog(QuotationChangeLogCreateReqVO createReqVO) {
        ProjectQuotation quotation = projectQuotationService.validateProjectQuotationExists(createReqVO.getQuotationId());
        for (ProjectChargeitem projectChargeitem : createReqVO.getChargeList()) {
            projectChargeitem.setProjectId(quotation.getProjectId());
        }
        for (ProjectSupplyOnly projectSupplyOnly : createReqVO.getSupplyList()) {
            projectSupplyOnly.setProjectId(quotation.getProjectId());
        }

        projectSupplyOnlyRepository.saveAll(createReqVO.getSupplyList());
        projectChargeitemRepository.saveAll(createReqVO.getChargeList());
        projectCategoryOnlyRepository.saveAll(createReqVO.getCategoryList());
        projectQuotationRepository.save(createReqVO.getQuotation());
        createReqVO.setProjectId(quotation.getProjectId());
        createReqVO.setCustomerId(quotation.getCustomerId());
        // 插入
        QuotationChangeLog quotationChangeLog = quotationChangeLogMapper.toEntity(createReqVO);
        quotationChangeLogRepository.save(quotationChangeLog);
        // 返回
        return quotationChangeLog.getId();
    }

    @Override
    public void updateQuotationChangeLog(QuotationChangeLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateQuotationChangeLogExists(updateReqVO.getId());
        // 更新
        QuotationChangeLog updateObj = quotationChangeLogMapper.toEntity(updateReqVO);
        quotationChangeLogRepository.save(updateObj);
    }

    @Override
    public void deleteQuotationChangeLog(Long id) {
        // 校验存在
        validateQuotationChangeLogExists(id);
        // 删除
        quotationChangeLogRepository.deleteById(id);
    }

    private void validateQuotationChangeLogExists(Long id) {
        quotationChangeLogRepository.findById(id).orElseThrow(() -> exception(QUOTATION_CHANGE_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<QuotationChangeLog> getQuotationChangeLog(Long id) {
        return quotationChangeLogRepository.findById(id);
    }

    @Override
    public List<QuotationChangeLog> getQuotationChangeLogList(Collection<Long> ids) {
        return StreamSupport.stream(quotationChangeLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<QuotationChangeLog> getQuotationChangeLogPage(QuotationChangeLogPageReqVO pageReqVO, QuotationChangeLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<QuotationChangeLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getQuotationId() != null) {
                predicates.add(cb.equal(root.get("quotationId"), pageReqVO.getQuotationId()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getPlanText() != null) {
                predicates.add(cb.equal(root.get("planText"), pageReqVO.getPlanText()));
            }

            if(pageReqVO.getJsonLogs() != null) {
                predicates.add(cb.equal(root.get("jsonLogs"), pageReqVO.getJsonLogs()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<QuotationChangeLog> page = quotationChangeLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<QuotationChangeLog> getQuotationChangeLogList(QuotationChangeLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<QuotationChangeLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getQutationId() != null) {
                predicates.add(cb.equal(root.get("qutationId"), exportReqVO.getQutationId()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getPlanText() != null) {
                predicates.add(cb.equal(root.get("planText"), exportReqVO.getPlanText()));
            }

            if(exportReqVO.getJsonLogs() != null) {
                predicates.add(cb.equal(root.get("jsonLogs"), exportReqVO.getJsonLogs()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return quotationChangeLogRepository.findAll(spec);
    }

    private Sort createSort(QuotationChangeLogPageOrder order) {
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

        if (order.getQutationId() != null) {
            orders.add(new Sort.Order(order.getQutationId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "qutationId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getPlanText() != null) {
            orders.add(new Sort.Order(order.getPlanText().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "planText"));
        }

        if (order.getJsonLogs() != null) {
            orders.add(new Sort.Order(order.getJsonLogs().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "jsonLogs"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}