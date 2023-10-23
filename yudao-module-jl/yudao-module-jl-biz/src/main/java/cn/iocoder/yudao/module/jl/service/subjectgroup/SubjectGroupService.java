package cn.iocoder.yudao.module.jl.service.subjectgroup;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.*;
import cn.iocoder.yudao.module.jl.entity.subjectgroup.SubjectGroup;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 专题小组 Service 接口
 *
 */
public interface SubjectGroupService {

    /**
     * 创建专题小组
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSubjectGroup(@Valid SubjectGroupCreateReqVO createReqVO);

    /**
     * 更新专题小组
     *
     * @param updateReqVO 更新信息
     */
    void updateSubjectGroup(@Valid SubjectGroupUpdateReqVO updateReqVO);

    /**
     * 删除专题小组
     *
     * @param id 编号
     */
    void deleteSubjectGroup(Long id);

    /**
     * 获得专题小组
     *
     * @param id 编号
     * @return 专题小组
     */
    Optional<SubjectGroup> getSubjectGroup(Long id);

    /**
     * 获得专题小组列表
     *
     * @param ids 编号
     * @return 专题小组列表
     */
    List<SubjectGroup> getSubjectGroupList(Collection<Long> ids);

    /**
     * 获得专题小组分页
     *
     * @param pageReqVO 分页查询
     * @return 专题小组分页
     */
    PageResult<SubjectGroup> getSubjectGroupPage(SubjectGroupPageReqVO pageReqVO, SubjectGroupPageOrder orderV0);

    /**
     * 获得专题小组列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 专题小组列表
     */
    List<SubjectGroup> getSubjectGroupList(SubjectGroupExportReqVO exportReqVO);

}
