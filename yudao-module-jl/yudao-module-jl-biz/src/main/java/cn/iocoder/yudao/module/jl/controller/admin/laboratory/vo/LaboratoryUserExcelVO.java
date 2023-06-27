package cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 实验室人员 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class LaboratoryUserExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("实验室 id")
    private Long labId;

    @ExcelProperty("人员 id")
    private Long userId;

    @ExcelProperty("备注描述")
    private String mark;

    @ExcelProperty("实验室人员等级")
    private String rank;

}
