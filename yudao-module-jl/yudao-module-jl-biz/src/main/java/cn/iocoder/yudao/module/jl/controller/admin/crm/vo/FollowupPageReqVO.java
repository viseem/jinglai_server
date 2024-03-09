package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 销售跟进分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FollowupPageReqVO extends PageParam {



    @Schema(description = "归属：ALL MY SUB")
    private String attribute = DataAttributeTypeEnums.MY.getStatus();

    @Schema(description = "in 查询 creators")
    private Long[] creators;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "客户id", example = "24011")
    private Long customerId;

    @Schema(description = "跟进实体的 id，项目、线索、款项，客户等", example = "29426")
    private Long refId;

    @Schema(description = "跟进类型：日常联系、销售线索、催款等", example = "1")
    private String type;

    //上面的是权限规则，这个是传进来的
    @Schema(description = "in 查询 creatorIds")
    private Long[] creatorIds;

    @Schema(description = "时间范围", example = "27395")
    private String timeRange;

}
