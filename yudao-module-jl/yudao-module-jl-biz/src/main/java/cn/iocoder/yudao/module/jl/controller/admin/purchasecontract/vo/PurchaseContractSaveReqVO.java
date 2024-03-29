package cn.iocoder.yudao.module.jl.controller.admin.purchasecontract.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 购销合同创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PurchaseContractSaveReqVO extends PurchaseContractBaseVO {

    private List<ProcurementItem> procurementItemList;

}
