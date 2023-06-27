package cn.iocoder.yudao.module.jl.service.projectcategory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryAttachment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目实验名目的附件 Service 接口
 *
 */
public interface ProjectCategoryAttachmentService {

    /**
     * 创建项目实验名目的附件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectCategoryAttachment(@Valid ProjectCategoryAttachmentCreateReqVO createReqVO);

    /**
     * 更新项目实验名目的附件
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectCategoryAttachment(@Valid ProjectCategoryAttachmentUpdateReqVO updateReqVO);

    /**
     * 删除项目实验名目的附件
     *
     * @param id 编号
     */
    void deleteProjectCategoryAttachment(Long id);

    /**
     * 获得项目实验名目的附件
     *
     * @param id 编号
     * @return 项目实验名目的附件
     */
    Optional<ProjectCategoryAttachment> getProjectCategoryAttachment(Long id);

    /**
     * 获得项目实验名目的附件列表
     *
     * @param ids 编号
     * @return 项目实验名目的附件列表
     */
    List<ProjectCategoryAttachment> getProjectCategoryAttachmentList(Collection<Long> ids);

    /**
     * 获得项目实验名目的附件分页
     *
     * @param pageReqVO 分页查询
     * @return 项目实验名目的附件分页
     */
    PageResult<ProjectCategoryAttachment> getProjectCategoryAttachmentPage(ProjectCategoryAttachmentPageReqVO pageReqVO, ProjectCategoryAttachmentPageOrder orderV0);

    /**
     * 获得项目实验名目的附件列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目实验名目的附件列表
     */
    List<ProjectCategoryAttachment> getProjectCategoryAttachmentList(ProjectCategoryAttachmentExportReqVO exportReqVO);

}
