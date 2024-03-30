package cn.iocoder.yudao.module.jl.controller.admin.productsop.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 产品sop Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProductSopExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("产品")
    private Long productId;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("内容")
    private String content;

}
