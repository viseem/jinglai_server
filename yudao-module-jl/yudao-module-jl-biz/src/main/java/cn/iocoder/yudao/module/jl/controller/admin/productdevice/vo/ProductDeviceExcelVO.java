package cn.iocoder.yudao.module.jl.controller.admin.productdevice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 产品库设备 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProductDeviceExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("产品")
    private Long productId;

    @ExcelProperty("设备")
    private Long deviceId;

    @ExcelProperty("备注")
    private String mark;

}
