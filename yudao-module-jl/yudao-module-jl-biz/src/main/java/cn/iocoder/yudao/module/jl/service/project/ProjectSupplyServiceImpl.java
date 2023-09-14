package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreIn;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreOut;
import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOutItem;
import cn.iocoder.yudao.module.jl.entity.project.*;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Predicate;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectSupplyMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectSupplyRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目中的实验名目的物资项 Service 实现类
 *
 */
@Service
@Validated
public class ProjectSupplyServiceImpl implements ProjectSupplyService {

    @Resource
    private ProjectSupplyRepository projectSupplyRepository;

    @Resource
    private ProjectSupplyMapper projectSupplyMapper;

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @Override
    @Transactional
    public Long createProjectSupply(ProjectSupplyCreateReqVO createReqVO) {
        // 插入
        ProjectSupply projectSupply = projectSupplyMapper.toEntity(createReqVO);

        //如果projectCategoryType等于 account，则根据type和projectId查询是否存在category
        if (createReqVO.getProjectCategoryType().equals("account")){
            ProjectCategory byProjectIdAndType = projectCategoryRepository.findByProjectIdAndType(createReqVO.getProjectId(), createReqVO.getProjectCategoryType());
            //如果byProjectIdAndType等于null，则新增一个ProjectCategory,如果不等于null，则获取id
            if(byProjectIdAndType!=null){
                projectSupply.setProjectCategoryId(byProjectIdAndType.getId());
            }else{
                ProjectCategory projectCategory = new ProjectCategory();
                projectCategory.setProjectId(createReqVO.getProjectId());
                projectCategory.setStage(ProjectCategoryStatusEnums.COMPLETE.getStatus());
                projectCategory.setType(createReqVO.getProjectCategoryType());
                projectCategory.setLabId(-2L);
                projectCategory.setName("出库增减项");
                ProjectCategory save = projectCategoryRepository.save(projectCategory);
                projectSupply.setProjectCategoryId(save.getId());
            }
        }
        projectSupplyRepository.save(projectSupply);

        // 返回
        return projectSupply.getId();
    }

    @Override
    public void updateProjectSupply(ProjectSupplyUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectSupplyExists(updateReqVO.getId());
        // 更新
        ProjectSupply updateObj = projectSupplyMapper.toEntity(updateReqVO);
        projectSupplyRepository.save(updateObj);
    }

    @Override
    public void deleteProjectSupply(Long id) {
        // 校验存在
        validateProjectSupplyExists(id);
        // 删除
        projectSupplyRepository.deleteById(id);
    }

    private void validateProjectSupplyExists(Long id) {
        projectSupplyRepository.findById(id).orElseThrow(() -> exception(PROJECT_SUPPLY_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectSupply> getProjectSupply(Long id) {
        Optional<ProjectSupply> byId = projectSupplyRepository.findById(id);
        byId.ifPresent(this::processSupplyItem);
        return byId;
    }

    @Override
    public List<ProjectSupply> getProjectSupplyList(Collection<Long> ids) {
        return StreamSupport.stream(projectSupplyRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectSupply> getProjectSupplyPage(ProjectSupplyPageReqVO pageReqVO, ProjectSupplyPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectSupply> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if(pageReqVO.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("categoryId"), pageReqVO.getCategoryId()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getSupplyId() != null) {
                predicates.add(cb.equal(root.get("supplyId"), pageReqVO.getSupplyId()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getBrand() != null) {
                predicates.add(cb.like(root.get("brand"), "%" + pageReqVO.getBrand() + "%"));
            }

            if(pageReqVO.getScheduleId() != null) {
                predicates.add(cb.equal(root.get("scheduleId"), pageReqVO.getScheduleId()));
            }

            if(pageReqVO.getFeeStandard() != null) {
                predicates.add(cb.equal(root.get("feeStandard"), pageReqVO.getFeeStandard()));
            }

            if(pageReqVO.getUnitFee() != null) {
                predicates.add(cb.equal(root.get("unitFee"), pageReqVO.getUnitFee()));
            }

            if(pageReqVO.getSource() != null) {
                predicates.add(cb.equal(root.get("source"), pageReqVO.getSource()));
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

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectSupply> page = projectSupplyRepository.findAll(spec, pageable);
        List<ProjectSupply> projectSupplies = page.getContent();
        // 计算物资数量
        // 已入库的
        if (projectSupplies.size() > 0) {
            projectSupplies.forEach(this::processSupplyItem);
        }


        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    private void processSupplyItem(ProjectSupply item) {
        int inedQuantity = 0; // 已入库数量
        int outedQuantity = 0; // 已出库数量
        int procurementedQuantity = 0; //已申请采购的数量

        if (item.getProcurements().size() > 0) {
            procurementedQuantity = item.getProcurements().stream()
                    .mapToInt(ProcurementItem::getQuantity)
                    .sum();
        }
        if (item.getSendIns().size() > 0) {

        }
        if (item.getPickups().size() > 0) {

        }

        if(item.getStoreLogs().size()>0){
            item.setLatestStoreLog(item.getStoreLogs().get(0));

            inedQuantity = item.getStoreLogs().stream()
                    .mapToInt(InventoryStoreIn::getInQuantity)
                    .sum();
        }

        if (item.getOutLogs().size() > 0) {
            outedQuantity += item.getOutLogs().stream()
                    .mapToInt(InventoryStoreOut::getOutQuantity)
                    .sum();
        }
        item.setProcurementedQuantity(procurementedQuantity);
        item.setInedQuantity(inedQuantity);
        item.setOutedQuantity(outedQuantity);
        item.setRemainQuantity(inedQuantity - outedQuantity);
    }

    @Override
    public List<ProjectSupply> getProjectSupplyList(ProjectSupplyExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectSupply> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if(exportReqVO.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("categoryId"), exportReqVO.getCategoryId()));
            }

            if(exportReqVO.getSupplyId() != null) {
                predicates.add(cb.equal(root.get("supplyId"), exportReqVO.getSupplyId()));
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


            return cb.and(predicates.toArray(new Predicate[0]));
        };
        // 执行查询
        List<ProjectSupply> projectSupplies = projectSupplyRepository.findAll(spec);

        return projectSupplies;
    }

    private Sort createSort(ProjectSupplyPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getCategoryId() != null) {
            orders.add(new Sort.Order(order.getCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "categoryId"));
        }

        if (order.getSupplyId() != null) {
            orders.add(new Sort.Order(order.getSupplyId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplyId"));
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


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}