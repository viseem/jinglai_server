package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 取货单申请 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SupplyPickupBaseVO {

    @Schema(description = "项目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11363")
    @NotNull(message = "项目 id不能为空")
    private Long projectId;

    @Schema(description = "实验名目库的名目 id", example = "12631")
    private Long projectCategoryId;

    @Schema(description = "取货单号")
    private String code;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "取货时间")
    private String sendDate;

    @Schema(description = "取货人", requiredMode = Schema.RequiredMode.REQUIRED, example = "24449")
    @NotNull(message = "取货人不能为空")
    private Long userId;

    @Schema(description = "取货地址")
    private String address;

    @Schema(description = "联系人姓名", example = "赵六")
    private String contactName;

    @Schema(description = "联系人电话")
    private String contactPhone;

}
