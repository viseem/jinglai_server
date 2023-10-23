package cn.iocoder.yudao.module.jl.service.subjectgroupmember;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember.vo.*;
import cn.iocoder.yudao.module.jl.entity.subjectgroupmember.SubjectGroupMember;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 专题小组成员 Service 接口
 *
 */
public interface SubjectGroupMemberService {

    /**
     * 创建专题小组成员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSubjectGroupMember(@Valid SubjectGroupMemberCreateReqVO createReqVO);

    /**
     * 更新专题小组成员
     *
     * @param updateReqVO 更新信息
     */
    void updateSubjectGroupMember(@Valid SubjectGroupMemberUpdateReqVO updateReqVO);

    /**
     * 删除专题小组成员
     *
     * @param id 编号
     */
    void deleteSubjectGroupMember(Long id);

    /**
     * 获得专题小组成员
     *
     * @param id 编号
     * @return 专题小组成员
     */
    Optional<SubjectGroupMember> getSubjectGroupMember(Long id);

    /**
     * 获得专题小组成员列表
     *
     * @param ids 编号
     * @return 专题小组成员列表
     */
    List<SubjectGroupMember> getSubjectGroupMemberList(Collection<Long> ids);

    /**
     * 获得专题小组成员分页
     *
     * @param pageReqVO 分页查询
     * @return 专题小组成员分页
     */
    PageResult<SubjectGroupMember> getSubjectGroupMemberPage(SubjectGroupMemberPageReqVO pageReqVO, SubjectGroupMemberPageOrder orderV0);

    /**
     * 获得专题小组成员列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 专题小组成员列表
     */
    List<SubjectGroupMember> getSubjectGroupMemberList(SubjectGroupMemberExportReqVO exportReqVO);

}
