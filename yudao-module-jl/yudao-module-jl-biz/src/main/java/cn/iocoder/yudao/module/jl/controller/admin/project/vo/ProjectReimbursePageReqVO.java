package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目报销分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectReimbursePageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "报销类型：物流成本、差旅费、其它", example = "2")
    private String type;

    @Schema(description = "项目的实验名目id", example = "29942")
    private Integer projectCategoryId;

    @Schema(description = "项目id", example = "15738")
    private Integer projectId;

    @Schema(description = "报销内容")
    private String content;

    @Schema(description = "凭证名字", example = "芋艿")
    private String proofName;

    @Schema(description = "凭证地址", example = "https://www.iocoder.cn")
    private String proofUrl;

    @Schema(description = "报销金额", example = "31733")
    private Integer price;

    @Schema(description = "备注")
    private String mark;

}
