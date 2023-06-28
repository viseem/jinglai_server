package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 项目实验名目的状态变更审批创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectCategoryApprovalCreateReqVO extends ProjectCategoryApprovalBaseVO {

}
