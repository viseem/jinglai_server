package cn.iocoder.yudao.module.jl.controller.admin.productuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 产品库人员 Order 设置，用于分页使用
 */
@Data
public class ProductUserPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String productId;

    @Schema(allowableValues = {"desc", "asc"})
    private String userId;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}
