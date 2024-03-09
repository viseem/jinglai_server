package cn.iocoder.yudao.module.jl.controller.admin.salesgroupmember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * 销售分组成员 Order 设置，用于分页使用
 */
@Data
public class SalesGroupMemberPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String groupId;

    @Schema(allowableValues = {"desc", "asc"})
    private String userId;

    @Schema(allowableValues = {"desc", "asc"})
    private String monthRefundKpi;

    @Schema(allowableValues = {"desc", "asc"})
    private String monthOrderKpi;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}
