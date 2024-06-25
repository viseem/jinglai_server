package cn.iocoder.yudao.module.jl.controller.admin.taskarrangerelation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 任务安排关系 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class TaskArrangeRelationExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("任务id")
    private Long taskId;

    @ExcelProperty("收费项id")
    private Long chargeItemId;

    @ExcelProperty("产品id")
    private Long productId;

}
