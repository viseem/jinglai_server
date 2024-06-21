package cn.iocoder.yudao.module.jl.controller.admin.taskproduct.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 任务产品中间 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class TaskProductExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("任务id")
    private Long taskId;

    @ExcelProperty("产品id")
    private Long productId;

}
