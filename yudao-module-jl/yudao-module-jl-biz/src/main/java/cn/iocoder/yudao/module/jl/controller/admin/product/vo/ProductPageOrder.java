package cn.iocoder.yudao.module.jl.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * 产品库 Order 设置，用于分页使用
 */
@Data
public class ProductPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String cate;

    @Schema(allowableValues = {"desc", "asc"})
    private String status;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String piGroupId;

    @Schema(allowableValues = {"desc", "asc"})
    private String experId;

    @Schema(allowableValues = {"desc", "asc"})
    private String infoUserId;

    @Schema(allowableValues = {"desc", "asc"})
    private String subject;

    @Schema(allowableValues = {"desc", "asc"})
    private String supplierId;

    @Schema(allowableValues = {"desc", "asc"})
    private String standardPrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String costPrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String competePrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String discountPrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String soldAmount;

    @Schema(allowableValues = {"desc", "asc"})
    private String soldCount;

}
