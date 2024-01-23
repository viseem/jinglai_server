package cn.iocoder.yudao.module.jl.controller.admin.commonattachment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 通用附件 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CommonAttachmentExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("id")
    private Integer refId;

    @ExcelProperty("文件名称")
    private String fileName;

    @ExcelProperty("文件地址")
    private String fileUrl;

    @ExcelProperty("备注")
    private String mark;

}
