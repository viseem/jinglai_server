package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目报销 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectReimburseExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("报销类型：物流成本、差旅费、其它")
    private String type;

    @ExcelProperty("项目的实验名目id")
    private Integer projectCategoryId;

    @ExcelProperty("项目id")
    private Integer projectId;

    @ExcelProperty("报销内容")
    private String content;

    @ExcelProperty("凭证名字")
    private String proofName;

    @ExcelProperty("凭证地址")
    private String proofUrl;

    @ExcelProperty("报销金额")
    private Integer price;

    @ExcelProperty("备注")
    private String mark;

}
