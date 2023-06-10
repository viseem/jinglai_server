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
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryContainerPlace;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.inventory.InventoryContainerPlaceMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.InventoryContainerPlaceRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 库管存储容器位置 Service 实现类
 *
 */
@Service
@Validated
public class InventoryContainerPlaceServiceImpl implements InventoryContainerPlaceService {

    @Resource
    private InventoryContainerPlaceRepository inventoryContainerPlaceRepository;

    @Resource
    private InventoryContainerPlaceMapper inventoryContainerPlaceMapper;

    @Override
    public Long createInventoryContainerPlace(InventoryContainerPlaceCreateReqVO createReqVO) {
        // 插入
        InventoryContainerPlace inventoryContainerPlace = inventoryContainerPlaceMapper.toEntity(createReqVO);
        inventoryContainerPlaceRepository.save(inventoryContainerPlace);
        // 返回
        return inventoryContainerPlace.getId();
    }

    @Override
    public void updateInventoryContainerPlace(InventoryContainerPlaceUpdateReqVO updateReqVO) {
        // 校验存在
        validateInventoryContainerPlaceExists(updateReqVO.getId());
        // 更新
        InventoryContainerPlace updateObj = inventoryContainerPlaceMapper.toEntity(updateReqVO);
        inventoryContainerPlaceRepository.save(updateObj);
    }

    @Override
    public void deleteInventoryContainerPlace(Long id) {
        // 校验存在
        validateInventoryContainerPlaceExists(id);
        // 删除
        inventoryContainerPlaceRepository.deleteById(id);
    }

    private void validateInventoryContainerPlaceExists(Long id) {
        inventoryContainerPlaceRepository.findById(id).orElseThrow(() -> exception(INVENTORY_CONTAINER_PLACE_NOT_EXISTS));
    }

    @Override
    public Optional<InventoryContainerPlace> getInventoryContainerPlace(Long id) {
        return inventoryContainerPlaceRepository.findById(id);
    }

    @Override
    public List<InventoryContainerPlace> getInventoryContainerPlaceList(Collection<Long> ids) {
        return StreamSupport.stream(inventoryContainerPlaceRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<InventoryContainerPlace> getInventoryContainerPlacePage(InventoryContainerPlacePageReqVO pageReqVO, InventoryContainerPlacePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<InventoryContainerPlace> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getContainerId() != null) {
                predicates.add(cb.equal(root.get("containerId"), pageReqVO.getContainerId()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getPlace() != null) {
                predicates.add(cb.equal(root.get("place"), pageReqVO.getPlace()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<InventoryContainerPlace> page = inventoryContainerPlaceRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<InventoryContainerPlace> getInventoryContainerPlaceList(InventoryContainerPlaceExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<InventoryContainerPlace> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getContainerId() != null) {
                predicates.add(cb.equal(root.get("containerId"), exportReqVO.getContainerId()));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getPlace() != null) {
                predicates.add(cb.equal(root.get("place"), exportReqVO.getPlace()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return inventoryContainerPlaceRepository.findAll(spec);
    }

    private Sort createSort(InventoryContainerPlacePageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getContainerId() != null) {
            orders.add(new Sort.Order(order.getContainerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "containerId"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getPlace() != null) {
            orders.add(new Sort.Order(order.getPlace().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "place"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}