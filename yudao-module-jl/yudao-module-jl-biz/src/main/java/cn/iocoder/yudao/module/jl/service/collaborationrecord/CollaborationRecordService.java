package cn.iocoder.yudao.module.jl.service.collaborationrecord;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.collaborationrecord.vo.*;
import cn.iocoder.yudao.module.jl.entity.collaborationrecord.CollaborationRecord;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 通用协作记录 Service 接口
 *
 */
public interface CollaborationRecordService {

    /**
     * 创建通用协作记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCollaborationRecord(@Valid CollaborationRecordCreateReqVO createReqVO);

    /**
     * 更新通用协作记录
     *
     * @param updateReqVO 更新信息
     */
    void updateCollaborationRecord(@Valid CollaborationRecordUpdateReqVO updateReqVO);

    /**
     * 删除通用协作记录
     *
     * @param id 编号
     */
    void deleteCollaborationRecord(Long id);

    /**
     * 获得通用协作记录
     *
     * @param id 编号
     * @return 通用协作记录
     */
    Optional<CollaborationRecord> getCollaborationRecord(Long id);

    /**
     * 获得通用协作记录列表
     *
     * @param ids 编号
     * @return 通用协作记录列表
     */
    List<CollaborationRecord> getCollaborationRecordList(Collection<Long> ids);

    /**
     * 获得通用协作记录分页
     *
     * @param pageReqVO 分页查询
     * @return 通用协作记录分页
     */
    PageResult<CollaborationRecord> getCollaborationRecordPage(CollaborationRecordPageReqVO pageReqVO, CollaborationRecordPageOrder orderV0);

    /**
     * 获得通用协作记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 通用协作记录列表
     */
    List<CollaborationRecord> getCollaborationRecordList(CollaborationRecordExportReqVO exportReqVO);

}
