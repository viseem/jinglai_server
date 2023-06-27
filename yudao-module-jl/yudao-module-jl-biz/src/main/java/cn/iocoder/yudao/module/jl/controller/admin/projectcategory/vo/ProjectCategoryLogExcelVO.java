package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目实验名目的操作日志 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectCategoryLogExcelVO {

    @ExcelProperty("岗位ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("实验名目 id")
    private Long projectCategoryId;

    @ExcelProperty("实验人员")
    private Long operatorId;

    @ExcelProperty("备注")
    private String mark;

}
