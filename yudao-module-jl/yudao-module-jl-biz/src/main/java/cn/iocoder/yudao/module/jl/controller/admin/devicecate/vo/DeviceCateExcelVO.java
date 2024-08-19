package cn.iocoder.yudao.module.jl.controller.admin.devicecate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 设备分类 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class DeviceCateExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("说明")
    private String mark;

    @ExcelProperty("父级id")
    private Long parentId;

    @ExcelProperty("标签")
    private String tagIds;

    @ExcelProperty("排序")
    private Integer sort;

    @ExcelProperty("详细介绍")
    private String content;

}
