package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 动物饲养笼架 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AnimalShelfBaseVO {

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "名字不能为空")
    private String name;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "缩略图名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "缩略图名称不能为空")
    private String fileName;

    @Schema(description = "缩略图地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "缩略图地址不能为空")
    private String fileUrl;

    @Schema(description = "位置", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "位置不能为空")
    private String location;

    @Schema(description = "管理人id", example = "28229")
    private Long managerId;

    @Schema(description = "描述说明")
    private String mark;

    @Schema(description = "饲养室id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24047")
    @NotNull(message = "饲养室id不能为空")
    private Long roomId;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer weight;

    private Integer colCount;

    private Integer rowCount;

    private Integer capacity;

}
