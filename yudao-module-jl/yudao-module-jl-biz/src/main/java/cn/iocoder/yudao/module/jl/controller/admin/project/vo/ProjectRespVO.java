package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.CustomerRespVO;
import cn.iocoder.yudao.module.jl.entity.crm.Customer;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.project.*;
import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import cn.iocoder.yudao.module.jl.entity.projectperson.ProjectPerson;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 项目管理 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectRespVO extends ProjectBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29196")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private Long currentScheduleId;

    private ProjectSchedule currentSchedule;

    private CustomerOnly customer;

    private User sales;

    private User manager;

    private User procurementer;
    private User intentorier;
    private User exper;
    private List<User> expers;


    @Schema(description = "物资成本")
    private Long supplyCost;

    @Schema(description = "采购成本")
    private Long procurementCost;

    @Schema(description = "收费项的成本")
    private Long chargeItemCost;

    @Schema(description = "委外的成本")
    private Long outsourceCost;

    @Schema(description = "报销的成本")
    private Long reimbursementCost;

    private List<ProjectConstractOnly> contracts = new ArrayList<>();
    private List<ProjectFundLog> fundLogs = new ArrayList<>();

    private ProjectApproval latestApproval;

    private List<ProjectCategoryOnly> categoryList;

    private List<ProjectPerson> persons;
}
