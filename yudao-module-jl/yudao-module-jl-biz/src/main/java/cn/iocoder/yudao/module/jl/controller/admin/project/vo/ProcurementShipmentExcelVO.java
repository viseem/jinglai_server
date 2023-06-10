package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目采购单物流信息 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProcurementShipmentExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("采购单id")
    private Long procurementId;

    @ExcelProperty("物流单号")
    private String shipmentNumber;

    @ExcelProperty("附件名称")
    private String fileName;

    @ExcelProperty("附件地址")
    private String fileUrl;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("预计送达日期")
    private LocalDateTime expectArrivalTime;

}
