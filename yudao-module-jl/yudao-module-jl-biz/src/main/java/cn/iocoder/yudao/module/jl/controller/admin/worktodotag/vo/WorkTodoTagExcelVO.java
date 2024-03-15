package cn.iocoder.yudao.module.jl.controller.admin.worktodotag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 工作任务 TODO 的标签 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class WorkTodoTagExcelVO {

    @ExcelProperty("名字")
    private String name;

    @ExcelProperty("类型：系统通用/用户自己创建")
    private String type;

}
