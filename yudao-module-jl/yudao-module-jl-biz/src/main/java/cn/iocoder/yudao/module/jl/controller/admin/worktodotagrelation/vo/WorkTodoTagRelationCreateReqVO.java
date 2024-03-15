package cn.iocoder.yudao.module.jl.controller.admin.worktodotagrelation.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 工作任务 TODO 与标签的关联创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkTodoTagRelationCreateReqVO extends WorkTodoTagRelationBaseVO {

}
