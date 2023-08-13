package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 客户联系人 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CrmContactExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("性别")
    private Byte gender;

    @ExcelProperty("客户id")
    private Integer customerId;

    @ExcelProperty("手机号")
    private String phone;

    @ExcelProperty("电话")
    private String tel;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("职务")
    private String position;

    @ExcelProperty("是否决策者")
    private Boolean isMaker;

    @ExcelProperty("详细地址")
    private String address;

    @ExcelProperty("省")
    private String province;

    @ExcelProperty("市")
    private String city;

    @ExcelProperty("区")
    private String area;

    @ExcelProperty("下次联系时间")
    private LocalDateTime nextContactTime;

    @ExcelProperty("备注")
    private String mark;

}
