package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "管理后台 - 库管操作的文件 item Request VO")
@Data
@ToString(callSuper = true)
public class CheckInAttachment {


    private String fileName;

    private String fileUrl;
}
