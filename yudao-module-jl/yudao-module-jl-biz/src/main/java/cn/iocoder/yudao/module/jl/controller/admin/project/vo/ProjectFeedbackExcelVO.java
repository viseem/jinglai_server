package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目反馈 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectFeedbackExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目 id")
    private Long projectId;

    @ExcelProperty("实验名目 id")
    private Long projectCategoryId;

    @ExcelProperty("内部人员 id")
    private Long userId;

    @ExcelProperty("客户 id")
    private Long customerId;

    @ExcelProperty("字典：内部反馈/客户反馈")
    private String type;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("反馈的内容")
    private String content;

    @ExcelProperty("处理结果")
    private String result;

}
