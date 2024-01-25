package cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.subgroupworkstation;

import cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.SubjectGroupBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 专题小组 Response VO")
@Data
@ToString(callSuper = true)
public class SubjectGroupWorkstationCountStatsRespVO {

    // 默认本月

    // 商机数量 用saleslead查询未成交的商机个数
    private Integer orderCount;
    // 商机金额 用saleslead查询未成交的商机金额
    private Integer orderAmount;
    // 成交数量 查询合同的数量
    private Integer dealCount;
    // 成交金额 查询合同的金额
    private Integer dealAmount;
    // 已收金额 查询合同的已收金额
    private Integer receivedAmount;
}
