package cn.iocoder.yudao.module.jl.controller.admin.picollaborationitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * pi组协作明细 Order 设置，用于分页使用
 */
@Data
public class PiCollaborationItemPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String collaborationId;

    @Schema(allowableValues = {"desc", "asc"})
    private String piId;

    @Schema(allowableValues = {"desc", "asc"})
    private String percent;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}
