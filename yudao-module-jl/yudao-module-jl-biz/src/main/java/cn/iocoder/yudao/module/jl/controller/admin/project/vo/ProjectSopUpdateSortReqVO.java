package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSop;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 项目中的实验名目的操作SOP更新 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectSopUpdateSortReqVO {

    private List<ProjectSop> list;

}
