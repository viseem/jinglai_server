package cn.iocoder.yudao.module.jl.controller.admin.inventoryproductlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 产品变更日志 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class InventoryProductLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("物资id")
    private Integer projectSupplyId;

    @ExcelProperty("位置")
    private String location;

    @ExcelProperty("数量")
    private Integer quantity;

    @ExcelProperty("说明")
    private String mark;

    @ExcelProperty("实验人员id")
    private Long experId;

}
