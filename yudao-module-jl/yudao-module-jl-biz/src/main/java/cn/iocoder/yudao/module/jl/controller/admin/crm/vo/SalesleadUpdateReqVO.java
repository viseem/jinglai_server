package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectConstractBaseVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectConstractItemVO;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 销售线索更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SalesleadUpdateReqVO extends SalesleadBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private Long id;

    @Schema(description = "竞争对手报价", example = "[]")
    private List<SalesleadCompetitorItemVO> competitorQuotations = new ArrayList<>();

    @Schema(description = "客户方案", example = "[]")
    private List<SalesleadCustomerPlanItemVO> customerPlans = new ArrayList<>();

    @Schema(description = "项目名字", example = "[]")
    private String projectName;
    @Schema(description = "项目类型", example = "")
    private String type;
    @Schema(description = "项目合同", example = "")
    private ProjectConstract contract;

    @Schema(description = "合同文件")
    private String contractStampFileUrl;
    @Schema(description = "合同文件名称")
    private String contractStampFileName;
    @Schema(description = "合同金额")
    private Long contractPrice;
    @Schema(description = "合同编号")
    private String contractSn;
    @Schema(description = "对方公司名称")
    private String contractCompanyName;

}
