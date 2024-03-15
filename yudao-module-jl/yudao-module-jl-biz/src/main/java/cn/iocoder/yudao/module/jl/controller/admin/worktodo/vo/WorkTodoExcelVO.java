package cn.iocoder.yudao.module.jl.controller.admin.worktodo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 工作任务 TODO Excel VO
 *
 * @author 惟象科技
 */
@Data
public class WorkTodoExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty(value = "重要程度：重要紧急 不重要不紧急 重要不紧急", converter = DictConvert.class)
    @DictFormat("todo_priority") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String priority;

    @ExcelProperty("期望截止日期（排期）")
    private LocalDateTime deadline;

    @ExcelProperty("状态：未受理、已处理")
    private String status;

    @ExcelProperty("类型(系统生成的任务、用户自己创建)")
    private String type;

}
