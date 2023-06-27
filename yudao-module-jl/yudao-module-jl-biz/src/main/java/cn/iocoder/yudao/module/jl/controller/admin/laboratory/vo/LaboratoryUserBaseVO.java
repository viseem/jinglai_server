package cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 实验室人员 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class LaboratoryUserBaseVO {

    @Schema(description = "实验室 id", example = "11854")
    private Long labId;

    @Schema(description = "人员 id", example = "14180")
    private Long userId;

    @Schema(description = "备注描述")
    private String mark;

    @Schema(description = "实验室人员等级")
    private String rank;

}
