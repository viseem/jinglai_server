package cn.iocoder.yudao.module.jl.controller.admin.visitlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 拜访记录 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class VisitLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("客户id")
    private Long customerId;

    @ExcelProperty("销售id")
    private Long salesId;

    @ExcelProperty("拜访时间")
    private LocalDateTime time;

    @ExcelProperty("拜访地址")
    private String address;

    @ExcelProperty("拜访目的")
    private String goal;

    @ExcelProperty("拜访途径")
    private String visitType;

    @ExcelProperty("拜访内容")
    private String content;

    @ExcelProperty("反馈")
    private String feedback;

    @ExcelProperty("评分")
    private Integer score;

    @ExcelProperty("备注")
    private String mark;

}
