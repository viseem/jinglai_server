package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart;

import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Schema(description = "")
@Data
@ToString(callSuper = true)
public class ChartInvoiceStatsResp {

    private List<ContractInvoiceLog> invoiceList;

}
