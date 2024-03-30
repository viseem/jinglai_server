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

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("分类")
    private String cate;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("pi组")
    private Long piGroupId;

    @ExcelProperty("实验负责人")
    private Long experId;

    @ExcelProperty("信息负责人")
    private Long infoUserId;

    @ExcelProperty("实施主体")
    private String subject;

    @ExcelProperty("供应商id")
    private Long supplierId;

    @ExcelProperty("标准价格")
    private BigDecimal standardPrice;

    @ExcelProperty("成本价格")
    private BigDecimal costPrice;

    @ExcelProperty("竞品价格")
    private BigDecimal competePrice;

    @ExcelProperty("优惠价格")
    private BigDecimal discountPrice;

    @ExcelProperty("已售金额")
    private BigDecimal soldAmount;

    @ExcelProperty("已售份数")
    private Integer soldCount;

}
