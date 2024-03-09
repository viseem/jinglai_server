package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 销售线索 Excel VO
 *
 * @author 惟象科技
 */
@Data
//excel内容居中
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER,verticalAlignment= VerticalAlignmentEnum.CENTER)//内容样式
public class SalesleadExcelVO {

    @ExcelProperty("客户")
    private String customerName;

    @ExcelProperty("销售")
    private String salesName;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty(value = "商机来源", converter = DictConvert.class)
    @DictFormat("sales_lead_source") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String source;

    @ExcelProperty(value = "业务类型", converter = DictConvert.class)
    @DictFormat("business_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String businessType;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("sales_lead_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer status;

    @ExcelProperty("预算(元)")
    private Long budget;

    @ExcelProperty("报价")
    private Long quotation;

/*    @ExcelProperty("客户id")
    private Long customerId;

    @ExcelProperty("项目id")
    private Long projectId;*/

    @ExcelProperty("最近跟进时间")
    private String lastFollowTime;

    @ExcelProperty("最近跟进内容")
    private String lastFollowContent;

    @ExcelProperty("关键需求")
    private String requirement;




}
