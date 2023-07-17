package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 动物饲养申请单 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AnimalFeedOrderBaseVO {

    @Schema(description = "饲养单名字", example = "李四")
    private String name;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "品系品种", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "品系品种不能为空")
    private String breed;

    @Schema(description = "周龄体重", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "周龄体重不能为空")
    private String age;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @Schema(description = "雌", requiredMode = Schema.RequiredMode.REQUIRED, example = "6065")
    @NotNull(message = "雌不能为空")
    private Integer femaleCount;

    @Schema(description = "雄", requiredMode = Schema.RequiredMode.REQUIRED, example = "4430")
    @NotNull(message = "雄不能为空")
    private Integer maleCount;

    @Schema(description = "供应商id", example = "24475")
    private Long supplierId;

    @Schema(description = "供应商名字", example = "李四")
    private String supplierName;

    @Schema(description = "合格证号")
    private String certificateNumber;

    @Schema(description = "许可证号")
    private String licenseNumber;

    @Schema(description = "开始日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "开始日期不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String startDate;

    @Schema(description = "结束日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "结束日期不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String endDate;

    @Schema(description = "有无传染性等实验", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "有无传染性等实验不能为空")
    private Boolean hasDanger;

    @Schema(description = "水迷宫等设备需求id：先做成字典")
    private String requestIds;

    @Schema(description = "水迷宫等设备需求id：先做成字典")
    private String conditionTypes;

    @Schema(description = "饲养类型：普通饲养", example = "1")
    private String feedType;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "项目id", example = "14724")
    private Long projectId;

    @Schema(description = "客户id", example = "16137")
    private Long customerId;

    @Schema(description = "状态")
    private String stage;

    @Schema(description = "回复")
    private String reply;

    @Schema(description = "计费规则")
    private String billRules;

    @Schema(description = "单价")
    private Integer unitFee;

    @Schema(description = "入库备注")
    private String inMark;
}
