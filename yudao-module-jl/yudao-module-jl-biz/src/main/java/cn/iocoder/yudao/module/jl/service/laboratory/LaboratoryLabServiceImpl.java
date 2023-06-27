package cn.iocoder.yudao.module.jl.service.laboratory;

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
import cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo.*;
import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryLab;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.laboratory.LaboratoryLabMapper;
import cn.iocoder.yudao.module.jl.repository.laboratory.LaboratoryLabRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 实验室 Service 实现类
 *
 */
@Service
@Validated
public class LaboratoryLabServiceImpl implements LaboratoryLabService {

    @Resource
    private LaboratoryLabRepository laboratoryLabRepository;

    @Resource
    private LaboratoryLabMapper laboratoryLabMapper;

    @Override
    public Long createLaboratoryLab(LaboratoryLabCreateReqVO createReqVO) {
        // 插入
        LaboratoryLab laboratoryLab = laboratoryLabMapper.toEntity(createReqVO);
        laboratoryLabRepository.save(laboratoryLab);
        // 返回
        return laboratoryLab.getId();
    }

    @Override
    public void updateLaboratoryLab(LaboratoryLabUpdateReqVO updateReqVO) {
        // 校验存在
        validateLaboratoryLabExists(updateReqVO.getId());
        // 更新
        LaboratoryLab updateObj = laboratoryLabMapper.toEntity(updateReqVO);
        laboratoryLabRepository.save(updateObj);
    }

    @Override
    public void deleteLaboratoryLab(Long id) {
        // 校验存在
        validateLaboratoryLabExists(id);
        // 删除
        laboratoryLabRepository.deleteById(id);
    }

    private void validateLaboratoryLabExists(Long id) {
        laboratoryLabRepository.findById(id).orElseThrow(() -> exception(LABORATORY_LAB_NOT_EXISTS));
    }

    @Override
    public Optional<LaboratoryLab> getLaboratoryLab(Long id) {
        return laboratoryLabRepository.findById(id);
    }

    @Override
    public List<LaboratoryLab> getLaboratoryLabList(Collection<Long> ids) {
        return StreamSupport.stream(laboratoryLabRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<LaboratoryLab> getLaboratoryLabPage(LaboratoryLabPageReqVO pageReqVO, LaboratoryLabPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<LaboratoryLab> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<LaboratoryLab> page = laboratoryLabRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<LaboratoryLab> getLaboratoryLabList(LaboratoryLabExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<LaboratoryLab> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return laboratoryLabRepository.findAll(spec);
    }

    private Sort createSort(LaboratoryLabPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getUserId() != null) {
            orders.add(new Sort.Order(order.getUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "userId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}