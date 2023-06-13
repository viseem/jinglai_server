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
import cn.iocoder.yudao.module.jl.entity.project.Procurement;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProcurementMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目采购单申请 Service 实现类
 *
 */
@Service
@Validated
public class ProcurementServiceImpl implements ProcurementService {

    @Resource
    private ProcurementRepository procurementRepository;

    @Resource
    private ProcurementMapper procurementMapper;

    @Override
    public Long createProcurement(ProcurementCreateReqVO createReqVO) {
        // 插入
        Procurement procurement = procurementMapper.toEntity(createReqVO);
        procurementRepository.save(procurement);
        // 返回
        return procurement.getId();
    }

    @Override
    public void updateProcurement(ProcurementUpdateReqVO updateReqVO) {
        if(updateReqVO.getId() != null) {
            // 存在 id，更新操作
            Long id = updateReqVO.getId();
            // 校验存在
            validateProcurementExists(id);
        } else {
            // 不存在 id，创新新的数据
        }

        // 更新
        Procurement updateObj = procurementMapper.toEntity(updateReqVO);
        updateObj = procurementRepository.save(updateObj);
    }

    @Override
    public void deleteProcurement(Long id) {
        // 校验存在
        validateProcurementExists(id);
        // 删除
        procurementRepository.deleteById(id);
    }

    private void validateProcurementExists(Long id) {
        procurementRepository.findById(id).orElseThrow(() -> exception(PROCUREMENT_NOT_EXISTS));
    }

    @Override
    public Optional<Procurement> getProcurement(Long id) {
        return procurementRepository.findById(id);
    }

    @Override
    public List<Procurement> getProcurementList(Collection<Long> ids) {
        return StreamSupport.stream(procurementRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Procurement> getProcurementPage(ProcurementPageReqVO pageReqVO, ProcurementPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Procurement> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if(pageReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), pageReqVO.getCode()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), pageReqVO.getStartDate()[0], pageReqVO.getStartDate()[1]));
            } 
            if(pageReqVO.getCheckUserId() != null) {
                predicates.add(cb.equal(root.get("checkUserId"), pageReqVO.getCheckUserId()));
            }

            if(pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }

            if(pageReqVO.getReceiverUserId() != null) {
                predicates.add(cb.equal(root.get("receiverUserId"), pageReqVO.getReceiverUserId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<Procurement> page = procurementRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<Procurement> getProcurementList(ProcurementExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Procurement> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if(exportReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), exportReqVO.getCode()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), exportReqVO.getStartDate()[0], exportReqVO.getStartDate()[1]));
            } 
            if(exportReqVO.getCheckUserId() != null) {
                predicates.add(cb.equal(root.get("checkUserId"), exportReqVO.getCheckUserId()));
            }

            if(exportReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), exportReqVO.getAddress()));
            }

            if(exportReqVO.getReceiverUserId() != null) {
                predicates.add(cb.equal(root.get("receiverUserId"), exportReqVO.getReceiverUserId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return procurementRepository.findAll(spec);
    }

    private Sort createSort(ProcurementPageOrder order) {
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

        if (order.getCode() != null) {
            orders.add(new Sort.Order(order.getCode().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "code"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getStartDate() != null) {
            orders.add(new Sort.Order(order.getStartDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "startDate"));
        }

        if (order.getCheckUserId() != null) {
            orders.add(new Sort.Order(order.getCheckUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "checkUserId"));
        }

        if (order.getAddress() != null) {
            orders.add(new Sort.Order(order.getAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        }

        if (order.getReceiverUserId() != null) {
            orders.add(new Sort.Order(order.getReceiverUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receiverUserId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}