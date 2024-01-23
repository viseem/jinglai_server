package cn.iocoder.yudao.module.jl.controller.admin.collaborationrecord.vo;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 通用协作记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CollaborationRecordBaseVO {

    @Schema(description = "外键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11122")
    @NotNull(message = "外键id不能为空")
    private Long refId;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "类型不能为空")
    private String type;

    @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "内容不能为空")
    private String content;

    @Schema(description = "父级id", example = "13368")
    private Long pid;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "附件", example = "2")
    private List<CommonAttachment> attachmentList;

    @Schema(description = "创建者", example = "2")
    private User user;

    private Long creator;



}
