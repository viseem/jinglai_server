package cn.iocoder.yudao.module.jl.service.projectperson;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectperson.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectperson.ProjectPerson;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目人员 Service 接口
 *
 */
public interface ProjectPersonService {

    /**
     * 创建项目人员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectPerson(@Valid ProjectPersonCreateReqVO createReqVO);

    /**
     * 更新项目人员
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectPerson(@Valid ProjectPersonUpdateReqVO updateReqVO);

    /**
     * 删除项目人员
     *
     * @param id 编号
     */
    void deleteProjectPerson(Long id);

    /**
     * 获得项目人员
     *
     * @param id 编号
     * @return 项目人员
     */
    Optional<ProjectPerson> getProjectPerson(Long id);

    /**
     * 获得项目人员列表
     *
     * @param ids 编号
     * @return 项目人员列表
     */
    List<ProjectPerson> getProjectPersonList(Collection<Long> ids);

    /**
     * 获得项目人员分页
     *
     * @param pageReqVO 分页查询
     * @return 项目人员分页
     */
    PageResult<ProjectPerson> getProjectPersonPage(ProjectPersonPageReqVO pageReqVO, ProjectPersonPageOrder orderV0);

    /**
     * 获得项目人员列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目人员列表
     */
    List<ProjectPerson> getProjectPersonList(ProjectPersonExportReqVO exportReqVO);

}
