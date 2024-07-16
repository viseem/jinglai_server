package cn.iocoder.yudao.module.jl.service.customerchangelog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.customerchangelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.customerchangelog.CustomerChangeLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 客户变更日志 Service 接口
 *
 */
public interface CustomerChangeLogService {

    /**
     * 创建客户变更日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomerChangeLog(@Valid CustomerChangeLogCreateReqVO createReqVO);

    /**
     * 更新客户变更日志
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomerChangeLog(@Valid CustomerChangeLogUpdateReqVO updateReqVO);

    /**
     * 删除客户变更日志
     *
     * @param id 编号
     */
    void deleteCustomerChangeLog(Long id);

    /**
     * 获得客户变更日志
     *
     * @param id 编号
     * @return 客户变更日志
     */
    Optional<CustomerChangeLog> getCustomerChangeLog(Long id);

    /**
     * 获得客户变更日志列表
     *
     * @param ids 编号
     * @return 客户变更日志列表
     */
    List<CustomerChangeLog> getCustomerChangeLogList(Collection<Long> ids);

    /**
     * 获得客户变更日志分页
     *
     * @param pageReqVO 分页查询
     * @return 客户变更日志分页
     */
    PageResult<CustomerChangeLog> getCustomerChangeLogPage(CustomerChangeLogPageReqVO pageReqVO, CustomerChangeLogPageOrder orderV0);

    /**
     * 获得客户变更日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 客户变更日志列表
     */
    List<CustomerChangeLog> getCustomerChangeLogList(CustomerChangeLogExportReqVO exportReqVO);

}
