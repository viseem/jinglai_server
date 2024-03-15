package cn.iocoder.yudao.module.jl.controller.admin.worktodotag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 工作任务 TODO 的标签 Order 设置，用于分页使用
 */
@Data
public class WorkTodoTagPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String type;

}
