package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectQuoteRespVO;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 销售线索 Response VO")
@Data
@ToString(callSuper = true)
public class SalesleadCountStatsRespVO {

    Integer notToProjectCount;


}
