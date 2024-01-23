package cn.iocoder.yudao.module.jl.service.auditconfig;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.auditconfig.vo.*;
import cn.iocoder.yudao.module.jl.entity.auditconfig.AuditConfig;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 审批配置表  Service 接口
 *
 */
public interface AuditConfigService {

    /**
     * 创建审批配置表 
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAuditConfig(@Valid AuditConfigCreateReqVO createReqVO);

    /**
     * 更新审批配置表 
     *
     * @param updateReqVO 更新信息
     */
    void updateAuditConfig(@Valid AuditConfigUpdateReqVO updateReqVO);

    /**
     * 删除审批配置表 
     *
     * @param id 编号
     */
    void deleteAuditConfig(Long id);

    /**
     * 获得审批配置表 
     *
     * @param id 编号
     * @return 审批配置表 
     */
    Optional<AuditConfig> getAuditConfig(Long id);

    /**
     * 获得审批配置表 列表
     *
     * @param ids 编号
     * @return 审批配置表 列表
     */
    List<AuditConfig> getAuditConfigList(Collection<Long> ids);

    /**
     * 获得审批配置表 分页
     *
     * @param pageReqVO 分页查询
     * @return 审批配置表 分页
     */
    PageResult<AuditConfig> getAuditConfigPage(AuditConfigPageReqVO pageReqVO, AuditConfigPageOrder orderV0);

    /**
     * 获得审批配置表 列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 审批配置表 列表
     */
    List<AuditConfig> getAuditConfigList(AuditConfigExportReqVO exportReqVO);

}
