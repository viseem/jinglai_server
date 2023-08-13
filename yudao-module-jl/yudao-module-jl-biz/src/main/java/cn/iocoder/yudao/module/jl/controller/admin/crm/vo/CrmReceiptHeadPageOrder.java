package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 客户发票抬头 Order 设置，用于分页使用
 */
@Data
public class CrmReceiptHeadPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String type;

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
    private String customerId;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}
