package cn.iocoder.yudao.module.jl.controller.admin.dept.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 部门 Order 设置，用于分页使用
 */
@Data
public class DeptPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String parentId;

    @Schema(allowableValues = {"desc", "asc"})
    private String sort;

    @Schema(allowableValues = {"desc", "asc"})
    private String leaderUserId;

    @Schema(allowableValues = {"desc", "asc"})
    private String phone;

    @Schema(allowableValues = {"desc", "asc"})
    private String email;

    @Schema(allowableValues = {"desc", "asc"})
    private String status;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

}
