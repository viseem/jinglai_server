package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 公司实验物资库存 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CompanySupplyExcelVO {

    @ExcelProperty("岗位ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("规则/单位")
    private String feeStandard;

    @ExcelProperty("单量")
    private Integer unitAmount;

    @ExcelProperty("数量")
    private Integer quantity;

    @ExcelProperty("物资 id")
    private Long supplyId;

    @ExcelProperty("存储位置")
    private String location;

    @ExcelProperty("项目物资id")
    private Long projectSupplyId;

    @ExcelProperty("所属客户")
    private Long customerId;

    @ExcelProperty("所属项目")
    private Long projectId;

    @ExcelProperty("所属类型：公司、客户")
    private String ownerType;

    @ExcelProperty("单价")
    private String unitFee;

    @ExcelProperty("有效期")
    private String validDate;

    @ExcelProperty("物资快照名称")
    private String fileName;

    @ExcelProperty("物资快照地址")
    private String fileUrl;

}
