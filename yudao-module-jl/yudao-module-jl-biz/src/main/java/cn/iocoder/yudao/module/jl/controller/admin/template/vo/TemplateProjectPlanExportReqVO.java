package cn.iocoder.yudao.module.jl.controller.admin.template.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目方案模板 Excel 导出 Request VO，参数和 TemplateProjectPlanPageReqVO 是一致的")
@Data
public class TemplateProjectPlanExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "参考文件", example = "张三")
    private String fileName;

    @Schema(description = "参考文件地址", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "模板：方案富文本")
    private String content;

}
