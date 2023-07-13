package cn.iocoder.yudao.module.jl.service.template;

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
import cn.iocoder.yudao.module.jl.controller.admin.template.vo.*;
import cn.iocoder.yudao.module.jl.entity.template.TemplateProjectPlan;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.template.TemplateProjectPlanMapper;
import cn.iocoder.yudao.module.jl.repository.template.TemplateProjectPlanRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目方案模板 Service 实现类
 *
 */
@Service
@Validated
public class TemplateProjectPlanServiceImpl implements TemplateProjectPlanService {

    @Resource
    private TemplateProjectPlanRepository templateProjectPlanRepository;

    @Resource
    private TemplateProjectPlanMapper templateProjectPlanMapper;

    @Override
    public Long createTemplateProjectPlan(TemplateProjectPlanCreateReqVO createReqVO) {
        // 插入
        TemplateProjectPlan templateProjectPlan = templateProjectPlanMapper.toEntity(createReqVO);
        templateProjectPlanRepository.save(templateProjectPlan);
        // 返回
        return templateProjectPlan.getId();
    }

    @Override
    public void updateTemplateProjectPlan(TemplateProjectPlanUpdateReqVO updateReqVO) {
        // 校验存在
        validateTemplateProjectPlanExists(updateReqVO.getId());
        // 更新
        TemplateProjectPlan updateObj = templateProjectPlanMapper.toEntity(updateReqVO);
        templateProjectPlanRepository.save(updateObj);
    }

    @Override
    public void deleteTemplateProjectPlan(Long id) {
        // 校验存在
        validateTemplateProjectPlanExists(id);
        // 删除
        templateProjectPlanRepository.deleteById(id);
    }

    private void validateTemplateProjectPlanExists(Long id) {
        templateProjectPlanRepository.findById(id).orElseThrow(() -> exception(TEMPLATE_PROJECT_PLAN_NOT_EXISTS));
    }

    @Override
    public Optional<TemplateProjectPlan> getTemplateProjectPlan(Long id) {
        return templateProjectPlanRepository.findById(id);
    }

    @Override
    public List<TemplateProjectPlan> getTemplateProjectPlanList(Collection<Long> ids) {
        return StreamSupport.stream(templateProjectPlanRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<TemplateProjectPlan> getTemplateProjectPlanPage(TemplateProjectPlanPageReqVO pageReqVO, TemplateProjectPlanPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<TemplateProjectPlan> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            if(pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<TemplateProjectPlan> page = templateProjectPlanRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<TemplateProjectPlan> getTemplateProjectPlanList(TemplateProjectPlanExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<TemplateProjectPlan> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + exportReqVO.getFileName() + "%"));
            }

            if(exportReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), exportReqVO.getFileUrl()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return templateProjectPlanRepository.findAll(spec);
    }

    private Sort createSort(TemplateProjectPlanPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getFileName() != null) {
            orders.add(new Sort.Order(order.getFileName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileName"));
        }

        if (order.getFileUrl() != null) {
            orders.add(new Sort.Order(order.getFileUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileUrl"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}