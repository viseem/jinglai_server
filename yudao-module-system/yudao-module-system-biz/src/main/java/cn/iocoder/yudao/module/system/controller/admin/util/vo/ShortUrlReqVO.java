package cn.iocoder.yudao.module.system.controller.admin.util.vo;

import cn.iocoder.yudao.framework.common.validation.InEnum;
import cn.iocoder.yudao.module.system.enums.social.SocialTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "短链服务 请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortUrlReqVO {


    @Schema(description = "原链接", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "原链接不能为空")
    private String originUrl;

    @Schema(description = "口令", requiredMode = Schema.RequiredMode.REQUIRED, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
    @NotEmpty(message = "口令不能为空")
    private String password;

    @Schema(description = "有效期", requiredMode = Schema.RequiredMode.REQUIRED, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
    @NotNull(message = "有效期不能为空")
    private Integer expireTime;

}