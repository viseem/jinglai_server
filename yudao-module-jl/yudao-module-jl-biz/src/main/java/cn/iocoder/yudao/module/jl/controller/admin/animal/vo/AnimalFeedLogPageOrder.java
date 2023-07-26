package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 动物饲养日志 Order 设置，用于分页使用
 */
@Data
public class AnimalFeedLogPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String feedOrderId;

    @Schema(allowableValues = {"desc", "asc"})
    private String changeQuantity;

    @Schema(allowableValues = {"desc", "asc"})
    private String changeCageQuantity;

    @Schema(allowableValues = {"desc", "asc"})
    private String type;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String files;

    @Schema(allowableValues = {"desc", "asc"})
    private String stores;

    @Schema(allowableValues = {"desc", "asc"})
    private String quantity;

    @Schema(allowableValues = {"desc", "asc"})
    private String cageQuantity;

}
