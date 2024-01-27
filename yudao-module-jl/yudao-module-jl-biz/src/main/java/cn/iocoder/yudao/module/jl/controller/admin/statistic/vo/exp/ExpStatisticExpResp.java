package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.exp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "实验端 任务统计数据")
@Data
@ToString(callSuper = true)
public class ExpStatisticExpResp {
    Integer totalCount;
    //待开展
    Integer waitCount;
    //开展中
    Integer doingCount;
    //已完成
    Integer completeCount;
    //已暂停
    Integer pauseCount;
    // 已延期
    Integer delayCount;
    // 即将到期
    Integer expireCount;
}
