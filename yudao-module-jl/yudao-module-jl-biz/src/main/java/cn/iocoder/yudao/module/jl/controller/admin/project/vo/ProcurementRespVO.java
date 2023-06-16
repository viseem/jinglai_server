package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.CustomerRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.user.vo.UserRespVO;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementPayment;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementShipment;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 项目采购单申请 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcurementRespVO extends ProcurementBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13907")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private List<ProcurementItemRespVO> items;

    private UserRespVO applyUser;

    private UserRespVO checkUser;

    private List<ProcurementShipmentRespVO> shipments;

    private List<ProcurementPaymentRespVO> payments;

    private ProjectRespVO project;
}
