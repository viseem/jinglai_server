package cn.iocoder.yudao.module.jl.controller.admin.commonlog.vo;

import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
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
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER,verticalAlignment= VerticalAlignmentEnum.CENTER)//内容样式
public class CommonLogExcelVO {

    @ExcelProperty("记录时间")
    private LocalDateTime logTime;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
