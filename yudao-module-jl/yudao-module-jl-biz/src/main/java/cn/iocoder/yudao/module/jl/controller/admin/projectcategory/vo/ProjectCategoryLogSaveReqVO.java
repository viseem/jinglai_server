package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryAttachment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 项目实验名目的操作日志更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectCategoryLogSaveReqVO extends ProjectCategoryLogBaseVO {

    @Schema(description = "id")
    private Long id;

    private List<ProjectCategoryAttachment> attachments;

}
