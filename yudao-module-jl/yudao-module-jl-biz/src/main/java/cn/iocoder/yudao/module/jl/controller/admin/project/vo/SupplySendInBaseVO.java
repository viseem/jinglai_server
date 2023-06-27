package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.validation.constraints.*;

/**
 * 物资寄来单申请 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SupplySendInBaseVO {

    @Schema(description = "项目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28953")
    @NotNull(message = "项目 id不能为空")
    private Long projectId;

    @Schema(description = "实验名目库的名目 id", example = "32059")
    private Long projectCategoryId;

    @Schema(description = "寄来单号")
    private String code;

    @Schema(description = "寄来物流单号")
    private String shipmentNumber;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "寄来时间")
    private LocalDateTime sendDate;

    private String brand;

    @Schema(description = "收货地址")
    private String address;

    @Schema(description = "收货人", example = "芋艿")
    private String receiverName;

    @Schema(description = "收货人电话")
    private String receiverPhone;

    @Schema(description = "是否需要签收")
    private Boolean waitCheckIn;

    @Schema(description = "是否需要入库")
    private Boolean waitStoreIn;

    private String shipmentCodes;

}
