package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "")
@Data
@ToString(callSuper = true)
public class WorkstationProjectCountStatsResp {

    Integer notCompleteProjectCount;
    Integer notProcessFeedbackCount;

    Integer notQuotationCount;

    Integer notCompleteTaskCount;

}
