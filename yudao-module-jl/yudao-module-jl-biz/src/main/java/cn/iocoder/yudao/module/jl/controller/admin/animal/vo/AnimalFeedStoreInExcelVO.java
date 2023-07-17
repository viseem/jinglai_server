package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 动物饲养入库 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class AnimalFeedStoreInExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("饲养单id")
    private Long feedOrderId;

    @ExcelProperty("房间id")
    private Long roomId;

    @ExcelProperty("架子ids")
    private String shelfIds;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("位置")
    private String location;

}
