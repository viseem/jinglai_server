package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目采购单物流信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SupplierPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "联系人", example = "李四")
    private String contactName;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "结算周期")
    private String paymentCycle;

    @Schema(description = "银行卡号", example = "14108")
    private String bankAccount;

    @Schema(description = "税号")
    private String taxNumber;

    @Schema(description = "备注")
    private String mark;

}
