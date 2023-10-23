package cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 专题小组 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class SubjectGroupExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("专题")
    private String area;

    @ExcelProperty("领域")
    private String subject;

    @ExcelProperty("组长")
    private Long leaderId;

    @ExcelProperty("商户组长")
    private Long businessLeaderId;

    @ExcelProperty("编号")
    private String code;

}
