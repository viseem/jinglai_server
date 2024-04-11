package cn.iocoder.yudao.module.jl.service.purchasecontract;

import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.jl.enums.ProcurementItemStatusEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementItemRepository;
import cn.iocoder.yudao.module.jl.service.commonattachment.CommonAttachmentService;
import cn.iocoder.yudao.module.jl.service.commonattachment.CommonAttachmentServiceImpl;
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
import javax.transaction.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.purchasecontract.vo.*;
import cn.iocoder.yudao.module.jl.entity.purchasecontract.PurchaseContract;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.purchasecontract.PurchaseContractMapper;
import cn.iocoder.yudao.module.jl.repository.purchasecontract.PurchaseContractRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.bpm.service.utils.ProcessInstanceKeyConstants.PROCUREMENT_PURCHASE_CONTRACT_AUDIT;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 购销合同 Service 实现类
 *
 */
@Service
@Validated
public class PurchaseContractServiceImpl implements PurchaseContractService {

    @Resource
    private PurchaseContractRepository purchaseContractRepository;

    @Resource
    private PurchaseContractMapper purchaseContractMapper;

    @Resource
    private CommonAttachmentServiceImpl commonAttachmentService;

    @Resource
    private ProcurementItemRepository procurementItemRepository;

    @Resource
    private BpmProcessInstanceApi processInstanceApi;

    @Override
    @Transactional
    public Long createPurchaseContract(PurchaseContractCreateReqVO createReqVO) {
        // 插入
        PurchaseContract purchaseContract = purchaseContractMapper.toEntity(createReqVO);
        purchaseContractRepository.save(purchaseContract);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(purchaseContract.getId(),"PROCUREMENT_PURCHASE_CONTRACT",createReqVO.getAttachmentList());

        // 返回
        return purchaseContract.getId();
    }

    @Override
    @Transactional
    public Long savePurchaseContract(PurchaseContractSaveReqVO reqVO) {
        // 插入
        PurchaseContract purchaseContract = purchaseContractMapper.toEntity(reqVO);
        purchaseContractRepository.save(purchaseContract);

        // 判断采购的类型

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(purchaseContract.getId(),"PROCUREMENT_PURCHASE_CONTRACT",reqVO.getAttachmentList());

        //保存procurementItemList
        reqVO.getProcurementItemList().forEach(item->{
            item.setStatus(ProcurementItemStatusEnums.INITIATE_ORDER.getStatus());
            item.setPurchaseContractId(purchaseContract.getId());
        });

        procurementItemRepository.saveAll(reqVO.getProcurementItemList());

        //加入审批流
        Map<String, Object> processInstanceVariables = new HashMap<>();
        String processInstanceId = processInstanceApi.createProcessInstance(getLoginUserId(),
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCUREMENT_PURCHASE_CONTRACT_AUDIT)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(purchaseContract.getId())));

        purchaseContractRepository.updateProcessInstanceIdById( processInstanceId,purchaseContract.getId());

        // 返回
        return purchaseContract.getId();
    }

    @Override
    @Transactional
    public void updatePurchaseContract(PurchaseContractUpdateReqVO updateReqVO) {
        // 校验存在
        validatePurchaseContractExists(updateReqVO.getId());
        // 更新
        PurchaseContract updateObj = purchaseContractMapper.toEntity(updateReqVO);
        purchaseContractRepository.save(updateObj);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(updateObj.getId(),"PROCUREMENT_PURCHASE_CONTRACT",updateReqVO.getAttachmentList());
    }

    @Override
    public void deletePurchaseContract(Long id) {
        // 校验存在
        validatePurchaseContractExists(id);
        // 删除
        purchaseContractRepository.deleteById(id);
    }

    private void validatePurchaseContractExists(Long id) {
        purchaseContractRepository.findById(id).orElseThrow(() -> exception(PURCHASE_CONTRACT_NOT_EXISTS));
    }

    @Override
    public Optional<PurchaseContract> getPurchaseContract(Long id) {
        return purchaseContractRepository.findById(id);
    }

    @Override
    public List<PurchaseContract> getPurchaseContractList(Collection<Long> ids) {
        return StreamSupport.stream(purchaseContractRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<PurchaseContract> getPurchaseContractPage(PurchaseContractPageReqVO pageReqVO, PurchaseContractPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<PurchaseContract> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getProcurementType() != null) {
                predicates.add(cb.equal(root.get("procurementType"), pageReqVO.getProcurementType()));
            }

            if(pageReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), pageReqVO.getSupplierId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getPriceStatus() != null) {
                predicates.add(cb.equal(root.get("priceStatus"), pageReqVO.getPriceStatus()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getAmount() != null) {
                predicates.add(cb.equal(root.get("amount"), pageReqVO.getAmount()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<PurchaseContract> page = purchaseContractRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<PurchaseContract> getPurchaseContractList(PurchaseContractExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<PurchaseContract> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), exportReqVO.getSupplierId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getAmount() != null) {
                predicates.add(cb.equal(root.get("amount"), exportReqVO.getAmount()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return purchaseContractRepository.findAll(spec);
    }

    private Sort createSort(PurchaseContractPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getSupplierId() != null) {
            orders.add(new Sort.Order(order.getSupplierId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplierId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getAmount() != null) {
            orders.add(new Sort.Order(order.getAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "amount"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}