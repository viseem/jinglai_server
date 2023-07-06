package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目实验委外 Excel 导出 Request VO，参数和 ProjectCategoryOutsourcePageReqVO 是一致的")
@Data
public class ProjectCategoryOutsourceExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目id", example = "1356")
    private Long projectId;

    @Schema(description = "项目的实验名目id", example = "11020")
    private Long projectCategoryId;

    @Schema(description = "类型：项目、实验、其它", example = "2")
    private String type;

    @Schema(description = "外包内容")
    private String content;

    @Schema(description = "外包供应商id", example = "20117")
    private Integer categorySupplierId;

    @Schema(description = "供应商报价", example = "9982")
    private Integer supplierPrice;

    @Schema(description = "销售价格", example = "32231")
    private Integer salePrice;

    @Schema(description = "购买价格", example = "12412")
    private Integer buyPrice;

    @Schema(description = "凭证名字", example = "芋艿")
    private String proofName;

    @Schema(description = "凭证地址 ", example = "https://www.iocoder.cn")
    private String proofUrl;

    @Schema(description = "备注")
    private String mark;

}
