package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "项目端 项目统计数据")
@Data
@ToString(callSuper = true)
public class ProjectStatisticProjectResp {
    Integer totalCount;
}
