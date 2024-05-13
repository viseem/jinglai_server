package cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 开票申请创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InvoiceApplicationCreateReqVO extends InvoiceApplicationBaseVO {

}
