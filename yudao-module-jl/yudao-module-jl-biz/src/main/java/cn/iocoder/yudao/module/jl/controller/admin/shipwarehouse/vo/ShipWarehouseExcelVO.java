package cn.iocoder.yudao.module.jl.controller.admin.shipwarehouse.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 收货仓库 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ShipWarehouseExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("负责人")
    private Long managerId;

    @ExcelProperty("详细地址")
    private String address;

}
