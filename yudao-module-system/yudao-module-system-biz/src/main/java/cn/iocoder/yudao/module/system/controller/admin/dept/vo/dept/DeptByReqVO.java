package cn.iocoder.yudao.module.system.controller.admin.dept.vo.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 部门列表 Request VO")
@Data
public class DeptByReqVO {

    @Schema(description = "负责人的用户编号", example = "2048")
    private Long leaderUserId;

}
