package cn.iocoder.yudao.module.jl.service.projectquotation;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectCategoryQuotationVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ScheduleSaveSupplyAndChargeItemReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.ProjectQuotationUpdatePlanReqVO;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectCategoryMapper;
import cn.iocoder.yudao.module.jl.repository.crm.SalesleadRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectChargeitemRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectSupplyRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectScheduleServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProjectServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
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
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectquotation.ProjectQuotationMapper;
import cn.iocoder.yudao.module.jl.repository.projectquotation.ProjectQuotationRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目报价 Service 实现类
 *
 */
@Service
@Validated
public class ProjectQuotationServiceImpl implements ProjectQuotationService {

    @Resource
    private ProjectQuotationRepository projectQuotationRepository;

    @Resource
    private SalesleadRepository salesleadRepository;

    @Resource
    private ProjectQuotationMapper projectQuotationMapper;

    @Resource
    private ProjectScheduleServiceImpl projectScheduleService;

    @Resource
    private ProjectServiceImpl projectService;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private ProjectSupplyRepository projectSupplyRepository;
    @Resource
    private ProjectChargeitemRepository projectChargeitemRepository;

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;
    @Resource
    private ProjectCategoryMapper projectCategoryMapper;


    @Override
    public Long createProjectQuotation(ProjectQuotationCreateReqVO createReqVO) {
        // 插入
        ProjectQuotation projectQuotation = projectQuotationMapper.toEntity(createReqVO);
        projectQuotationRepository.save(projectQuotation);
        // 返回
        return projectQuotation.getId();
    }

    @Override
    public void updateProjectQuotation(ProjectQuotationUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectQuotationExists(updateReqVO.getId());
        // 更新
        ProjectQuotation updateObj = projectQuotationMapper.toEntity(updateReqVO);
        projectQuotationRepository.save(updateObj);
    }

    @Override
    public void updateProjectQuotationDiscount(ProjectQuotationNoRequireVO updateReqVO) {
        projectQuotationRepository.updateDiscountById(updateReqVO.getDiscount(), updateReqVO.getId());
    }

    @Override
    @Transactional
    public void saveProjectQuotation(ProjectQuotationSaveReqVO updateReqVO) {

        ProjectSimple projectSimple = projectService.validateProjectExists(updateReqVO.getProjectId());
        updateReqVO.setCustomerId(projectSimple.getCustomerId());

        if(updateReqVO.getCode()==null || updateReqVO.getCode().isEmpty()){
            updateReqVO.setCode("默认");
        }
        ProjectQuotation updateObj = projectQuotationMapper.toEntity(updateReqVO);
        ProjectQuotation save = projectQuotationRepository.save(updateObj);

        // 如果项目的quotationId为空或者是新的报价版本 更新一下项目的currentQuotationId 注意：不为空的时候更新 专门的更新版本的 有另一个接口
        if(projectSimple.getCurrentQuotationId()==null || updateReqVO.getId()==null){
            projectRepository.updateCurrentQuotationIdById(save.getId(),updateReqVO.getProjectId());
        }

        ScheduleSaveSupplyAndChargeItemReqVO saveReqVO = new ScheduleSaveSupplyAndChargeItemReqVO();
        saveReqVO.setProjectId(save.getProjectId());
        saveReqVO.setSupplyList(updateReqVO.getSupplyList());
        saveReqVO.setChargeList(updateReqVO.getChargeList());
        saveReqVO.setProjectCategoryType("only");
        saveReqVO.setProjectQuotationId(save.getId());

        if(updateReqVO.getCategoryList()!=null&& !updateReqVO.getCategoryList().isEmpty()){
            for (ProjectCategoryQuotationVO projectCategory : updateReqVO.getCategoryList()) {
                if(projectCategory.getIsOld()){
                    projectCategory.setOriginId(projectCategory.getId());
                    projectCategory.setId(null);
                }
                projectCategory.setQuotationId(saveReqVO.getProjectQuotationId());
                ProjectCategory save1 = projectCategoryRepository.save(projectCategoryMapper.toEntity(projectCategory));
                projectCategory.setId(save1.getId());
            }
//            projectCategoryRepository.saveAll(projectCategoryMapper.toEntityQuotation(updateReqVO.getCategoryList()));
        }
        saveReqVO.setCategoryList(updateReqVO.getCategoryList());

        projectScheduleService.saveScheduleSupplyAndChargeItem(saveReqVO);

        //可能还需要更新一下saleslead的价格 ，他可能直接保存的当前版本
        salesleadRepository.updateQuotationByProjectId(updateReqVO.getProjectId(), updateReqVO.getQuotationAmount());

    }

    @Override
    @Transactional
    public Long updateProjectQuotationPlan(ProjectQuotationUpdatePlanReqVO updateReqVO){
        if(updateReqVO.getId()==null){
            // 校验存在
            ProjectSimple projectSimple = projectService.validateProjectExists(updateReqVO.getProjectId());
            updateReqVO.setCustomerId(projectSimple.getCustomerId());
            ProjectQuotation updateObj = projectQuotationMapper.toEntity(updateReqVO);
            ProjectQuotation save = projectQuotationRepository.save(updateObj);
            updateReqVO.setId(save.getId());
        }

        if(updateReqVO.getPlanText()!=null&&updateReqVO.getPlanText().length()>5){
            projectQuotationRepository.updatePlanTextById(updateReqVO.getPlanText(), updateReqVO.getId());
        }

        Long id = updateReqVO.getId();
        /*if(updateReqVO.getCategoryList()!=null&& !updateReqVO.getCategoryList().isEmpty()){
            for (ProjectCategoryQuotationVO projectCategory : updateReqVO.getCategoryList()) {
                projectCategory.setQuotationId(id);
            }
            projectCategoryRepository.saveAll(projectCategoryMapper.toEntityQuotation(updateReqVO.getCategoryList()));
        }*/
//        projectRepository.updateCurrentQuotationIdById(updateReqVO.getId(),updateReqVO.getProjectId());

        return id;
    }

    @Override
    @Transactional
    public void deleteProjectQuotation(Long id) {
        // 校验存在
        validateProjectQuotationExists(id);

        //删除supply
        projectSupplyRepository.deleteByQuotationId(id);
        //删除chargeitem
        projectChargeitemRepository.deleteByQuotationId(id);
        //删除category
        projectCategoryRepository.deleteByQuotationId(id);

        // 删除
        projectQuotationRepository.deleteById(id);
    }

    private void validateProjectQuotationExists(Long id) {
        projectQuotationRepository.findById(id).orElseThrow(() -> exception(PROJECT_QUOTATION_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectQuotation> getProjectQuotation(Long id) {
        return projectQuotationRepository.findById(id);
    }

    @Override
    public List<ProjectQuotation> getProjectQuotationList(Collection<Long> ids) {
        return StreamSupport.stream(projectQuotationRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectQuotation> getProjectQuotationPage(ProjectQuotationPageReqVO pageReqVO, ProjectQuotationPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectQuotation> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), pageReqVO.getCode()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getPlanText() != null) {
                predicates.add(cb.equal(root.get("planText"), pageReqVO.getPlanText()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectQuotation> page = projectQuotationRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public ProjectQuotationExportRespVO getProjectQuotationList(ProjectQuotationExportReqVO exportReqVO) {
        ProjectQuotationExportRespVO resp = new ProjectQuotationExportRespVO();
        List<ProjectQuotationItemVO> quotationList = new ArrayList<>();
        //查询物资列表
        List<ProjectSupply> byQuotationId = projectSupplyRepository.findByQuotationId(exportReqVO.getQuotationId());
        resp.setSupplyCount(byQuotationId.size());
        //遍历物资列表赋值到resp的itemList
        for (ProjectSupply projectSupply : byQuotationId) {
            ProjectQuotationItemVO item = new ProjectQuotationItemVO();
            item.setProjectCategoryName("实验材料准备");
            item.setName(projectSupply.getName());
            item.setMark(projectSupply.getMark());
            item.setProjectCategoryId(projectSupply.getProjectCategoryId());
            item.setBrand(projectSupply.getBrand());
            item.setBuyPrice(projectSupply.getBuyPrice());
            item.setProductCode(projectSupply.getProductCode());
            item.setUnitFee(projectSupply.getUnitFee());
            item.setQuantity(projectSupply.getQuantity());
            item.setType(projectSupply.getType());
            item.setSpec(projectSupply.getSpec());
            quotationList.add(item);
        }
        quotationList.add(new ProjectQuotationItemVO(){
            {
                setProjectCategoryName("实验材料费小计");
            }
        });

        // 查询收费项列表，并按照projectCategoryId排序
        List<ProjectChargeitem> byQuotationId1 = projectChargeitemRepository.findByQuotationId(exportReqVO.getQuotationId());
        resp.setChargeCount(byQuotationId1.size());
        //byQuotationId1按照projectCategoryId排序 升序
        byQuotationId1.sort(Comparator.comparing(ProjectChargeitem::getProjectCategoryId));
        //遍历收费项列表赋值到resp的itemList
        for (ProjectChargeitem projectChargeitem : byQuotationId1) {
            ProjectQuotationItemVO item = new ProjectQuotationItemVO();
            if(projectChargeitem.getCategory()!=null){
                System.out.println("category---"+projectChargeitem.getCategory().getName());
                item.setProjectCategoryName(projectChargeitem.getCategory().getName());
                item.setProjectCategoryCycle(projectChargeitem.getCategory().getCycle());
            }
            item.setName(projectChargeitem.getName());
            item.setMark(projectChargeitem.getMark());
            item.setProjectCategoryId(projectChargeitem.getProjectCategoryId());
            item.setUnitFee(BigDecimal.valueOf(projectChargeitem.getUnitFee()));
            item.setQuantity(projectChargeitem.getQuantity());
            item.setSpec(projectChargeitem.getSpec());
            quotationList.add(item);
        }
        quotationList.add(new ProjectQuotationItemVO(){
            {
                setProjectCategoryName("实验服务小计");
            }
        });
        quotationList.add(new ProjectQuotationItemVO(){
            {
                setProjectCategoryName("合 计");
            }
        });
        resp.setRowCount(quotationList.size());
        resp.setQuotationItems(quotationList);
        return resp;
    }

    private Sort createSort(ProjectQuotationPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getCode() != null) {
            orders.add(new Sort.Order(order.getCode().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "code"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getPlanText() != null) {
            orders.add(new Sort.Order(order.getPlanText().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "planText"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}