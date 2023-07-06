package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目实验委外供应商 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectCategorySupplierExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("联系人")
    private String contactName;

    @ExcelProperty("联系方式")
    private String contactPhone;

    @ExcelProperty("擅长领域")
    private String advantage;

}
