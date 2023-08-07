package cn.iocoder.yudao.module.jl.service.approval;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.approval.vo.*;
import cn.iocoder.yudao.module.jl.entity.approval.Approval;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 审批 Service 接口
 *
 */
public interface ApprovalService {

    /**
     * 创建审批
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createApproval(@Valid ApprovalCreateReqVO createReqVO);

    /**
     * 更新审批
     *
     * @param updateReqVO 更新信息
     */
    void updateApproval(@Valid ApprovalUpdateReqVO updateReqVO);

    /**
     * 删除审批
     *
     * @param id 编号
     */
    void deleteApproval(Long id);

    /**
     * 获得审批
     *
     * @param id 编号
     * @return 审批
     */
    Optional<Approval> getApproval(Long id);

    /**
     * 获得审批列表
     *
     * @param ids 编号
     * @return 审批列表
     */
    List<Approval> getApprovalList(Collection<Long> ids);

    /**
     * 获得审批分页
     *
     * @param pageReqVO 分页查询
     * @return 审批分页
     */
    PageResult<Approval> getApprovalPage(ApprovalPageReqVO pageReqVO, ApprovalPageOrder orderV0);

    /**
     * 获得审批列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 审批列表
     */
    List<Approval> getApprovalList(ApprovalExportReqVO exportReqVO);

}
