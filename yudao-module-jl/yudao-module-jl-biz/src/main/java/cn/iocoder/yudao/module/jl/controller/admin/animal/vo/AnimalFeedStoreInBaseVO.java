package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 动物饲养入库 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AnimalFeedStoreInBaseVO {

    @Schema(description = "饲养单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2577")
    @NotNull(message = "饲养单id不能为空")
    private Long feedOrderId;

    @Schema(description = "房间id", example = "22761")
    private Long roomId;

    @Schema(description = "架子ids")
    private String shelfIds;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "位置code")
    private String locationCode;

    private User user;
}
