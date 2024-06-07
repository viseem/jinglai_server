package cn.iocoder.yudao.module.system.controller.admin.util.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "短链服务 请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilStoreGetReqVO {


    @Schema(description = "id")
    private String storeId;

    @Schema(description = "口令")
    private String storePwd;


}
