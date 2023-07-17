package cn.iocoder.yudao.module.jl.service.animal;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalShelf;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 动物饲养笼架 Service 接口
 *
 */
public interface AnimalShelfService {

    /**
     * 创建动物饲养笼架
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAnimalShelf(@Valid AnimalShelfCreateReqVO createReqVO);

    /**
     * 更新动物饲养笼架
     *
     * @param updateReqVO 更新信息
     */
    void updateAnimalShelf(@Valid AnimalShelfUpdateReqVO updateReqVO);

    /**
     * 删除动物饲养笼架
     *
     * @param id 编号
     */
    void deleteAnimalShelf(Long id);

    /**
     * 获得动物饲养笼架
     *
     * @param id 编号
     * @return 动物饲养笼架
     */
    Optional<AnimalShelf> getAnimalShelf(Long id);

    /**
     * 获得动物饲养笼架列表
     *
     * @param ids 编号
     * @return 动物饲养笼架列表
     */
    List<AnimalShelf> getAnimalShelfList(Collection<Long> ids);

    /**
     * 获得动物饲养笼架分页
     *
     * @param pageReqVO 分页查询
     * @return 动物饲养笼架分页
     */
    PageResult<AnimalShelf> getAnimalShelfPage(AnimalShelfPageReqVO pageReqVO, AnimalShelfPageOrder orderV0);

    /**
     * 获得动物饲养笼架列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 动物饲养笼架列表
     */
    List<AnimalShelf> getAnimalShelfList(AnimalShelfExportReqVO exportReqVO);

}
