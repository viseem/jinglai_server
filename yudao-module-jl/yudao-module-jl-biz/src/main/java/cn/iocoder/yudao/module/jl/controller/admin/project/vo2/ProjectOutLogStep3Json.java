package cn.iocoder.yudao.module.jl.controller.admin.project.vo2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 项目管理 Excel VO
 *
 * @author 惟象科技
 */
@Schema(description = "管理后台 - 皮带 Response VO")
@Data
@ToString(callSuper = true)
public class ProjectOutLogStep3Json {


    @Schema(description ="客户评分")
    private List<CustomerScore> customerScores;

    @Data
    @Schema(description = "")
    public static class CustomerScore {
        @Schema(description = "")
        private String label;
        @Schema(description = "")
        private String value;
    }
}
