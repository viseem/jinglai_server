package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 实验产品入库明细 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProductInItemExcelVO {

    @ExcelProperty("岗位ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("实验产品入库表 id")
    private Long productInId;

    @ExcelProperty("产自实验物资的 id")
    private Long sourceSupplyId;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("规则/单位")
    private String feeStandard;

    @ExcelProperty("单价")
    private String unitFee;

    @ExcelProperty("单量")
    private Integer unitAmount;

    @ExcelProperty("数量")
    private Integer quantity;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("房间 id")
    private Long roomId;

    @ExcelProperty("储存器材 id")
    private Long containerId;

    @ExcelProperty("区域位置 id")
    private Long zoomId;

    @ExcelProperty("有效期")
    private String validDate;

    @ExcelProperty("存储温度")
    private String temperature;

}
