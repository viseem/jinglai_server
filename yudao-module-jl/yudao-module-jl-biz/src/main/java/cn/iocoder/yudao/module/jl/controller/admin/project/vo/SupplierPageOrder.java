package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目采购单物流信息 Order 设置，用于分页使用
 */
@Data
public class SupplierPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String contactName;

    @Schema(allowableValues = {"desc", "asc"})
    private String contactPhone;

    @Schema(allowableValues = {"desc", "asc"})
    private String paymentCycle;

    @Schema(allowableValues = {"desc", "asc"})
    private String bankAccount;

    @Schema(allowableValues = {"desc", "asc"})
    private String taxNumber;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}
