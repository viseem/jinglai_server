package cn.iocoder.yudao.module.jl.controller.admin.worktodotag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 工作任务 TODO 的标签创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkTodoTagCreateReqVO extends WorkTodoTagBaseVO {

}
