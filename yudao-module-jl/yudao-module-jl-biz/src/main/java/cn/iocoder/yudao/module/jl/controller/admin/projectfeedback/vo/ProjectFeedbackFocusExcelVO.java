package cn.iocoder.yudao.module.jl.controller.admin.projectfeedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 晶莱项目反馈关注人员 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectFeedbackFocusExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("用户id")
    private Long userId;

    @ExcelProperty("项目反馈id")
    private Long projectFeedbackId;

}
