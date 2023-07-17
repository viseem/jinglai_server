package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 动物饲养笼架 Excel 导出 Request VO，参数和 AnimalShelfPageReqVO 是一致的")
@Data
public class AnimalShelfExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名字", example = "王五")
    private String name;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "缩略图名称", example = "芋艿")
    private String fileName;

    @Schema(description = "缩略图地址", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "管理人id", example = "28229")
    private Long managerId;

    @Schema(description = "描述说明")
    private String mark;

    @Schema(description = "饲养室id", example = "24047")
    private Long roomId;

    @Schema(description = "排序")
    private Integer weight;

}
