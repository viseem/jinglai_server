package cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 专题小组成员 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class SubjectGroupMemberExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("分组id")
    private Long groupId;

    @ExcelProperty("用户id")
    private Long userId;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("备注")
    private String mark;

}
