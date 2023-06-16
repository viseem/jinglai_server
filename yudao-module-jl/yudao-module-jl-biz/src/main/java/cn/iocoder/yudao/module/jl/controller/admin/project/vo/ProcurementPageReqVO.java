package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.controller.admin.user.vo.UserRespVO;
import cn.iocoder.yudao.module.jl.entity.user.User;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目采购单申请分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcurementPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目 id", example = "8583")
    private Long projectId;

    @Schema(description = "实验名目库的名目 id", example = "20444")
    private Long projectCategoryId;

    @Schema(description = "采购单号")
    private String code;

    private String shipmentCodes;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "根据状态查询", example = "2")
    private String queryStatus;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "采购发起时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] startDate;

    @Schema(description = "签收陪审人", example = "20159")
    private Long checkUserId;

    @Schema(description = "收货地址")
    private String address;

    @Schema(description = "收货人id", example = "29752")
    private String receiverUserId;

}
