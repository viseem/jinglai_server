package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.crm.Customer;
import cn.iocoder.yudao.module.jl.entity.project.ProjectApproval;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSchedule;
import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 项目管理 Response VO")
@Data
@ToString(callSuper = true)
public class ProjectStatsRespVO {
    //预开展个数
    private Integer preCount;
    //开展个数
    private Integer doingCount;
    //完成个数
    private Integer outedCount;
    //暂停个数
    private Integer pauseCount;
    //出库中个数
    private Integer outingCount;

    private Map<String,Integer> countMap;
}
