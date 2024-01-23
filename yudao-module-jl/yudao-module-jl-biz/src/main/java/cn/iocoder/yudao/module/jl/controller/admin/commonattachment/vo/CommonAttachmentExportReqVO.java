package cn.iocoder.yudao.module.jl.controller.admin.commonattachment.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 通用附件 Excel 导出 Request VO，参数和 CommonAttachmentPageReqVO 是一致的")
@Data
public class CommonAttachmentExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "类型", example = "2")
    private String type;

    @Schema(description = "id", example = "10568")
    private Integer refId;

    @Schema(description = "文件名称", example = "赵六")
    private String fileName;

    @Schema(description = "文件地址", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "备注")
    private String mark;

}
