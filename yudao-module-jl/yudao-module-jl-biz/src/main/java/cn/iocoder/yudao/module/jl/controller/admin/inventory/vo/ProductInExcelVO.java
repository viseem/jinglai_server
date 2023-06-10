package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 实验产品入库 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProductInExcelVO {

    @ExcelProperty("岗位ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目 id")
    private Long projectId;

    @ExcelProperty("实验名目库的名目 id")
    private Long categoryId;

    @ExcelProperty("实验名目 id")
    private Long projectCategoryId;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("备注")
    private String mark;

}
