package cn.iocoder.yudao.module.jl.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 公司资产（设备）预约 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class AssetDeviceLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("设备id")
    private Long deviceId;

    @ExcelProperty("预约说明")
    private String mark;

    @ExcelProperty("开始时间")
    private String startDate;

    @ExcelProperty("结束时间")
    private String endDate;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("用途分类：项目")
    private String useType;

    @ExcelProperty("项目设备id")
    private Long projectDeviceId;

}
