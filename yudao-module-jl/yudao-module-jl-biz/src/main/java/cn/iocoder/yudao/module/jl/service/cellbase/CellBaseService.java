package cn.iocoder.yudao.module.jl.service.cellbase;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.cellbase.vo.*;
import cn.iocoder.yudao.module.jl.entity.cellbase.CellBase;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 细胞数据 Service 接口
 *
 */
public interface CellBaseService {

    /**
     * 创建细胞数据
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCellBase(@Valid CellBaseCreateReqVO createReqVO);

    /**
     * 更新细胞数据
     *
     * @param updateReqVO 更新信息
     */
    void updateCellBase(@Valid CellBaseUpdateReqVO updateReqVO);

    /**
     * 删除细胞数据
     *
     * @param id 编号
     */
    void deleteCellBase(Long id);

    /**
     * 获得细胞数据
     *
     * @param id 编号
     * @return 细胞数据
     */
    Optional<CellBase> getCellBase(Long id);

    /**
     * 获得细胞数据列表
     *
     * @param ids 编号
     * @return 细胞数据列表
     */
    List<CellBase> getCellBaseList(Collection<Long> ids);

    /**
     * 获得细胞数据分页
     *
     * @param pageReqVO 分页查询
     * @return 细胞数据分页
     */
    PageResult<CellBase> getCellBasePage(CellBasePageReqVO pageReqVO, CellBasePageOrder orderV0);

    /**
     * 获得细胞数据列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 细胞数据列表
     */
    List<CellBase> getCellBaseList(CellBaseExportReqVO exportReqVO);

}
