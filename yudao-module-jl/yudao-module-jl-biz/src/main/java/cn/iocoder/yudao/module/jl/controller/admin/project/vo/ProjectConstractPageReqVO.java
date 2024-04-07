package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目合同分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectConstractPageReqVO extends PageParam {

    @Schema(description = "归属：ALL MY SUB")
    private String attribute = DataAttributeTypeEnums.ANY.getStatus();

    @Schema(description = "in 查询 creators")
    private Long[] creators;

    //上面的是权限规则，这个是传进来的
    @Schema(description = "in 查询 creatorIds")
    private Long[] creatorIds;

    @Schema(description = "时间范围", example = "27395")
    private String timeRange;

    @Schema(description = "签订日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] signedTime;

    @Schema(description = "月份", example = "27395")
    private Integer month;
    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目 id", example = "12507")
    private Long projectId;

    @Schema(description = "合同名字", example = "赵六")
    private String name;

    @Schema(description = "合同文件 URL", example = "https://www.iocoder.cn")
    private String fileUrl;

    private Long customerId;

    @Schema(description = "合同状态：起效、失效、其它", example = "2")
    private String status;

    @Schema(description = "合同收款状态", example = "2")
    private String receivedStatus;

    @Schema(description = "合同开票状态", example = "2")
    private String invoicedStatus;

    @Schema(description = "合同类型", example = "1")
    private String type;

    @Schema(description = "合同金额", example = "30614")
    private Long price;

    @Schema(description = "签订销售人员", example = "32406")
    private Long salesId;

    @Schema(description = "合同编号")
    private String sn;

    @Schema(description = "合同文件名", example = "芋艿")
    private String fileName;

    @Schema(description = "关键字", example = "芋艿")
    private String keyword;

    private Integer isOuted;
}
