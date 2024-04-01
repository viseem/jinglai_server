package cn.iocoder.yudao.module.jl.controller.admin.product.vo;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 产品库更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductUpdateReqVO extends ProductBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15295")
    @NotNull(message = "ID不能为空")
    private Long id;

    private String attachmentType;

    private List<CommonAttachment> attachmentList;

}
