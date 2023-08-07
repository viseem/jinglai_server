package cn.iocoder.yudao.module.jl.service.approval;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.approval.vo.*;
import cn.iocoder.yudao.module.jl.entity.approval.ApprovalProgress;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 审批流程 Service 接口
 *
 */
public interface ApprovalProgressService {

    /**
     * 创建审批流程
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createApprovalProgress(@Valid ApprovalProgressCreateReqVO createReqVO);

    /**
     * 更新审批流程
     *
     * @param updateReqVO 更新信息
     */
    void updateApprovalProgress(@Valid ApprovalProgressUpdateReqVO updateReqVO);

    /**
     * 删除审批流程
     *
     * @param id 编号
     */
    void deleteApprovalProgress(Long id);

    /**
     * 获得审批流程
     *
     * @param id 编号
     * @return 审批流程
     */
    Optional<ApprovalProgress> getApprovalProgress(Long id);

    /**
     * 获得审批流程列表
     *
     * @param ids 编号
     * @return 审批流程列表
     */
    List<ApprovalProgress> getApprovalProgressList(Collection<Long> ids);

    /**
     * 获得审批流程分页
     *
     * @param pageReqVO 分页查询
     * @return 审批流程分页
     */
    PageResult<ApprovalProgress> getApprovalProgressPage(ApprovalProgressPageReqVO pageReqVO, ApprovalProgressPageOrder orderV0);

    /**
     * 获得审批流程列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 审批流程列表
     */
    List<ApprovalProgress> getApprovalProgressList(ApprovalProgressExportReqVO exportReqVO);

}
