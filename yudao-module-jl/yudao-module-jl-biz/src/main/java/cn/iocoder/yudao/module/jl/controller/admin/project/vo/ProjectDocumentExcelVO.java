package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目文档 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectDocumentExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("名称")
    private String fileName;

    @ExcelProperty("地址")
    private String fileUrl;

    @ExcelProperty("项目")
    private Long projectId;

    @ExcelProperty("备注")
    private String mark;

}
