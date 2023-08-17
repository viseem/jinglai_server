package cn.iocoder.yudao.module.jl.service.animal;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedOrder;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 动物饲养申请单 Service 接口
 *
 */
public interface AnimalFeedOrderService {

    /**
     * 创建动物饲养申请单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAnimalFeedOrder(@Valid AnimalFeedOrderCreateReqVO createReqVO);

    /**
     * 更新动物饲养申请单
     *
     * @param updateReqVO 更新信息
     */
    void updateAnimalFeedOrder(@Valid AnimalFeedOrderUpdateReqVO updateReqVO);

    /**
     * save更新动物饲养申请单
     *
     * @param saveReqVO 更新信息
     */
    void saveAnimalFeedOrder(@Valid AnimalFeedOrderSaveReqVO saveReqVO);

    /**
     * store更新动物饲养申请单
     *
     * @param storeReqVO 更新信息
     */
    void storeAnimalFeedOrder(@Valid AnimalFeedOrderStoreReqVO storeReqVO);

    /**
     * 删除动物饲养申请单
     *
     * @param id 编号
     */
    void deleteAnimalFeedOrder(Long id);

    /**
     * 获得动物饲养申请单
     *
     * @param id 编号
     * @return 动物饲养申请单
     */
    Optional<AnimalFeedOrder> getAnimalFeedOrder(Long id);

    /**
     * 获得动物饲养申请单列表
     *
     * @param ids 编号
     * @return 动物饲养申请单列表
     */
    List<AnimalFeedOrder> getAnimalFeedOrderList(Collection<Long> ids);

    /**
     * 获得动物饲养申请单分页
     *
     * @param pageReqVO 分页查询
     * @return 动物饲养申请单分页
     */
    PageResult<AnimalFeedOrder> getAnimalFeedOrderPage(AnimalFeedOrderPageReqVO pageReqVO, AnimalFeedOrderPageOrder orderV0);

    /*
    *
    * 获得动物饲养单的统计信息
    * */
    AnimalFeedOrderStatsCountRespVO getAnimalFeedOrderStatsCount();

    /**
     * 获得动物饲养申请单列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 动物饲养申请单列表
     */
    List<AnimalFeedOrder> getAnimalFeedOrderList(AnimalFeedOrderExportReqVO exportReqVO);

}
