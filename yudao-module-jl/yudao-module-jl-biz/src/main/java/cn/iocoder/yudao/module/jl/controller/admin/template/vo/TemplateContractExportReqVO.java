package cn.iocoder.yudao.module.jl.controller.admin.template.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 合同模板 Excel 导出 Request VO，参数和 TemplateContractPageReqVO 是一致的")
@Data
public class TemplateContractExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "文件名", example = "芋艿")
    private String fileName;

    @Schema(description = "文件地址", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "关键备注")
    private String mark;

    @Schema(description = "合同类型：项目合同、饲养合同", example = "2")
    private String type;

    @Schema(description = "合同用途")
    private String useWay;

    @Schema(description = "合同名称", example = "芋艿")
    private String name;

}
