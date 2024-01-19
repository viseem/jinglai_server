package cn.iocoder.yudao.module.jl.service.cellbase;

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
import cn.iocoder.yudao.module.jl.controller.admin.cellbase.vo.*;
import cn.iocoder.yudao.module.jl.entity.cellbase.CellBase;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.cellbase.CellBaseMapper;
import cn.iocoder.yudao.module.jl.repository.cellbase.CellBaseRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 细胞数据 Service 实现类
 *
 */
@Service
@Validated
public class CellBaseServiceImpl implements CellBaseService {

    @Resource
    private CellBaseRepository cellBaseRepository;

    @Resource
    private CellBaseMapper cellBaseMapper;

    @Override
    public Long createCellBase(CellBaseCreateReqVO createReqVO) {
        // 插入
        CellBase cellBase = cellBaseMapper.toEntity(createReqVO);
        cellBaseRepository.save(cellBase);
        // 返回
        return cellBase.getId();
    }

    @Override
    public void updateCellBase(CellBaseUpdateReqVO updateReqVO) {
        // 校验存在
        validateCellBaseExists(updateReqVO.getId());
        // 更新
        CellBase updateObj = cellBaseMapper.toEntity(updateReqVO);
        cellBaseRepository.save(updateObj);
    }

    @Override
    public void deleteCellBase(Long id) {
        // 校验存在
        validateCellBaseExists(id);
        // 删除
        cellBaseRepository.deleteById(id);
    }

    private void validateCellBaseExists(Long id) {
        cellBaseRepository.findById(id).orElseThrow(() -> exception(CELL_BASE_NOT_EXISTS));
    }

    @Override
    public Optional<CellBase> getCellBase(Long id) {
        return cellBaseRepository.findById(id);
    }

    @Override
    public List<CellBase> getCellBaseList(Collection<Long> ids) {
        return StreamSupport.stream(cellBaseRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CellBase> getCellBasePage(CellBasePageReqVO pageReqVO, CellBasePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CellBase> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), pageReqVO.getQuantity()));
            }

            if(pageReqVO.getGenerationCount() != null) {
                predicates.add(cb.equal(root.get("generationCount"), pageReqVO.getGenerationCount()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CellBase> page = cellBaseRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CellBase> getCellBaseList(CellBaseExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CellBase> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), exportReqVO.getQuantity()));
            }

            if(exportReqVO.getGenerationCount() != null) {
                predicates.add(cb.equal(root.get("generationCount"), exportReqVO.getGenerationCount()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return cellBaseRepository.findAll(spec);
    }

    private Sort createSort(CellBasePageOrder order) {
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

        if (order.getQuantity() != null) {
            orders.add(new Sort.Order(order.getQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quantity"));
        }

        if (order.getGenerationCount() != null) {
            orders.add(new Sort.Order(order.getGenerationCount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "generationCount"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}