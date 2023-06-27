package cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 实验室 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class LaboratoryLabBaseVO {

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "负责人", example = "27103")
    private Long userId;

    @Schema(description = "备注描述")
    private String mark;

}
