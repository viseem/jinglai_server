package cn.iocoder.yudao.module.jl.service.projectsettlement;

import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.ProjectQuotationExportRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.ProjectQuotationItemVO;
import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.repository.project.ProjectChargeitemRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectSupplyRepository;
import cn.iocoder.yudao.module.jl.service.commonattachment.CommonAttachmentServiceImpl;
import cn.iocoder.yudao.module.jl.service.projectquotation.ProjectQuotationServiceImpl;
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
import cn.iocoder.yudao.module.jl.controller.admin.projectsettlement.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectsettlement.ProjectSettlement;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectsettlement.ProjectSettlementMapper;
import cn.iocoder.yudao.module.jl.repository.projectsettlement.ProjectSettlementRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目结算 Service 实现类
 *
 */
@Service
@Validated
public class ProjectSettlementServiceImpl implements ProjectSettlementService {

    @Resource
    private ProjectSettlementRepository projectSettlementRepository;

    @Resource
    private ProjectSettlementMapper projectSettlementMapper;

    @Resource
    private ProjectQuotationServiceImpl projectQuotationService;

    @Resource
    private CommonAttachmentServiceImpl commonAttachmentService;

    @Resource
    private ProjectSupplyRepository projectSupplyRepository;
    @Resource
    private ProjectChargeitemRepository projectChargeitemRepository;

    @Override
    @Transactional
    public Long createProjectSettlement(ProjectSettlementCreateReqVO createReqVO) {

        ProjectQuotation quotation = projectQuotationService.validateProjectQuotationExists(createReqVO.getQuotationId());
        createReqVO.setProjectId(quotation.getProjectId());
        createReqVO.setCustomerId(quotation.getCustomerId());

        // 插入
        ProjectSettlement projectSettlement = projectSettlementMapper.toEntity(createReqVO);
        projectSettlementRepository.save(projectSettlement);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(projectSettlement.getId(), "PROJECT_SETTLEMENT", createReqVO.getAttachmentList());
        // 返回
        return projectSettlement.getId();
    }

    @Override
    @Transactional
    public void updateProjectSettlement(ProjectSettlementUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectSettlementExists(updateReqVO.getId());
        // 更新
        ProjectSettlement updateObj = projectSettlementMapper.toEntity(updateReqVO);
        projectSettlementRepository.save(updateObj);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(updateObj.getId(), "PROJECT_SETTLEMENT", updateReqVO.getAttachmentList());
    }

    @Override
    public void deleteProjectSettlement(Long id) {
        // 校验存在
        validateProjectSettlementExists(id);
        // 删除
        projectSettlementRepository.deleteById(id);
    }

    private void validateProjectSettlementExists(Long id) {
        projectSettlementRepository.findById(id).orElseThrow(() -> exception(PROJECT_SETTLEMENT_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectSettlement> getProjectSettlement(Long id) {
        return projectSettlementRepository.findById(id);
    }

    public BigDecimal getSettlementAmountByProjectId(Long projectId){
        return projectSettlementRepository.findByProjectId(projectId).stream()
                .map(ProjectSettlement::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<ProjectSettlement> getProjectSettlementList(Collection<Long> ids) {
        return StreamSupport.stream(projectSettlementRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectSettlement> getProjectSettlementPage(ProjectSettlementPageReqVO pageReqVO, ProjectSettlementPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectSettlement> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getQuotationId() != null) {
                predicates.add(cb.equal(root.get("quotationId"), pageReqVO.getQuotationId()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }


            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }


            if(pageReqVO.getPaidAmount() != null) {
                predicates.add(cb.equal(root.get("paidAmount"), pageReqVO.getPaidAmount()));
            }

            if(pageReqVO.getReminderDate() != null) {
                predicates.add(cb.between(root.get("reminderDate"), pageReqVO.getReminderDate()[0], pageReqVO.getReminderDate()[1]));
            } 
            if(pageReqVO.getAmount() != null) {
                predicates.add(cb.equal(root.get("amount"), pageReqVO.getAmount()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectSettlement> page = projectSettlementRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public ProjectSettlementExportRespVO getProjectSettlementList(ProjectSettlementExportReqVO exportReqVO) {
        // 创建 Specification
        ProjectSettlementExportRespVO resp = new ProjectSettlementExportRespVO();
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
            item.setBrand(projectSupply.getCurrentBrand());
            item.setBuyPrice(projectSupply.getBuyPrice());
            item.setProductCode(projectSupply.getCurrentCatalogNumber());
            item.setUnitFee(projectSupply.getCurrentPrice());
            item.setQuantity(projectSupply.getCurrentQuantity());
            item.setType(projectSupply.getType());
            item.setSpec(projectSupply.getCurrentSpec());
            quotationList.add(item);
        }
        quotationList.add(new ProjectQuotationItemVO() {
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
            if (projectChargeitem.getCategory() != null) {
                item.setProjectCategoryName(projectChargeitem.getCategory().getName());
                item.setProjectCategoryCycle(projectChargeitem.getCategory().getCycle());
            }
            item.setName(projectChargeitem.getName());
            item.setMark(projectChargeitem.getMark());
            item.setProjectCategoryId(projectChargeitem.getProjectCategoryId());
            item.setUnitFee(projectChargeitem.getCurrentPrice());
            item.setQuantity(projectChargeitem.getCurrentQuantity());
            item.setSpec(projectChargeitem.getCurrentSpec());
            quotationList.add(item);
        }
        quotationList.add(new ProjectQuotationItemVO() {
            {
                setProjectCategoryName("实验服务小计");
            }
        });
        quotationList.add(new ProjectQuotationItemVO() {
            {
                setProjectCategoryName("合 计");
            }
        });
        quotationList.add(new ProjectQuotationItemVO() {
            {
                setProjectCategoryName("最终优惠价");
                setPriceAmount(exportReqVO.getResultDiscountAmount());
            }
        });
//        resp.setRowCount(quotationList.size());
        resp.setQuotationItems(quotationList);
        return resp;
    }

    private Sort createSort(ProjectSettlementPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getPaidAmount() != null) {
            orders.add(new Sort.Order(order.getPaidAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "paidAmount"));
        }

        if (order.getReminderDate() != null) {
            orders.add(new Sort.Order(order.getReminderDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "reminderDate"));
        }

        if (order.getAmount() != null) {
            orders.add(new Sort.Order(order.getAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "amount"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}