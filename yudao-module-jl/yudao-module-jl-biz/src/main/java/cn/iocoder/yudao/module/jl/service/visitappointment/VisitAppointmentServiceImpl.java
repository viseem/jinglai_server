package cn.iocoder.yudao.module.jl.service.visitappointment;

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
import cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo.*;
import cn.iocoder.yudao.module.jl.entity.visitappointment.VisitAppointment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.visitappointment.VisitAppointmentMapper;
import cn.iocoder.yudao.module.jl.repository.visitappointment.VisitAppointmentRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 晶莱到访预约 Service 实现类
 *
 */
@Service
@Validated
public class VisitAppointmentServiceImpl implements VisitAppointmentService {

    @Resource
    private VisitAppointmentRepository visitAppointmentRepository;

    @Resource
    private VisitAppointmentMapper visitAppointmentMapper;

    @Override
    public Long createVisitAppointment(VisitAppointmentCreateReqVO createReqVO) {
        // 插入
        VisitAppointment visitAppointment = visitAppointmentMapper.toEntity(createReqVO);
        visitAppointmentRepository.save(visitAppointment);
        // 返回
        return visitAppointment.getId();
    }

    @Override
    public void updateVisitAppointment(VisitAppointmentUpdateReqVO updateReqVO) {
        // 校验存在
        validateVisitAppointmentExists(updateReqVO.getId());
        // 更新
        VisitAppointment updateObj = visitAppointmentMapper.toEntity(updateReqVO);
        visitAppointmentRepository.save(updateObj);
    }

    @Override
    public void deleteVisitAppointment(Long id) {
        // 校验存在
        validateVisitAppointmentExists(id);
        // 删除
        visitAppointmentRepository.deleteById(id);
    }

    private void validateVisitAppointmentExists(Long id) {
        visitAppointmentRepository.findById(id).orElseThrow(() -> exception(VISIT_APPOINTMENT_NOT_EXISTS));
    }

    @Override
    public Optional<VisitAppointment> getVisitAppointment(Long id) {
        return visitAppointmentRepository.findById(id);
    }

    @Override
    public List<VisitAppointment> getVisitAppointmentList(Collection<Long> ids) {
        return StreamSupport.stream(visitAppointmentRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<VisitAppointment> getVisitAppointmentPage(VisitAppointmentPageReqVO pageReqVO, VisitAppointmentPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<VisitAppointment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }

            if(pageReqVO.getDeviceId() != null) {
                predicates.add(cb.equal(root.get("deviceId"), pageReqVO.getDeviceId()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getVisitTime() != null) {
                predicates.add(cb.between(root.get("visitTime"), pageReqVO.getVisitTime()[0], pageReqVO.getVisitTime()[1]));
            } 
            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<VisitAppointment> page = visitAppointmentRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<VisitAppointment> getVisitAppointmentList(VisitAppointmentExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<VisitAppointment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }

            if(exportReqVO.getDeviceId() != null) {
                predicates.add(cb.equal(root.get("deviceId"), exportReqVO.getDeviceId()));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getVisitTime() != null) {
                predicates.add(cb.between(root.get("visitTime"), exportReqVO.getVisitTime()[0], exportReqVO.getVisitTime()[1]));
            } 
            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return visitAppointmentRepository.findAll(spec);
    }

    private Sort createSort(VisitAppointmentPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getUserId() != null) {
            orders.add(new Sort.Order(order.getUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "userId"));
        }

        if (order.getDeviceId() != null) {
            orders.add(new Sort.Order(order.getDeviceId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "deviceId"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getVisitTime() != null) {
            orders.add(new Sort.Order(order.getVisitTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "visitTime"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}