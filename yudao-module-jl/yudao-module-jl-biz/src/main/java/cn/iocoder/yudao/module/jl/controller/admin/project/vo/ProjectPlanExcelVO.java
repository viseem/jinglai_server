package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目实验方案 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectPlanExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目id")
    private Integer projectId;

    @ExcelProperty("客户id")
    private Integer customerId;

    @ExcelProperty("安排单id")
    private Integer scheduleId;

    @ExcelProperty("方案text")
    private String planText;

    @ExcelProperty("版本")
    private String version;

}
