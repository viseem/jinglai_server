package cn.iocoder.yudao.module.jl.service.contractinvoicelog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 合同发票记录 Service 接口
 *
 */
public interface ContractInvoiceLogService {

    /**
     * 创建合同发票记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createContractInvoiceLog(@Valid ContractInvoiceLogCreateReqVO createReqVO);

    /**
     * 更新合同发票记录
     *
     * @param updateReqVO 更新信息
     */
    void updateContractInvoiceLog(@Valid ContractInvoiceLogUpdateReqVO updateReqVO);

    /**
     * 删除合同发票记录
     *
     * @param id 编号
     */
    void deleteContractInvoiceLog(Long id);

    /**
     * 获得合同发票记录
     *
     * @param id 编号
     * @return 合同发票记录
     */
    Optional<ContractInvoiceLog> getContractInvoiceLog(Long id);

    /**
     * 获得合同发票记录列表
     *
     * @param ids 编号
     * @return 合同发票记录列表
     */
    List<ContractInvoiceLog> getContractInvoiceLogList(Collection<Long> ids);

    /**
     * 获得合同发票记录分页
     *
     * @param pageReqVO 分页查询
     * @return 合同发票记录分页
     */
    PageResult<ContractInvoiceLog> getContractInvoiceLogPage(ContractInvoiceLogPageReqVO pageReqVO, ContractInvoiceLogPageOrder orderV0);

    /**
     * 获得合同发票记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 合同发票记录列表
     */
    List<ContractInvoiceLog> getContractInvoiceLogList(ContractInvoiceLogExportReqVO exportReqVO);

}
