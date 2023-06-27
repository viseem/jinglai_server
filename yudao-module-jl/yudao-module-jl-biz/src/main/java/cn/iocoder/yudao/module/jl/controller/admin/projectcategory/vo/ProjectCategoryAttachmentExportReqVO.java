package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目实验名目的附件 Excel 导出 Request VO，参数和 ProjectCategoryAttachmentPageReqVO 是一致的")
@Data
public class ProjectCategoryAttachmentExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "实验名目 id", example = "4209")
    private Long projectCategoryId;

    @Schema(description = "文件名", example = "张三")
    private String fileName;

    @Schema(description = "文件地址", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "文件类型", example = "1")
    private byte[] type;

}
