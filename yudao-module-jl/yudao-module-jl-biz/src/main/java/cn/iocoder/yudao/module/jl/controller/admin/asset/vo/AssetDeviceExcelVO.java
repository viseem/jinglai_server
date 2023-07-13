package cn.iocoder.yudao.module.jl.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 公司资产（设备） Excel VO
 *
 * @author 惟象科技
 */
@Data
public class AssetDeviceExcelVO {

    @ExcelProperty("岗位ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("设备名称")
    private String name;

    @ExcelProperty("所属类型：公司、租赁")
    private String ownerType;

    @ExcelProperty("管理人 id")
    private Long managerId;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("设备快照名称")
    private String fileName;

    @ExcelProperty("设备快照地址")
    private String fileUrl;

    @ExcelProperty("设备类型：细胞仪器、解剖仪器")
    private String type;

    @ExcelProperty("位置")
    private String location;

    @ExcelProperty("设备状态：空闲、忙碌(前端先自己算)")
    private String status;

    @ExcelProperty("设备编码：后端生成")
    private String sn;

}
