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
import cn.iocoder.yudao.module.jl.entity.template.TemplateContract;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.template.TemplateContractMapper;
import cn.iocoder.yudao.module.jl.repository.template.TemplateContractRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 合同模板 Service 实现类
 *
 */
@Service
@Validated
public class TemplateContractServiceImpl implements TemplateContractService {

    @Resource
    private TemplateContractRepository templateContractRepository;

    @Resource
    private TemplateContractMapper templateContractMapper;

    @Override
    public Long createTemplateContract(TemplateContractCreateReqVO createReqVO) {
        // 插入
        TemplateContract templateContract = templateContractMapper.toEntity(createReqVO);
        templateContractRepository.save(templateContract);
        // 返回
        return templateContract.getId();
    }

    @Override
    public void updateTemplateContract(TemplateContractUpdateReqVO updateReqVO) {
        // 校验存在
        validateTemplateContractExists(updateReqVO.getId());
        // 更新
        TemplateContract updateObj = templateContractMapper.toEntity(updateReqVO);
        templateContractRepository.save(updateObj);
    }

    @Override
    public void deleteTemplateContract(Long id) {
        // 校验存在
        validateTemplateContractExists(id);
        // 删除
        templateContractRepository.deleteById(id);
    }

    private void validateTemplateContractExists(Long id) {
        templateContractRepository.findById(id).orElseThrow(() -> exception(TEMPLATE_CONTRACT_NOT_EXISTS));
    }

    @Override
    public Optional<TemplateContract> getTemplateContract(Long id) {
        return templateContractRepository.findById(id);
    }

    @Override
    public List<TemplateContract> getTemplateContractList(Collection<Long> ids) {
        return StreamSupport.stream(templateContractRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<TemplateContract> getTemplateContractPage(TemplateContractPageReqVO pageReqVO, TemplateContractPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<TemplateContract> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            if(pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getUseWay() != null) {
                predicates.add(cb.equal(root.get("useWay"), pageReqVO.getUseWay()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<TemplateContract> page = templateContractRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<TemplateContract> getTemplateContractList(TemplateContractExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<TemplateContract> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + exportReqVO.getFileName() + "%"));
            }

            if(exportReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), exportReqVO.getFileUrl()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getUseWay() != null) {
                predicates.add(cb.equal(root.get("useWay"), exportReqVO.getUseWay()));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return templateContractRepository.findAll(spec);
    }

    private Sort createSort(TemplateContractPageOrder order) {
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

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getUseWay() != null) {
            orders.add(new Sort.Order(order.getUseWay().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "useWay"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}