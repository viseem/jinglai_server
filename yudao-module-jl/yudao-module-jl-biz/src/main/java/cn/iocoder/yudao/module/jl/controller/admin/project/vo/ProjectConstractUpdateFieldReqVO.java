package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 项目合同更新 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectConstractUpdateFieldReqVO {
    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22660")
    @NotNull(message = "岗位ID不能为空")
    private Long id;
    @Schema(description = "项目 id")
    private Long projectId;

    @Schema(description = "客户id")
    private Long customerId;

    @Schema(description = "合同名字", example = "赵六")
    private String name;

    @Schema(description = "合同文件 URL")
    private String fileUrl;

    @Schema(description = "合同状态：起效、失效、其它", example = "2")
    private String status;


    @Schema(description = "合同类型", example = "1")
    private String type;

    @Schema(description = "合同金额", example = "30614")
    private Long price;

    @Schema(description = "结算金额", example = "30614")
    private Long realPrice;

    @Schema(description = "签订销售人员", example = "32406")
    private Long salesId;

    @Schema(description = "合同编号")
    private String sn;

    @Schema(description = "合同文件名", example = "芋艿")
    private String fileName;

    @Schema(description = "是否收齐", example = "芋艿")
    private Boolean isCollectAll;

}
