package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 库管存储容器位置 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class InventoryContainerPlaceExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("库房id：可以用字典")
    private Integer containerId;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("所在位置")
    private String place;

    @ExcelProperty("备注描述")
    private String mark;

}
