package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目采购单申请 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProcurementBaseVO {

    @Schema(description = "项目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8583")
    @NotNull(message = "项目 id不能为空")
    private Long projectId;

    @Schema(description = "实验名目库的名目 id", example = "20444")
    private Long projectCategoryId;

    @Schema(description = "采购单号")
    private String code;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "备注")
    private String mark;

    private String reply;

    @Schema(description = "采购发起时间")
    private String startDate;

    @Schema(description = "签收陪审人", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "20159")
    private Long checkUserId;

    @Schema(description = "收货地址")
    private String address;

    @Schema(description = "收货人id", example = "29752")
    private String receiverUserId;

}
