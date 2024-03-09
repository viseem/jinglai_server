package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.projectperson.ProjectPerson;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 项目管理更新 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectUpdateTagReqVO  {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29196")
    @NotNull(message = "ID不能为空")
    private Long projectId;


    private String tagIds;

}
