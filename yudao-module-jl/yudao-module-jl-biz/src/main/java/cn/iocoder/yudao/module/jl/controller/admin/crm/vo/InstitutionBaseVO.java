package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.validation.constraints.*;

/**
 * 机构/公司 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InstitutionBaseVO {

    @Schema(description = "省份")
    private String province;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotNull(message = "名字不能为空")
    private String name;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "备注信息")
    private String mark;

    @Schema(description = "机构类型枚举值", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "机构类型枚举值不能为空")
    private String type;

    @Schema(description = "发票抬头")
    private String billTitle;

    @Schema(description = "开票方式")
    private String billWay;

    @Schema(description = "发票要求")
    private String billRequest;

    @Schema(description = "银行账号")
    private String bankAccount;

    @Schema(description = "开户行")
    private String bankBranch;

    @Schema(description = "科室")
    private String department;

    @Schema(description = "企业码")
    private String code;

    @Schema(description = "资料内容")
    private String dataContent;


    @Schema(description = "法定代表人")
    private String legalRepresentative;

    @Schema(description = "注册资本")
    private String registerCapital;

    @Schema(description = "成立日期")
    private String establishDate;

    @Schema(description = "信用代码")
    private String creditCode;

    @Schema(description = "注册地址")
    private String registerAddress;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "纳税人识别号")
    private String billCode;

    @Schema(description = "网址")
    private String website;

    @Schema(description = "简介")
    private String profile;

    @Schema(description = "经营范围")
    private String businessScope;


    private List<CommonAttachment> attachmentList;


}
