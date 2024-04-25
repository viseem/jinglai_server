package cn.iocoder.yudao.module.jl.controller.admin.suppliertag.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 供应商标签创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SupplierTagCreateReqVO extends SupplierTagBaseVO {

}
