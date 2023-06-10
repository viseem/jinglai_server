package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 实验产品入库 Order 设置，用于分页使用
 */
@Data
public class ProductInPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String categoryId;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectCategoryId;

    @Schema(allowableValues = {"desc", "asc"})
    private String status;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}
