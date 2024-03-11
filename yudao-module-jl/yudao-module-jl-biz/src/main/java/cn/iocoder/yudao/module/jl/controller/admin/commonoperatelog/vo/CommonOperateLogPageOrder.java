package cn.iocoder.yudao.module.jl.controller.admin.commonoperatelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 通用操作记录 Order 设置，用于分页使用
 */
@Data
public class CommonOperateLogPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String oldContent;

    @Schema(allowableValues = {"desc", "asc"})
    private String newContent;

    @Schema(allowableValues = {"desc", "asc"})
    private String type;

    @Schema(allowableValues = {"desc", "asc"})
    private String subType;

    @Schema(allowableValues = {"desc", "asc"})
    private String eventType;

    @Schema(allowableValues = {"desc", "asc"})
    private String refId;

    @Schema(allowableValues = {"desc", "asc"})
    private String subRefId;

}
