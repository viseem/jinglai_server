package cn.iocoder.yudao.module.jl.service.projectquotation;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ScheduleSaveSupplyAndChargeItemReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.ProjectQuotationUpdatePlanReqVO;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.repository.project.ProjectChargeitemRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectSupplyRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectScheduleServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProjectServiceImpl;
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
        projectScheduleService.saveScheduleSupplyAndChargeItem(saveReqVO);


    }

    @Override
    public Long updateProjectQuotationPlan(ProjectQuotationUpdatePlanReqVO updateReqVO){
        if(updateReqVO.getId()==null){
            // 校验存在
            ProjectSimple projectSimple = projectService.validateProjectExists(updateReqVO.getProjectId());
            updateReqVO.setCustomerId(projectSimple.getCustomerId());
            ProjectQuotation updateObj = projectQuotationMapper.toEntity(updateReqVO);
            ProjectQuotation save = projectQuotationRepository.save(updateObj);
            updateReqVO.setId(save.getId());
        }
        projectQuotationRepository.updatePlanTextById(updateReqVO.getPlanText(), updateReqVO.getId());

        return updateReqVO.getId();
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
    public List<ProjectQuotation> getProjectQuotationList(ProjectQuotationExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectQuotation> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), exportReqVO.getCode()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getPlanText() != null) {
                predicates.add(cb.equal(root.get("planText"), exportReqVO.getPlanText()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectQuotationRepository.findAll(spec);
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