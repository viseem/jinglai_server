package cn.iocoder.yudao.module.jl.controller.admin.asset.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 公司资产（设备） Excel 导出 Request VO，参数和 AssetDevicePageReqVO 是一致的")
@Data
public class AssetDeviceExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "设备名称", example = "王五")
    private String name;

    @Schema(description = "所属类型：公司、租赁", example = "1")
    private String ownerType;

    @Schema(description = "设备类型：细胞仪器、解剖仪器", example = "1")
    private String type;

    @Schema(description = "位置：字典")
    private String location;

    @Schema(description = "管理人 id", example = "11048")
    private Long managerId;

    @Schema(description = "设备状态：空闲、忙碌(前端先自己算)", example = "1")
    private String status;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "设备快照名称", example = "李四")
    private String fileName;

    @Schema(description = "设备快照地址", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "设备编码：后端生成")
    private String code;

}