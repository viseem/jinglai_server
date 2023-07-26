package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 动物笼位 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class AnimalBoxExcelVO {

    @ExcelProperty("``")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("编码")
    private String code;

    @ExcelProperty("位置")
    private String location;

    @ExcelProperty("容量")
    private Integer capacity;

    @ExcelProperty("现有")
    private Integer quantity;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("架子id")
    private Long shelfId;

    @ExcelProperty("房间id")
    private Long roomId;

    @ExcelProperty("行索引")
    private Integer rowIndex;

    @ExcelProperty("列索引")
    private Integer colIndex;

    @ExcelProperty("饲养单")
    private Long feedOrderId;

}
