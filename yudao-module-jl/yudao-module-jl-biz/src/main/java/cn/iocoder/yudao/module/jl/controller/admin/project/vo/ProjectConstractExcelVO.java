package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目合同 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class ProjectConstractExcelVO {

    @ExcelProperty("合同名字")
    private String name;
    @ExcelProperty("对方公司名称")
    private String companyName;
    @ExcelProperty("文件名")
    private String stampFileName;

    @ExcelProperty("编号")
    private String sn;

    @ExcelProperty("金额")
    private Long price;

    @ExcelProperty("已收金额")
    private Long receivedPrice;

    @ExcelProperty("已开票")
    private Long invoicedPrice;

    @ExcelProperty("项目 id")
    private Long projectId;

    @ExcelProperty("合同状态：起效、失效、其它")
    private String status;

    @ExcelProperty("合同类型")
    private String type;

    @ExcelProperty("签订销售人员")
    private String salesName;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("合同下载地址")
    private String stampFileUrl;

}
