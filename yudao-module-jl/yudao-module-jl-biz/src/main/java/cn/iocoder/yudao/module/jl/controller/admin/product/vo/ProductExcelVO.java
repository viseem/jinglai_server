package cn.iocoder.yudao.module.jl.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 产品库 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProductExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("分类")
    private String cateName;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("规格")
    private String spec;

    @ExcelProperty("价格")
    private String standardPrice;

    @ExcelProperty("备注")
    private String mark;

}
