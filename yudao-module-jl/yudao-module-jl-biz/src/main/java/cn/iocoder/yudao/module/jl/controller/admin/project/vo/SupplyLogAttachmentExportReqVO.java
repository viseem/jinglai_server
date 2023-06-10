package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 库管项目物资库存变更日志附件 Excel 导出 Request VO，参数和 SupplyLogAttachmentPageReqVO 是一致的")
@Data
public class SupplyLogAttachmentExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目物资日志表id", example = "24682")
    private Long projectSupplyLogId;

    @Schema(description = "附件名称", example = "王五")
    private String fileName;

    @Schema(description = "附件地址", example = "https://www.iocoder.cn")
    private String fileUrl;

}
