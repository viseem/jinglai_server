package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 实验名目的操作记录创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectCategoryLogCreateReqVO extends ProjectCategoryLogBaseVO {

}
