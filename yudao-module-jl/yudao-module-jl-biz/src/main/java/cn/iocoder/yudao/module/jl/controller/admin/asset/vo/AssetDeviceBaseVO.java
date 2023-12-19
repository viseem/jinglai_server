package cn.iocoder.yudao.module.jl.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 公司资产（设备） Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AssetDeviceBaseVO {

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "设备名称不能为空")
    private String name;

    @Schema(description = "所属类型：公司、租赁", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "所属类型：公司、租赁不能为空")
    private String ownerType;

    @Schema(description = "实验室id", example = "11048")
    private Long labId;

    @Schema(description = "管理人 id", example = "11048")
    private Long managerId;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "设备快照名称", example = "李四")
    private String fileName;

    @Schema(description = "设备快照地址", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "设备类型：细胞仪器、解剖仪器", example = "2")
    private String type;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "设备状态：空闲、忙碌(前端先自己算)", example = "2")
    private String status;

    @Schema(description = "设备编码：后端生成")
    private String sn;

    @Schema(description = "颜色标识")
    private String color;

}
