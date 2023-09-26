package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "管理后台 - 动物饲养申请单 Response VO")
@Data
@ToString(callSuper = true)
public class AnimalFeedOrderStatsCountRespVO {


    private Long waitingFeedCount;
    private Long feedingCount;

    private Long endCount;

}
