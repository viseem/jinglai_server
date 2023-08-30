package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "")
@Data
@ToString(callSuper = true)
public class WorkstationWarehouseCountStatsResp {

    Integer waitingCheckInProcurementCount=0;
    Integer waitingCheckInSendInCount=0;
    Integer waitingCheckInPickupCount=0;
    Integer waitingOrdersCheckInCount=0;

    public Integer getWaitingOrdersCheckInCount() {
        return waitingCheckInProcurementCount+waitingCheckInSendInCount+waitingCheckInPickupCount;
    }

    Integer waitingInProcurementCount=0;
    Integer waitingInSendInCount=0;
    Integer waitingInPickupCount=0;
    Integer waitingOrdersInCount=0;

    public Integer getWaitingOrdersInCount() {
        return waitingInProcurementCount+waitingInSendInCount+waitingInPickupCount;
    }

    Integer waitingOrdersCount=0;

    public Integer getWaitingOrdersCount() {
        return this.getWaitingOrdersInCount()+this.getWaitingOrdersCheckInCount();
    }

    Integer waitingProductInCount=0;

    Integer waitingSupplyOutCount=0;
    Integer waitingSendOutCount=0;
    Integer waitingOutCount=0;

    public Integer getWaitingOutCount() {
        return waitingSupplyOutCount+waitingSendOutCount;
    }
}
