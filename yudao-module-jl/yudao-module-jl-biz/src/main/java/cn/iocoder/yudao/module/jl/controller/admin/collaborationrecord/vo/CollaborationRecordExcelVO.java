package cn.iocoder.yudao.module.jl.controller.admin.collaborationrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 通用协作记录 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CollaborationRecordExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("外键id")
    private Long refId;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("父级id")
    private Long pid;

    @ExcelProperty("状态")
    private String status;

}
