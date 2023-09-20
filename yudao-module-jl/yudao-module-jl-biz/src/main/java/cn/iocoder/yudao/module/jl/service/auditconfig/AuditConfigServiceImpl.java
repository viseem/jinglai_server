package cn.iocoder.yudao.module.jl.service.auditconfig;

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
import cn.iocoder.yudao.module.jl.controller.admin.auditconfig.vo.*;
import cn.iocoder.yudao.module.jl.entity.auditconfig.AuditConfig;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.auditconfig.AuditConfigMapper;
import cn.iocoder.yudao.module.jl.repository.auditconfig.AuditConfigRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 审批配置表  Service 实现类
 *
 */
@Service
@Validated
public class AuditConfigServiceImpl implements AuditConfigService {

    @Resource
    private AuditConfigRepository auditConfigRepository;

    @Resource
    private AuditConfigMapper auditConfigMapper;

    @Override
    public Long createAuditConfig(AuditConfigCreateReqVO createReqVO) {
        // 插入
        AuditConfig auditConfig = auditConfigMapper.toEntity(createReqVO);
        auditConfigRepository.save(auditConfig);
        // 返回
        return auditConfig.getId();
    }

    @Override
    public void updateAuditConfig(AuditConfigUpdateReqVO updateReqVO) {
        // 校验存在
        validateAuditConfigExists(updateReqVO.getId());
        // 更新
        AuditConfig updateObj = auditConfigMapper.toEntity(updateReqVO);
        auditConfigRepository.save(updateObj);
    }

    @Override
    public void deleteAuditConfig(Long id) {
        // 校验存在
        validateAuditConfigExists(id);
        // 删除
        auditConfigRepository.deleteById(id);
    }

    private void validateAuditConfigExists(Long id) {
        auditConfigRepository.findById(id).orElseThrow(() -> exception(AUDIT_CONFIG_NOT_EXISTS));
    }

    @Override
    public Optional<AuditConfig> getAuditConfig(Long id) {
        return auditConfigRepository.findById(id);
    }

    @Override
    public List<AuditConfig> getAuditConfigList(Collection<Long> ids) {
        return StreamSupport.stream(auditConfigRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<AuditConfig> getAuditConfigPage(AuditConfigPageReqVO pageReqVO, AuditConfigPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<AuditConfig> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getRoute() != null) {
                predicates.add(cb.equal(root.get("route"), pageReqVO.getRoute()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getNeedAudit() != null) {
                predicates.add(cb.equal(root.get("needAudit"), pageReqVO.getNeedAudit()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<AuditConfig> page = auditConfigRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<AuditConfig> getAuditConfigList(AuditConfigExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<AuditConfig> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getRoute() != null) {
                predicates.add(cb.equal(root.get("route"), exportReqVO.getRoute()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getNeedAudit() != null) {
                predicates.add(cb.equal(root.get("needAudit"), exportReqVO.getNeedAudit()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return auditConfigRepository.findAll(spec);
    }

    private Sort createSort(AuditConfigPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getRoute() != null) {
            orders.add(new Sort.Order(order.getRoute().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "route"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getNeedAudit() != null) {
            orders.add(new Sort.Order(order.getNeedAudit().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "needAudit"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}