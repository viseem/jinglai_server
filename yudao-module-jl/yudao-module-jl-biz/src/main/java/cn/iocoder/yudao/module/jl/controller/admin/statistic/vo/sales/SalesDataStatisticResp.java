package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "销售数据统计响应")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesDataStatisticResp {
    
    @Schema(description = "是否正在刷新")
    private Boolean isRefreshing;
    
    @Schema(description = "是否正在查询中（实时计算）")
    private Boolean isQuerying;
    
    @Schema(description = "统计数据列表")
    private List<SalesDataItem> data;
    
    @Schema(description = "销售数据项")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalesDataItem {
        @Schema(description = "用户ID")
        private Long userId;
        
        @Schema(description = "用户名称")
        private String userName;
        
        @Schema(description = "成交金额")
        private BigDecimal orderAmount = BigDecimal.ZERO;
        
        @Schema(description = "应收金额")
        private BigDecimal accountsReceivable = BigDecimal.ZERO;
        
        @Schema(description = "已开票金额")
        private BigDecimal invoiceAmount = BigDecimal.ZERO;
        
        @Schema(description = "回款金额")
        private BigDecimal paymentAmount = BigDecimal.ZERO;
        
        @Schema(description = "更新时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime updateTime;
    }
}

