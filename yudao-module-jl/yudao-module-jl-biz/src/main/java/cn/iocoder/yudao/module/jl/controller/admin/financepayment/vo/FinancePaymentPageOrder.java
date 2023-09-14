package cn.iocoder.yudao.module.jl.controller.admin.financepayment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * 财务打款 Order 设置，用于分页使用
 */
@Data
public class FinancePaymentPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String refId;

    @Schema(allowableValues = {"desc", "asc"})
    private String type;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String auditUserId;

    @Schema(allowableValues = {"desc", "asc"})
    private String auditMark;

    @Schema(allowableValues = {"desc", "asc"})
    private String auditStatus;

    @Schema(allowableValues = {"desc", "asc"})
    private String price;

    @Schema(allowableValues = {"desc", "asc"})
    private String proofUrl;

    @Schema(allowableValues = {"desc", "asc"})
    private String proofName;

    @Schema(allowableValues = {"desc", "asc"})
    private String paymentUrl;

    @Schema(allowableValues = {"desc", "asc"})
    private String paymentName;

}
