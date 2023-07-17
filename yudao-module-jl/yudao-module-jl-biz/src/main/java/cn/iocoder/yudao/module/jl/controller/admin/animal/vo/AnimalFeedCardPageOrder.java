package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 动物饲养鼠牌 Order 设置，用于分页使用
 */
@Data
public class AnimalFeedCardPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String feedOrderId;

    @Schema(allowableValues = {"desc", "asc"})
    private String seq;

    @Schema(allowableValues = {"desc", "asc"})
    private String breed;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String gender;

    @Schema(allowableValues = {"desc", "asc"})
    private String quantity;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerId;

}
