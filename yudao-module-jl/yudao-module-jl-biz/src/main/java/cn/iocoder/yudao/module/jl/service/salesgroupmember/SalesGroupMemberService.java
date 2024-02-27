package cn.iocoder.yudao.module.jl.service.salesgroupmember;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.salesgroupmember.vo.*;
import cn.iocoder.yudao.module.jl.entity.salesgroupmember.SalesGroupMember;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 销售分组成员 Service 接口
 *
 */
public interface SalesGroupMemberService {

    /**
     * 创建销售分组成员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSalesGroupMember(@Valid SalesGroupMemberCreateReqVO createReqVO);

    /**
     * 更新销售分组成员
     *
     * @param updateReqVO 更新信息
     */
    void updateSalesGroupMember(@Valid SalesGroupMemberUpdateReqVO updateReqVO);

    /**
     * 删除销售分组成员
     *
     * @param id 编号
     */
    void deleteSalesGroupMember(Long id);

    /**
     * 获得销售分组成员
     *
     * @param id 编号
     * @return 销售分组成员
     */
    Optional<SalesGroupMember> getSalesGroupMember(Long id);

    /**
     * 获得销售分组成员列表
     *
     * @param ids 编号
     * @return 销售分组成员列表
     */
    List<SalesGroupMember> getSalesGroupMemberList(Collection<Long> ids);

    /**
     * 获得销售分组成员分页
     *
     * @param pageReqVO 分页查询
     * @return 销售分组成员分页
     */
    PageResult<SalesGroupMember> getSalesGroupMemberPage(SalesGroupMemberPageReqVO pageReqVO, SalesGroupMemberPageOrder orderV0);

    /**
     * 获得销售分组成员列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 销售分组成员列表
     */
    List<SalesGroupMember> getSalesGroupMemberList(SalesGroupMemberExportReqVO exportReqVO);

}
