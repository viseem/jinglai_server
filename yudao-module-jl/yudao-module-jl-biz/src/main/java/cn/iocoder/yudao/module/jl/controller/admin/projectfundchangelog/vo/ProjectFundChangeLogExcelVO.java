package cn.iocoder.yudao.module.jl.controller.admin.projectfundchangelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 款项计划变更日志 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectFundChangeLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("款项计划")
    private Long projectFundId;

    @ExcelProperty("原状态")
    private String originStatus;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("销售id")
    private Long salesId;

    @ExcelProperty("客户id")
    private Long customerId;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("合同id")
    private Long contractId;

    @ExcelProperty("款项名称")
    private String name;

    @ExcelProperty("款项金额")
    private Long price;

    @ExcelProperty("款项应收日期")
    private LocalDateTime deadline;

    @ExcelProperty("变更类型 默认1：状态变更")
    private String changeType;

}
