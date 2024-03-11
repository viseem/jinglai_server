package cn.iocoder.yudao.module.jl.controller.admin.picollaboration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * PI组协作 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class PiCollaborationExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("协作的事项id")
    private Long targetId;

    @ExcelProperty("协作的事项类型")
    private String targetType;

    @ExcelProperty("备注")
    private String mark;

}
