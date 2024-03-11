package cn.iocoder.yudao.module.jl.controller.admin.picollaborationitem.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - pi组协作明细创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PiCollaborationItemCreateReqVO extends PiCollaborationItemBaseVO {

}
