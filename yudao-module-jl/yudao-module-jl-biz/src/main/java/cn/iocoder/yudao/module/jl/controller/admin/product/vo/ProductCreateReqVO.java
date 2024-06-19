package cn.iocoder.yudao.module.jl.controller.admin.product.vo;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 产品库创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductCreateReqVO extends ProductBaseVO {
    private List<CommonAttachment> attachmentList;
    private String attachmentType;
}
