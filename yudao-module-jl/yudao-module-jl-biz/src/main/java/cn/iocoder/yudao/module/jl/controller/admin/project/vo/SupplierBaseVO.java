package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
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

    @Schema(description = "分类类型")
    private String cateType;

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

    @Schema(description = "擅长领域")
    private String goodAt;

    private List<CommonAttachment> attachmentList;



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

    /**
     * 支行
     */
    @Schema(description = "支行")
    private String subBranch;

    /**
     * 支行
     */
    @Schema(description = "渠道类型")
    private String channelType;

    @Schema(description = "服务目录")
    private String serviceCatalog;

    @Schema(description = "核心优势")
    private String advantage;

    @Schema(description = "劣势")
    private String disadvantage;

    @Schema(description = "公司负责人")
    private String companyManager;

    @Schema(description = "商务负责人")
    private String businessManager;

    @Schema(description = "技术负责人")
    private String techManager;

    @Schema(description = "售中负责人")
    private String manager;

    @Schema(description = "售后负责人")
    private String afterManager;

    @Schema(description = "公司地址")
    private String address;

    @Schema(description = "标签")
    private String tagIds;
}
