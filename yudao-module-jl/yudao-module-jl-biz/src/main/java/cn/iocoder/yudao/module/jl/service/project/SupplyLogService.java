package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplyLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目物资变更日志 Service 接口
 *
 */
public interface SupplyLogService {

    /**
     * 创建项目物资变更日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplyLog(@Valid SupplyLogCreateReqVO createReqVO);

    /**
     * 更新项目物资变更日志
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplyLog(@Valid SupplyLogUpdateReqVO updateReqVO);

    /**
     * 删除项目物资变更日志
     *
     * @param id 编号
     */
    void deleteSupplyLog(Long id);

    /**
     * 获得项目物资变更日志
     *
     * @param id 编号
     * @return 项目物资变更日志
     */
    Optional<SupplyLog> getSupplyLog(Long id);

    /**
     * 获得项目物资变更日志列表
     *
     * @param ids 编号
     * @return 项目物资变更日志列表
     */
    List<SupplyLog> getSupplyLogList(Collection<Long> ids);

    /**
     * 获得项目物资变更日志分页
     *
     * @param pageReqVO 分页查询
     * @return 项目物资变更日志分页
     */
    PageResult<SupplyLog> getSupplyLogPage(SupplyLogPageReqVO pageReqVO, SupplyLogPageOrder orderV0);

    /**
     * 获得项目物资变更日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目物资变更日志列表
     */
    List<SupplyLog> getSupplyLogList(SupplyLogExportReqVO exportReqVO);

}
