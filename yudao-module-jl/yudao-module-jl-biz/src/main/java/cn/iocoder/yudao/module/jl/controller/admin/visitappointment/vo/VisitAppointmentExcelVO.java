package cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 晶莱到访预约 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class VisitAppointmentExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("陪同人员id")
    private Long userId;

    @ExcelProperty("设备id")
    private Long deviceId;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("到访时间")
    private LocalDateTime visitTime;

    @ExcelProperty("客户id")
    private Long customerId;

}
