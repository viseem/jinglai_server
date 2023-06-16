package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 签收记录 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class InventoryCheckInExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目 id")
    private Long projectId;

    @ExcelProperty("实验物资名称 id")
    private Long projectSupplyId;

    @ExcelProperty("签收数量")
    private Integer inQuantity;

    @ExcelProperty("类型，采购，寄送，自取")
    private String type;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("采购，寄送，自取的 id")
    private Long refId;

    @ExcelProperty("采购，寄送，自取的子元素 id")
    private Long refItemId;

}
