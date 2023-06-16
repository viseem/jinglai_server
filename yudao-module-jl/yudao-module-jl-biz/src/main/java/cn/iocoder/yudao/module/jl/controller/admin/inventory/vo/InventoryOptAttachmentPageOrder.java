package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 库管操作附件记录 Order 设置，用于分页使用
 */
@Data
public class InventoryOptAttachmentPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String type;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String status;

    @Schema(allowableValues = {"desc", "asc"})
    private String refId;

    @Schema(allowableValues = {"desc", "asc"})
    private String refItemId;

    @Schema(allowableValues = {"desc", "asc"})
    private String fileName;

    @Schema(allowableValues = {"desc", "asc"})
    private String fileUrl;

}
