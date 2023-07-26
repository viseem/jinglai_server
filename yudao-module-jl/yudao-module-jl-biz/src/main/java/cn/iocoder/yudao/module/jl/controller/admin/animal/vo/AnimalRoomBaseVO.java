package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 动物饲养室 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AnimalRoomBaseVO {

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "管理人id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22285")
    @NotNull(message = "管理人id不能为空")
    private Long managerId;

    @Schema(description = "描述说明")
    private String mark;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer weight;

    @Schema(description = "缩略图名称", example = "张三")
    private String fileName;

    @Schema(description = "缩略图地址", example = "https://www.iocoder.cn")
    private String fileUrl;

}
