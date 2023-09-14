package cn.iocoder.yudao.module.jl.controller.admin.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 晶莱反馈 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class FeedbackExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("重要程度：重要紧急 不重要不紧急 重要不紧急")
    private String importance;

    @ExcelProperty("期望截止日期（排期）")
    private String deadline;

    @ExcelProperty("状态：未受理、处理中、已解决")
    private String status;

    @ExcelProperty("截图地址")
    private String fileUrl;

}
