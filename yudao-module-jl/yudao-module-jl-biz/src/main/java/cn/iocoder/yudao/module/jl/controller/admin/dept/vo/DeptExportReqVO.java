package cn.iocoder.yudao.module.jl.controller.admin.dept.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 部门 Excel 导出 Request VO，参数和 DeptPageReqVO 是一致的")
@Data
public class DeptExportReqVO {

    @Schema(description = "部门名称", example = "张三")
    private String name;

    @Schema(description = "父部门id", example = "7034")
    private Long parentId;

    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "负责人", example = "23928")
    private Long leaderUserId;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "部门状态（0正常 1停用）", example = "2")
    private Byte status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
