package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目实验委外供应商 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectCategorySupplierBaseVO {

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "联系人", example = "芋艿")
    private String contactName;

    @Schema(description = "联系方式")
    private String contactPhone;

    @Schema(description = "擅长领域")
    private String advantage;

}
