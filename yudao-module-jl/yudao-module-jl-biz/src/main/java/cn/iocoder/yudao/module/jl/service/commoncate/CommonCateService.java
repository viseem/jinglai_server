package cn.iocoder.yudao.module.jl.service.commoncate;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.commoncate.vo.*;
import cn.iocoder.yudao.module.jl.entity.commoncate.CommonCate;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 通用分类 Service 接口
 *
 */
public interface CommonCateService {

    /**
     * 创建通用分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCommonCate(@Valid CommonCateCreateReqVO createReqVO);

    /**
     * 更新通用分类
     *
     * @param updateReqVO 更新信息
     */
    void updateCommonCate(@Valid CommonCateUpdateReqVO updateReqVO);

    /**
     * 删除通用分类
     *
     * @param id 编号
     */
    void deleteCommonCate(Long id);

    /**
     * 获得通用分类
     *
     * @param id 编号
     * @return 通用分类
     */
    Optional<CommonCate> getCommonCate(Long id);

    /**
     * 获得通用分类列表
     *
     * @param ids 编号
     * @return 通用分类列表
     */
    List<CommonCate> getCommonCateList(Collection<Long> ids);

    /**
     * 获得通用分类分页
     *
     * @param pageReqVO 分页查询
     * @return 通用分类分页
     */
    PageResult<CommonCate> getCommonCatePage(CommonCatePageReqVO pageReqVO, CommonCatePageOrder orderV0);

    /**
     * 获得通用分类列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 通用分类列表
     */
    List<CommonCate> getCommonCateList(CommonCateExportReqVO exportReqVO);

}
