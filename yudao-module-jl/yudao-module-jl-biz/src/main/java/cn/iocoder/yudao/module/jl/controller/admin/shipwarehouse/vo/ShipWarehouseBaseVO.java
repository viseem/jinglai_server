package cn.iocoder.yudao.module.jl.controller.admin.shipwarehouse.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 收货仓库 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ShipWarehouseBaseVO {

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "负责人", example = "3646")
    private Long managerId;

    @Schema(description = "详细地址")
    private String address;

}
