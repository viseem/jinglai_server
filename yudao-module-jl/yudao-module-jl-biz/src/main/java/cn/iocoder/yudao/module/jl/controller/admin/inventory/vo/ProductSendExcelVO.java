package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 实验产品寄送 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProductSendExcelVO {

    @ExcelProperty("岗位ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目 id")
    private Long projectId;

    @ExcelProperty("实验名目库的名目 id")
    private Long projectCategoryId;

    @ExcelProperty("寄送单号")
    private String code;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("寄送时间")
    private String sendDate;

    @ExcelProperty("收货地址")
    private String address;

    @ExcelProperty("收货人")
    private String receiverName;

    @ExcelProperty("收货人电话")
    private String receiverPhone;

}
