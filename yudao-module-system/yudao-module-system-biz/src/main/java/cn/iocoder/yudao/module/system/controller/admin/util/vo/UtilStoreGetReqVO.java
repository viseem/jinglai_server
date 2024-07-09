package cn.iocoder.yudao.module.system.controller.admin.util.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Schema(description = "短链服务 请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilStoreGetReqVO implements Serializable {


    @Schema(description = "id")
    private String storeId;

    @Schema(description = "口令")
    private String storePwd;

    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    @Schema(description = "页码，从 1 开始", requiredMode = Schema.RequiredMode.REQUIRED,example = "1")
    @NotNull(message = "页码不能为空")
    @Min(value = -1, message = "页码最小值为 1")
    private Integer pageNo = PAGE_NO;

    @Schema(description = "每页条数，最大值为 300", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 300, message = "每页条数最大值为 300")
    private Integer pageSize = PAGE_SIZE;

}
