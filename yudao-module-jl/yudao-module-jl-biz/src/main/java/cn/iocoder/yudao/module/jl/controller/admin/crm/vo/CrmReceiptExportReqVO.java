package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 客户发票 Excel 导出 Request VO，参数和 CrmReceiptPageReqVO 是一致的")
@Data
public class CrmReceiptExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "发票申请编号")
    private String code;

    @Schema(description = "客户id", example = "24061")
    private Long customerId;

    @Schema(description = "合同id", example = "7977")
    private Long contractId;

    @Schema(description = "回款id", example = "11772")
    private Long fundId;

    @Schema(description = "开票金额", example = "27166")
    private BigDecimal price;

    @Schema(description = "开票日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] date;

    @Schema(description = "开票类型：增值税专用发票", example = "1")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "发票号码")
    private Integer number;

    @Schema(description = "实际开票日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] actualDate;

    @Schema(description = "物流单号")
    private String shipmentNumber;

    @Schema(description = "开发方式：电子、纸质")
    private String way;

    @Schema(description = "发票抬头id", example = "15228")
    private Long receiptHeadId;

    @Schema(description = "发票抬头")
    private String title;

    @Schema(description = "纳税人识别号")
    private String taxerNumber;

    @Schema(description = "开户行", example = "赵六")
    private String bankName;

    @Schema(description = "开户账号", example = "12964")
    private String bankAccount;

    @Schema(description = "开票地址")
    private String address;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "抬头类型", example = "2")
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

}
