package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 物资寄来单申请 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class SupplySendInExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目 id")
    private Long projectId;

    @ExcelProperty("实验名目库的名目 id")
    private Long projectCategoryId;

    @ExcelProperty("寄来单号")
    private String code;

    @ExcelProperty("寄来物流单号")
    private String shipmentNumber;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("寄来时间")
    private String sendDate;

    @ExcelProperty("收货地址")
    private String address;

    @ExcelProperty("收货人")
    private String receiverName;

    @ExcelProperty("收货人电话")
    private String receiverPhone;

}
