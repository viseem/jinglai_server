package cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo;

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
 * 合同发票记录 Order 设置，用于分页使用
 */
@Data
public class ContractInvoiceLogPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String code;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerId;

    @Schema(allowableValues = {"desc", "asc"})
    private String contractId;

    @Schema(allowableValues = {"desc", "asc"})
    private String price;

    @Schema(allowableValues = {"desc", "asc"})
    private String date;

    @Schema(allowableValues = {"desc", "asc"})
    private String type;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String number;

    @Schema(allowableValues = {"desc", "asc"})
    private String actualDate;

    @Schema(allowableValues = {"desc", "asc"})
    private String shipmentNumber;

    @Schema(allowableValues = {"desc", "asc"})
    private String way;

    @Schema(allowableValues = {"desc", "asc"})
    private String receiptHeadId;

    @Schema(allowableValues = {"desc", "asc"})
    private String title;

    @Schema(allowableValues = {"desc", "asc"})
    private String taxerNumber;

    @Schema(allowableValues = {"desc", "asc"})
    private String bankName;

    @Schema(allowableValues = {"desc", "asc"})
    private String bankAccount;

    @Schema(allowableValues = {"desc", "asc"})
    private String address;

    @Schema(allowableValues = {"desc", "asc"})
    private String phone;

    @Schema(allowableValues = {"desc", "asc"})
    private String headType;

    @Schema(allowableValues = {"desc", "asc"})
    private String sendContact;

    @Schema(allowableValues = {"desc", "asc"})
    private String sendPhone;

    @Schema(allowableValues = {"desc", "asc"})
    private String sendAddress;

    @Schema(allowableValues = {"desc", "asc"})
    private String sendProvince;

    @Schema(allowableValues = {"desc", "asc"})
    private String sendCity;

    @Schema(allowableValues = {"desc", "asc"})
    private String sendArea;

    @Schema(allowableValues = {"desc", "asc"})
    private String manager;

    @Schema(allowableValues = {"desc", "asc"})
    private String receivedPrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String status;

}
