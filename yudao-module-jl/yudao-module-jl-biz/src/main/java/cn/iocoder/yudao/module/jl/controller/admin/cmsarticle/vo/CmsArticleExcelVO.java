package cn.iocoder.yudao.module.jl.controller.admin.cmsarticle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 文章 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class CmsArticleExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("副标题")
    private String subTitle;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("浏览次数")
    private Integer lookCount;

    @ExcelProperty("排序")
    private Integer sort;

}
