package cn.iocoder.yudao.module.jl.controller.admin.inventorystorelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 物品出入库日志 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class InventoryStoreLogExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("物品来源")
    private String source;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("目录号")
    private String catalogNumber;

    @ExcelProperty("品牌")
    private String brand;

    @ExcelProperty("规格")
    private String spec;

    @ExcelProperty("单位")
    private String unit;

    @ExcelProperty("变更数量")
    private Integer changeNum;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("房间id")
    private Long roomId;

    @ExcelProperty("容器id")
    private Long containerId;

    @ExcelProperty("位置id")
    private Long placeId;

    @ExcelProperty("位置详情")
    private String location;

    @ExcelProperty("明细的id")
    private Long sourceItemId;

    @ExcelProperty("项目物资的id")
    private Long projectSupplyId;

    @ExcelProperty("项目id")
    private Long projectId;

    @ExcelProperty("购销合同id")
    private Long purchaseContractId;

    @ExcelProperty("采购单id")
    private Long procurementId;

}
