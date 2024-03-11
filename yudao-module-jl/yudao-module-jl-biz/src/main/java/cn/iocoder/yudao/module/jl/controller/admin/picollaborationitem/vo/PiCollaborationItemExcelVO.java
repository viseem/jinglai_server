package cn.iocoder.yudao.module.jl.controller.admin.picollaborationitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * pi组协作明细 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class PiCollaborationItemExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("协助的主表id")
    private Long collaborationId;

    @ExcelProperty("pi组id")
    private Long piId;

    @ExcelProperty("占比")
    private BigDecimal percent;

    @ExcelProperty("备注")
    private String mark;

}
