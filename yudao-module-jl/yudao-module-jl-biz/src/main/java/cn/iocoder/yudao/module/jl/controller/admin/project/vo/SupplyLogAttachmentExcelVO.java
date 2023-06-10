package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 库管项目物资库存变更日志附件 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class SupplyLogAttachmentExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目物资日志表id")
    private Long projectSupplyLogId;

    @ExcelProperty("附件名称")
    private String fileName;

    @ExcelProperty("附件地址")
    private String fileUrl;

}
