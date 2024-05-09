package cn.iocoder.yudao.module.jl.controller.admin.inventorystorelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 物品出入库日志 Order 设置，用于分页使用
 */
@Data
public class InventoryStoreLogPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String source;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String catalogNumber;

    @Schema(allowableValues = {"desc", "asc"})
    private String brand;

    @Schema(allowableValues = {"desc", "asc"})
    private String spec;

    @Schema(allowableValues = {"desc", "asc"})
    private String unit;

    @Schema(allowableValues = {"desc", "asc"})
    private String changeNum;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String roomId;

    @Schema(allowableValues = {"desc", "asc"})
    private String containerId;

    @Schema(allowableValues = {"desc", "asc"})
    private String placeId;

    @Schema(allowableValues = {"desc", "asc"})
    private String location;

    @Schema(allowableValues = {"desc", "asc"})
    private String sourceItemId;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectSupplyId;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String purchaseContractId;

    @Schema(allowableValues = {"desc", "asc"})
    private String procurementId;

}
