package cn.iocoder.yudao.module.jl.service.inventory;

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
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryContainer;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.inventory.InventoryContainerMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.InventoryContainerRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 库管存储容器 Service 实现类
 *
 */
@Service
@Validated
public class InventoryContainerServiceImpl implements InventoryContainerService {

    @Resource
    private InventoryContainerRepository inventoryContainerRepository;

    @Resource
    private InventoryContainerMapper inventoryContainerMapper;

    @Override
    public Long createInventoryContainer(InventoryContainerCreateReqVO createReqVO) {
        // 插入
        InventoryContainer inventoryContainer = inventoryContainerMapper.toEntity(createReqVO);
        inventoryContainerRepository.save(inventoryContainer);
        // 返回
        return inventoryContainer.getId();
    }

    @Override
    public void updateInventoryContainer(InventoryContainerUpdateReqVO updateReqVO) {
        // 校验存在
        validateInventoryContainerExists(updateReqVO.getId());
        // 更新
        InventoryContainer updateObj = inventoryContainerMapper.toEntity(updateReqVO);
        inventoryContainerRepository.save(updateObj);
    }

    @Override
    public void deleteInventoryContainer(Long id) {
        // 校验存在
        validateInventoryContainerExists(id);
        // 删除
        inventoryContainerRepository.deleteById(id);
    }

    private void validateInventoryContainerExists(Long id) {
        inventoryContainerRepository.findById(id).orElseThrow(() -> exception(INVENTORY_CONTAINER_NOT_EXISTS));
    }

    @Override
    public Optional<InventoryContainer> getInventoryContainer(Long id) {
        return inventoryContainerRepository.findById(id);
    }

    @Override
    public List<InventoryContainer> getInventoryContainerList(Collection<Long> ids) {
        return StreamSupport.stream(inventoryContainerRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<InventoryContainer> getInventoryContainerPage(InventoryContainerPageReqVO pageReqVO, InventoryContainerPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<InventoryContainer> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getRoomId() != null) {
                predicates.add(cb.equal(root.get("roomId"), pageReqVO.getRoomId()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getPlace() != null) {
                predicates.add(cb.equal(root.get("place"), pageReqVO.getPlace()));
            }

            if(pageReqVO.getGuardianUserId() != null) {
                predicates.add(cb.equal(root.get("guardianUserId"), pageReqVO.getGuardianUserId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<InventoryContainer> page = inventoryContainerRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<InventoryContainer> getInventoryContainerList(InventoryContainerExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<InventoryContainer> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getRoomId() != null) {
                predicates.add(cb.equal(root.get("roomId"), exportReqVO.getRoomId()));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getPlace() != null) {
                predicates.add(cb.equal(root.get("place"), exportReqVO.getPlace()));
            }

            if(exportReqVO.getGuardianUserId() != null) {
                predicates.add(cb.equal(root.get("guardianUserId"), exportReqVO.getGuardianUserId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return inventoryContainerRepository.findAll(spec);
    }

    private Sort createSort(InventoryContainerPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getRoomId() != null) {
            orders.add(new Sort.Order(order.getRoomId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "roomId"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getPlace() != null) {
            orders.add(new Sort.Order(order.getPlace().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "place"));
        }

        if (order.getGuardianUserId() != null) {
            orders.add(new Sort.Order(order.getGuardianUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "guardianUserId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}