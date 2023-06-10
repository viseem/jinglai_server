package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目采购单打款 Order 设置，用于分页使用
 */
@Data
public class ProcurementPaymentPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String procurementId;

    @Schema(allowableValues = {"desc", "asc"})
    private String paymentDate;

    @Schema(allowableValues = {"desc", "asc"})
    private String amount;

    @Schema(allowableValues = {"desc", "asc"})
    private String supplierId;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String proofName;

    @Schema(allowableValues = {"desc", "asc"})
    private String proofUrl;

}
