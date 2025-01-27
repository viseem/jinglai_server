package cn.iocoder.yudao.module.jl.service.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreOut;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import cn.iocoder.yudao.module.jl.enums.InventorySupplyOutApprovalEnums;
import cn.iocoder.yudao.module.jl.mapper.inventory.SupplyOutItemMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.InventoryStoreOutRepository;
import cn.iocoder.yudao.module.jl.repository.inventory.SupplyOutItemRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectSupplyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
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
import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOut;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.inventory.SupplyOutMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.SupplyOutRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 出库申请 Service 实现类
 */
@Service
@Validated
public class SupplyOutServiceImpl implements SupplyOutService {

    @Resource
    private SupplyOutRepository supplyOutRepository;

    @Resource
    private InventoryStoreOutRepository inventoryStoreOutRepository;

    @Resource
    private SupplyOutItemRepository supplyOutItemRepository;

    @Resource
    private SupplyOutMapper supplyOutMapper;

    @Resource
    private SupplyOutItemMapper supplyOutItemMapper;
    private final ProjectSupplyRepository projectSupplyRepository;

    public SupplyOutServiceImpl(ProjectSupplyRepository projectSupplyRepository) {
        this.projectSupplyRepository = projectSupplyRepository;
    }

    @Override
    public Long createSupplyOut(SupplyOutCreateReqVO createReqVO) {
        // 插入
        SupplyOut supplyOut = supplyOutMapper.toEntity(createReqVO);
        supplyOutRepository.save(supplyOut);
        // 返回
        return supplyOut.getId();
    }



    @Override
    @Transactional
    public Long saveSupplyOut(SupplyOutSaveReqVO saveReqVO) {

        Long supplyOutId = saveReqVO.getId();
        Long creator;
        //存在 id，校验存在
        if (supplyOutId != null) {
            SupplyOut supplyOut = validateSupplyOutExists(supplyOutId);
            creator = supplyOut.getCreator();
        } else {
            creator = null;
        }

        // 创建、或更新主表
        SupplyOut supplyOut = supplyOutMapper.toEntity(saveReqVO);
        supplyOut = supplyOutRepository.save(supplyOut);
        Long saveSupplyId = supplyOut.getId();

        //保存新的items
        supplyOutItemRepository.saveAll(saveReqVO.getItems().stream().map(item -> {
            item.setSupplyOutId(saveSupplyId);
            return item;
        }).collect(Collectors.toList()));

        //保存出库日志 supplyStoreOut
        if(supplyOutId != null&&supplyOutId>0&& Objects.equals(saveReqVO.getStatus(), InventorySupplyOutApprovalEnums.ACCEPT.getStatus())){
            inventoryStoreOutRepository.saveAll(saveReqVO.getItems().stream().map(item -> {
                InventoryStoreOut inventoryStoreOut = new InventoryStoreOut();
                inventoryStoreOut.setOutQuantity(item.getOutQuantity());
                inventoryStoreOut.setMark(item.getMark());
                inventoryStoreOut.setProjectSupplyId(item.getProjectSupplyId());
                inventoryStoreOut.setRefId(supplyOutId);
                inventoryStoreOut.setRefItemId(item.getId());
                inventoryStoreOut.setApplyUser(creator);
                return inventoryStoreOut;
            }).collect(Collectors.toList()));
        }

        // 返回
        return supplyOut.getId();
    }

    @Override
    public void updateSupplyOut(SupplyOutUpdateReqVO updateReqVO) {
        // 校验存在
        validateSupplyOutExists(updateReqVO.getId());
        // 更新
        SupplyOut updateObj = supplyOutMapper.toEntity(updateReqVO);
        supplyOutRepository.save(updateObj);
    }

    @Override
    public void deleteSupplyOut(Long id) {
        // 校验存在
        validateSupplyOutExists(id);
        // 删除
        supplyOutRepository.deleteById(id);
    }

    private SupplyOut validateSupplyOutExists(Long id) {
        Optional<SupplyOut> byId = supplyOutRepository.findById(id);
        if (!byId.isPresent()) {
            throw exception(SUPPLY_OUT_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<SupplyOut> getSupplyOut(Long id) {
        return supplyOutRepository.findById(id);
    }

    @Override
    public List<SupplyOut> getSupplyOutList(Collection<Long> ids) {
        return StreamSupport.stream(supplyOutRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<SupplyOut> getSupplyOutPage(SupplyOutPageReqVO pageReqVO, SupplyOutPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<SupplyOut> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if (pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if (pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if (pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<SupplyOut> page = supplyOutRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<SupplyOut> getSupplyOutList(SupplyOutExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<SupplyOut> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if (exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if (exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if (exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return supplyOutRepository.findAll(spec);
    }

    private Sort createSort(SupplyOutPageOrder order) {
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

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}