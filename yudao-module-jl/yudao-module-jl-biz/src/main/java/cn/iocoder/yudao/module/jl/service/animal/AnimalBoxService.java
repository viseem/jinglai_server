package cn.iocoder.yudao.module.jl.service.animal;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalBox;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 动物笼位 Service 接口
 *
 */
public interface AnimalBoxService {

    /**
     * 创建动物笼位
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAnimalBox(@Valid AnimalBoxCreateReqVO createReqVO);

    /**
     * 更新动物笼位
     *
     * @param updateReqVO 更新信息
     */
    void updateAnimalBox(@Valid AnimalBoxUpdateReqVO updateReqVO);

    /**
     * save更新动物笼位和log
     *
     * @param saveReqVO 更新信息
     */
    void saveAnimalBox(@Valid AnimalBoxSaveReqVO saveReqVO);

    /**
     * 删除动物笼位
     *
     * @param id 编号
     */
    void deleteAnimalBox(Long id);

    void clearAnimalBox(Long id);

    /**
     * 获得动物笼位
     *
     * @param id 编号
     * @return 动物笼位
     */
    Optional<AnimalBox> getAnimalBox(Long id);

    /**
     * 获得动物笼位列表
     *
     * @param ids 编号
     * @return 动物笼位列表
     */
    List<AnimalBox> getAnimalBoxList(Collection<Long> ids);

    /**
     * 获得动物笼位分页
     *
     * @param pageReqVO 分页查询
     * @return 动物笼位分页
     */
    PageResult<AnimalBox> getAnimalBoxPage(AnimalBoxPageReqVO pageReqVO, AnimalBoxPageOrder orderV0);

    /**
     * 获得动物笼位列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 动物笼位列表
     */
    List<AnimalBox> getAnimalBoxList(AnimalBoxExportReqVO exportReqVO);

}
