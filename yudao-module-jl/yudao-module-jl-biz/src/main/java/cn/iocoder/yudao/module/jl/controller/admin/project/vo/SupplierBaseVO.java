package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.validation.constraints.*;

/**
 * 项目采购单物流信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SupplierBaseVO {

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "联系人", example = "李四")
    private String contactName;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "结算周期")
    private String paymentCycle;

    @Schema(description = "银行卡号", example = "14108")
    private String bankAccount;

    @Schema(description = "税号")
    private String taxNumber;

    @Schema(description = "备注")
    private String mark;


    /**
     * 发票抬头
     */
    @Schema(description = "发票抬头")
    private String billTitle;

    /**
     * 开票方式
     */
    @Schema(description = "开票方式")
    private String billWay;

    /**
     * 发票要求
     */
    @Schema(description = "发票要求：textarea")
    private String billRequest;

    /**
     * 所属部门
     */
    @Schema(description = "联系人部门")
    private String contact_department;

    /**
     * 产品
     */
    @Schema(description = "产品")
    private String product;

    /**
     * 服务折扣
     */
    @Schema(description = "服务折扣")
    private String discount;

    /**
     * 联系人的职位
     */
    @Schema(description = "联系人的职位")
    private String contactLevel;

}
