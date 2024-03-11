package cn.iocoder.yudao.module.jl.controller.admin.picollaboration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * PI组协作 Order 设置，用于分页使用
 */
@Data
public class PiCollaborationPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String targetId;

    @Schema(allowableValues = {"desc", "asc"})
    private String targetType;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}
