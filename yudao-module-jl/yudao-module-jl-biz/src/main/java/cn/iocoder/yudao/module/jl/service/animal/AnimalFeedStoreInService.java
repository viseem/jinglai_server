package cn.iocoder.yudao.module.jl.service.animal;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedStoreIn;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 动物饲养入库 Service 接口
 *
 */
public interface AnimalFeedStoreInService {

    /**
     * 创建动物饲养入库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAnimalFeedStoreIn(@Valid AnimalFeedStoreInCreateReqVO createReqVO);

    /**
     * 更新动物饲养入库
     *
     * @param updateReqVO 更新信息
     */
    void updateAnimalFeedStoreIn(@Valid AnimalFeedStoreInUpdateReqVO updateReqVO);

    /**
     * 删除动物饲养入库
     *
     * @param id 编号
     */
    void deleteAnimalFeedStoreIn(Long id);

    /**
     * 获得动物饲养入库
     *
     * @param id 编号
     * @return 动物饲养入库
     */
    Optional<AnimalFeedStoreIn> getAnimalFeedStoreIn(Long id);

    /**
     * 获得动物饲养入库列表
     *
     * @param ids 编号
     * @return 动物饲养入库列表
     */
    List<AnimalFeedStoreIn> getAnimalFeedStoreInList(Collection<Long> ids);

    /**
     * 获得动物饲养入库分页
     *
     * @param pageReqVO 分页查询
     * @return 动物饲养入库分页
     */
    PageResult<AnimalFeedStoreIn> getAnimalFeedStoreInPage(AnimalFeedStoreInPageReqVO pageReqVO, AnimalFeedStoreInPageOrder orderV0);

    /**
     * 获得动物饲养入库列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 动物饲养入库列表
     */
    List<AnimalFeedStoreIn> getAnimalFeedStoreInList(AnimalFeedStoreInExportReqVO exportReqVO);

}
