package cn.iocoder.yudao.module.jl.service.laboratory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo.*;
import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryLab;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 实验室 Service 接口
 *
 */
public interface LaboratoryLabService {

    /**
     * 创建实验室
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLaboratoryLab(@Valid LaboratoryLabCreateReqVO createReqVO);

    /**
     * 更新实验室
     *
     * @param updateReqVO 更新信息
     */
    void updateLaboratoryLab(@Valid LaboratoryLabUpdateReqVO updateReqVO);

    /**
     * 删除实验室
     *
     * @param id 编号
     */
    void deleteLaboratoryLab(Long id);

    /**
     * 获得实验室
     *
     * @param id 编号
     * @return 实验室
     */
    Optional<LaboratoryLab> getLaboratoryLab(Long id);

    /**
     * 获得实验室列表
     *
     * @param ids 编号
     * @return 实验室列表
     */
    List<LaboratoryLab> getLaboratoryLabList(Collection<Long> ids);

    /**
     * 获得实验室分页
     *
     * @param pageReqVO 分页查询
     * @return 实验室分页
     */
    PageResult<LaboratoryLab> getLaboratoryLabPage(LaboratoryLabPageReqVO pageReqVO, LaboratoryLabPageOrder orderV0);

    /**
     * 获得实验室列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 实验室列表
     */
    List<LaboratoryLab> getLaboratoryLabList(LaboratoryLabExportReqVO exportReqVO);

}
