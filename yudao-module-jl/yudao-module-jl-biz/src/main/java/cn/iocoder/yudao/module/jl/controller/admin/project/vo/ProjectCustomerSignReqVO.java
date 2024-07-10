package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.projectperson.ProjectPerson;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreGetReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 项目管理更新 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectCustomerSignReqVO extends UtilStoreGetReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29196")
    @NotNull(message = "ID不能为空")
    private Long projectId;


    /**
     * 客户签名图片地址
     */
    @Schema(description = "签字", requiredMode = Schema.RequiredMode.REQUIRED, example = "29196")
    @NotNull(message = "签字不能为空")
    private String customerSignImgUrl;

    /**
     * 客户签名备注
     */
    private String customerSignMark;


}
