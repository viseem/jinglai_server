package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryLab;
import cn.iocoder.yudao.module.jl.entity.project.*;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.repository.laboratory.LaboratoryLabRepository;
import cn.iocoder.yudao.module.jl.repository.project.*;
import cn.iocoder.yudao.module.jl.repository.projectcategory.ProjectCategoryAttachmentRepository;
import cn.iocoder.yudao.module.jl.repository.projectcategory.ProjectCategorySupplierRepository;
import cn.iocoder.yudao.module.jl.repository.user.UserRepository;
import org.jetbrains.annotations.NotNull;
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

import javax.persistence.criteria.*;
import javax.transaction.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectCategoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.jl.utils.JLSqlUtils.*;

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
    private ProjectCategorySimpleRepository projectCategorySimpleRepository;

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
    private LaboratoryLabRepository laboratoryLabRepository;

    @Resource
    private ProjectServiceImpl projectService;

    private final ProjectCategorySupplierRepository projectCategorySupplierRepository;
    private final UserRepository userRepository;

    public ProjectCategoryServiceImpl(ProjectCategorySupplierRepository projectCategorySupplierRepository,
                                      UserRepository userRepository) {
        this.projectCategorySupplierRepository = projectCategorySupplierRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Long createProjectCategory(ProjectCategoryCreateReqVO createReqVO) {

        ProjectSimple projectSimple = projectService.validateProjectExists(createReqVO.getProjectId());

        createReqVO.setCustomerId(projectSimple.getCustomerId());

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

        ProjectSimple projectSimple = projectService.validateProjectExists(updateReqVO.getProjectId());

        updateReqVO.setCustomerId(projectSimple.getCustomerId());

        // 更新
        ProjectCategory updateObj = projectCategoryMapper.toEntity(updateReqVO);
        projectCategoryRepository.save(updateObj);
    }

    @Override
    @Transactional
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

    public ProjectCategory processQuotationProjectCategory(String categoryType,Long projectId,Long quotationId){
        if (categoryType.equals("account")|| categoryType.equals("only")){
            ProjectCategory byProjectIdAndType = null;
            String  projectCategoryName = "出库增减项";

            if(categoryType.equals("account")){
                byProjectIdAndType = projectCategoryRepository.findByProjectIdAndQuotationIdAndType(projectId,quotationId, categoryType);
            }

            if(categoryType.equals("only")){
                projectCategoryName = "独立报价";
                byProjectIdAndType = projectCategoryRepository.findByProjectIdAndQuotationIdAndType(projectId,quotationId, categoryType);
            }

            //如果byProjectIdAndType等于null，则新增一个ProjectCategory,如果不等于null，则获取id
            if(byProjectIdAndType!=null){
                return byProjectIdAndType;
            }else{
                ProjectCategory projectCategory = new ProjectCategory();
                projectCategory.setProjectId(projectId);
                projectCategory.setStage(ProjectCategoryStatusEnums.COMPLETE.getStatus());
                projectCategory.setType(categoryType);
                projectCategory.setLabId(-2L);
                projectCategory.setName(projectCategoryName);
                ProjectCategory save = projectCategoryRepository.save(projectCategory);
                return save;
            }
        }else{
            throw exception(PROJECT_CATEGORY_NOT_EXISTS);
        }

    }

    @Override
    @Transactional
    public void deleteSoftProjectCategory(Long id){
        // 校验存在
        validateProjectCategoryExists(id);

        //软删除物资
        projectSupplyRepository.updateDeletedByProjectCategoryId(true,id);
        //软删除收费项
        projectChargeitemRepository.updateDeletedByProjectCategoryId(true,id);
        //软删除sop
        projectSopRepository.updateDeletedByProjectCategoryId(true,id);
        //软删除attachment
        projectCategoryAttachmentRepository.updateDeletedByProjectCategoryId(true,id);

        // 删除
        projectCategoryRepository.deleteById(id);
    }

    @Override
    public void restoreDeletedProjectCategory(Long id){
        projectCategoryRepository.updateDeletedById(false,id);

        //软删除物资 恢复
        projectSupplyRepository.updateDeletedByProjectCategoryId(false,id);
        //软删除收费项 恢复
        projectChargeitemRepository.updateDeletedByProjectCategoryId(false,id);
        //软删除sop 恢复
        projectSopRepository.updateDeletedByProjectCategoryId(false,id);
        //软删除attachment 恢复
        projectCategoryAttachmentRepository.updateDeletedByProjectCategoryId(false,id);
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
        ProjectCategory category  = byId.get();

        //以逗号分隔category的labIds 为list，然后查询出来
        String labIds = category.getLabIds();
        if(labIds!=null&& !labIds.isEmpty()){
            category.setLabList(idsString2QueryList(category.getLabIds(),laboratoryLabRepository));
        }

        String ids = category.getFocusIds();
        if(ids!=null&& !ids.isEmpty()){
            category.setFocusList(idsString2QueryList(category.getFocusIds(),userRepository));
        }
        return byId;
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
        Specification<ProjectCategory> spec = getProjectCategorySimpleSpecification(pageReqVO);

        // 执行查询
        Page<ProjectCategory> page = projectCategoryRepository.findAll(spec, pageable);
//        List<ProjectCategory> content = page.getContent();

/*        if(content!=null&&content.size()>0){
            content.forEach(this::processProjectCategoryItem);
        }*/
        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public PageResult<ProjectCategorySimple> getProjectCategoryPageSimple(ProjectCategoryPageReqVO pageReqVO, ProjectCategoryPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectCategorySimple> spec = getProjectCategorySimpleSpecification(pageReqVO);

        // 执行查询
        Page<ProjectCategorySimple> page = projectCategorySimpleRepository.findAll(spec, pageable);
//        List<ProjectCategorySimple> content = page.getContent();

/*        if(content!=null&&content.size()>0){
            content.forEach(this::processProjectCategorySimpleItem);
        }*/
        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @NotNull
    private static <T>Specification<T> getProjectCategorySimpleSpecification(ProjectCategoryPageReqVO pageReqVO) {
        Specification<T> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getAttribute() != null) {
                if(!pageReqVO.getAttribute().equals(DataAttributeTypeEnums.MY.getStatus())){
                    mysqlFindInSet(getLoginUserId(),"focusIds", root, cb, predicates);
                }else{
                    predicates.add(cb.equal(root.get("operatorId"), getLoginUserId()));
                }
            }else{
                //这个目前 前端传过来的
                if(pageReqVO.getOperatorId() != null) {
                    predicates.add(cb.equal(root.get("operatorId"), pageReqVO.getOperatorId()));
                }
            }

            if(pageReqVO.getFocusId() != null) {
                mysqlFindInSet(pageReqVO.getFocusId(),"focusIds", root, cb, predicates);
            }

            if(pageReqVO.getApprovalStage() != null) {
                predicates.add(cb.equal(root.get("approvalStage"), pageReqVO.getApprovalStage()));
            }

            if(pageReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), pageReqVO.getStage()));
            }

            // 直接写死，是1的时候去查一下1 别的都不进行处理
            if(pageReqVO.getHasFeedback()!=null&& pageReqVO.getHasFeedback()==1) {
                predicates.add(cb.equal(root.get("hasFeedback"), 1));
            }
            if(pageReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), pageReqVO.getStage()));
            }
            if(pageReqVO.getQuotationId() != null) {
                predicates.add(cb.equal(root.get("quotationId"), pageReqVO.getQuotationId()));
            }
            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }
            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }
            if(pageReqVO.getScheduleId() != null) {
                predicates.add(cb.equal(root.get("scheduleId"), pageReqVO.getScheduleId()));
            }

            //TODO all换成enum
            if(pageReqVO.getTypes()!=null&& pageReqVO.getTypes().size()>0){
                predicates.add(root.get("type").in(pageReqVO.getTypes()));
            }else{
                if(pageReqVO.getType() != null&& !pageReqVO.getType().equals("all")) {
                    predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
                }
            }

            if(pageReqVO.getCategoryType() != null) {
                predicates.add(cb.equal(root.get("categoryType"), pageReqVO.getCategoryType()));
            }

            if(pageReqVO.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("categoryId"), pageReqVO.getCategoryId()));
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
                mysqlFindInSet(pageReqVO.getLabId(),"labIds", root, cb, predicates);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return spec;
    }




    private void processProjectCategoryItem(ProjectCategory projectCategory) {
/*        List<ProjectCategoryApproval> approvalList = projectCategory.getApprovalList();
        if (!approvalList.isEmpty()) {
            Optional<ProjectCategoryApproval> latestApproval = approvalList.stream()
                    .max(Comparator.comparing(ProjectCategoryApproval::getCreateTime));
            projectCategory.setLatestApproval(latestApproval.orElse(null));
        }*/
    }

    private void processProjectCategorySimpleItem(ProjectCategorySimple projectCategory) {
/*        List<ProjectCategoryApproval> approvalList = projectCategory.getApprovalList();
        if (!approvalList.isEmpty()) {
            Optional<ProjectCategoryApproval> latestApproval = approvalList.stream()
                    .max(Comparator.comparing(ProjectCategoryApproval::getCreateTime));
            projectCategory.setLatestApproval(latestApproval.orElse(null));
        }*/
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


//        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
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