package cn.iocoder.yudao.module.jl.service.salesgroup;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.salesgroup.vo.*;
import cn.iocoder.yudao.module.jl.entity.salesgroup.SalesGroup;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 销售分组 Service 接口
 *
 */
public interface SalesGroupService {

    /**
     * 创建销售分组
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSalesGroup(@Valid SalesGroupCreateReqVO createReqVO);

    /**
     * 更新销售分组
     *
     * @param updateReqVO 更新信息
     */
    void updateSalesGroup(@Valid SalesGroupUpdateReqVO updateReqVO);

    /**
     * 删除销售分组
     *
     * @param id 编号
     */
    void deleteSalesGroup(Long id);

    /**
     * 获得销售分组
     *
     * @param id 编号
     * @return 销售分组
     */
    Optional<SalesGroup> getSalesGroup(Long id);

    /**
     * 获得销售分组列表
     *
     * @param ids 编号
     * @return 销售分组列表
     */
    List<SalesGroup> getSalesGroupList(Collection<Long> ids);

    /**
     * 获得销售分组分页
     *
     * @param pageReqVO 分页查询
     * @return 销售分组分页
     */
    PageResult<SalesGroup> getSalesGroupPage(SalesGroupPageReqVO pageReqVO, SalesGroupPageOrder orderV0);

    /**
     * 获得销售分组列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 销售分组列表
     */
    List<SalesGroup> getSalesGroupList(SalesGroupExportReqVO exportReqVO);

}
