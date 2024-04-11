package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bpm.enums.message.BpmMessageEnum;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.ProjectCategoryAttachmentBaseVO;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import cn.iocoder.yudao.module.jl.entity.project.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryAttachment;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryOutsource;
import cn.iocoder.yudao.module.jl.enums.ContractFundStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
import cn.iocoder.yudao.module.jl.enums.SalesLeadStatusEnums;
import cn.iocoder.yudao.module.jl.mapper.project.*;
import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategoryAttachmentMapper;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogRepository;
import cn.iocoder.yudao.module.jl.repository.crm.SalesleadRepository;
import cn.iocoder.yudao.module.jl.repository.financepayment.FinancePaymentRepository;
import cn.iocoder.yudao.module.jl.repository.project.*;
import cn.iocoder.yudao.module.jl.repository.projectcategory.ProjectCategoryAttachmentRepository;
import cn.iocoder.yudao.module.jl.repository.projectcategory.ProjectCategoryOutsourceRepository;
import cn.iocoder.yudao.module.jl.repository.projectquotation.ProjectQuotationRepository;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.PROJECT_SCHEDULE_NOT_EXISTS;

/**
 * 项目安排单 Service 实现类
 */
@Service
@Validated
public class ProjectScheduleServiceImpl implements ProjectScheduleService {

    @Resource
    private ProjectScheduleRepository projectScheduleRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private ProjectScheduleMapper projectScheduleMapper;


    @Resource
    private ProjectSopRepository projectSopRepository;

    @Resource
    private ProjectSopMapper projectSopMapper;

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @Resource
    private ProjectCategoryMapper projectCategoryMapper;

    @Resource
    private ProjectSupplyRepository projectSupplyRepository;

    @Resource
    private ProjectReimburseRepository projectReimburseRepository;

    @Resource
    private ProjectCategoryOutsourceRepository projectCategoryOutsourceRepository;

    @Resource
    private ProcurementItemRepository procurementItemRepository;

    @Resource
    private ProjectCategoryAttachmentRepository projectCategoryAttachmentRepository;

    @Resource
    private ProjectCategoryAttachmentMapper projectCategoryAttachmentMapper;

    @Resource
    private ProjectSupplyMapper projectSupplyMapper;

    @Resource
    private ProjectChargeitemRepository projectChargeitemRepository;

    @Resource
    private ProjectChargeitemMapper projectChargeitemMapper;

    @Resource
    private FinancePaymentRepository financePaymentRepository;

    @Resource
    private ProcurementPaymentRepository procurementPaymentRepository;

    private final SalesleadRepository salesleadRepository;

    @Resource
    private ProjectConstractRepository projectConstractRepository;

    @Resource
    private ProjectQuotationRepository projectQuotationRepository;

    @Resource
    private ContractInvoiceLogRepository contractInvoiceLogRepository;

    @Resource
    private ProjectServiceImpl projectServiceImpl;

    @Resource
    private ProjectOnlyRepository projectOnlyRepository;

    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;

    public ProjectScheduleServiceImpl(SalesleadRepository salesleadRepository) {
        this.salesleadRepository = salesleadRepository;
    }

    @Override
    public Long createProjectSchedule(ProjectScheduleCreateReqVO createReqVO) {
        // 插入
        ProjectSchedule projectSchedule = projectScheduleMapper.toEntity(createReqVO);
        projectScheduleRepository.save(projectSchedule);
        // 返回
        return projectSchedule.getId();
    }

    /*
     * 计算合同应收
     * */
    @Override
    public BigDecimal getContractAmountByProjectId(Long id) {
        BigDecimal cost = BigDecimal.ZERO;
        List<ProjectConstract> byProjectIdAndStatus = projectConstractRepository.findByProjectIdAndStatus(id, ProjectContractStatusEnums.SIGNED.getStatus());
        for (ProjectConstract projectConstract : byProjectIdAndStatus) {
            if (projectConstract.getPrice() != null) {
                cost = cost.add(projectConstract.getPrice());
            }
        }
        return cost;
    }

    @Override
    public BigDecimal getInvoiceAmountByProjectId(Long id){
        List<ContractInvoiceLog> byProjectId = contractInvoiceLogRepository.findByProjectId(id);
        BigDecimal cost = BigDecimal.ZERO;
        for (ContractInvoiceLog contractInvoiceLog : byProjectId) {
            if (contractInvoiceLog.getReceivedPrice() != null&&Objects.equals(contractInvoiceLog.getStatus(), ContractFundStatusEnums.AUDITED.getStatus())) {
                cost = cost.add(contractInvoiceLog.getReceivedPrice());
            }
        }
        return cost;
    }

    /*
     * 计算合同已收款
     * */
    @Override
    public BigDecimal getContractReceivedAmountByProjectId(Long id) {
        BigDecimal cost = BigDecimal.ZERO;
        List<ProjectConstract> byProjectIdAndStatus = projectConstractRepository.findByProjectIdAndStatus(id, ProjectContractStatusEnums.SIGNED.getStatus());

        for (ProjectConstract projectConstract : byProjectIdAndStatus) {
            if (projectConstract.getReceivedPrice() != null) {
                cost = cost.add(projectConstract.getReceivedPrice());
            }
        }

        return cost;
    }

    /**
     * 计算当前安排单的物资成本
     *
     * @param id
     * @return
     */
    @Override
    public Long getSupplyCostByScheduleId(Long id) {
        long cost = 0L;

        // 计算物资的成本
        List<ProjectSupply> projectSupplyList = projectSupplyRepository.findByScheduleId(id);
        for (ProjectSupply projectSupply : projectSupplyList) {
            if (projectSupply.getBuyPrice() != null) {
                cost += projectSupply.getBuyPrice().longValue() * projectSupply.getQuantity();
            }
        }

        return cost;
    }

    /**
     * 计算当前安排单的收费项安排
     *
     * @param id
     * @return
     */
    @Override
    public Long getChargeItemCostByScheduleId(Long id) {
        long cost = 0;

        // 计算收费项的成本
        List<ProjectChargeitem> projectChargeitemList = projectChargeitemRepository.findByScheduleId(id);
        for (ProjectChargeitem projectChargeitem : projectChargeitemList) {
            if (projectChargeitem.getBuyPrice() != null) {
                cost += projectChargeitem.getBuyPrice().longValue() * projectChargeitem.getQuantity();
            }
        }

        return cost;
    }

    /**
     * 计算当前安排单的物资成本
     *
     * @param id
     * @return
     */
    @Override
    public Long getSupplyQuotationByScheduleId(Long id) {
        long cost = 0L;

        // 计算物资的成本
        List<ProjectSupply> projectSupplyList = projectSupplyRepository.findByScheduleId(id);
        for (ProjectSupply projectSupply : projectSupplyList) {
            if (projectSupply.getUnitFee() != null) {
                cost += projectSupply.getUnitFee().longValue() * projectSupply.getQuantity();
            }
        }

        return cost;
    }

    /**
     * 计算当前安排单的收费项安排
     *
     * @param id
     * @return
     */
    @Override
    public Long getChargeItemQuotationByScheduleId(Long id) {
        long cost = 0;

        // 计算收费项的成本
        List<ProjectChargeitem> projectChargeitemList = projectChargeitemRepository.findByScheduleId(id);
        for (ProjectChargeitem projectChargeitem : projectChargeitemList) {
            if (projectChargeitem.getUnitFee() != null) {
                cost += projectChargeitem.getUnitFee().longValue() * projectChargeitem.getQuantity();
            }
        }

        return cost;
    }

    /**
     * 计算当前安排单的采购成本
     *
     * @param id
     * @return
     */
    @Override
    public Long getProcurementCostByScheduleId(Long id) {
        long cost = 0;

        // 计算采购的成本
        List<ProcurementItem> procurementItemList = procurementItemRepository.findByScheduleId(id);
        for (ProcurementItem procurementItem : procurementItemList) {
            if (procurementItem.getBuyPrice() != null) {
                cost += procurementItem.getBuyPrice().longValue() * procurementItem.getQuantity();
            }

        }

        return cost;
    }

    /**
     * 计算当前安排单的报销
     *
     * @param id
     * @return
     */
    @Override
    public Long getReimburseCostByScheduleId(Long id) {
        long cost = 0;

        // 计算报销的成本
        List<ProjectReimburse> projectReimburseList = projectReimburseRepository.findByScheduleId(id);
        for (ProjectReimburse projectReimburse : projectReimburseList) {
            if (projectReimburse.getPrice() != null) {
                cost += projectReimburse.getPrice().longValue();
            }
        }

        return cost;
    }

    /**
     * 计算当前安排单的报销
     *
     * @param id
     * @return
     */
    @Override
    public Long getCategoryOutSourceCostByScheduleId(Long id) {
        long cost = 0;

        // 计算委外的成本
        List<ProjectCategoryOutsource> projectCategoryOutsourceList = projectCategoryOutsourceRepository.findByScheduleId(id);
        for (ProjectCategoryOutsource projectCategoryOutsource : projectCategoryOutsourceList) {
            if (projectCategoryOutsource.getBuyPrice() != null) {
                cost += projectCategoryOutsource.getBuyPrice();
            }
        }

        return cost;
    }

    //---------------计算各类成本 通过projectId

    /**
     * 计算当前安排单的物资成本
     *
     * @param id
     * @return
     */
    @Override
    public Long getSupplyCostByProjectId(Long id) {
        long cost = 0L;

        // 计算物资的成本
        List<ProjectSupply> projectSupplyList = projectSupplyRepository.findByProjectId(id);
        for (ProjectSupply projectSupply : projectSupplyList) {
            if (projectSupply.getBuyPrice() != null) {
                cost += projectSupply.getBuyPrice().longValue() * projectSupply.getQuantity();
            }
        }

        return cost;
    }

    /**
     * 计算当前安排单的收费项安排
     *
     * @param id
     * @return
     */
    @Override
    public Long getChargeItemCostByProjectId(Long id) {
        long cost = 0;

        // 计算收费项的成本
        List<ProjectChargeitem> projectChargeitemList = projectChargeitemRepository.findByProjectId(id);
        for (ProjectChargeitem projectChargeitem : projectChargeitemList) {
            if (projectChargeitem.getBuyPrice() != null) {
                cost += projectChargeitem.getBuyPrice().longValue() * projectChargeitem.getQuantity();
            }
        }

        return cost;
    }

    /**
     * 计算当前安排单的物资成本
     *
     * @param id
     * @return
     */
    @Override
    public Long getSupplyQuotationByQuotationId(Long id) {
        long cost = 0L;

        // 计算物资的成本
        List<ProjectSupply> projectSupplyList = projectSupplyRepository.findByQuotationId(id);
        for (ProjectSupply projectSupply : projectSupplyList) {
            if (projectSupply.getUnitFee() != null) {
                cost += projectSupply.getUnitFee().longValue() * projectSupply.getQuantity();
            }
        }

        return cost;
    }

    /**
     * 计算当前安排单的收费项安排
     *
     * @param id
     * @return
     */
    @Override
    public BigDecimal getChargeItemQuotationByQuotationId(Long id) {

        //查询category的折扣
        List<ProjectCategory> categoryList = projectCategoryRepository.findByQuotationIdAndType(id,"schedule");
        List<ProjectChargeitem> projectChargeitemList = projectChargeitemRepository.findByQuotationId(id);
        BigDecimal amount = BigDecimal.ZERO;


        //ProjectCategory是一级分类，ProjectChargeitem是二级分类，ProjectCategory的折扣是二级分类的折扣
        for (ProjectCategory projectCategory : categoryList) {
            System.out.println("projectCategory:" + projectCategory.getName());

            BigDecimal categoryPrice = BigDecimal.ZERO;
            for (ProjectChargeitem projectChargeitem : projectChargeitemList) {
                if (Objects.equals(projectChargeitem.getProjectCategoryId(), projectCategory.getId())) {
                    if (projectChargeitem.getUnitFee() != null && projectChargeitem.getQuantity() != null) {
                        BigDecimal unitFee = new BigDecimal(projectChargeitem.getUnitFee());
                        BigDecimal quantity = new BigDecimal(projectChargeitem.getQuantity());
                        BigDecimal price = unitFee.multiply(quantity);
                        if(projectChargeitem.getDiscount()!=null){
                            price=price.multiply(new BigDecimal(projectChargeitem.getDiscount())).divide(BigDecimal.valueOf(100));
                        }
                        categoryPrice = categoryPrice.add(price);
                    }
                }
            }

            if(projectCategory.getDiscount()!=null){
                categoryPrice=categoryPrice.multiply(new BigDecimal(projectCategory.getDiscount())).divide(BigDecimal.valueOf(100));
            }

            amount = amount.add(categoryPrice);
        }

        // 计算收费项的成本
/*        List<ProjectChargeitem> projectChargeitemList = projectChargeitemRepository.findByQuotationId(id);
        for (ProjectChargeitem projectChargeitem : projectChargeitemList) {
            if (projectChargeitem.getUnitFee() != null&& projectChargeitem.getQuantity() != null) {
                BigDecimal unitFee = new BigDecimal(projectChargeitem.getUnitFee());
                BigDecimal quantity = new BigDecimal(projectChargeitem.getQuantity());
                BigDecimal price = unitFee.multiply(quantity);
                if(projectChargeitem.getDiscount()!=null){
                    price=price.multiply(new BigDecimal(projectChargeitem.getDiscount())).divide(BigDecimal.valueOf(100));
                }
                cost = cost.add(price);
            }
        }*/

        return amount;
    }

    /**
     * 计算当前安排单的采购成本
     *
     * @param id
     * @return
     */
    @Override
    public Long getProcurementCostByProjectId(Long id) {
        long cost = 0;
/*
        List<ProcurementPayment> byProjectId = procurementPaymentRepository.findByProjectId(id);
        for (ProcurementPayment procurementPayment : byProjectId) {
            if (procurementPayment.getAmount() != null) {
                cost += procurementPayment.getAmount();
            }
        }*/


        return cost;
    }

    /**
     * 计算当前安排单的报销
     *
     * @param id
     * @return
     */
    @Override
    public Long getReimburseCostByProjectId(Long id) {
        long cost = 0;
        System.out.println("id:" + id);
        // 计算报销的成本
        List<ProjectReimburse> projectReimburseList = projectReimburseRepository.findByProjectId(id);
        for (ProjectReimburse projectReimburse : projectReimburseList) {
            if (projectReimburse.getPaidPrice() != null) {
                cost += projectReimburse.getPaidPrice().longValue();
            }
        }

        return cost;
    }

    /**
     * 计算当前安排单的报销
     *
     * @param id
     * @return
     */
    @Override
    public Long getCategoryOutSourceCostByProjectId(Long id) {
        long cost = 0;

        // 计算委外的成本
        List<ProjectCategoryOutsource> projectCategoryOutsourceList = projectCategoryOutsourceRepository.findByProjectId(id);
        for (ProjectCategoryOutsource projectCategoryOutsource : projectCategoryOutsourceList) {
            if (projectCategoryOutsource.getPaidPrice() != null) {
                cost += projectCategoryOutsource.getPaidPrice().longValue();
            }
        }

        return cost;
    }


    /**
     * @param saveReqVO
     * @return
     */
    @Override
    public Long saveProjectSchedule(ProjectScheduleSaveReqVO saveReqVO) {
        // 如果提供了 scheduleId ，则更新。否则，创建
        Long scheduleId;
        Long projectId = saveReqVO.getProjectId();
        if (saveReqVO.getId() != null) {
            scheduleId = saveReqVO.getId();
            // 校验存在
            validateProjectScheduleExists(saveReqVO.getId());
            // 更新
            ProjectSchedule updateObj = projectScheduleMapper.toEntity(saveReqVO);
            projectScheduleRepository.save(updateObj);
        } else {
            // 创建
            ProjectSchedule projectSchedule = projectScheduleMapper.toEntity(saveReqVO);
            projectScheduleRepository.save(projectSchedule);
            scheduleId = projectSchedule.getId();

            projectRepository.findById(saveReqVO.getProjectId()).ifPresent(project -> {
                // 如果只有一条数据，则设置为主安排单
                long scheduleCnt = projectScheduleRepository.countByProjectId(saveReqVO.getProjectId());
                boolean isMainSchedule = scheduleCnt == 1;
                if (isMainSchedule) {
                    project.setCurrentScheduleId(scheduleId);
                    projectRepository.save(project);
                }
            });
        }

        List<ProjectCategoryWithSupplyAndChargeItemVO> categoryList = saveReqVO.getCategoryList();
        if (categoryList != null && categoryList.size() >= 1) {
            List<ProjectCategory> categories = projectCategoryRepository.findByScheduleIdOrderByIdAsc(scheduleId);
            // 获取 categories 里的 id
            List<Long> categoryIds = categories.stream().map(ProjectCategory::getId).collect(Collectors.toList());
            // 删除原来的
            // projectCategoryRepository.deleteByScheduleId(scheduleId);
            // projectSupplyRepository.deleteByProjectCategoryIdIn(categoryIds);
            // projectChargeitemRepository.deleteByProjectCategoryIdIn(categoryIds);
            projectSopRepository.deleteByProjectCategoryIdIn(categoryIds);
            projectCategoryAttachmentRepository.deleteByProjectCategoryIdIn(categoryIds);

            // 保存新的
            for (int i = 0; i < categoryList.size(); i++) {
                // 保存实验名目
                ProjectCategoryWithSupplyAndChargeItemVO category = categoryList.get(i);
                category.setType("schedule");
                if (category.getStage() == null) {
                    category.setStage("0");
                }
                category.setScheduleId(scheduleId);
                category.setProjectId(projectId);
                ProjectCategory categoryDo = projectCategoryMapper.toEntity(category);
                projectCategoryRepository.save(categoryDo);

                // 保存收费项
                List<ProjectChargeitemSubClass> chargetItemList = category.getChargeList();
                if (chargetItemList != null && chargetItemList.size() >= 1) {
                    List<ProjectChargeitemSubClass> projectChargeitemList = chargetItemList.stream().map(chargeItem -> {
                        chargeItem.setProjectCategoryId(categoryDo.getId());
                        chargeItem.setCategoryId(categoryDo.getCategoryId());
                        chargeItem.setProjectId(saveReqVO.getProjectId());
                        chargeItem.setScheduleId(scheduleId);
                        return chargeItem;
                    }).collect(Collectors.toList());

                    List<ProjectChargeitem> projectChargeitems = projectChargeitemMapper.toEntity(projectChargeitemList);
                    projectChargeitemRepository.saveAll(projectChargeitems);
                }

                // 保存物资项
                List<ProjectSupplySubClass> supplyList = category.getSupplyList();
                if (supplyList != null && supplyList.size() >= 1) {
                    List<ProjectSupplySubClass> projectSupplyList = supplyList.stream().map(supply -> {
                        supply.setProjectCategoryId(categoryDo.getId());
                        supply.setCategoryId(categoryDo.getCategoryId());
                        supply.setProjectId(saveReqVO.getProjectId());
                        supply.setScheduleId(scheduleId);
                        return supply;
                    }).collect(Collectors.toList());

                    List<ProjectSupply> projectSupplies = projectSupplyMapper.toEntity(projectSupplyList);
                    projectSupplyRepository.saveAll(projectSupplies);
                }

                // 保存 SOP
                List<ProjectSopBaseVO> sopList = category.getSopList();
                if (sopList != null && sopList.size() >= 1) {
                    List<ProjectSopBaseVO> projectSopList = sopList.stream().map(sop -> {
                        sop.setProjectCategoryId(categoryDo.getId());
                        sop.setCategoryId(categoryDo.getCategoryId());
                        return sop;
                    }).collect(Collectors.toList());

                    List<ProjectSop> projectSupplies = projectSopMapper.toEntity(projectSopList);
                    projectSopRepository.saveAll(projectSupplies);
                }

                // 保存 附件attachment
                List<ProjectCategoryAttachmentBaseVO> attachmentList = category.getAttachmentList();
                if (attachmentList != null && attachmentList.size() >= 1) {
                    List<ProjectCategoryAttachmentBaseVO> projectAttachmentList = attachmentList.stream().map(attachment -> {
                        attachment.setProjectCategoryId(categoryDo.getId());
                        return attachment;
                    }).collect(Collectors.toList());

                    List<ProjectCategoryAttachment> projectCategoryAttachments = projectCategoryAttachmentMapper.toEntity(projectAttachmentList);
                    projectCategoryAttachmentRepository.saveAll(projectCategoryAttachments);
                }


            }
        }
        return scheduleId;
    }

    @Override
    @Transactional
    public void saveScheduleSupplyAndChargeItem(ScheduleSaveSupplyAndChargeItemReqVO saveReq) {

        if (saveReq.getProjectCategoryType() != null) {

            saveReq.getSupplyList().forEach(supply -> {
                supply.setCreateType(0);
                supply.setProjectId(saveReq.getProjectId());
                supply.setQuotationId(saveReq.getProjectQuotationId());
            });
            if (saveReq.getCategoryList() != null && !saveReq.getCategoryList().isEmpty()) {
                saveReq.getChargeList().forEach(charge -> {
                    for (ProjectCategoryQuotationVO projectCategoryQuotationVO : saveReq.getCategoryList()) {
                        if (projectCategoryQuotationVO.getIsOld() && Objects.equals(projectCategoryQuotationVO.getOriginId(), charge.getProjectCategoryId())) {
                            charge.setProjectCategoryId(projectCategoryQuotationVO.getId());
                        }
                    }
                    charge.setProjectId(saveReq.getProjectId());
                    charge.setQuotationId(saveReq.getProjectQuotationId());
                });
            } else {
                saveReq.getChargeList().forEach(charge -> {
                    charge.setProjectId(saveReq.getProjectId());
                    charge.setQuotationId(saveReq.getProjectQuotationId());
                });
            }

        }

        //批量保存saveReq中的supplyList
        projectSupplyRepository.saveAll(saveReq.getSupplyList());
        projectChargeitemRepository.saveAll(saveReq.getChargeList());


        // 更新报价金额
//       accountSalesleadQuotation(saveReq.getProjectId());

    }

    // 完成报价
    @Override
    @Transactional
    public Long updateScheduleSaleslead(ProjectScheduleSaledleadsUpdateReqVO updateReqVO) {
        salesleadRepository.updateStatusByProjectId(Integer.valueOf(SalesLeadStatusEnums.IS_QUOTATION.getStatus()), updateReqVO.getProjectId());
//        accountSalesleadQuotation(updateReqVO.getProjectId(),updateReqVO.getQuotationId());
        projectRepository.updateCurrentQuotationIdById(updateReqVO.getQuotationId(), updateReqVO.getProjectId());
        projectQuotationRepository.updateDiscountById(updateReqVO.getQuotationDiscount(), updateReqVO.getQuotationId());
        projectQuotationRepository.updateOriginPriceById(updateReqVO.getOriginPrice(), updateReqVO.getQuotationId());
        salesleadRepository.updateQuotationByProjectId(updateReqVO.getProjectId(), updateReqVO.getQuotationAmount());

        //发送通知
        //发送站内通知
        Map<String, Object> templateParams = new HashMap<>();
//        templateParams.put("processInstanceName", reqDTO.getProcessInstanceName());
        templateParams.put("id", updateReqVO.getSalesleadId());
        //发给商机的销售
        notifyMessageSendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO(
                updateReqVO.getSalesId(),
                BpmMessageEnum.NOTIFY_WHEN_QUOTATIONED.getTemplateCode(), templateParams
        ));
        return null;
    }


    @Override
    @Transactional
    public Long saveProjectScheduleCategory(ProjectScheduleCategorySaveReqVO category) {
        if (category.getId() != null) {
            List<Long> categoryIds = new ArrayList<>();
            categoryIds.add(category.getId());

            projectSopRepository.deleteByProjectCategoryIdIn(categoryIds);
            projectCategoryAttachmentRepository.deleteByProjectCategoryIdIn(categoryIds);
        }

        category.setType(category.getType());
        //TODO 改为enum
        if (category.getType() != null && category.getType().equals("quotation")) {
            category.setName("报价");
            category.setLabId(-1L);

        }

        ProjectCategory categoryDo = projectCategoryMapper.toEntity(category);
        ProjectCategory save = projectCategoryRepository.save(categoryDo);

        //注入一下todo
//        commonTodoService.injectCommonTodoLogByTypeAndRefId(CommonTodoEnums.TYPE_PROJECT_CATEGORY.getStatus(), save.getId());
        // 设置一下实验人员
        Optional<ProjectOnly> byId = projectOnlyRepository.findById(category.getProjectId());
        byId.ifPresent(projectOnly -> projectServiceImpl.updateProjectFocusIdsById(category.getProjectId(), Collections.singletonList(category.getOperatorId()), projectOnly.getFocusIds()));

        // 保存收费项
        List<ProjectChargeitemSubClass> chargetItemList = category.getChargeList();
        if (chargetItemList != null && chargetItemList.size() >= 1) {
            List<ProjectChargeitemSubClass> projectChargeitemList = chargetItemList.stream().map(chargeItem -> {
                chargeItem.setProjectCategoryId(categoryDo.getId());
                chargeItem.setCategoryId(categoryDo.getCategoryId());
                chargeItem.setProjectId(category.getProjectId());
                chargeItem.setScheduleId(category.getScheduleId());
                return chargeItem;
            }).collect(Collectors.toList());

            List<ProjectChargeitem> projectChargeitems = projectChargeitemMapper.toEntity(projectChargeitemList);
            projectChargeitemRepository.saveAll(projectChargeitems);
        }

        // 保存物资项
        List<ProjectSupplySubClass> supplyList = category.getSupplyList();
        if (supplyList != null && supplyList.size() >= 1) {
            List<ProjectSupplySubClass> projectSupplyList = supplyList.stream().map(supply -> {
                supply.setProjectCategoryId(categoryDo.getId());
                supply.setCategoryId(categoryDo.getCategoryId());
                supply.setProjectId(category.getProjectId());
                supply.setScheduleId(category.getScheduleId());
                return supply;
            }).collect(Collectors.toList());

            List<ProjectSupply> projectSupplies = projectSupplyMapper.toEntity(projectSupplyList);
            projectSupplyRepository.saveAll(projectSupplies);
        }

        // 保存 SOP
        List<ProjectSopBaseVO> sopList = category.getSopList();
        if (sopList != null && sopList.size() >= 1) {
            List<ProjectSopBaseVO> projectSopList = sopList.stream().map(sop -> {
                sop.setProjectCategoryId(categoryDo.getId());
                sop.setCategoryId(categoryDo.getCategoryId());
                return sop;
            }).collect(Collectors.toList());

            List<ProjectSop> projectSupplies = projectSopMapper.toEntity(projectSopList);
            projectSopRepository.saveAll(projectSupplies);
        }

        // 保存 附件attachment
        projectCategoryAttachmentRepository.deleteByProjectCategoryId(categoryDo.getId());
        List<ProjectCategoryAttachmentBaseVO> attachmentList = category.getAttachmentList();
        if (attachmentList != null && attachmentList.size() >= 1) {
            List<ProjectCategoryAttachmentBaseVO> projectAttachmentList = attachmentList.stream().map(attachment -> {
                attachment.setProjectCategoryId(categoryDo.getId());
                return attachment;
            }).collect(Collectors.toList());

            List<ProjectCategoryAttachment> projectCategoryAttachments = projectCategoryAttachmentMapper.toEntity(projectAttachmentList);
            projectCategoryAttachmentRepository.saveAll(projectCategoryAttachments);
        }
        //TODO 改为enum

        if (category.getType() != null && category.getType().equals("quotation")) {
//            accountSalesleadQuotation(save.getProjectId());
        }

        // 修改报价金额
//        accountSalesleadQuotation(save.getProjectId());
        return save.getId();
    }


    @Override
    public void updateProjectSchedule(ProjectScheduleUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectScheduleExists(updateReqVO.getId());
        // 更新
        ProjectSchedule updateObj = projectScheduleMapper.toEntity(updateReqVO);
        projectScheduleRepository.save(updateObj);
    }


    public String replaceCustomTaskString(String originString, String replacement, Long categoryId) {
        // 创建一个正则表达式，匹配符合特定模式的子串
        String regexPattern = "<a\\sdata-w-e-type=\"taskDom\"\\sdata-w-e-id=\"" + categoryId + "\".*?<\\/a>";
        Pattern pattern = Pattern.compile(regexPattern, Pattern.DOTALL);

        // 使用正则表达式的 replace 方法替换匹配的子串
        String result = pattern.matcher(originString).replaceAll(replacement);

        return result;
    }


    @Override
    @Transactional
    public void updateSchedulePlanByContentHtml(ProjectScheduleUpdatePlanReqVO updateReqVO) {
        ProjectSchedule projectSchedule = validateProjectScheduleExists(updateReqVO.getScheduleId());

        //把projectSchedule的planText字符串中等于updateReqVO的contentHtml的 替换为 updateReqVO的newContentHtml
        String planText = projectSchedule.getPlanText();
//        String contentHtml = updateReqVO.getContentHtml();
        String newContentHtml = updateReqVO.getNewContentHtml();
        if (newContentHtml.equals(updateReqVO.getProjectCategoryId().toString())) {
            newContentHtml = updateReqVO.getProjectCategoryContent();
        }
        String replace = replaceCustomTaskString(planText, newContentHtml, updateReqVO.getProjectCategoryId());
        projectScheduleRepository.updatePlanTextById(replace, updateReqVO.getScheduleId());

        //更新projectCategory的content
        projectCategoryRepository.updateContentById(updateReqVO.getProjectCategoryContent(), updateReqVO.getProjectCategoryId());

    }

    @Override
    public void deleteProjectSchedule(Long id) {
        // 校验存在
        validateProjectScheduleExists(id);
        // 删除
        projectScheduleRepository.deleteById(id);
    }

    private ProjectSchedule validateProjectScheduleExists(Long id) {
        Optional<ProjectSchedule> byId = projectScheduleRepository.findById(id);
        if (byId.isEmpty()) {
            throw exception(PROJECT_SCHEDULE_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<ProjectSchedule> getProjectSchedule(Long id) {
        return projectScheduleRepository.findById(id);
    }

    @Override
    public List<ProjectSchedule> getProjectScheduleList(Collection<Long> ids) {
        return StreamSupport.stream(projectScheduleRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectSchedule> getProjectSchedulePage(ProjectSchedulePageReqVO pageReqVO, ProjectSchedulePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectSchedule> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if (pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if (pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectSchedule> page = projectScheduleRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectSchedule> getProjectScheduleList(ProjectScheduleExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectSchedule> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if (exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if (exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectScheduleRepository.findAll(spec);
    }

    private Sort createSort(ProjectSchedulePageOrder order) {
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

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}