package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目采购单物流信息 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class SupplierExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("联系人")
    private String contactName;

    @ExcelProperty("联系电话")
    private String contactPhone;

    @ExcelProperty("结算周期")
    private String paymentCycle;

    @ExcelProperty("银行卡号")
    private String bankAccount;

    @ExcelProperty("税号")
    private String taxNumber;

    @ExcelProperty("备注")
    private String mark;

}
