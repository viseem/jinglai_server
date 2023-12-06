package cn.iocoder.yudao.module.infra.controller.admin.file.vo.file.word;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - html转word Request VO")
@Data
public class Html2WordReqVO {

    @Schema(description = "html", example = "yudaoyuanma.png")
    private String html;

    private Long quotationId;

}
