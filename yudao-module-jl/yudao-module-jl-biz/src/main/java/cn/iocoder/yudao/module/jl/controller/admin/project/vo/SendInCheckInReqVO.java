package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 采购签收 Request VO")
@Data
@ToString(callSuper = true)
public class SendInCheckInReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private Long sendInId;

    private List<CheckInItem> list;

    private List<CheckInAttachment> attachments;

    private String mark;

}