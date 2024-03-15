package cn.iocoder.yudao.module.jl.controller.admin.worktodotagrelation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 工作任务 TODO 与标签的关联 Order 设置，用于分页使用
 */
@Data
public class WorkTodoTagRelationPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String todoId;

    @Schema(allowableValues = {"desc", "asc"})
    private String tagId;

}
