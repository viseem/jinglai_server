package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

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

    @Schema(description = "附件地址")
    private String fileUrl;

    @Schema(description = "附件名称")
    private String fileName;

    @Schema(description = "状态", example = "2")
    private String status;

    private Long scheduleId;

    @Schema(description = "是否需要签收")
    private Boolean waitCheckIn;

    @Schema(description = "是否需要入库")
    private Boolean waitStoreIn;

    private String shipmentCodes;

    @Schema(description = "备注")
    private String mark;

    private String reply;

    @Schema(description = "采购发起时间")
    private String startDate;

    @Schema(description = "签收陪审人")
    private Long checkUserId;

    @Schema(description = "收货地址", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "20159")
    private String address;

    @Schema(description = "收货人id", example = "29752")
    private String receiverUserId;
    @Schema(description = "收货类型", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "20159")
    private String receiverType;
    @Schema(description = "收货人姓名", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "20159")
    private String receiverName;
    @Schema(description = "收货人联系方式")
    private String receiverPhone;

    @Schema(description = "流程实例id", example = "29752")
    private String processInstanceId;
}
