package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

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
 * 客户联系人 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CrmContactBaseVO {

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "姓名不能为空")
    private String name;

    @Schema(description = "性别")
    private Byte gender;

    @Schema(description = "客户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19449")
    @NotNull(message = "客户id不能为空")
    private Integer customerId;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "电话")
    private String tel;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "职务")
    private String position;

    @Schema(description = "是否决策者")
    private Boolean isMaker;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String area;

    @Schema(description = "下次联系时间")
    private String nextContactTime;

    @Schema(description = "备注")
    private String mark;

}
