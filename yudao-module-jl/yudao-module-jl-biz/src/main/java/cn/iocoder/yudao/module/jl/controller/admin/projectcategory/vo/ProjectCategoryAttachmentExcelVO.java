package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目实验名目的附件 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectCategoryAttachmentExcelVO {

    @ExcelProperty("岗位ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("实验名目 id")
    private Long projectCategoryId;

    @ExcelProperty("文件名")
    private String fileName;

    @ExcelProperty("文件地址")
    private String fileUrl;

    @ExcelProperty("文件类型")
    private byte[] type;

}
