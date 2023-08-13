package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 客户联系人 Order 设置，用于分页使用
 */
@Data
public class CrmContactPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String gender;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerId;

    @Schema(allowableValues = {"desc", "asc"})
    private String phone;

    @Schema(allowableValues = {"desc", "asc"})
    private String tel;

    @Schema(allowableValues = {"desc", "asc"})
    private String email;

    @Schema(allowableValues = {"desc", "asc"})
    private String position;

    @Schema(allowableValues = {"desc", "asc"})
    private String isMaker;

    @Schema(allowableValues = {"desc", "asc"})
    private String address;

    @Schema(allowableValues = {"desc", "asc"})
    private String province;

    @Schema(allowableValues = {"desc", "asc"})
    private String city;

    @Schema(allowableValues = {"desc", "asc"})
    private String area;

    @Schema(allowableValues = {"desc", "asc"})
    private String nextContactTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}
