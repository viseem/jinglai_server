package cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo;

import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.enums.ContractFundStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ContractInvoiceStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectFundEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 合同发票记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ContractInvoiceLogBaseVO {

    @Schema(description = "发票申请编号")
    private String code;

    @Schema(description = "开票凭证")
    private String fileUrl;

    private String fileName;

    @Schema(description = "附件")
    private String attachmentUrl;

    private String attachmentName;

    @Schema(description = "客户id")
    private Long customerId;

    @Schema(description = "项目id")
    private Long projectId;

    @Schema(description = "销售id")
    private Long salesId;

    @Schema(description = "合同id", requiredMode = Schema.RequiredMode.REQUIRED, example = "397")
    @NotNull(message = "合同id不能为空")
    private Long contractId;

    @Schema(description = "开票金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "7678")
    @NotNull(message = "开票金额不能为空")
    private BigDecimal price;

    private String priceStatus = ContractInvoiceStatusEnums.RECEIVED_NO.getStatus();

    @Schema(description = "开票日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime date;

    @Schema(description = "开票类型：增值税专用发票", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "开票类型：增值税专用发票不能为空")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "发票号码")
    private String number;

    @Schema(description = "实际开票日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime actualDate;

    @Schema(description = "物流单号")
    private String shipmentNumber;

    @Schema(description = "开发方式：电子、纸质")
    private String way;

    @Schema(description = "发票抬头id", example = "20307")
    private Long receiptHeadId;

    @Schema(description = "发票抬头")
    private String title;

    @Schema(description = "纳税人识别号")
    private String taxerNumber;

    @Schema(description = "开户行", example = "王五")
    private String bankName;

    @Schema(description = "开户账号", example = "14877")
    private String bankAccount;

    @Schema(description = "开票地址")
    private String address;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "抬头类型", example = "1")
    private String headType;

    @Schema(description = "邮寄联系人")
    private String sendContact;

    @Schema(description = "联系电话")
    private String sendPhone;

    @Schema(description = "电子邮箱")
    private String sendEmail;

    @Schema(description = "详细地址")
    private String sendAddress;

    @Schema(description = "邮寄省份")
    private String sendProvince;

    @Schema(description = "邮寄市")
    private String sendCity;

    @Schema(description = "邮寄区")
    private String sendArea;

    @Schema(description = "负责人")
    private Long manager;

    @Schema(description = "实际已收")
    private BigDecimal receivedPrice=BigDecimal.ZERO;

    @Schema(description = "状态")
    private String status = ContractInvoiceStatusEnums.NOT_INVOICE.getStatus();

    @Schema(description = "核验人")
    private Long auditId;

    private ProjectConstractOnly contract;

    private CustomerOnly customer;

    private User auditor;

    private User user;

}
