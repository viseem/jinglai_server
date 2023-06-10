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
import cn.iocoder.yudao.module.jl.entity.project.SupplySendInItem;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.SupplySendInItemMapper;
import cn.iocoder.yudao.module.jl.repository.project.SupplySendInItemRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 物资寄来单申请明细 Service 实现类
 *
 */
@Service
@Validated
public class SupplySendInItemServiceImpl implements SupplySendInItemService {

    @Resource
    private SupplySendInItemRepository supplySendInItemRepository;

    @Resource
    private SupplySendInItemMapper supplySendInItemMapper;

    @Override
    public Long createSupplySendInItem(SupplySendInItemCreateReqVO createReqVO) {
        // 插入
        SupplySendInItem supplySendInItem = supplySendInItemMapper.toEntity(createReqVO);
        supplySendInItemRepository.save(supplySendInItem);
        // 返回
        return supplySendInItem.getId();
    }

    @Override
    public void updateSupplySendInItem(SupplySendInItemUpdateReqVO updateReqVO) {
        // 校验存在
        validateSupplySendInItemExists(updateReqVO.getId());
        // 更新
        SupplySendInItem updateObj = supplySendInItemMapper.toEntity(updateReqVO);
        supplySendInItemRepository.save(updateObj);
    }

    @Override
    public void deleteSupplySendInItem(Long id) {
        // 校验存在
        validateSupplySendInItemExists(id);
        // 删除
        supplySendInItemRepository.deleteById(id);
    }

    private void validateSupplySendInItemExists(Long id) {
        supplySendInItemRepository.findById(id).orElseThrow(() -> exception(SUPPLY_SEND_IN_ITEM_NOT_EXISTS));
    }

    @Override
    public Optional<SupplySendInItem> getSupplySendInItem(Long id) {
        return supplySendInItemRepository.findById(id);
    }

    @Override
    public List<SupplySendInItem> getSupplySendInItemList(Collection<Long> ids) {
        return StreamSupport.stream(supplySendInItemRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<SupplySendInItem> getSupplySendInItemPage(SupplySendInItemPageReqVO pageReqVO, SupplySendInItemPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<SupplySendInItem> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if(pageReqVO.getSupplySendInId() != null) {
                predicates.add(cb.equal(root.get("supplySendInId"), pageReqVO.getSupplySendInId()));
            }

            if(pageReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), pageReqVO.getProjectSupplyId()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getFeeStandard() != null) {
                predicates.add(cb.equal(root.get("feeStandard"), pageReqVO.getFeeStandard()));
            }

            if(pageReqVO.getUnitFee() != null) {
                predicates.add(cb.equal(root.get("unitFee"), pageReqVO.getUnitFee()));
            }

            if(pageReqVO.getUnitAmount() != null) {
                predicates.add(cb.equal(root.get("unitAmount"), pageReqVO.getUnitAmount()));
            }

            if(pageReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), pageReqVO.getQuantity()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getValidDate() != null) {
                predicates.add(cb.between(root.get("validDate"), pageReqVO.getValidDate()[0], pageReqVO.getValidDate()[1]));
            } 
            if(pageReqVO.getTemperature() != null) {
                predicates.add(cb.equal(root.get("temperature"), pageReqVO.getTemperature()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<SupplySendInItem> page = supplySendInItemRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<SupplySendInItem> getSupplySendInItemList(SupplySendInItemExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<SupplySendInItem> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if(exportReqVO.getSupplySendInId() != null) {
                predicates.add(cb.equal(root.get("supplySendInId"), exportReqVO.getSupplySendInId()));
            }

            if(exportReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), exportReqVO.getProjectSupplyId()));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getFeeStandard() != null) {
                predicates.add(cb.equal(root.get("feeStandard"), exportReqVO.getFeeStandard()));
            }

            if(exportReqVO.getUnitFee() != null) {
                predicates.add(cb.equal(root.get("unitFee"), exportReqVO.getUnitFee()));
            }

            if(exportReqVO.getUnitAmount() != null) {
                predicates.add(cb.equal(root.get("unitAmount"), exportReqVO.getUnitAmount()));
            }

            if(exportReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), exportReqVO.getQuantity()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getValidDate() != null) {
                predicates.add(cb.between(root.get("validDate"), exportReqVO.getValidDate()[0], exportReqVO.getValidDate()[1]));
            } 
            if(exportReqVO.getTemperature() != null) {
                predicates.add(cb.equal(root.get("temperature"), exportReqVO.getTemperature()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return supplySendInItemRepository.findAll(spec);
    }

    private Sort createSort(SupplySendInItemPageOrder order) {
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

        if (order.getSupplySendInId() != null) {
            orders.add(new Sort.Order(order.getSupplySendInId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplySendInId"));
        }

        if (order.getProjectSupplyId() != null) {
            orders.add(new Sort.Order(order.getProjectSupplyId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectSupplyId"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getFeeStandard() != null) {
            orders.add(new Sort.Order(order.getFeeStandard().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "feeStandard"));
        }

        if (order.getUnitFee() != null) {
            orders.add(new Sort.Order(order.getUnitFee().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "unitFee"));
        }

        if (order.getUnitAmount() != null) {
            orders.add(new Sort.Order(order.getUnitAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "unitAmount"));
        }

        if (order.getQuantity() != null) {
            orders.add(new Sort.Order(order.getQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quantity"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getValidDate() != null) {
            orders.add(new Sort.Order(order.getValidDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "validDate"));
        }

        if (order.getTemperature() != null) {
            orders.add(new Sort.Order(order.getTemperature().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "temperature"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}