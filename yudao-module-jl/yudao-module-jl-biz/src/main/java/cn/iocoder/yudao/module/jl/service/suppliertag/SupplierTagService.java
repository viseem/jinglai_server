package cn.iocoder.yudao.module.jl.service.suppliertag;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.suppliertag.vo.*;
import cn.iocoder.yudao.module.jl.entity.suppliertag.SupplierTag;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 供应商标签 Service 接口
 *
 */
public interface SupplierTagService {

    /**
     * 创建供应商标签
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplierTag(@Valid SupplierTagCreateReqVO createReqVO);

    /**
     * 更新供应商标签
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplierTag(@Valid SupplierTagUpdateReqVO updateReqVO);

    /**
     * 删除供应商标签
     *
     * @param id 编号
     */
    void deleteSupplierTag(Long id);

    /**
     * 获得供应商标签
     *
     * @param id 编号
     * @return 供应商标签
     */
    Optional<SupplierTag> getSupplierTag(Long id);

    /**
     * 获得供应商标签列表
     *
     * @param ids 编号
     * @return 供应商标签列表
     */
    List<SupplierTag> getSupplierTagList(Collection<Long> ids);

    /**
     * 获得供应商标签分页
     *
     * @param pageReqVO 分页查询
     * @return 供应商标签分页
     */
    PageResult<SupplierTag> getSupplierTagPage(SupplierTagPageReqVO pageReqVO, SupplierTagPageOrder orderV0);

    /**
     * 获得供应商标签列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 供应商标签列表
     */
    List<SupplierTag> getSupplierTagList(SupplierTagExportReqVO exportReqVO);

}
