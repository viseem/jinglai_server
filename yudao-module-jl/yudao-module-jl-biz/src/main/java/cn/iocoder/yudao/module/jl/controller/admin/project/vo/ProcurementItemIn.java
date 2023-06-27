package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 采购签收 item Request VO")
@Data
@ToString(callSuper = true)
public class ProcurementItemIn {


    private Long projectSupplyId;

    private String status;

    private String mark;

    private Integer inNum;

    private Long roomId;

    private Long placeId;

    private Long containerId;

    private String temperature;

    private LocalDateTime validDate;
}
