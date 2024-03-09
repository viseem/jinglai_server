package cn.iocoder.yudao.module.jl.controller.admin.salesgroupmember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 销售分组成员更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SalesGroupMemberUpdateReqVO extends SalesGroupMemberBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14715")
    @NotNull(message = "ID不能为空")
    private Long id;

}
