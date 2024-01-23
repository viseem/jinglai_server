package cn.iocoder.yudao.module.jl.controller.open.projectcategory.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 公司资产（设备）分页 Request VO")
@Data
@ToString(callSuper = true)
public class LabExpStatsReqVO {


    @Schema(description = "实验室id", example = "王五")
    private Long labId;


}
