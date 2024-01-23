package cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 合同收款记录 Order 设置，用于分页使用
 */
@Data
public class ContractFundLogPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String price;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String fileUrl;

    @Schema(allowableValues = {"desc", "asc"})
    private String fileName;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String paidTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String payer;

    @Schema(allowableValues = {"desc", "asc"})
    private String payee;

    @Schema(allowableValues = {"desc", "asc"})
    private String contractId;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerId;

}
