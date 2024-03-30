package cn.iocoder.yudao.module.jl.controller.admin.productdevice.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 产品库设备创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductDeviceCreateReqVO extends ProductDeviceBaseVO {

}
