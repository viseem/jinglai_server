package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 取货单申请 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class SupplyPickupExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目 id")
    private Long projectId;

    @ExcelProperty("实验名目库的名目 id")
    private Long projectCategoryId;

    @ExcelProperty("取货单号")
    private String code;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("取货时间")
    private String sendDate;

    @ExcelProperty("取货人")
    private Long userId;

    @ExcelProperty("取货地址")
    private String address;

    @ExcelProperty("联系人姓名")
    private String contactName;

    @ExcelProperty("联系人电话")
    private String contactPhone;

}
