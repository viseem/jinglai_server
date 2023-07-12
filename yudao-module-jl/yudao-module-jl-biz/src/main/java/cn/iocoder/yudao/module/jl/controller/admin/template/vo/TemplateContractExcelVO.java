package cn.iocoder.yudao.module.jl.controller.admin.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 合同模板 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class TemplateContractExcelVO {

    @ExcelProperty("岗位ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("文件名")
    private String fileName;

    @ExcelProperty("文件地址")
    private String fileUrl;

    @ExcelProperty("关键备注")
    private String mark;

    @ExcelProperty("合同类型：项目合同、饲养合同")
    private String type;

    @ExcelProperty("合同用途")
    private String useWay;

    @ExcelProperty("合同名称")
    private String name;

}
