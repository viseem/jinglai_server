package cn.iocoder.yudao.module.jl.controller.admin.projectfundlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目款项 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectFundLogExcelVO {

    @ExcelProperty("岗位ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("收款金额")
    private Long price;

    @ExcelProperty("项目 id")
    private Long projectId;

    @ExcelProperty("支付凭证上传地址")
    private String receiptUrl;

    @ExcelProperty("支付时间")
    private LocalDateTime paidTime;

    @ExcelProperty("支付凭证文件名称")
    private String receiptName;

    @ExcelProperty("付款方")
    private String payer;

    @ExcelProperty("收款方")
    private String payee;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("项目款项主表id")
    private Long projectFundId;

}
