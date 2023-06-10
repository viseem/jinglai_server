package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.CompanySupply;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 公司实验物资库存 Service 接口
 *
 */
public interface CompanySupplyService {

    /**
     * 创建公司实验物资库存
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCompanySupply(@Valid CompanySupplyCreateReqVO createReqVO);

    /**
     * 更新公司实验物资库存
     *
     * @param updateReqVO 更新信息
     */
    void updateCompanySupply(@Valid CompanySupplyUpdateReqVO updateReqVO);

    /**
     * 删除公司实验物资库存
     *
     * @param id 编号
     */
    void deleteCompanySupply(Long id);

    /**
     * 获得公司实验物资库存
     *
     * @param id 编号
     * @return 公司实验物资库存
     */
    Optional<CompanySupply> getCompanySupply(Long id);

    /**
     * 获得公司实验物资库存列表
     *
     * @param ids 编号
     * @return 公司实验物资库存列表
     */
    List<CompanySupply> getCompanySupplyList(Collection<Long> ids);

    /**
     * 获得公司实验物资库存分页
     *
     * @param pageReqVO 分页查询
     * @return 公司实验物资库存分页
     */
    PageResult<CompanySupply> getCompanySupplyPage(CompanySupplyPageReqVO pageReqVO, CompanySupplyPageOrder orderV0);

    /**
     * 获得公司实验物资库存列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 公司实验物资库存列表
     */
    List<CompanySupply> getCompanySupplyList(CompanySupplyExportReqVO exportReqVO);

}
