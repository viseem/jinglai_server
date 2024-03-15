package cn.iocoder.yudao.module.jl.controller.admin.worktodotagrelation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 工作任务 TODO 与标签的关联 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class WorkTodoTagRelationExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("todo id")
    private Long todoId;

    @ExcelProperty("tag id")
    private Long tagId;

}
