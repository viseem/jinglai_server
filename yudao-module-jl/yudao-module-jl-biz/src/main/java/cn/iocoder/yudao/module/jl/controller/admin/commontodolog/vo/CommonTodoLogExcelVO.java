package cn.iocoder.yudao.module.jl.controller.admin.commontodolog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 通用TODO记录 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CommonTodoLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("实验任务id")
    private Long refId;

    @ExcelProperty("todo类型")
    private String type;

}
