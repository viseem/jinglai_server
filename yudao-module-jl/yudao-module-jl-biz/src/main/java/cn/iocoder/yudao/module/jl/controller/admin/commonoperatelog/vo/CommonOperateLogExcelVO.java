package cn.iocoder.yudao.module.jl.controller.admin.commonoperatelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 通用操作记录 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CommonOperateLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("旧内容")
    private String oldContent;

    @ExcelProperty("新内容")
    private String newContent;

    @ExcelProperty("父类型")
    private String type;

    @ExcelProperty("子类型")
    private String subType;

    @ExcelProperty("事件类型")
    private String eventType;

    @ExcelProperty("父类型关联id")
    private Long refId;

    @ExcelProperty("子类型关联id")
    private Long subRefId;

}
