package cn.iocoder.yudao.module.jl.controller.admin.inventoryproductlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 产品变更日志 Order 设置，用于分页使用
 */
@Data
public class InventoryProductLogPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectSupplyId;

    @Schema(allowableValues = {"desc", "asc"})
    private String location;

    @Schema(allowableValues = {"desc", "asc"})
    private String quantity;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String experId;

}
