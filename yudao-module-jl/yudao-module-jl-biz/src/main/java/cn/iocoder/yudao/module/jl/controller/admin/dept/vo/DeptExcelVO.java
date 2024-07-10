package cn.iocoder.yudao.module.jl.controller.admin.dept.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 部门 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class DeptExcelVO {

    @ExcelProperty("部门id")
    private Long id;

    @ExcelProperty("部门名称")
    private String name;

    @ExcelProperty("父部门id")
    private Long parentId;

    @ExcelProperty("显示顺序")
    private Integer sort;

    @ExcelProperty("负责人")
    private Long leaderUserId;

    @ExcelProperty("联系电话")
    private String phone;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("部门状态（0正常 1停用）")
    private Byte status;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
