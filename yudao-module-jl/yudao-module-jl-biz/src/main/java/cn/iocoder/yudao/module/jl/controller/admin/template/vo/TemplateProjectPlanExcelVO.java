package cn.iocoder.yudao.module.jl.controller.admin.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目方案模板 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class TemplateProjectPlanExcelVO {

    @ExcelProperty("岗位ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("参考文件")
    private String fileName;

    @ExcelProperty("参考文件地址")
    private String fileUrl;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("模板：方案富文本")
    private String content;

}
