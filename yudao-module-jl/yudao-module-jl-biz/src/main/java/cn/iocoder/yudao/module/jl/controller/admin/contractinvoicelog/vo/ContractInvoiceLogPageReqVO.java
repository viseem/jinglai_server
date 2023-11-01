package cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo;

import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 合同发票记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractInvoiceLogPageReqVO extends PageParam {

    @Schema(description = "归属：ALL MY SUB")
    private String attribute = DataAttributeTypeEnums.MY.getStatus();

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "发票申请编号")
    private String code;

    @Schema(description = "客户id", example = "18855")
    private Long customerId;

    @Schema(description = "合同id", example = "397")
    private Long contractId;

    @Schema(description = "开票金额", example = "7678")
    private Long price;

    @Schema(description = "开票日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] date;

    @Schema(description = "开票类型：增值税专用发票", example = "1")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "发票号码")
    private String number;

    @Schema(description = "实际开票日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] actualDate;

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

    @Schema(description = "实际已收", example = "2937")
    private Long receivedPrice;

    @Schema(description = "状态", example = "1")
    private String status;

}
