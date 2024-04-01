package cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo;

import cn.iocoder.yudao.module.jl.entity.laboratory.*;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Schema(description = "管理后台 - 实验名目 Response VO")
@Data
@ToString(callSuper = true)
public class CategorySimpleRespVO{

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "名字不能为空")
    private String name;

    @Schema(description = "技术难度")
    private String difficultyLevel;

    @Schema(description = "重要备注说明")
    private String mark;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "实验室id")
    private Long labId;

    @Schema(description = "历史操作次数", example = "0")
    private Integer actionCount;

  /*  @Schema(description = "原理")
    private String principle;

    @Schema(description = "目的")
    private String purpose;

    @Schema(description = "准备")
    private String preparation;

    @Schema(description = "注意事项")
    private String caution;*/

    @Schema(description = "标签")
    private String tagIds;

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10130")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;


}
