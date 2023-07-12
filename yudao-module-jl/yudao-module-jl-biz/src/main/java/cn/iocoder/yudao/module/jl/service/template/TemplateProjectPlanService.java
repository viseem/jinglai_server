package cn.iocoder.yudao.module.jl.service.template;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.template.vo.*;
import cn.iocoder.yudao.module.jl.entity.template.TemplateProjectPlan;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目方案模板 Service 接口
 *
 */
public interface TemplateProjectPlanService {

    /**
     * 创建项目方案模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTemplateProjectPlan(@Valid TemplateProjectPlanCreateReqVO createReqVO);

    /**
     * 更新项目方案模板
     *
     * @param updateReqVO 更新信息
     */
    void updateTemplateProjectPlan(@Valid TemplateProjectPlanUpdateReqVO updateReqVO);

    /**
     * 删除项目方案模板
     *
     * @param id 编号
     */
    void deleteTemplateProjectPlan(Long id);

    /**
     * 获得项目方案模板
     *
     * @param id 编号
     * @return 项目方案模板
     */
    Optional<TemplateProjectPlan> getTemplateProjectPlan(Long id);

    /**
     * 获得项目方案模板列表
     *
     * @param ids 编号
     * @return 项目方案模板列表
     */
    List<TemplateProjectPlan> getTemplateProjectPlanList(Collection<Long> ids);

    /**
     * 获得项目方案模板分页
     *
     * @param pageReqVO 分页查询
     * @return 项目方案模板分页
     */
    PageResult<TemplateProjectPlan> getTemplateProjectPlanPage(TemplateProjectPlanPageReqVO pageReqVO, TemplateProjectPlanPageOrder orderV0);

    /**
     * 获得项目方案模板列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目方案模板列表
     */
    List<TemplateProjectPlan> getTemplateProjectPlanList(TemplateProjectPlanExportReqVO exportReqVO);

}
