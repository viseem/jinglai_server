package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplyLogAttachment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 库管项目物资库存变更日志附件 Service 接口
 *
 */
public interface SupplyLogAttachmentService {

    /**
     * 创建库管项目物资库存变更日志附件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplyLogAttachment(@Valid SupplyLogAttachmentCreateReqVO createReqVO);

    /**
     * 更新库管项目物资库存变更日志附件
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplyLogAttachment(@Valid SupplyLogAttachmentUpdateReqVO updateReqVO);

    /**
     * 删除库管项目物资库存变更日志附件
     *
     * @param id 编号
     */
    void deleteSupplyLogAttachment(Long id);

    /**
     * 获得库管项目物资库存变更日志附件
     *
     * @param id 编号
     * @return 库管项目物资库存变更日志附件
     */
    Optional<SupplyLogAttachment> getSupplyLogAttachment(Long id);

    /**
     * 获得库管项目物资库存变更日志附件列表
     *
     * @param ids 编号
     * @return 库管项目物资库存变更日志附件列表
     */
    List<SupplyLogAttachment> getSupplyLogAttachmentList(Collection<Long> ids);

    /**
     * 获得库管项目物资库存变更日志附件分页
     *
     * @param pageReqVO 分页查询
     * @return 库管项目物资库存变更日志附件分页
     */
    PageResult<SupplyLogAttachment> getSupplyLogAttachmentPage(SupplyLogAttachmentPageReqVO pageReqVO, SupplyLogAttachmentPageOrder orderV0);

    /**
     * 获得库管项目物资库存变更日志附件列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 库管项目物资库存变更日志附件列表
     */
    List<SupplyLogAttachment> getSupplyLogAttachmentList(SupplyLogAttachmentExportReqVO exportReqVO);

}
