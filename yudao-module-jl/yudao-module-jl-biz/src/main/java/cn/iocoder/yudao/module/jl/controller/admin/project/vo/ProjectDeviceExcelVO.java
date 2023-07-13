package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目设备 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectDeviceExcelVO {

    @ExcelProperty("岗位ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("设备id")
    private Long deviceId;

    @ExcelProperty("设备名称")
    private String name;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("开始时间")
    private LocalDateTime startDate;

    @ExcelProperty("结束时间")
    private LocalDateTime endDate;

}
