package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 出库记录 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class InventoryStoreOutExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目 id")
    private Long projectId;

    @ExcelProperty("实验物资名称 id")
    private Long projectSupplyId;

    @ExcelProperty("出库数量")
    private Integer outQuantity;

    @ExcelProperty("类型，产品入库，客户寄来")
    private String type;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("产品入库，客户寄来的 id")
    private Long refId;

    @ExcelProperty("产品入库，客户寄来的子元素 id")
    private Long refItemId;

    @ExcelProperty("保存温度")
    private String temperature;

    @ExcelProperty("有效截止期")
    private LocalDateTime validDate;

    @ExcelProperty("申请人")
    private Long applyUser;

}
