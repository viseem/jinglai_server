package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 出库申请 Order 设置，用于分页使用
 */
@Data
public class SupplyOutPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectCategoryId;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String status;

}