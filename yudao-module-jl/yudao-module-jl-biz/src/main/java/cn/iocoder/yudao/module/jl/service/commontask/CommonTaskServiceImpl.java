package cn.iocoder.yudao.module.jl.service.commontask;

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
import cn.iocoder.yudao.module.jl.controller.admin.commontask.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontask.CommonTask;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.commontask.CommonTaskMapper;
import cn.iocoder.yudao.module.jl.repository.commontask.CommonTaskRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 通用任务 Service 实现类
 *
 */
@Service
@Validated
public class CommonTaskServiceImpl implements CommonTaskService {

    @Resource
    private CommonTaskRepository commonTaskRepository;

    @Resource
    private CommonTaskMapper commonTaskMapper;

    @Override
    public Long createCommonTask(CommonTaskCreateReqVO createReqVO) {
        // 插入
        CommonTask commonTask = commonTaskMapper.toEntity(createReqVO);
        commonTaskRepository.save(commonTask);
        // 返回
        return commonTask.getId();
    }

    @Override
    public void updateCommonTask(CommonTaskUpdateReqVO updateReqVO) {
        // 校验存在
        validateCommonTaskExists(updateReqVO.getId());
        // 更新
        CommonTask updateObj = commonTaskMapper.toEntity(updateReqVO);
        commonTaskRepository.save(updateObj);
    }

    @Override
    public void deleteCommonTask(Long id) {
        // 校验存在
        validateCommonTaskExists(id);
        // 删除
        commonTaskRepository.deleteById(id);
    }

    private void validateCommonTaskExists(Long id) {
        commonTaskRepository.findById(id).orElseThrow(() -> exception(COMMON_TASK_NOT_EXISTS));
    }

    @Override
    public Optional<CommonTask> getCommonTask(Long id) {
        return commonTaskRepository.findById(id);
    }

    @Override
    public List<CommonTask> getCommonTaskList(Collection<Long> ids) {
        return StreamSupport.stream(commonTaskRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CommonTask> getCommonTaskPage(CommonTaskPageReqVO pageReqVO, CommonTaskPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CommonTask> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), pageReqVO.getStartDate()[0], pageReqVO.getStartDate()[1]));
            } 
            if(pageReqVO.getEndDate() != null) {
                predicates.add(cb.between(root.get("endDate"), pageReqVO.getEndDate()[0], pageReqVO.getEndDate()[1]));
            } 
            if(pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }

            if(pageReqVO.getFocusIds() != null) {
                predicates.add(cb.equal(root.get("focusIds"), pageReqVO.getFocusIds()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getChargeitemId() != null) {
                predicates.add(cb.equal(root.get("chargeitemId"), pageReqVO.getChargeitemId()));
            }

            if(pageReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), pageReqVO.getProductId()));
            }

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if(pageReqVO.getQuotationId() != null) {
                predicates.add(cb.equal(root.get("quotationId"), pageReqVO.getQuotationId()));
            }

            if(pageReqVO.getProjectName() != null) {
                predicates.add(cb.like(root.get("projectName"), "%" + pageReqVO.getProjectName() + "%"));
            }

            if(pageReqVO.getCustomerName() != null) {
                predicates.add(cb.like(root.get("customerName"), "%" + pageReqVO.getCustomerName() + "%"));
            }

            if(pageReqVO.getChargeitemName() != null) {
                predicates.add(cb.like(root.get("chargeitemName"), "%" + pageReqVO.getChargeitemName() + "%"));
            }

            if(pageReqVO.getProductName() != null) {
                predicates.add(cb.like(root.get("productName"), "%" + pageReqVO.getProductName() + "%"));
            }

            if(pageReqVO.getProjectCategoryName() != null) {
                predicates.add(cb.like(root.get("projectCategoryName"), "%" + pageReqVO.getProjectCategoryName() + "%"));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getLevel() != null) {
                predicates.add(cb.equal(root.get("level"), pageReqVO.getLevel()));
            }

            if(pageReqVO.getAssignUserId() != null) {
                predicates.add(cb.equal(root.get("assignUserId"), pageReqVO.getAssignUserId()));
            }

            if(pageReqVO.getAssignUserName() != null) {
                predicates.add(cb.like(root.get("assignUserName"), "%" + pageReqVO.getAssignUserName() + "%"));
            }

            if(pageReqVO.getLabIds() != null) {
                predicates.add(cb.equal(root.get("labIds"), pageReqVO.getLabIds()));
            }

            if(pageReqVO.getSort() != null) {
                predicates.add(cb.equal(root.get("sort"), pageReqVO.getSort()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CommonTask> page = commonTaskRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CommonTask> getCommonTaskList(CommonTaskExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CommonTask> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), exportReqVO.getStartDate()[0], exportReqVO.getStartDate()[1]));
            } 
            if(exportReqVO.getEndDate() != null) {
                predicates.add(cb.between(root.get("endDate"), exportReqVO.getEndDate()[0], exportReqVO.getEndDate()[1]));
            } 
            if(exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }

            if(exportReqVO.getFocusIds() != null) {
                predicates.add(cb.equal(root.get("focusIds"), exportReqVO.getFocusIds()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getChargeitemId() != null) {
                predicates.add(cb.equal(root.get("chargeitemId"), exportReqVO.getChargeitemId()));
            }

            if(exportReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), exportReqVO.getProductId()));
            }

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if(exportReqVO.getQuotationId() != null) {
                predicates.add(cb.equal(root.get("quotationId"), exportReqVO.getQuotationId()));
            }

            if(exportReqVO.getProjectName() != null) {
                predicates.add(cb.like(root.get("projectName"), "%" + exportReqVO.getProjectName() + "%"));
            }

            if(exportReqVO.getCustomerName() != null) {
                predicates.add(cb.like(root.get("customerName"), "%" + exportReqVO.getCustomerName() + "%"));
            }

            if(exportReqVO.getChargeitemName() != null) {
                predicates.add(cb.like(root.get("chargeitemName"), "%" + exportReqVO.getChargeitemName() + "%"));
            }

            if(exportReqVO.getProductName() != null) {
                predicates.add(cb.like(root.get("productName"), "%" + exportReqVO.getProductName() + "%"));
            }

            if(exportReqVO.getProjectCategoryName() != null) {
                predicates.add(cb.like(root.get("projectCategoryName"), "%" + exportReqVO.getProjectCategoryName() + "%"));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getLevel() != null) {
                predicates.add(cb.equal(root.get("level"), exportReqVO.getLevel()));
            }

            if(exportReqVO.getAssignUserId() != null) {
                predicates.add(cb.equal(root.get("assignUserId"), exportReqVO.getAssignUserId()));
            }

            if(exportReqVO.getAssignUserName() != null) {
                predicates.add(cb.like(root.get("assignUserName"), "%" + exportReqVO.getAssignUserName() + "%"));
            }

            if(exportReqVO.getLabIds() != null) {
                predicates.add(cb.equal(root.get("labIds"), exportReqVO.getLabIds()));
            }

            if(exportReqVO.getSort() != null) {
                predicates.add(cb.equal(root.get("sort"), exportReqVO.getSort()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return commonTaskRepository.findAll(spec);
    }

    private Sort createSort(CommonTaskPageOrder order) {
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

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getStartDate() != null) {
            orders.add(new Sort.Order(order.getStartDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "startDate"));
        }

        if (order.getEndDate() != null) {
            orders.add(new Sort.Order(order.getEndDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "endDate"));
        }

        if (order.getUserId() != null) {
            orders.add(new Sort.Order(order.getUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "userId"));
        }

        if (order.getFocusIds() != null) {
            orders.add(new Sort.Order(order.getFocusIds().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "focusIds"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getChargeitemId() != null) {
            orders.add(new Sort.Order(order.getChargeitemId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "chargeitemId"));
        }

        if (order.getProductId() != null) {
            orders.add(new Sort.Order(order.getProductId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productId"));
        }

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getQuotationId() != null) {
            orders.add(new Sort.Order(order.getQuotationId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quotationId"));
        }

        if (order.getProjectName() != null) {
            orders.add(new Sort.Order(order.getProjectName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectName"));
        }

        if (order.getCustomerName() != null) {
            orders.add(new Sort.Order(order.getCustomerName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerName"));
        }

        if (order.getChargeitemName() != null) {
            orders.add(new Sort.Order(order.getChargeitemName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "chargeitemName"));
        }

        if (order.getProductName() != null) {
            orders.add(new Sort.Order(order.getProductName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productName"));
        }

        if (order.getProjectCategoryName() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryName"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getLevel() != null) {
            orders.add(new Sort.Order(order.getLevel().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "level"));
        }

        if (order.getAssignUserId() != null) {
            orders.add(new Sort.Order(order.getAssignUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "assignUserId"));
        }

        if (order.getAssignUserName() != null) {
            orders.add(new Sort.Order(order.getAssignUserName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "assignUserName"));
        }

        if (order.getLabIds() != null) {
            orders.add(new Sort.Order(order.getLabIds().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "labIds"));
        }

        if (order.getSort() != null) {
            orders.add(new Sort.Order(order.getSort().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sort"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}