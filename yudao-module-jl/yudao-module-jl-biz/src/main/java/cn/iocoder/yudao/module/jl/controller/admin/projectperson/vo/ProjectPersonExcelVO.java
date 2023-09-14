package cn.iocoder.yudao.module.jl.controller.admin.projectperson.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目人员 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectPersonExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("人员")
    private Long userId;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("项目id")
    private Long projectId;

}
