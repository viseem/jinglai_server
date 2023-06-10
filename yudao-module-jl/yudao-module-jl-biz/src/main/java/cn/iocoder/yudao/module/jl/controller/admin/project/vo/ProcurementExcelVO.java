package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目采购单申请 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProcurementExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目 id")
    private Long projectId;

    @ExcelProperty("实验名目库的名目 id")
    private Long projectCategoryId;

    @ExcelProperty("采购单号")
    private String code;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("采购发起时间")
    private String startDate;

    @ExcelProperty("签收陪审人")
    private Long checkUserId;

    @ExcelProperty("收货地址")
    private String address;

    @ExcelProperty("收货人id")
    private String receiverUserId;

}
