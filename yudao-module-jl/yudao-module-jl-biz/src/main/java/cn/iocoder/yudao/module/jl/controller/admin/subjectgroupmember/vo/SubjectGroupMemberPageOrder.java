package cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 专题小组成员 Order 设置，用于分页使用
 */
@Data
public class SubjectGroupMemberPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String groupId;

    @Schema(allowableValues = {"desc", "asc"})
    private String userId;

    @Schema(allowableValues = {"desc", "asc"})
    private String type;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}
