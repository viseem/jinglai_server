package cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 合同发票记录创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractInvoiceLogCreateReqVO extends ContractInvoiceLogBaseVO {

}
