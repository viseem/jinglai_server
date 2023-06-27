package cn.iocoder.yudao.module.jl.service.laboratory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo.*;
import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryUser;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 实验室人员 Service 接口
 *
 */
public interface LaboratoryUserService {

    /**
     * 创建实验室人员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLaboratoryUser(@Valid LaboratoryUserCreateReqVO createReqVO);

    /**
     * 更新实验室人员
     *
     * @param updateReqVO 更新信息
     */
    void updateLaboratoryUser(@Valid LaboratoryUserUpdateReqVO updateReqVO);

    /**
     * 删除实验室人员
     *
     * @param id 编号
     */
    void deleteLaboratoryUser(Long id);

    /**
     * 获得实验室人员
     *
     * @param id 编号
     * @return 实验室人员
     */
    Optional<LaboratoryUser> getLaboratoryUser(Long id);

    /**
     * 获得实验室人员列表
     *
     * @param ids 编号
     * @return 实验室人员列表
     */
    List<LaboratoryUser> getLaboratoryUserList(Collection<Long> ids);

    /**
     * 获得实验室人员分页
     *
     * @param pageReqVO 分页查询
     * @return 实验室人员分页
     */
    PageResult<LaboratoryUser> getLaboratoryUserPage(LaboratoryUserPageReqVO pageReqVO, LaboratoryUserPageOrder orderV0);

    /**
     * 获得实验室人员列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 实验室人员列表
     */
    List<LaboratoryUser> getLaboratoryUserList(LaboratoryUserExportReqVO exportReqVO);

}
