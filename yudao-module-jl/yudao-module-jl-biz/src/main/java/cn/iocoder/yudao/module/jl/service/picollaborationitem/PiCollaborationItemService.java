package cn.iocoder.yudao.module.jl.service.picollaborationitem;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.picollaborationitem.vo.*;
import cn.iocoder.yudao.module.jl.entity.picollaborationitem.PiCollaborationItem;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * pi组协作明细 Service 接口
 *
 */
public interface PiCollaborationItemService {

    /**
     * 创建pi组协作明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPiCollaborationItem(@Valid PiCollaborationItemCreateReqVO createReqVO);

    /**
     * 更新pi组协作明细
     *
     * @param updateReqVO 更新信息
     */
    void updatePiCollaborationItem(@Valid PiCollaborationItemUpdateReqVO updateReqVO);

    /**
     * 删除pi组协作明细
     *
     * @param id 编号
     */
    void deletePiCollaborationItem(Long id);

    /**
     * 获得pi组协作明细
     *
     * @param id 编号
     * @return pi组协作明细
     */
    Optional<PiCollaborationItem> getPiCollaborationItem(Long id);

    /**
     * 获得pi组协作明细列表
     *
     * @param ids 编号
     * @return pi组协作明细列表
     */
    List<PiCollaborationItem> getPiCollaborationItemList(Collection<Long> ids);

    /**
     * 获得pi组协作明细分页
     *
     * @param pageReqVO 分页查询
     * @return pi组协作明细分页
     */
    PageResult<PiCollaborationItem> getPiCollaborationItemPage(PiCollaborationItemPageReqVO pageReqVO, PiCollaborationItemPageOrder orderV0);

    /**
     * 获得pi组协作明细列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return pi组协作明细列表
     */
    List<PiCollaborationItem> getPiCollaborationItemList(PiCollaborationItemExportReqVO exportReqVO);

}
