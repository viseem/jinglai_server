package cn.iocoder.yudao.module.jl.controller.admin.customerchangelog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 客户变更日志创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerChangeLogCreateReqVO extends CustomerChangeLogBaseVO {

}