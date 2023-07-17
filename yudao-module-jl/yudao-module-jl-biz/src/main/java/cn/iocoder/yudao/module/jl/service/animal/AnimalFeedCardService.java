package cn.iocoder.yudao.module.jl.service.animal;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedCard;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 动物饲养鼠牌 Service 接口
 *
 */
public interface AnimalFeedCardService {

    /**
     * 创建动物饲养鼠牌
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAnimalFeedCard(@Valid AnimalFeedCardCreateReqVO createReqVO);

    /**
     * 更新动物饲养鼠牌
     *
     * @param updateReqVO 更新信息
     */
    void updateAnimalFeedCard(@Valid AnimalFeedCardUpdateReqVO updateReqVO);

    /**
     * 删除动物饲养鼠牌
     *
     * @param id 编号
     */
    void deleteAnimalFeedCard(Long id);

    /**
     * 获得动物饲养鼠牌
     *
     * @param id 编号
     * @return 动物饲养鼠牌
     */
    Optional<AnimalFeedCard> getAnimalFeedCard(Long id);

    /**
     * 获得动物饲养鼠牌列表
     *
     * @param ids 编号
     * @return 动物饲养鼠牌列表
     */
    List<AnimalFeedCard> getAnimalFeedCardList(Collection<Long> ids);

    /**
     * 获得动物饲养鼠牌分页
     *
     * @param pageReqVO 分页查询
     * @return 动物饲养鼠牌分页
     */
    PageResult<AnimalFeedCard> getAnimalFeedCardPage(AnimalFeedCardPageReqVO pageReqVO, AnimalFeedCardPageOrder orderV0);

    /**
     * 获得动物饲养鼠牌列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 动物饲养鼠牌列表
     */
    List<AnimalFeedCard> getAnimalFeedCardList(AnimalFeedCardExportReqVO exportReqVO);

}
