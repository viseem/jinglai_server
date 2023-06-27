package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryOptAttachment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 库管操作附件记录 Service 接口
 *
 */
public interface InventoryOptAttachmentService {

    /**
     * 创建库管操作附件记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInventoryOptAttachment(@Valid InventoryOptAttachmentCreateReqVO createReqVO);

    /**
     * 更新库管操作附件记录
     *
     * @param updateReqVO 更新信息
     */
    void updateInventoryOptAttachment(@Valid InventoryOptAttachmentUpdateReqVO updateReqVO);

    /**
     * 删除库管操作附件记录
     *
     * @param id 编号
     */
    void deleteInventoryOptAttachment(Long id);

    /**
     * 获得库管操作附件记录
     *
     * @param id 编号
     * @return 库管操作附件记录
     */
    Optional<InventoryOptAttachment> getInventoryOptAttachment(Long id);

    /**
     * 获得库管操作附件记录列表
     *
     * @param ids 编号
     * @return 库管操作附件记录列表
     */
    List<InventoryOptAttachment> getInventoryOptAttachmentList(Collection<Long> ids);

    /**
     * 获得库管操作附件记录分页
     *
     * @param pageReqVO 分页查询
     * @return 库管操作附件记录分页
     */
    PageResult<InventoryOptAttachment> getInventoryOptAttachmentPage(InventoryOptAttachmentPageReqVO pageReqVO, InventoryOptAttachmentPageOrder orderV0);

    /**
     * 获得库管操作附件记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 库管操作附件记录列表
     */
    List<InventoryOptAttachment> getInventoryOptAttachmentList(InventoryOptAttachmentExportReqVO exportReqVO);

}
