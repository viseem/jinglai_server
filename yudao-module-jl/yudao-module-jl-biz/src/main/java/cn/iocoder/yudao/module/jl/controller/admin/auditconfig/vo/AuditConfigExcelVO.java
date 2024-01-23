package cn.iocoder.yudao.module.jl.controller.admin.auditconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 审批配置表  Excel VO
 *
 * @author 惟象科技
 */
@Data
public class AuditConfigExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("路由")
    private String route;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("是否需要审批")
    private Boolean needAudit;

}
