package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目实验委外 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectCategoryOutsourceExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("项目的实验名目id")
    private Long projectCategoryId;

    @ExcelProperty("类型：项目、实验、其它")
    private String type;

    @ExcelProperty("外包内容")
    private String content;

    @ExcelProperty("外包供应商id")
    private Integer categorySupplierId;

    @ExcelProperty("供应商报价")
    private Integer supplierPrice;

    @ExcelProperty("销售价格")
    private Integer salePrice;

    @ExcelProperty("购买价格")
    private Integer buyPrice;

    @ExcelProperty("凭证名字")
    private String proofName;

    @ExcelProperty("凭证地址 ")
    private String proofUrl;

    @ExcelProperty("备注")
    private String mark;

}
