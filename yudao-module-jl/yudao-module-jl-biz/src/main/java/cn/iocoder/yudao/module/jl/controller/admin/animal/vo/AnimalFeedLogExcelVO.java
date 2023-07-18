package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 动物饲养日志 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class AnimalFeedLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("饲养单id")
    private Integer feedOrderId;

    @ExcelProperty("变更数量")
    private Integer changeQuantity;

    @ExcelProperty("变更笼数")
    private Integer changeCageQuantity;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("说明")
    private String mark;

    @ExcelProperty("变更附件")
    private String files;

    @ExcelProperty("变更位置")
    private String stores;

    @ExcelProperty("变更后数量")
    private Integer quantity;

    @ExcelProperty("变更后笼数")
    private Integer cageQuantity;

}
