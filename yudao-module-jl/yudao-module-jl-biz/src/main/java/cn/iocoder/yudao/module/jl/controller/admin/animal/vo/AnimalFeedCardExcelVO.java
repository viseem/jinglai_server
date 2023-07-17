package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 动物饲养鼠牌 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class AnimalFeedCardExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("饲养单id")
    private Long feedOrderId;

    @ExcelProperty("序号")
    private Integer seq;

    @ExcelProperty("品系品种")
    private String breed;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("性别")
    private Byte gender;

    @ExcelProperty("数量")
    private Integer quantity;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("客户id")
    private Long customerId;

}
