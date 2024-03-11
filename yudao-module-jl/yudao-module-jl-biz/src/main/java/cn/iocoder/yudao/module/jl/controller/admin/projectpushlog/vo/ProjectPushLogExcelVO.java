package cn.iocoder.yudao.module.jl.controller.admin.projectpushlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目推进记录 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectPushLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("推进内容")
    private String content;

    @ExcelProperty("推进时间")
    private LocalDateTime recordTime;

    @ExcelProperty("阶段")
    private String stage;

    @ExcelProperty("进度")
    private BigDecimal progress;

    @ExcelProperty("预期进度")
    private BigDecimal expectedProgress;

    @ExcelProperty("风险")
    private String risk;

    @ExcelProperty("备注")
    private String mark;

}
