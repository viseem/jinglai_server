package cn.iocoder.yudao.module.jl.service.commonattachment;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.commonattachment.vo.*;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 通用附件 Service 接口
 *
 */
public interface CommonAttachmentService {

    /**
     * 创建通用附件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCommonAttachment(@Valid CommonAttachmentCreateReqVO createReqVO);

    /**
     * 更新通用附件
     *
     * @param updateReqVO 更新信息
     */
    void updateCommonAttachment(@Valid CommonAttachmentUpdateReqVO updateReqVO);

    /**
     * 删除通用附件
     *
     * @param id 编号
     */
    void deleteCommonAttachment(Long id);

    /**
     * 获得通用附件
     *
     * @param id 编号
     * @return 通用附件
     */
    Optional<CommonAttachment> getCommonAttachment(Long id);

    /**
     * 获得通用附件列表
     *
     * @param ids 编号
     * @return 通用附件列表
     */
    List<CommonAttachment> getCommonAttachmentList(Collection<Long> ids);

    /**
     * 获得通用附件分页
     *
     * @param pageReqVO 分页查询
     * @return 通用附件分页
     */
    PageResult<CommonAttachment> getCommonAttachmentPage(CommonAttachmentPageReqVO pageReqVO, CommonAttachmentPageOrder orderV0);

    /**
     * 获得通用附件列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 通用附件列表
     */
    List<CommonAttachment> getCommonAttachmentList(CommonAttachmentExportReqVO exportReqVO);

}
