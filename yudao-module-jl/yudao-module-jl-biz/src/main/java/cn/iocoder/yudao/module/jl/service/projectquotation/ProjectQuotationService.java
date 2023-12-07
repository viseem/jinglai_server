package cn.iocoder.yudao.module.jl.service.projectquotation;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.ProjectQuotationUpdatePlanReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目报价 Service 接口
 *
 */
public interface ProjectQuotationService {

    /**
     * 创建项目报价
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectQuotation(@Valid ProjectQuotationCreateReqVO createReqVO);

    /**
     * 更新项目报价
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectQuotation(@Valid ProjectQuotationUpdateReqVO updateReqVO);

    void updateProjectQuotationDiscount(@Valid ProjectQuotationNoRequireVO updateReqVO);


    /**
     * 更新项目报价
     *
     * @param updateReqVO 更新信息
     */
    void saveProjectQuotation(@Valid ProjectQuotationSaveReqVO updateReqVO);

    /**
     * 更新项目报价
     *
     * @param updateReqVO 更新信息
     */
    Long updateProjectQuotationPlan(@Valid ProjectQuotationUpdatePlanReqVO updateReqVO);


    /**
     * 删除项目报价
     *
     * @param id 编号
     */
    void deleteProjectQuotation(Long id);

    /**
     * 获得项目报价
     *
     * @param id 编号
     * @return 项目报价
     */
    Optional<ProjectQuotation> getProjectQuotation(Long id);

    /**
     * 获得项目报价列表
     *
     * @param ids 编号
     * @return 项目报价列表
     */
    List<ProjectQuotation> getProjectQuotationList(Collection<Long> ids);

    /**
     * 获得项目报价分页
     *
     * @param pageReqVO 分页查询
     * @return 项目报价分页
     */
    PageResult<ProjectQuotation> getProjectQuotationPage(ProjectQuotationPageReqVO pageReqVO, ProjectQuotationPageOrder orderV0);

    /**
     * 获得项目报价列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目报价列表
     */
    ProjectQuotationExportRespVO getProjectQuotationList(ProjectQuotationExportReqVO exportReqVO);

}
