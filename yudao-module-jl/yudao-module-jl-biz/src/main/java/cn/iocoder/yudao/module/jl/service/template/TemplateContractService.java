package cn.iocoder.yudao.module.jl.service.template;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.template.vo.*;
import cn.iocoder.yudao.module.jl.entity.template.TemplateContract;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 合同模板 Service 接口
 *
 */
public interface TemplateContractService {

    /**
     * 创建合同模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTemplateContract(@Valid TemplateContractCreateReqVO createReqVO);

    /**
     * 更新合同模板
     *
     * @param updateReqVO 更新信息
     */
    void updateTemplateContract(@Valid TemplateContractUpdateReqVO updateReqVO);

    /**
     * 删除合同模板
     *
     * @param id 编号
     */
    void deleteTemplateContract(Long id);

    /**
     * 获得合同模板
     *
     * @param id 编号
     * @return 合同模板
     */
    Optional<TemplateContract> getTemplateContract(Long id);

    /**
     * 获得合同模板列表
     *
     * @param ids 编号
     * @return 合同模板列表
     */
    List<TemplateContract> getTemplateContractList(Collection<Long> ids);

    /**
     * 获得合同模板分页
     *
     * @param pageReqVO 分页查询
     * @return 合同模板分页
     */
    PageResult<TemplateContract> getTemplateContractPage(TemplateContractPageReqVO pageReqVO, TemplateContractPageOrder orderV0);

    /**
     * 获得合同模板列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 合同模板列表
     */
    List<TemplateContract> getTemplateContractList(TemplateContractExportReqVO exportReqVO);

}
