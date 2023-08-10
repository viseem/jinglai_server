package cn.iocoder.yudao.module.jl.service.contract;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.jl.entity.contract.ContractApproval;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 合同状态变更记录 Service 接口
 *
 */
public interface ContractApprovalService {

    /**
     * 创建合同状态变更记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createContractApproval(@Valid ContractApprovalCreateReqVO createReqVO);

    /**
     * 更新合同状态变更记录
     *
     * @param updateReqVO 更新信息
     */
    void updateContractApproval(@Valid ContractApprovalUpdateReqVO updateReqVO);

    /**
     * 删除合同状态变更记录
     *
     * @param id 编号
     */
    void deleteContractApproval(Long id);

    /**
     * 获得合同状态变更记录
     *
     * @param id 编号
     * @return 合同状态变更记录
     */
    Optional<ContractApproval> getContractApproval(Long id);

    /**
     * 获得合同状态变更记录列表
     *
     * @param ids 编号
     * @return 合同状态变更记录列表
     */
    List<ContractApproval> getContractApprovalList(Collection<Long> ids);

    /**
     * 获得合同状态变更记录分页
     *
     * @param pageReqVO 分页查询
     * @return 合同状态变更记录分页
     */
    PageResult<ContractApproval> getContractApprovalPage(ContractApprovalPageReqVO pageReqVO, ContractApprovalPageOrder orderV0);

    /**
     * 获得合同状态变更记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 合同状态变更记录列表
     */
    List<ContractApproval> getContractApprovalList(ContractApprovalExportReqVO exportReqVO);

}
