package cn.iocoder.yudao.module.jl.service.project;

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
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplyLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.SupplyLogMapper;
import cn.iocoder.yudao.module.jl.repository.project.SupplyLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目物资变更日志 Service 实现类
 *
 */
@Service
@Validated
public class SupplyLogServiceImpl implements SupplyLogService {

    @Resource
    private SupplyLogRepository supplyLogRepository;

    @Resource
    private SupplyLogMapper supplyLogMapper;

    @Override
    public Long createSupplyLog(SupplyLogCreateReqVO createReqVO) {
        // 插入
        SupplyLog supplyLog = supplyLogMapper.toEntity(createReqVO);
        supplyLogRepository.save(supplyLog);
        // 返回
        return supplyLog.getId();
    }

    @Override
    public void updateSupplyLog(SupplyLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateSupplyLogExists(updateReqVO.getId());
        // 更新
        SupplyLog updateObj = supplyLogMapper.toEntity(updateReqVO);
        supplyLogRepository.save(updateObj);
    }

    @Override
    public void deleteSupplyLog(Long id) {
        // 校验存在
        validateSupplyLogExists(id);
        // 删除
        supplyLogRepository.deleteById(id);
    }

    private void validateSupplyLogExists(Long id) {
        supplyLogRepository.findById(id).orElseThrow(() -> exception(SUPPLY_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<SupplyLog> getSupplyLog(Long id) {
        return supplyLogRepository.findById(id);
    }

    @Override
    public List<SupplyLog> getSupplyLogList(Collection<Long> ids) {
        return StreamSupport.stream(supplyLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<SupplyLog> getSupplyLogPage(SupplyLogPageReqVO pageReqVO, SupplyLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<SupplyLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), pageReqVO.getRefId()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getLog() != null) {
                predicates.add(cb.equal(root.get("log"), pageReqVO.getLog()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<SupplyLog> page = supplyLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<SupplyLog> getSupplyLogList(SupplyLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<SupplyLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), exportReqVO.getRefId()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getLog() != null) {
                predicates.add(cb.equal(root.get("log"), exportReqVO.getLog()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return supplyLogRepository.findAll(spec);
    }

    private Sort createSort(SupplyLogPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getRefId() != null) {
            orders.add(new Sort.Order(order.getRefId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refId"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getLog() != null) {
            orders.add(new Sort.Order(order.getLog().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "log"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}