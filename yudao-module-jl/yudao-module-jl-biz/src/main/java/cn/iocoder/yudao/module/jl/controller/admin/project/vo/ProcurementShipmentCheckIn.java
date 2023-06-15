package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 采购签收 item Request VO")
@Data
@ToString(callSuper = true)
public class ProcurementShipmentCheckIn {


    private Long projectSupplyId;

    private String status;

    private String mark;

    private Integer checkInNum;
}
