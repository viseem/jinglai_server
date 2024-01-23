package cn.iocoder.yudao.module.jl.controller.admin.reminder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 晶莱提醒事项 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ReminderExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("合同id")
    private Long contractId;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("截止日期")
    private String deadline;

    @ExcelProperty("状态")
    private String status;

}
