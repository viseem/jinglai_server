package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Schema(description = "项目端 项目统计数据")
@Data
@ToString(callSuper = true)
public class ProjectStatisticProjectTagResp {

    /*
    //预付款未到
    FIRST_FUND_NOT("1", "预付款未到"),
    //预付款已到
    FIRST_FUND_DONE("101", "预付款已到"),
    //推进
    DOING("3", "推进"),
    //暂停
    PAUSE("4", "暂停"),
    //中止
    STOP("5", "中止"),
    //退单
    BACK("6", "退单"),
    //提前出库
    OUT_EARLY("8", "提前出库"),
    //已完成待出库
    DONE_WAIT_OUT("11", "已完成待出库"),
    //已完成待收尾款
    DONE_WAIT_LAST_FUND("12", "已完成待收尾款"),
    * */

    //预付款未到
    Long firstFundNotCount;
    //预付款已到
    Long firstFundDoneCount;
    //推进
    Long doingCount;
    //暂停
    Long pauseCount;
    //中止
    Long stopCount;
    //退单
    Long backCount;
    //提前出库
    Long outEarlyCount;
    //已完成待出库
    Long doneWaitOutCount;
    //已完成待收尾款
    Long doneWaitLastFundCount;
}
