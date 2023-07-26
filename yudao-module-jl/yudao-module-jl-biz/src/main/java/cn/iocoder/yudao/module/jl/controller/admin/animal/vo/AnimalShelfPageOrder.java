package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 动物饲养笼架 Order 设置，用于分页使用
 */
@Data
public class AnimalShelfPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String code;

    @Schema(allowableValues = {"desc", "asc"})
    private String fileName;

    @Schema(allowableValues = {"desc", "asc"})
    private String fileUrl;

    @Schema(allowableValues = {"desc", "asc"})
    private String location;

    @Schema(allowableValues = {"desc", "asc"})
    private String managerId;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String roomId;

    @Schema(allowableValues = {"desc", "asc"})
    private String order;

}
