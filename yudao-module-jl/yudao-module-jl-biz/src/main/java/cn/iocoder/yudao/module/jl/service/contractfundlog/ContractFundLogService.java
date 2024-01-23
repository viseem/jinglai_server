package cn.iocoder.yudao.module.jl.service.contractfundlog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 合同收款记录 Service 接口
 *
 */
public interface ContractFundLogService {

    /**
     * 创建合同收款记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createContractFundLog(@Valid ContractFundLogCreateReqVO createReqVO);

    /**
     * 更新合同收款记录
     *
     * @param updateReqVO 更新信息
     */
    void updateContractFundLog(@Valid ContractFundLogUpdateReqVO updateReqVO);

    /**
     * 删除合同收款记录
     *
     * @param id 编号
     */
    void deleteContractFundLog(Long id);

    /**
     * 获得合同收款记录
     *
     * @param id 编号
     * @return 合同收款记录
     */
    Optional<ContractFundLog> getContractFundLog(Long id);

    /**
     * 获得合同收款记录列表
     *
     * @param ids 编号
     * @return 合同收款记录列表
     */
    List<ContractFundLog> getContractFundLogList(Collection<Long> ids);

    /**
     * 获得合同收款记录分页
     *
     * @param pageReqVO 分页查询
     * @return 合同收款记录分页
     */
    PageResult<ContractFundLog> getContractFundLogPage(ContractFundLogPageReqVO pageReqVO, ContractFundLogPageOrder orderV0);

    /**
     * 获得合同收款记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 合同收款记录列表
     */
    List<ContractFundLog> getContractFundLogList(ContractFundLogExportReqVO exportReqVO);

}
