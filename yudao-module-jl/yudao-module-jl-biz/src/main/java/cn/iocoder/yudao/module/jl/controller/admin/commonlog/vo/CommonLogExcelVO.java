package cn.iocoder.yudao.module.jl.controller.admin.commonlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 管控记录 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CommonLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("记录时间")
    private LocalDateTime logTime;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("外键id")
    private Long refId;

}
