package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目反馈分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectFeedbackPageReqVO extends PageParam {

    @Schema(description = "归属：ALL MY SUB")
    private String attribute = DataAttributeTypeEnums.ALL.getStatus();

    @Schema(description = "in 查询 creators")
    private Long[] creators;

    private Long creator;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目 id", example = "15989")
    private Long projectId;

    @Schema(description = "实验名目 id", example = "5559")
    private Long projectCategoryId;

    @Schema(description = "售前/售中/售后")
    private String projectStage;

    @Schema(description = "具体的反馈内容")
    private String feedType;

    @Schema(description = "内部人员 id", example = "5172")
    private Long userId;

    @Schema(description = "客户 id", example = "13743")
    private Long customerId;

    @Schema(description = "字典：内部反馈/客户反馈", example = "2")
    private String type;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "反馈的内容")
    private String content;

    @Schema(description = "处理结果")
    private String result;

}
