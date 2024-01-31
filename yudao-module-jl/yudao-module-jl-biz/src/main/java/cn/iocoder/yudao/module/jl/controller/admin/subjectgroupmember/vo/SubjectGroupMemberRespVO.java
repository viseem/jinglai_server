package cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 专题小组成员 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubjectGroupMemberRespVO extends SubjectGroupMemberBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25802")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    /*
    * 销售
    * */
    @Schema(description = "本月订单金额")
    private BigDecimal monthOrderFund = BigDecimal.ZERO;
    @Schema(description = "本月回款金额")
    private BigDecimal monthReturnFund= BigDecimal.ZERO;

    /*
     * 项目
     * */
    @Schema(description = "手头未出库的项目数")
    private Integer notOutProjectNum = 0;
    @Schema(description = "两周内到期的项目数")
    private Integer twoWeekExpireProjectNum = 0;


    /*
     * 实验
     * */
    @Schema(description = "未出库的任务数")
    private Integer notOutExpNum = 0;
    @Schema(description = "两周内到期的任务数")
    private Integer twoWeekExpireExpNum = 0;
}
