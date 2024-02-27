package cn.iocoder.yudao.module.jl.controller.admin.salesgroupmember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 销售分组成员 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class SalesGroupMemberExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("分组id")
    private Integer groupId;

    @ExcelProperty("用户id")
    private Integer userId;

    @ExcelProperty("月度回款目标")
    private BigDecimal monthRefundKpi;

    @ExcelProperty("月度订单目标")
    private BigDecimal monthOrderKpi;

    @ExcelProperty("备注")
    private String mark;

}
