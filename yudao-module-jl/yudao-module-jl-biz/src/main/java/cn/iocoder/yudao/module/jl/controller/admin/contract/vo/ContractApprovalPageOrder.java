package cn.iocoder.yudao.module.jl.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 合同状态变更记录 Order 设置，用于分页使用
 */
@Data
public class ContractApprovalPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String stage;

    @Schema(allowableValues = {"desc", "asc"})
    private String stageMark;

    @Schema(allowableValues = {"desc", "asc"})
    private String approvalMark;

    @Schema(allowableValues = {"desc", "asc"})
    private String approvalStage;

    @Schema(allowableValues = {"desc", "asc"})
    private String contractId;

    @Schema(allowableValues = {"desc", "asc"})
    private String originStage;

    @Schema(allowableValues = {"desc", "asc"})
    private String processInstanceId;

}
