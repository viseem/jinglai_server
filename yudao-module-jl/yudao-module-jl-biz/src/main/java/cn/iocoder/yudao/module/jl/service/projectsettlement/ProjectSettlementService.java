package cn.iocoder.yudao.module.jl.service.projectsettlement;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectsettlement.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectsettlement.ProjectSettlement;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目结算 Service 接口
 *
 */
public interface ProjectSettlementService {

    /**
     * 创建项目结算
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectSettlement(@Valid ProjectSettlementCreateReqVO createReqVO);

    /**
     * 更新项目结算
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectSettlement(@Valid ProjectSettlementUpdateReqVO updateReqVO);

    /**
     * 删除项目结算
     *
     * @param id 编号
     */
    void deleteProjectSettlement(Long id);

    /**
     * 获得项目结算
     *
     * @param id 编号
     * @return 项目结算
     */
    Optional<ProjectSettlement> getProjectSettlement(Long id);

    /**
     * 获得项目结算列表
     *
     * @param ids 编号
     * @return 项目结算列表
     */
    List<ProjectSettlement> getProjectSettlementList(Collection<Long> ids);

    /**
     * 获得项目结算分页
     *
     * @param pageReqVO 分页查询
     * @return 项目结算分页
     */
    PageResult<ProjectSettlement> getProjectSettlementPage(ProjectSettlementPageReqVO pageReqVO, ProjectSettlementPageOrder orderV0);

    /**
     * 获得项目结算列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目结算列表
     */
    ProjectSettlementExportRespVO getProjectSettlementList(ProjectSettlementExportReqVO exportReqVO);

}
