package cn.iocoder.yudao.module.jl.service.invoiceapplication;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo.InvoiceItemVO;
import cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication.vo.*;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import cn.iocoder.yudao.module.jl.entity.invoiceapplication.InvoiceApplication;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.enums.ContractInvoiceAuditStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ContractInvoiceStatusEnums;
import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import cn.iocoder.yudao.module.jl.mapper.invoiceapplication.InvoiceApplicationMapper;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogRepository;
import cn.iocoder.yudao.module.jl.repository.invoiceapplication.InvoiceApplicationRepository;
import cn.iocoder.yudao.module.jl.repository.user.UserRepository;
import cn.iocoder.yudao.module.jl.service.commonattachment.CommonAttachmentServiceImpl;
import cn.iocoder.yudao.module.jl.service.contractinvoicelog.ContractInvoiceLogServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProjectConstractServiceImpl;
import cn.iocoder.yudao.module.jl.utils.DateAttributeGenerator;
import cn.iocoder.yudao.module.system.api.dict.DictDataApiImpl;
import cn.iocoder.yudao.module.system.api.dict.dto.DictDataRespDTO;
import cn.iocoder.yudao.module.system.enums.DictTypeConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.jetbrains.annotations.NotNull;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 开票申请 Service 实现类
 */
@Service
@Validated
public class InvoiceApplicationServiceImpl implements InvoiceApplicationService {

    @Resource
    private InvoiceApplicationRepository invoiceApplicationRepository;

    @Resource
    private InvoiceApplicationMapper invoiceApplicationMapper;

    @Resource
    private UserRepository userRepository;

    @Resource
    private ContractInvoiceLogRepository contractInvoiceLogRepository;

    @Resource
    private ContractInvoiceLogOnlyRepository contractInvoiceLogOnlyRepository;

    @Resource
    private DateAttributeGenerator dateAttributeGenerator;

    @Resource
    private CommonAttachmentServiceImpl commonAttachmentService;

    @Resource
    private DictDataApiImpl dictDataApi;

    @Resource
    private ProjectConstractServiceImpl projectConstractServiceImpl;

    @Override
    @Transactional
    public Long createInvoiceApplication(InvoiceApplicationCreateReqVO createReqVO) {

        if (createReqVO.getContractInvoiceLogList() == null || createReqVO.getContractInvoiceLogList().isEmpty()) {
            throw exception(CONTRACT_INVOICE_LOG_LIST_EMPTY);
        }
        InvoiceApplication invoiceApplication = invoiceApplicationMapper.toEntity(createReqVO);
        List<ContractInvoiceLog> contractInvoiceLogList = createReqVO.getContractInvoiceLogList();

        saveInvoiceApplicationAndInvoiceList(invoiceApplication, contractInvoiceLogList);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(invoiceApplication.getId(), "INVOICE_APPLICATION", createReqVO.getAttachmentList());

        // 返回
        return invoiceApplication.getId();
    }

    @Transactional
    public void saveInvoiceApplicationAndInvoiceList(InvoiceApplication invoiceApplication, List<ContractInvoiceLog> contractInvoiceLogList) {
        Long salesId = null;
        if (invoiceApplication.getId() == null) {
            salesId = getLoginUserId();
            // 获取当前登录人，即为销售
            Optional<User> byId = userRepository.findById(getLoginUserId());
            if (byId.isEmpty()) {
                throw exception(USER_NOT_EXISTS);
            }
            User sales = byId.get();

            invoiceApplication.setSalesId(sales.getId());
            invoiceApplication.setSalesName(sales.getNickname());
        }
        invoiceApplication.setInvoiceCount(contractInvoiceLogList.size());
        invoiceApplicationRepository.save(invoiceApplication);

        //这里有很多id，从前端带过来把
        for (ContractInvoiceLog contractInvoiceLog : contractInvoiceLogList) {


            contractInvoiceLog.setTitle(invoiceApplication.getHead());
            contractInvoiceLog.setTaxerNumber(invoiceApplication.getTaxNumber());
            contractInvoiceLog.setBankAccount(invoiceApplication.getBankAccount());
            contractInvoiceLog.setBankName(invoiceApplication.getBankName());
            contractInvoiceLog.setSendAddress(invoiceApplication.getSendAddress());
            contractInvoiceLog.setPhone(invoiceApplication.getPhone());
            contractInvoiceLog.setAddress(invoiceApplication.getAddress());
            contractInvoiceLog.setSendEmail(invoiceApplication.getEmail());
            if (salesId != null) {
                contractInvoiceLog.setSalesId(salesId);
            }
            contractInvoiceLog.setApplicationId(invoiceApplication.getId());

            // 设置发票的状态为审批中
            contractInvoiceLog.setAuditStatus(invoiceApplication.getStatus());
        }
        contractInvoiceLogRepository.saveAll(contractInvoiceLogList);


    }

    @Override
    @Transactional
    public void updateInvoiceApplication(InvoiceApplicationUpdateReqVO updateReqVO) {

        // 校验存在
        validateInvoiceApplicationExists(updateReqVO.getId());

        if (updateReqVO.getContractInvoiceLogList() == null || updateReqVO.getContractInvoiceLogList().isEmpty()) {
            throw exception(CONTRACT_INVOICE_LOG_LIST_EMPTY);
        }

        // 更新
        InvoiceApplication invoiceApplication = invoiceApplicationMapper.toEntity(updateReqVO);
//        invoiceApplicationRepository.save(invoiceApplication);
        List<ContractInvoiceLog> contractInvoiceLogList = updateReqVO.getContractInvoiceLogList();
        saveInvoiceApplicationAndInvoiceList(invoiceApplication, contractInvoiceLogList);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(updateReqVO.getId(), "INVOICE_APPLICATION", updateReqVO.getAttachmentList());
    }

    @Override
    @Transactional
    public void updateInvoiceApplicationStatus(InvoiceApplicationUpdateStatusReqVO updateReqVO) {
        // 获取当前登录人，即为审批人
        Optional<User> byId = userRepository.findById(getLoginUserId());
        if (byId.isEmpty()) {
            throw exception(USER_NOT_EXISTS);
        }
        invoiceApplicationRepository.updateStatusAndAuditIdAndAuditNameAndAuditMarkById(updateReqVO.getStatus(), getLoginUserId(), byId.get().getNickname(), updateReqVO.getAuditMark(), updateReqVO.getId());

        if (updateReqVO.getStatus().equals(ContractInvoiceAuditStatusEnums.ACCEPT.getStatus())) {
            contractInvoiceLogRepository.updateStatusByApplicationId(ContractInvoiceStatusEnums.NOT_INVOICE.getStatus(), updateReqVO.getId());
        }
        contractInvoiceLogRepository.updateAuditStatusByApplicationId(updateReqVO.getStatus(), updateReqVO.getId());
    }

    @Override
    public void deleteInvoiceApplication(Long id) {
        // 校验存在
        validateInvoiceApplicationExists(id);
        // 删除
        invoiceApplicationRepository.deleteById(id);
    }

    private InvoiceApplication validateInvoiceApplicationExists(Long id) {
        Optional<InvoiceApplication> byId = invoiceApplicationRepository.findById(id);
        if (byId.isEmpty()) {
            throw exception(INVOICE_APPLICATION_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<InvoiceApplication> getInvoiceApplication(Long id) {
        return invoiceApplicationRepository.findById(id);
    }

    @Override
    public List<InvoiceApplication> getInvoiceApplicationList(Collection<Long> ids) {
        return StreamSupport.stream(invoiceApplicationRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<InvoiceApplication> getInvoiceApplicationPage(InvoiceApplicationPageReqVO pageReqVO, InvoiceApplicationPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<InvoiceApplication> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            //如果不是any，则都是in查询
            if (pageReqVO.getAttribute() != null && !pageReqVO.getAttribute().equals(DataAttributeTypeEnums.ANY.getStatus())) {
                Long[] users = pageReqVO.getSalesId() != null ? dateAttributeGenerator.processAttributeUsersWithUserId(pageReqVO.getAttribute(), pageReqVO.getSalesId()) : dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
                predicates.add(root.get("salesId").in(Arrays.stream(users).toArray()));
            }

            if (pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if (pageReqVO.getCustomerName() != null) {
                predicates.add(cb.like(root.get("customerName"), "%" + pageReqVO.getCustomerName() + "%"));
            }

            if (pageReqVO.getCustomerInvoiceHeadId() != null) {
                predicates.add(cb.equal(root.get("customerInvoiceHeadId"), pageReqVO.getCustomerInvoiceHeadId()));
            }

            if (pageReqVO.getRequire() != null) {
                predicates.add(cb.equal(root.get("require"), pageReqVO.getRequire()));
            }

            if (pageReqVO.getHead() != null) {
                predicates.add(cb.equal(root.get("head"), pageReqVO.getHead()));
            }

            if (pageReqVO.getTaxNumber() != null) {
                predicates.add(cb.equal(root.get("taxNumber"), pageReqVO.getTaxNumber()));
            }

            if (pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }

            if (pageReqVO.getSendAddress() != null) {
                predicates.add(cb.equal(root.get("sendAddress"), pageReqVO.getSendAddress()));
            }

            if (pageReqVO.getPhone() != null) {
                predicates.add(cb.equal(root.get("phone"), pageReqVO.getPhone()));
            }

            if (pageReqVO.getBankName() != null) {
                predicates.add(cb.like(root.get("bankName"), "%" + pageReqVO.getBankName() + "%"));
            }

            if (pageReqVO.getBankAccount() != null) {
                predicates.add(cb.equal(root.get("bankAccount"), pageReqVO.getBankAccount()));
            }

            if (pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if (pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if (pageReqVO.getInvoiceCount() != null) {
                predicates.add(cb.equal(root.get("invoiceCount"), pageReqVO.getInvoiceCount()));
            }

            if (pageReqVO.getTotalAmount() != null) {
                predicates.add(cb.equal(root.get("totalAmount"), pageReqVO.getTotalAmount()));
            }

            if (pageReqVO.getInvoicedAmount() != null) {
                predicates.add(cb.equal(root.get("invoicedAmount"), pageReqVO.getInvoicedAmount()));
            }

            if (pageReqVO.getReceivedAmount() != null) {
                predicates.add(cb.equal(root.get("receivedAmount"), pageReqVO.getReceivedAmount()));
            }

            if (pageReqVO.getRefundedAmount() != null) {
                predicates.add(cb.equal(root.get("refundedAmount"), pageReqVO.getRefundedAmount()));
            }

            if (pageReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), pageReqVO.getSalesId()));
            }

            if (pageReqVO.getSalesName() != null) {
                predicates.add(cb.like(root.get("salesName"), "%" + pageReqVO.getSalesName() + "%"));
            }

            if (pageReqVO.getAuditId() != null) {
                predicates.add(cb.equal(root.get("auditId"), pageReqVO.getAuditId()));
            }

            if (pageReqVO.getAuditName() != null) {
                predicates.add(cb.like(root.get("auditName"), "%" + pageReqVO.getAuditName() + "%"));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<InvoiceApplication> page = invoiceApplicationRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<InvoiceApplicationExcelRowItemVO> getInvoiceApplicationList(InvoiceApplicationExportReqVO exportReqVO) {

        InvoiceApplication invoiceApplication = validateInvoiceApplicationExists(exportReqVO.getId());
        List<InvoiceApplicationExcelRowItemVO> itemList = new ArrayList<>();

        InvoiceApplicationExcelRowItemVO rowItem1 = new InvoiceApplicationExcelRowItemVO();
        rowItem1.setCol1("开票申请");
        itemList.add(rowItem1);

        InvoiceApplicationExcelRowItemVO rowItem2 = new InvoiceApplicationExcelRowItemVO();
        rowItem2.setCol1("销售：");
        rowItem2.setCol2(invoiceApplication.getSalesName());
        rowItem2.setCol3("客户姓名：");
        rowItem2.setCol4(invoiceApplication.getCustomerName());
        itemList.add(rowItem2);

        InvoiceApplicationExcelRowItemVO rowItem3 = new InvoiceApplicationExcelRowItemVO();
        String content = String.format(
                "单位抬头：%s\n" +
                "税号：%s\n" +
                "单位地址：%s\n" +
                "电话：%s\n" +
                "开户银行：%s\n" +
                "银行账户：%s\n",
                Objects.requireNonNullElse(invoiceApplication.getHead(), ""),
                Objects.requireNonNullElse(invoiceApplication.getTaxNumber(), ""),
                Objects.requireNonNullElse(invoiceApplication.getAddress(), ""),
                Objects.requireNonNullElse(invoiceApplication.getPhone(), ""),
                Objects.requireNonNullElse(invoiceApplication.getBankName(), ""),
                Objects.requireNonNullElse(invoiceApplication.getBankAccount(), "")
                );
        rowItem3.setCol1(content);
        rowItem3.setCol4("寄送地址/备注：\n"+Objects.requireNonNullElse(invoiceApplication.getSendAddress(), "")+"\n"+Objects.requireNonNullElse(invoiceApplication.getMark(), ""));
        itemList.add(rowItem3);


        List<ContractInvoiceLog> invoiceLogList = contractInvoiceLogRepository.findByApplicationId(exportReqVO.getId());
        if (invoiceLogList != null && !invoiceLogList.isEmpty()) {

            List<DictDataRespDTO> invoiceTypes = dictDataApi.getDictDataByType(DictTypeConstants.RECEIPT_TYPE);


            // 遍历invoiceLogList，使用json解析其中itemJsonStr(name,price,count,amount)字符，并放入itemList
            ObjectMapper objectMapper = new ObjectMapper();
            int invoiceCount = 0;
            for (ContractInvoiceLog log : invoiceLogList) {
                try {
                    invoiceCount++;

                    //从invoiceTypes中查找value等于log.getType的label
                    String invoiceType = invoiceTypes.stream().filter(dictDataRespDTO -> dictDataRespDTO.getValue().equals(log.getType())).findFirst().map(DictDataRespDTO::getLabel).orElse("");

                    InvoiceApplicationExcelRowItemVO invoiceItem = new InvoiceApplicationExcelRowItemVO();
                    invoiceItem.setCol1("发票"+invoiceCount);
                    invoiceItem.setCol2("发票种类：");
                    invoiceItem.setCol3(invoiceType);
                    invoiceItem.setCol4("发票类型：");
                    invoiceItem.setCol5(log.getHeadType());
                    invoiceItem.setCol6("开票单位：");
                    invoiceItem.setCol7(log.getFromTitle());
                    itemList.add(invoiceItem);
                    addHeadRow(itemList);


                    List<InvoiceItemVO> items = objectMapper.readValue(log.getItemJsonStr(), new TypeReference<>() {
                    });
                    for (InvoiceItemVO item : items) {
                        InvoiceApplicationExcelRowItemVO rowItemVO = processRowItem(item);
                        itemList.add(rowItemVO);
                    }
                } catch (Exception e) {
                    System.out.println("InvoiceApplication export excel err: 解析json失败");
                }
            }

        }

        return itemList;
    }

    private void addHeadRow(List<InvoiceApplicationExcelRowItemVO> itemList) {
        InvoiceApplicationExcelRowItemVO rowItemVO = new InvoiceApplicationExcelRowItemVO();
        rowItemVO.setCol1("名称");
        rowItemVO.setCol2("品牌");
        rowItemVO.setCol3("货号");
        rowItemVO.setCol4("规格");
        rowItemVO.setCol5("单价(元)");
        rowItemVO.setCol6("数量");
        rowItemVO.setCol7("金额");
        itemList.add(rowItemVO);
    }

    @NotNull
    private static InvoiceApplicationExcelRowItemVO processRowItem(InvoiceItemVO item) {
        InvoiceApplicationExcelRowItemVO rowItemVO = new InvoiceApplicationExcelRowItemVO();
        rowItemVO.setCol1(item.getName());
        rowItemVO.setCol2(item.getBrand());
        rowItemVO.setCol3(item.getProductCode());
        rowItemVO.setCol4(item.getSpec());
        rowItemVO.setCol5(item.getPrice());
        rowItemVO.setCol6(item.getQuantity());
        rowItemVO.setCol7(item.getAmount());
        return rowItemVO;
    }

    private Sort createSort(InvoiceApplicationPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getCustomerName() != null) {
            orders.add(new Sort.Order(order.getCustomerName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerName"));
        }

        if (order.getCustomerInvoiceHeadId() != null) {
            orders.add(new Sort.Order(order.getCustomerInvoiceHeadId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerInvoiceHeadId"));
        }

        if (order.getRequire() != null) {
            orders.add(new Sort.Order(order.getRequire().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "require"));
        }

        if (order.getHead() != null) {
            orders.add(new Sort.Order(order.getHead().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "head"));
        }

        if (order.getTaxNumber() != null) {
            orders.add(new Sort.Order(order.getTaxNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "taxNumber"));
        }

        if (order.getAddress() != null) {
            orders.add(new Sort.Order(order.getAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        }

        if (order.getSendAddress() != null) {
            orders.add(new Sort.Order(order.getSendAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sendAddress"));
        }

        if (order.getPhone() != null) {
            orders.add(new Sort.Order(order.getPhone().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "phone"));
        }

        if (order.getBankName() != null) {
            orders.add(new Sort.Order(order.getBankName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "bankName"));
        }

        if (order.getBankAccount() != null) {
            orders.add(new Sort.Order(order.getBankAccount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "bankAccount"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getInvoiceCount() != null) {
            orders.add(new Sort.Order(order.getInvoiceCount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "invoiceCount"));
        }

        if (order.getTotalAmount() != null) {
            orders.add(new Sort.Order(order.getTotalAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "totalAmount"));
        }

        if (order.getInvoicedAmount() != null) {
            orders.add(new Sort.Order(order.getInvoicedAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "invoicedAmount"));
        }

        if (order.getReceivedAmount() != null) {
            orders.add(new Sort.Order(order.getReceivedAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receivedAmount"));
        }

        if (order.getRefundedAmount() != null) {
            orders.add(new Sort.Order(order.getRefundedAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refundedAmount"));
        }

        if (order.getSalesId() != null) {
            orders.add(new Sort.Order(order.getSalesId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salesId"));
        }

        if (order.getSalesName() != null) {
            orders.add(new Sort.Order(order.getSalesName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salesName"));
        }

        if (order.getAuditId() != null) {
            orders.add(new Sort.Order(order.getAuditId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "auditId"));
        }

        if (order.getAuditName() != null) {
            orders.add(new Sort.Order(order.getAuditName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "auditName"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}