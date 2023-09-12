package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryApproval;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectChargeitemMapper;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectSupplyMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectChargeitemRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectSopRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectSupplyRepository;
import cn.iocoder.yudao.module.jl.repository.projectcategory.ProjectCategoryAttachmentRepository;
import cn.iocoder.yudao.module.jl.repository.projectcategory.ProjectCategorySupplierRepository;
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
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectCategoryMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目的实验名目 Service 实现类
 *
 */
@Service
@Validated
public class ProjectCategoryServiceImpl implements ProjectCategoryService {

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @Resource
    private ProjectCategoryMapper projectCategoryMapper;

    @Resource
    private ProjectSupplyRepository projectSupplyRepository;

    @Resource
    private ProjectChargeitemRepository projectChargeitemRepository;

    @Resource
    private ProjectSopRepository projectSopRepository;

    @Resource
    private ProjectCategoryAttachmentRepository projectCategoryAttachmentRepository;

    @Resource
    private ProjectChargeitemMapper projectChargeitemMapper;

    @Resource
    private ProjectSupplyMapper projectSupplyMapper;
    private final ProjectCategorySupplierRepository projectCategorySupplierRepository;

    public ProjectCategoryServiceImpl(ProjectCategorySupplierRepository projectCategorySupplierRepository) {
        this.projectCategorySupplierRepository = projectCategorySupplierRepository;
    }

    @Override
    public Long createProjectCategory(ProjectCategoryCreateReqVO createReqVO) {
        // 插入
        ProjectCategory projectCategory = projectCategoryMapper.toEntity(createReqVO);
        projectCategoryRepository.save(projectCategory);
        // 返回
        return projectCategory.getId();
    }

    /** 更新项目的实验名目的收费项和物资项
     * @param updateReqVO
     * @return
     */
    @Override
    public Boolean updateSupplyAndChargeItem(ProjectCategoryUpdateSupplyAndChargeItemReqVO updateReqVO) {
        List<ProjectChargeitem> chargeList = updateReqVO.getChargeList();
        // 遍历 chargeList ，更新数据
        chargeList.forEach(chargeItem -> {
            if(chargeItem.getId() == null) {
                chargeItem.setIsAppend(1);
                chargeItem.setCategoryId(updateReqVO.getCategoryId());
                chargeItem.setProjectCategoryId(updateReqVO.getProjectCategoryId());
                chargeItem.setScheduleId(updateReqVO.getScheduleId());
                chargeItem.setQuantity(0);
                chargeItem.setChargeItemId(chargeItem.getChargeItemId());
                chargeItem.setProjectId(updateReqVO.getProjectId());
            }

        });
        projectChargeitemRepository.saveAll(chargeList);


        List<ProjectSupply> supplyList = updateReqVO.getSupplyList();
        // 遍历 supplyList ，更新数据
        supplyList.forEach(supplyItem -> {
            if(supplyItem.getId() == null) {
                supplyItem.setIsAppend(1);
                supplyItem.setIsAppend(1);
                supplyItem.setCategoryId(updateReqVO.getCategoryId());
                supplyItem.setProjectCategoryId(updateReqVO.getProjectCategoryId());
                supplyItem.setScheduleId(updateReqVO.getScheduleId());
                supplyItem.setQuantity(supplyItem.getFinalUsageNum());
                supplyItem.setProjectId(updateReqVO.getProjectId());
            }
        });
        projectSupplyRepository.saveAll(supplyList);

        return null;
    }

    @Override
    public void updateProjectCategory(ProjectCategoryUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectCategoryExists(updateReqVO.getId());
        // 更新
        ProjectCategory updateObj = projectCategoryMapper.toEntity(updateReqVO);
        projectCategoryRepository.save(updateObj);
    }

    @Override
    public void deleteProjectCategory(Long id) {
        // 校验存在
        validateProjectCategoryExists(id);

        //删除物资
        projectSupplyRepository.deleteByProjectCategoryId(id);
        //删除收费项
        projectChargeitemRepository.deleteByProjectCategoryId(id);
        //删除sop
        projectSopRepository.deleteByProjectCategoryId(id);
        //删除attachment
        projectCategoryAttachmentRepository.deleteByProjectCategoryId(id);
        // 删除
        projectCategoryRepository.deleteById(id);
    }

    @Override
    public void deleteSoftProjectCategory(Long id){
        // 校验存在
        validateProjectCategoryExists(id);
        // 删除
        projectCategoryRepository.deleteById(id);
    }

    @Override
    public void restoreDeletedProjectCategory(Long id){

        projectCategoryRepository.updateDeletedById(false,id);
    }

    @Override
    public void deleteProjectCategoryBy(ProjectCategoryDeleteByReqVO deleteByReqVO) {
        if (deleteByReqVO.getLabId()!=null){
            projectCategoryRepository.deleteByLabId(deleteByReqVO.getLabId());
        }
    }

    private void validateProjectCategoryExists(Long id) {
        projectCategoryRepository.findById(id).orElseThrow(() -> exception(PROJECT_CATEGORY_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectCategory> getProjectCategory(Long id) {
        Optional<ProjectCategory> byId = projectCategoryRepository.findById(id);
/*        if (byId.isPresent()) {
            List<ProjectCategoryApproval> approvalList = byId.get().getApprovalList();
            if (!approvalList.isEmpty()) {
                Optional<ProjectCategoryApproval> latestApproval = approvalList.stream()
                        .max(Comparator.comparing(ProjectCategoryApproval::getCreateTime));
                byId.get().setLatestApproval(latestApproval.orElse(null));
                latestApproval.ifPresent(approval -> {
                    byId.get().setApprovalStage(approval.getApprovalStage());
                    byId.get().setRequestStage(approval.getStage());
                });
            }
        }*/
        byId.ifPresent(this::processProjectCategoryItem);

        return projectCategoryRepository.findById(id);
    }

    @Override
    public List<ProjectCategory> getProjectCategoryList(Collection<Long> ids) {
        return StreamSupport.stream(projectCategoryRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectCategory> getProjectCategoryPage(ProjectCategoryPageReqVO pageReqVO, ProjectCategoryPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectCategory> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if(pageReqVO.getApprovalStage() != null) {
                predicates.add(cb.equal(root.get("approvalStage"), pageReqVO.getApprovalStage()));
            }

            if(pageReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), pageReqVO.getStage()));
            }

            // 直接写死，是1的时候去查一下1 别的都不进行处理
            if(pageReqVO.getHasFeedback()!=null&&pageReqVO.getHasFeedback()==1) {
                predicates.add(cb.equal(root.get("hasFeedback"), 1));
            }
            if(pageReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), pageReqVO.getStage()));
            }

            if(pageReqVO.getQuoteId() != null) {
                predicates.add(cb.equal(root.get("quoteId"), pageReqVO.getQuoteId()));
            }
            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }
            if(pageReqVO.getScheduleId() != null) {
                predicates.add(cb.equal(root.get("scheduleId"), pageReqVO.getScheduleId()));
            }

            //TODO all换成enum

            if(pageReqVO.getType() != null&& !pageReqVO.getType().equals("all")) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getCategoryType() != null) {
                predicates.add(cb.equal(root.get("categoryType"), pageReqVO.getCategoryType()));
            }

            if(pageReqVO.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("categoryId"), pageReqVO.getCategoryId()));
            }

            if(pageReqVO.getOperatorId() != null) {
                predicates.add(cb.equal(root.get("operatorId"), pageReqVO.getOperatorId()));
            }

            if(pageReqVO.getDemand() != null) {
                predicates.add(cb.equal(root.get("demand"), pageReqVO.getDemand()));
            }

            if(pageReqVO.getInterference() != null) {
                predicates.add(cb.equal(root.get("interference"), pageReqVO.getInterference()));
            }

            if(pageReqVO.getDependIds() != null) {
                predicates.add(cb.equal(root.get("dependIds"), pageReqVO.getDependIds()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }
            if(pageReqVO.getLabId() != null) {
                predicates.add(cb.equal(root.get("labId"), pageReqVO.getLabId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectCategory> page = projectCategoryRepository.findAll(spec, pageable);
        List<ProjectCategory> content = page.getContent();

        if(content!=null&&content.size()>0){
            content.forEach(this::processProjectCategoryItem);
        }
        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    private void processProjectCategoryItem(ProjectCategory projectCategory) {
        List<ProjectCategoryApproval> approvalList = projectCategory.getApprovalList();
        if (!approvalList.isEmpty()) {
            Optional<ProjectCategoryApproval> latestApproval = approvalList.stream()
                    .max(Comparator.comparing(ProjectCategoryApproval::getCreateTime));

/*            String approvalStage = latestApproval.map(ProjectCategoryApproval::getApprovalStage).orElse(null);
            String requestStage = latestApproval.map(ProjectCategoryApproval::getStage).orElse(null);*/

            projectCategory.setLatestApproval(latestApproval.orElse(null));
//            projectCategory.setApprovalStage(approvalStage);
//            projectCategory.setRequestStage(requestStage);
        }
    }

    @Override
    public List<ProjectCategory> getProjectCategoryList(ProjectCategoryExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectCategory> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getQuoteId() != null) {
                predicates.add(cb.equal(root.get("quoteId"), exportReqVO.getQuoteId()));
            }

            if(exportReqVO.getScheduleId() != null) {
                predicates.add(cb.equal(root.get("scheduleId"), exportReqVO.getScheduleId()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getCategoryType() != null) {
                predicates.add(cb.equal(root.get("categoryType"), exportReqVO.getCategoryType()));
            }

            if(exportReqVO.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("categoryId"), exportReqVO.getCategoryId()));
            }

            if(exportReqVO.getOperatorId() != null) {
                predicates.add(cb.equal(root.get("operatorId"), exportReqVO.getOperatorId()));
            }

            if(exportReqVO.getDemand() != null) {
                predicates.add(cb.equal(root.get("demand"), exportReqVO.getDemand()));
            }

            if(exportReqVO.getInterference() != null) {
                predicates.add(cb.equal(root.get("interference"), exportReqVO.getInterference()));
            }

            if(exportReqVO.getDependIds() != null) {
                predicates.add(cb.equal(root.get("dependIds"), exportReqVO.getDependIds()));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectCategoryRepository.findAll(spec);
    }

    private Sort createSort(ProjectCategoryPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getQuoteId() != null) {
            orders.add(new Sort.Order(order.getQuoteId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quoteId"));
        }

        if (order.getScheduleId() != null) {
            orders.add(new Sort.Order(order.getScheduleId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "scheduleId"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getCategoryType() != null) {
            orders.add(new Sort.Order(order.getCategoryType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "categoryType"));
        }

        if (order.getCategoryId() != null) {
            orders.add(new Sort.Order(order.getCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "categoryId"));
        }

        if (order.getOperatorId() != null) {
            orders.add(new Sort.Order(order.getOperatorId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "operatorId"));
        }

        if (order.getDemand() != null) {
            orders.add(new Sort.Order(order.getDemand().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "demand"));
        }

        if (order.getInterference() != null) {
            orders.add(new Sort.Order(order.getInterference().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "interference"));
        }

        if (order.getDependIds() != null) {
            orders.add(new Sort.Order(order.getDependIds().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "dependIds"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}