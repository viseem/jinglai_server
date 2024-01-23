package cn.iocoder.yudao.module.jl.service.shipwarehouse;

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
import cn.iocoder.yudao.module.jl.controller.admin.shipwarehouse.vo.*;
import cn.iocoder.yudao.module.jl.entity.shipwarehouse.ShipWarehouse;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.shipwarehouse.ShipWarehouseMapper;
import cn.iocoder.yudao.module.jl.repository.shipwarehouse.ShipWarehouseRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 收货仓库 Service 实现类
 *
 */
@Service
@Validated
public class ShipWarehouseServiceImpl implements ShipWarehouseService {

    @Resource
    private ShipWarehouseRepository shipWarehouseRepository;

    @Resource
    private ShipWarehouseMapper shipWarehouseMapper;

    @Override
    public Long createShipWarehouse(ShipWarehouseCreateReqVO createReqVO) {
        // 插入
        ShipWarehouse shipWarehouse = shipWarehouseMapper.toEntity(createReqVO);
        shipWarehouseRepository.save(shipWarehouse);
        // 返回
        return shipWarehouse.getId();
    }

    @Override
    public void updateShipWarehouse(ShipWarehouseUpdateReqVO updateReqVO) {
        // 校验存在
        validateShipWarehouseExists(updateReqVO.getId());
        // 更新
        ShipWarehouse updateObj = shipWarehouseMapper.toEntity(updateReqVO);
        shipWarehouseRepository.save(updateObj);
    }

    @Override
    public void deleteShipWarehouse(Long id) {
        // 校验存在
        validateShipWarehouseExists(id);
        // 删除
        shipWarehouseRepository.deleteById(id);
    }

    private void validateShipWarehouseExists(Long id) {
        shipWarehouseRepository.findById(id).orElseThrow(() -> exception(SHIP_WAREHOUSE_NOT_EXISTS));
    }

    @Override
    public Optional<ShipWarehouse> getShipWarehouse(Long id) {
        return shipWarehouseRepository.findById(id);
    }

    @Override
    public List<ShipWarehouse> getShipWarehouseList(Collection<Long> ids) {
        return StreamSupport.stream(shipWarehouseRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ShipWarehouse> getShipWarehousePage(ShipWarehousePageReqVO pageReqVO, ShipWarehousePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ShipWarehouse> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), pageReqVO.getManagerId()));
            }

            if(pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ShipWarehouse> page = shipWarehouseRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ShipWarehouse> getShipWarehouseList(ShipWarehouseExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ShipWarehouse> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), exportReqVO.getManagerId()));
            }

            if(exportReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), exportReqVO.getAddress()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return shipWarehouseRepository.findAll(spec);
    }

    private Sort createSort(ShipWarehousePageOrder order) {
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

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getManagerId() != null) {
            orders.add(new Sort.Order(order.getManagerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "managerId"));
        }

        if (order.getAddress() != null) {
            orders.add(new Sort.Order(order.getAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}