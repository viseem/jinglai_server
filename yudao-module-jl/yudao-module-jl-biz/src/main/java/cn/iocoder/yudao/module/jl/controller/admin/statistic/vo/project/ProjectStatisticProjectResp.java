package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Schema(description = "项目端 项目统计数据")
@Data
@ToString(callSuper = true)
public class ProjectStatisticProjectResp {
    Integer waitCount;
    Integer doingApprovalCount;
    Integer doingCount;
    // 实验安排数量
    Integer expArrangeCount;
    //结算数量
    Integer settlementCount;
    //出库审批数量
    Integer outApprovalCount;
    // 即将到期
    Integer expireCount;
    // 已过期
    Integer delayCount;

    List<List<Long>> idsList;

}
