package cn.iocoder.yudao.module.jl.service.animal;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalRoom;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 动物饲养室 Service 接口
 *
 */
public interface AnimalRoomService {

    /**
     * 创建动物饲养室
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAnimalRoom(@Valid AnimalRoomCreateReqVO createReqVO);

    /**
     * 更新动物饲养室
     *
     * @param updateReqVO 更新信息
     */
    void updateAnimalRoom(@Valid AnimalRoomUpdateReqVO updateReqVO);

    /**
     * 删除动物饲养室
     *
     * @param id 编号
     */
    void deleteAnimalRoom(Long id);

    /**
     * 获得动物饲养室
     *
     * @param id 编号
     * @return 动物饲养室
     */
    Optional<AnimalRoom> getAnimalRoom(Long id);

    /**
     * 获得动物饲养室列表
     *
     * @param ids 编号
     * @return 动物饲养室列表
     */
    List<AnimalRoom> getAnimalRoomList(Collection<Long> ids);

    /**
     * 获得动物饲养室分页
     *
     * @param pageReqVO 分页查询
     * @return 动物饲养室分页
     */
    PageResult<AnimalRoom> getAnimalRoomPage(AnimalRoomPageReqVO pageReqVO, AnimalRoomPageOrder orderV0);

    /**
     * 获得动物饲养室列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 动物饲养室列表
     */
    List<AnimalRoom> getAnimalRoomList(AnimalRoomExportReqVO exportReqVO);

}
