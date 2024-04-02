package cn.iocoder.yudao.module.jl.controller.admin.product.vo;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import cn.iocoder.yudao.module.jl.entity.productcate.ProductCate;
import cn.iocoder.yudao.module.jl.entity.productcate.ProductCateOnly;
import cn.iocoder.yudao.module.jl.entity.productdevice.ProductDevice;
import cn.iocoder.yudao.module.jl.entity.productsop.ProductSop;
import cn.iocoder.yudao.module.jl.entity.productuser.ProductUser;
import cn.iocoder.yudao.module.jl.entity.subjectgroup.SubjectGroupOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 产品库 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductRespVO extends ProductBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15295")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "创建时间")
    private LocalDateTime updateTime;

    private SubjectGroupOnly subjectGroup;
    private ProductCateOnly cate;
    private User exper;
    private User infoUser;
    private User createUser;

    private List<CommonAttachment> attachmentList;

    private List<ProductUser> userList;
    private List<ProductDevice> deviceList;
    private List<ProductSop> sopList;
}
