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
import cn.iocoder.yudao.module.jl.entity.project.ProcurementShipment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProcurementShipmentMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementShipmentRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目采购单物流信息 Service 实现类
 *
 */
@Service
@Validated
public class ProcurementShipmentServiceImpl implements ProcurementShipmentService {

    @Resource
    private ProcurementShipmentRepository procurementShipmentRepository;

    @Resource
    private ProcurementShipmentMapper procurementShipmentMapper;

    @Override
    public Long createProcurementShipment(ProcurementShipmentCreateReqVO createReqVO) {
        // 插入
        ProcurementShipment procurementShipment = procurementShipmentMapper.toEntity(createReqVO);
        procurementShipmentRepository.save(procurementShipment);
        // 返回
        return procurementShipment.getId();
    }

    @Override
    public void updateProcurementShipment(ProcurementShipmentUpdateReqVO updateReqVO) {
        // 校验存在
        validateProcurementShipmentExists(updateReqVO.getId());
        // 更新
        ProcurementShipment updateObj = procurementShipmentMapper.toEntity(updateReqVO);
        procurementShipmentRepository.save(updateObj);
    }

    @Override
    public void deleteProcurementShipment(Long id) {
        // 校验存在
        validateProcurementShipmentExists(id);
        // 删除
        procurementShipmentRepository.deleteById(id);
    }

    private void validateProcurementShipmentExists(Long id) {
        procurementShipmentRepository.findById(id).orElseThrow(() -> exception(PROCUREMENT_SHIPMENT_NOT_EXISTS));
    }

    @Override
    public Optional<ProcurementShipment> getProcurementShipment(Long id) {
        return procurementShipmentRepository.findById(id);
    }

    @Override
    public List<ProcurementShipment> getProcurementShipmentList(Collection<Long> ids) {
        return StreamSupport.stream(procurementShipmentRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProcurementShipment> getProcurementShipmentPage(ProcurementShipmentPageReqVO pageReqVO, ProcurementShipmentPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProcurementShipment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProcurementId() != null) {
                predicates.add(cb.equal(root.get("procurementId"), pageReqVO.getProcurementId()));
            }

            if(pageReqVO.getShipmentNumber() != null) {
                predicates.add(cb.equal(root.get("shipmentNumber"), pageReqVO.getShipmentNumber()));
            }

            if(pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            if(pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getExpectArrivalTime() != null) {
                predicates.add(cb.between(root.get("expectArrivalTime"), pageReqVO.getExpectArrivalTime()[0], pageReqVO.getExpectArrivalTime()[1]));
            } 

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProcurementShipment> page = procurementShipmentRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProcurementShipment> getProcurementShipmentList(ProcurementShipmentExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProcurementShipment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProcurementId() != null) {
                predicates.add(cb.equal(root.get("procurementId"), exportReqVO.getProcurementId()));
            }

            if(exportReqVO.getShipmentNumber() != null) {
                predicates.add(cb.equal(root.get("shipmentNumber"), exportReqVO.getShipmentNumber()));
            }

            if(exportReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + exportReqVO.getFileName() + "%"));
            }

            if(exportReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), exportReqVO.getFileUrl()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getExpectArrivalTime() != null) {
                predicates.add(cb.between(root.get("expectArrivalTime"), exportReqVO.getExpectArrivalTime()[0], exportReqVO.getExpectArrivalTime()[1]));
            } 

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return procurementShipmentRepository.findAll(spec);
    }

    private Sort createSort(ProcurementShipmentPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProcurementId() != null) {
            orders.add(new Sort.Order(order.getProcurementId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "procurementId"));
        }

        if (order.getShipmentNumber() != null) {
            orders.add(new Sort.Order(order.getShipmentNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "shipmentNumber"));
        }

        if (order.getFileName() != null) {
            orders.add(new Sort.Order(order.getFileName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileName"));
        }

        if (order.getFileUrl() != null) {
            orders.add(new Sort.Order(order.getFileUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileUrl"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getExpectArrivalTime() != null) {
            orders.add(new Sort.Order(order.getExpectArrivalTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "expectArrivalTime"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}