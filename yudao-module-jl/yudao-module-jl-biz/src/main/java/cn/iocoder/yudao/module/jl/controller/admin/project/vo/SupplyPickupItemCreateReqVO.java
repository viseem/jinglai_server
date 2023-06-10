package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 取货单申请明细创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SupplyPickupItemCreateReqVO extends SupplyPickupItemBaseVO {

}
