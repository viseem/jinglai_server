package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo;

import cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.PIGroupRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember.vo.SubjectGroupMemberRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Schema(description = "")
@Data
@ToString(callSuper = true)
public class PIGroupKPIStatisticResp {
    PIGroupRespVO basePIGroup;
    List<SubjectGroupMemberRespVO> members;
}
