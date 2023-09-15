package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectDocument;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目文档 Service 接口
 *
 */
public interface ProjectDocumentService {

    /**
     * 创建项目文档
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectDocument(@Valid ProjectDocumentCreateReqVO createReqVO);

    /**
     * 更新项目文档
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectDocument(@Valid ProjectDocumentUpdateReqVO updateReqVO);

    /**
     * 删除项目文档
     *
     * @param id 编号
     */
    void deleteProjectDocument(Long id);

    /**
     * 获得项目文档
     *
     * @param id 编号
     * @return 项目文档
     */
    Optional<ProjectDocument> getProjectDocument(Long id);

    /**
     * 获得项目文档列表
     *
     * @param ids 编号
     * @return 项目文档列表
     */
    List<ProjectDocument> getProjectDocumentList(Collection<Long> ids);

    /**
     * 获得项目文档分页
     *
     * @param pageReqVO 分页查询
     * @return 项目文档分页
     */
    PageResult<ProjectDocument> getProjectDocumentPage(ProjectDocumentPageReqVO pageReqVO, ProjectDocumentPageOrder orderV0);

    /**
     * 获得项目文档列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目文档列表
     */
    List<ProjectDocument> getProjectDocumentList(ProjectDocumentExportReqVO exportReqVO);

}
