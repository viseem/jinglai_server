package cn.iocoder.yudao.module.jl.controller.admin.devicecate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 设备分类 Order 设置，用于分页使用
 */
@Data
public class DeviceCatePageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String parentId;

    @Schema(allowableValues = {"desc", "asc"})
    private String tagIds;

    @Schema(allowableValues = {"desc", "asc"})
    private String sort;

    @Schema(allowableValues = {"desc", "asc"})
    private String content;

}
