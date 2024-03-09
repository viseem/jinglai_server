package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 项目合同 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectConstractBaseVO {

    @Schema(description = "项目 id")
    private Long projectId;

    @Schema(description = "客户id")
    private Long customerId;

    @Schema(description = "合同名字", example = "赵六")
    private String name;

    @Schema(description = "公司名称", example = "赵六", requiredMode = Schema.RequiredMode.REQUIRED)
    private String companyName;
    private String mark;

    @Schema(description = "合同文件 URL")
    private String fileUrl;

    @Schema(description = "合同附件json")
    private String jsonFile;

    @Schema(description = "盖章合同文件 URL")
    private String stampFileUrl;
    @Schema(description = "盖章合同文件 URL")
    private String stampFileName;

    @Schema(description = "合同状态：起效、失效、其它", example = "2")
    private String status = ProjectContractStatusEnums.WAIT_SIGN.getStatus();

    private String payStatus;
    @Schema(description = "合同业务类型", example = "1")
    private String type;

    @Schema(description = "合同类型", example = "1")
    private String contractType;

    @Schema(description = "合同金额", example = "30614", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    @Schema(description = "纸面金额", example = "30614", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal paperPrice;

    @Schema(description = "结算金额", example = "30614")
    private BigDecimal realPrice;

    @Schema(description = "签订销售人员", example = "32406")
    private Long salesId;

    @Schema(description = "合同编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12507")
    private String sn;

    @Schema(description = "合同文件名", example = "芋艿")
    private String fileName;

    @Schema(description = "合同关联的文档id", example = "芋艿")
    private String projectDocumentId;

    private Integer isOuted;

    @Schema(description = "签订日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime signedTime;
}
