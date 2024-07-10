package cn.iocoder.yudao.module.jl.controller.admin.dept.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 部门更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeptUpdateReqVO extends DeptBaseVO {

    @Schema(description = "部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12587")
    @NotNull(message = "部门id不能为空")
    private Long id;

}
