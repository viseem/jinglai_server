package cn.iocoder.yudao.module.jl.controller.admin.projectperson.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 项目人员创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectPersonCreateReqVO extends ProjectPersonBaseVO {

}
