package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目实验委外供应商 Excel 导出 Request VO，参数和 ProjectCategorySupplierPageReqVO 是一致的")
@Data
public class ProjectCategorySupplierExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名称", example = "张三")
    private String name;

    @Schema(description = "联系人", example = "芋艿")
    private String contactName;

    @Schema(description = "联系方式")
    private String contactPhone;

    @Schema(description = "擅长领域")
    private String advantage;

}
