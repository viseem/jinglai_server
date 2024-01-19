package cn.iocoder.yudao.module.jl.controller.admin.cellbase.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 细胞数据 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CellBaseExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("数量")
    private String quantity;

    @ExcelProperty("传代次数")
    private String generationCount;

    @ExcelProperty("项目id")
    private Integer projectId;

    @ExcelProperty("客户id")
    private Integer customerId;

    @ExcelProperty("备注")
    private String mark;

}
