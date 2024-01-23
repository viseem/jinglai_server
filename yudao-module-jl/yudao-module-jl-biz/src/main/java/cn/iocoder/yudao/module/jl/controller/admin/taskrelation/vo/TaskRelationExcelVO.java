package cn.iocoder.yudao.module.jl.controller.admin.taskrelation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 任务关系依赖 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class TaskRelationExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("等级")
    private Integer level;

    @ExcelProperty("任务id：category sop")
    private Long taskId;

    @ExcelProperty("依赖id")
    private Long dependId;

}
