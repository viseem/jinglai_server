package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedCard;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedLog;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedStoreIn;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 动物饲养申请单 Response VO")
@Data
@ToString(callSuper = true)
public class AnimalFeedOrderStatsCountRespVO {


    private Long waitingFeedCount;
    private Long feedingCount;

    private Long endCount;

}
