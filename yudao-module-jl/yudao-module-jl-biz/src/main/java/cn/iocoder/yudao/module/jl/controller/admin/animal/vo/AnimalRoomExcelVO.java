package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 动物饲养室 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class AnimalRoomExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("编号")
    private String code;

    @ExcelProperty("管理人id")
    private Long managerId;

    @ExcelProperty("描述说明")
    private String mark;

    @ExcelProperty("位置")
    private String location;

    @ExcelProperty("排序")
    private Integer order;

    @ExcelProperty("缩略图名称")
    private String fileName;

    @ExcelProperty("缩略图地址")
    private String fileUrl;

}
