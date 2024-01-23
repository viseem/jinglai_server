package cn.iocoder.yudao.module.jl.service.crmsubjectgroup;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.crmsubjectgroup.vo.*;
import cn.iocoder.yudao.module.jl.entity.crmsubjectgroup.CrmSubjectGroup;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 客户课题组 Service 接口
 *
 */
public interface CrmSubjectGroupService {

    /**
     * 创建客户课题组
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCrmSubjectGroup(@Valid CrmSubjectGroupCreateReqVO createReqVO);

    /**
     * 更新客户课题组
     *
     * @param updateReqVO 更新信息
     */
    void updateCrmSubjectGroup(@Valid CrmSubjectGroupUpdateReqVO updateReqVO);

    /**
     * 删除客户课题组
     *
     * @param id 编号
     */
    void deleteCrmSubjectGroup(Long id);

    /**
     * 获得客户课题组
     *
     * @param id 编号
     * @return 客户课题组
     */
    Optional<CrmSubjectGroup> getCrmSubjectGroup(Long id);

    /**
     * 获得客户课题组列表
     *
     * @param ids 编号
     * @return 客户课题组列表
     */
    List<CrmSubjectGroup> getCrmSubjectGroupList(Collection<Long> ids);

    /**
     * 获得客户课题组分页
     *
     * @param pageReqVO 分页查询
     * @return 客户课题组分页
     */
    PageResult<CrmSubjectGroup> getCrmSubjectGroupPage(CrmSubjectGroupPageReqVO pageReqVO, CrmSubjectGroupPageOrder orderV0);

    /**
     * 获得客户课题组列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 客户课题组列表
     */
    List<CrmSubjectGroup> getCrmSubjectGroupList(CrmSubjectGroupExportReqVO exportReqVO);

}
