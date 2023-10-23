package cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 专题小组 Order 设置，用于分页使用
 */
@Data
public class SubjectGroupPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String area;

    @Schema(allowableValues = {"desc", "asc"})
    private String subject;

    @Schema(allowableValues = {"desc", "asc"})
    private String leaderId;

    @Schema(allowableValues = {"desc", "asc"})
    private String businessLeaderId;

    @Schema(allowableValues = {"desc", "asc"})
    private String code;

}
