package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目物资变更日志 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class SupplyLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("相关的id，采购、寄来、取货")
    private Long refId;

    @ExcelProperty("类型：采购、寄来、取货")
    private Long type;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("备注描述")
    private String mark;

    @ExcelProperty("变更描述")
    private String log;

}
