package cn.iocoder.yudao.module.jl.controller.admin.projectoutlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * example 空 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectOutLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("客户id")
    private Long customerId;

    @ExcelProperty("内部数据评审json")
    private String dataSignJson;

    @ExcelProperty("客户评价")
    private String customerComment;

    @ExcelProperty("客户打分")
    private String customerScoreJson;

    @ExcelProperty("报价金额")
    private BigDecimal quotationPrice;

    @ExcelProperty("报价备注")
    private String quotationMark;

    @ExcelProperty("已收金额")
    private BigDecimal receivedPrice;

    @ExcelProperty("已收备注")
    private String receivedMark;

    @ExcelProperty("合同金额")
    private BigDecimal contractPrice;

    @ExcelProperty("合同备注")
    private String contractMark;

    @ExcelProperty("材料成本")
    private BigDecimal supplyPrice;

    @ExcelProperty("材料备注")
    private String supplyMark;

    @ExcelProperty("委外成本")
    private BigDecimal outsourcePrice;

    @ExcelProperty("委外备注")
    private String outsourceMark;

    @ExcelProperty("客户签字链接")
    private String customerSignImgUrl;

    @ExcelProperty("客户签字时间")
    private LocalDateTime custoamerSignTime;

    @ExcelProperty("步骤的确认日志")
    private String stepsJsonLog;

    @ExcelProperty("出库金额，理论上等于已收金额")
    private BigDecimal resultPrice;

    @ExcelProperty("出库金额备注")
    private String resultMark;

}
