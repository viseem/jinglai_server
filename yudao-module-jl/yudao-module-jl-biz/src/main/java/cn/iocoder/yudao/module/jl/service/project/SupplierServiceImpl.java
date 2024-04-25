package cn.iocoder.yudao.module.jl.service.project;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.jl.service.commonattachment.CommonAttachmentServiceImpl;
import cn.iocoder.yudao.module.jl.service.projectsupplierinvoice.ProjectSupplierInvoiceServiceImpl;
import cn.iocoder.yudao.module.system.api.dict.DictDataApiImpl;
import cn.iocoder.yudao.module.system.api.dict.dto.DictDataRespDTO;
import cn.iocoder.yudao.module.system.convert.user.UserConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.enums.DictTypeConstants;
import com.xingyuv.captcha.util.StringUtils;
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

import javax.persistence.criteria.Predicate;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.Supplier;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.SupplierMapper;
import cn.iocoder.yudao.module.jl.repository.project.SupplierRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.jl.utils.JLSqlUtils.mysqlFindInSet;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.USER_IMPORT_LIST_IS_EMPTY;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.USER_USERNAME_EXISTS;

/**
 * 项目采购单物流信息 Service 实现类
 *
 */
@Service
@Validated
public class SupplierServiceImpl implements SupplierService {

    @Resource
    private SupplierRepository supplierRepository;

    @Resource
    private SupplierMapper supplierMapper;

    @Resource
    private ProcurementPaymentServiceImpl procurementPaymentService;

    @Resource
    private ProjectSupplierInvoiceServiceImpl projectSupplierInvoiceService;

    @Resource
    private DictDataApiImpl dictDataApi;

    @Resource
    private CommonAttachmentServiceImpl commonAttachmentService;

    @Override
    @Transactional
    public Long createSupplier(SupplierCreateReqVO createReqVO) {
        // 插入
        Supplier supplier = supplierMapper.toEntity(createReqVO);
        supplierRepository.save(supplier);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(supplier.getId(),"SUPPLIER",createReqVO.getAttachmentList());

        // 返回
        return supplier.getId();
    }

    @Override
    @Transactional
    public void updateSupplier(SupplierUpdateReqVO updateReqVO) {
        // 校验存在
        validateSupplierExists(updateReqVO.getId());
        // 更新
        Supplier updateObj = supplierMapper.toEntity(updateReqVO);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(updateReqVO.getId(),"SUPPLIER",updateReqVO.getAttachmentList());

        supplierRepository.save(updateObj);
    }

    @Override
    public void deleteSupplier(Long id) {
        // 校验存在
        validateSupplierExists(id);
        // 删除
        supplierRepository.deleteById(id);
    }

    private void validateSupplierExists(Long id) {
        supplierRepository.findById(id).orElseThrow(() -> exception(SUPPLIER_NOT_EXISTS));
    }

    @Override
    public Optional<Supplier> getSupplier(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public SupplierStatsRespVO getSupplierStats(Long id) {
        SupplierStatsRespVO supplierStatsRespVO = new SupplierStatsRespVO();
        supplierStatsRespVO.setFundAmount(procurementPaymentService.sumAmountBySupplierId(id));
        supplierStatsRespVO.setInvoiceAmount(projectSupplierInvoiceService.sumAmountBySupplierId(id));
        return supplierStatsRespVO;
    }

    @Override
    public List<Supplier> getSupplierList(Collection<Long> ids) {
        return StreamSupport.stream(supplierRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Supplier> getSupplierPage(SupplierPageReqVO pageReqVO, SupplierPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Supplier> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getCateType() != null) {
                predicates.add(cb.like(root.get("cateType"), "%" + pageReqVO.getCateType() + "%"));
            }

            if(pageReqVO.getTagId() != null) {
                mysqlFindInSet(pageReqVO.getTagId(),"tagIds", root, cb, predicates);
            }

            if(pageReqVO.getContactPhone() != null) {
                predicates.add(cb.equal(root.get("contactPhone"), pageReqVO.getContactPhone()));
            }

            if(pageReqVO.getGoodAt() != null) {
                predicates.add(cb.like(root.get("goodAt"), "%" + pageReqVO.getGoodAt() + "%"));
            }

            if(pageReqVO.getContactName() != null) {
                predicates.add(cb.like(root.get("contactName"), "%" + pageReqVO.getContactName() + "%"));
            }

            if(pageReqVO.getContactPhone() != null) {
                predicates.add(cb.equal(root.get("contactPhone"), pageReqVO.getContactPhone()));
            }

            if(pageReqVO.getPaymentCycle() != null) {
                predicates.add(cb.equal(root.get("paymentCycle"), pageReqVO.getPaymentCycle()));
            }

            if(pageReqVO.getBankAccount() != null) {
                predicates.add(cb.equal(root.get("bankAccount"), pageReqVO.getBankAccount()));
            }

            if(pageReqVO.getTaxNumber() != null) {
                predicates.add(cb.equal(root.get("taxNumber"), pageReqVO.getTaxNumber()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getBillTitle() != null) {
                predicates.add(cb.equal(root.get("billTitle"), pageReqVO.getBillTitle()));
            }

            if(pageReqVO.getBillWay() != null) {
                predicates.add(cb.equal(root.get("billWay"), pageReqVO.getBillWay()));
            }

            if(pageReqVO.getBillRequest() != null) {
                predicates.add(cb.equal(root.get("billRequest"), pageReqVO.getBillRequest()));
            }

            if(pageReqVO.getContactDepartment() != null) {
                predicates.add(cb.equal(root.get("contactDepartment"), pageReqVO.getContactDepartment()));
            }

            if(pageReqVO.getProduct() != null) {
                predicates.add(cb.equal(root.get("product"), pageReqVO.getProduct()));
            }

            if(pageReqVO.getDiscount() != null) {
                predicates.add(cb.equal(root.get("discount"), pageReqVO.getDiscount()));
            }

            if(pageReqVO.getContactLevel() != null) {
                predicates.add(cb.equal(root.get("contactLevel"), pageReqVO.getContactLevel()));
            }

            if(pageReqVO.getSubBranch() != null) {
                predicates.add(cb.equal(root.get("subBranch"), pageReqVO.getSubBranch()));
            }

            if(pageReqVO.getChannelType() != null) {
                predicates.add(cb.equal(root.get("channelType"), pageReqVO.getChannelType()));
            }

            if(pageReqVO.getServiceCatalog() != null) {
                predicates.add(cb.equal(root.get("serviceCatalog"), pageReqVO.getServiceCatalog()));
            }

            if(pageReqVO.getAdvantage() != null) {
                predicates.add(cb.equal(root.get("advantage"), pageReqVO.getAdvantage()));
            }

            if(pageReqVO.getDisadvantage() != null) {
                predicates.add(cb.equal(root.get("disadvantage"), pageReqVO.getDisadvantage()));
            }

            if(pageReqVO.getCompanyManager() != null) {
                predicates.add(cb.equal(root.get("companyManager"), pageReqVO.getCompanyManager()));
            }

            if(pageReqVO.getBusinessManager() != null) {
                predicates.add(cb.equal(root.get("businessManager"), pageReqVO.getBusinessManager()));
            }

            if(pageReqVO.getTechManager() != null) {
                predicates.add(cb.equal(root.get("techManager"), pageReqVO.getTechManager()));
            }

            if(pageReqVO.getManager() != null) {
                predicates.add(cb.equal(root.get("manager"), pageReqVO.getManager()));
            }

            if(pageReqVO.getAfterManager() != null) {
                predicates.add(cb.equal(root.get("afterManager"), pageReqVO.getAfterManager()));
            }

            if(pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<Supplier> page = supplierRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<Supplier> getSupplierList(SupplierExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Supplier> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getContactName() != null) {
                predicates.add(cb.like(root.get("contactName"), "%" + exportReqVO.getContactName() + "%"));
            }

            if(exportReqVO.getContactPhone() != null) {
                predicates.add(cb.equal(root.get("contactPhone"), exportReqVO.getContactPhone()));
            }

            if(exportReqVO.getPaymentCycle() != null) {
                predicates.add(cb.equal(root.get("paymentCycle"), exportReqVO.getPaymentCycle()));
            }

            if(exportReqVO.getBankAccount() != null) {
                predicates.add(cb.equal(root.get("bankAccount"), exportReqVO.getBankAccount()));
            }

            if(exportReqVO.getTaxNumber() != null) {
                predicates.add(cb.equal(root.get("taxNumber"), exportReqVO.getTaxNumber()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        List<Supplier> supplierList = supplierRepository.findAll(spec);
        List<DictDataRespDTO> channelTypes = dictDataApi.getDictDataByType(DictTypeConstants.SUPPLIER_CHANNEL_TYPE);
        List<DictDataRespDTO> billWays = dictDataApi.getDictDataByType(DictTypeConstants.SUPPLIER_BILL_WAY_TYPE);
        List<DictDataRespDTO> paymentCycles = dictDataApi.getDictDataByType(DictTypeConstants.SUPPLIER_PAYMENT_CYCLE);

        for (Supplier supplier : supplierList) {
            for (DictDataRespDTO channelType : channelTypes) {
                if (channelType.getValue().equals(supplier.getChannelType())) {
                    supplier.setChannelTypeStr(channelType.getLabel());
                }
            }
            for (DictDataRespDTO billWay : billWays) {
                if (billWay.getValue().equals(supplier.getBillWay())) {
                    supplier.setBillWayStr(billWay.getLabel());
                }
            }
            for (DictDataRespDTO paymentCycle : paymentCycles) {
                if (paymentCycle.getValue().equals(supplier.getPaymentCycle())) {
                    supplier.setPaymentCycleStr(paymentCycle.getLabel());
                }
            }
        }

        return supplierList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public SupplierImportRespVO importList(List<SupplierImportVO> importUsers, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importUsers)) {
            throw exception(USER_IMPORT_LIST_IS_EMPTY);
        }
        SupplierImportRespVO respVO = SupplierImportRespVO.builder().createNames(new ArrayList<>())
                .updateNames(new ArrayList<>()).failureNames(new LinkedHashMap<>()).build();
        importUsers.forEach(importUser -> {

            Supplier supplier = supplierMapper.toEntity(importUser);

            //根据字典label查询字典id
            if(StringUtils.contains(importUser.getChannelTypeStr(), "厂家")){
                supplier.setChannelType("1");
            }
            if(StringUtils.contains(importUser.getChannelTypeStr(), "经销商")){
                supplier.setChannelType("2");
            }
            if(StringUtils.contains(importUser.getChannelTypeStr(), "厂家/经销商") || StringUtils.contains(importUser.getChannelTypeStr(), "经销商/厂家") ){
                supplier.setChannelType("3");
            }

            if(StringUtils.contains(importUser.getBillWayStr(), "增值税专票")){
                supplier.setBillWay("1");
            }
            if(StringUtils.contains(importUser.getBillWayStr(), "增值税普票")){
                supplier.setBillWay("2");
            }
            if(StringUtils.contains(importUser.getBillWayStr(), "不开票")){
                supplier.setBillWay("3");
            }
            if(StringUtils.contains(importUser.getBillWayStr(), "替票")){
                supplier.setBillWay("4");
            }
            if(StringUtils.contains(importUser.getPaymentCycleStr(), "日度付款")){
                supplier.setPaymentCycle("1");
            }
            if(StringUtils.contains(importUser.getPaymentCycleStr(), "周度付款")){
                supplier.setPaymentCycle("2");
            }
            if(StringUtils.contains(importUser.getPaymentCycleStr(), "月度付款")){
                supplier.setPaymentCycle("3");
            }
            if(StringUtils.contains(importUser.getPaymentCycleStr(), "季度付款")){
                supplier.setPaymentCycle("4");
            }
            if(StringUtils.contains(importUser.getPaymentCycleStr(), "年度付款")){
                supplier.setPaymentCycle("5");
            }
            // 判断如果不存在，在进行插入
            Supplier byName = supplierRepository.findByName(importUser.getName());
            if (byName == null) {
                supplierRepository.save(supplier);
                respVO.getCreateNames().add(importUser.getName());
                return;
            }
            // 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureNames().put(importUser.getName(), SUPPLIER_EXISTS.getMsg());
                return;
            }
            supplier.setId(byName.getId());
            supplierRepository.save(supplier);
            respVO.getUpdateNames().add(importUser.getName());
        });
        return respVO;
    }

    private Sort createSort(SupplierPageOrder order) {
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

        if (order.getContactName() != null) {
            orders.add(new Sort.Order(order.getContactName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "contactName"));
        }

        if (order.getContactPhone() != null) {
            orders.add(new Sort.Order(order.getContactPhone().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "contactPhone"));
        }

        if (order.getPaymentCycle() != null) {
            orders.add(new Sort.Order(order.getPaymentCycle().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "paymentCycle"));
        }

        if (order.getBankAccount() != null) {
            orders.add(new Sort.Order(order.getBankAccount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "bankAccount"));
        }

        if (order.getTaxNumber() != null) {
            orders.add(new Sort.Order(order.getTaxNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "taxNumber"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}