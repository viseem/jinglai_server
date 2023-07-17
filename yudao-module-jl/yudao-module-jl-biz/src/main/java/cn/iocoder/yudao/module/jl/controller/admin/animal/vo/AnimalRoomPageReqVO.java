package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 动物饲养室分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AnimalRoomPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "管理人id", example = "22285")
    private Long managerId;

    @Schema(description = "描述说明")
    private String mark;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "排序")
    private Integer weight;

    @Schema(description = "缩略图名称", example = "张三")
    private String fileName;

    @Schema(description = "缩略图地址", example = "https://www.iocoder.cn")
    private String fileUrl;

}
