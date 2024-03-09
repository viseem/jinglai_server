package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 动物饲养申请单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AnimalFeedOrderPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "饲养单名字", example = "李四")
    private String name;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "品系品种")
    private String breed;

    @Schema(description = "品种")
    private String breedCate;

    @Schema(description = "品系")
    private String strainCate;

    @Schema(description = "周龄体重")
    private String age;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "雌", example = "6065")
    private Integer femaleCount;

    @Schema(description = "雄", example = "4430")
    private Integer maleCount;

    @Schema(description = "供应商id", example = "24475")
    private Long supplierId;

    @Schema(description = "供应商名字", example = "李四")
    private String supplierName;

    @Schema(description = "合格证号")
    private String certificateNumber;

    @Schema(description = "许可证号")
    private String licenseNumber;

    @Schema(description = "开始日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String startDate;

    @Schema(description = "结束日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String endDate;

    @Schema(description = "有无传染性等实验")
    private Boolean hasDanger;

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

}
