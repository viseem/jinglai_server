package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 库管操作附件记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InventoryOptAttachmentPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目 id", example = "27687")
    private Long projectId;

    @Schema(description = "类型，采购，寄送，自取", example = "2")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "采购，寄送，自取的 id", example = "28696")
    private Long refId;

    @Schema(description = "采购，寄送，自取的子元素 id", example = "13927")
    private Long refItemId;

    @Schema(description = "名称", example = "张三")
    private String fileName;

    @Schema(description = "地址", example = "https://www.iocoder.cn")
    private String fileUrl;

}
