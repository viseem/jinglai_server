package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 库管房间号 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InventoryRoomBaseVO {

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "负责人", example = "22222")
    private Long guardianUserId;

    @Schema(description = "备注描述")
    private String mark;

    @Schema(description = "收件人", example = "赵六")
    private String receiverName;

    @Schema(description = "收件人电话", example = "赵六")
    private String receiverPhone;

    @Schema(description = "收件地址", example = "赵六")
    private String receiveAddress;


}
