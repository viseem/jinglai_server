package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;

import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectPageReqVO extends PageParam {
    @Schema(description = "归属：ALL MY SUB")
    private String attribute;

    @Schema(description = "in 查询 creators")
    private Long[] creators;

    // 上面那个是权限使用的，这个是直接传进来的
    @Schema(description = "in 查询 managerIds")
    private Long[] managerIds;

    @Schema(description = "in 查询 managerIds")
    private Long[] notManagerIds;

    private Long[] salesIds;

    private Integer expireDayLimit;
    private Boolean isDelay;

    private Boolean isSale;

    @Schema(description = "in 查询 managers")
    private Long[] managers;
    private Long creator;
    private Long subjectGroupId;

    private Long focusId;

    private Long tagId;

    private List<String> stageArr;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "销售线索 id", example = "15320")
    private Long salesleadId;

    @Schema(description = "项目名字", example = "赵六")
    private String name;

    @Schema(description = "项目编号", example = "赵六")
    private String code;

    @Schema(description = "项目开展阶段")
    private String stage;

    @Schema(description = "项目状态", example = "1")
    private String status;

    @Schema(description = "项目类型", example = "1")
    private String type;

    @Schema(description = "启动时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startDate;

    @Schema(description = "截止时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endDate;

    @Schema(description = "项目负责人", example = "6150")
    private Long managerId;

    @Schema(description = "参与者 ids，数组")
    private String participants;

    @Schema(description = "销售 id", example = "16310")
    private Long salesId;

    @Schema(description = "销售 id", example = "8556")
    private Long customerId;

    @Schema(description = "采购 id", example = "8556")
    private Long procurementerId;

    @Schema(description = "库管 id", example = "8556")
    private Long inventorierId;

    @Schema(description = "实验负责人 id", example = "8556")
    private Long experId;

    @Schema(description = "实验人员 id", example = "8556")
    private Long expersId;


}
