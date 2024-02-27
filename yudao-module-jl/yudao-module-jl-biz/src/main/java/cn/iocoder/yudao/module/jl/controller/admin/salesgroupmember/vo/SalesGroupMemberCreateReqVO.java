package cn.iocoder.yudao.module.jl.controller.admin.salesgroupmember.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 销售分组成员创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SalesGroupMemberCreateReqVO extends SalesGroupMemberBaseVO {

}
