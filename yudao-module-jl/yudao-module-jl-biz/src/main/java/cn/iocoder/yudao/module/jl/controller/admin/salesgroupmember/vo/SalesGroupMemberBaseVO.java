package cn.iocoder.yudao.module.jl.controller.admin.salesgroupmember.vo;

import cn.iocoder.yudao.module.jl.entity.salesgroup.SalesGroup;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * 销售分组成员 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SalesGroupMemberBaseVO {

    @Schema(description = "分组id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20497")
    @NotNull(message = "分组id不能为空")
    private Long groupId;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13658")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "月度回款目标")
    private BigDecimal monthRefundKpi;

    @Schema(description = "月度订单目标")
    private BigDecimal monthOrderKpi;

    @Schema(description = "备注")
    private String mark;

    private User user;
    private SalesGroup group;

}
