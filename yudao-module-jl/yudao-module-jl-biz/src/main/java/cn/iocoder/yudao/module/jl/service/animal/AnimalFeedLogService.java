package cn.iocoder.yudao.module.jl.service.animal;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 动物饲养日志 Service 接口
 *
 */
public interface AnimalFeedLogService {

    /**
     * 创建动物饲养日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAnimalFeedLog(@Valid AnimalFeedLogCreateReqVO createReqVO);

    /**
     * 更新动物饲养日志
     *
     * @param updateReqVO 更新信息
     */
    void updateAnimalFeedLog(@Valid AnimalFeedLogUpdateReqVO updateReqVO);

    /**
     * save更新动物饲养日志
     *
     * @param saveReqVO 更新信息
     */
    void saveAnimalFeedLog(@Valid AnimalFeedLogSaveReqVO saveReqVO);

    /**
     * 删除动物饲养日志
     *
     * @param id 编号
     */
    void deleteAnimalFeedLog(Long id);

    /**
     * 获得动物饲养日志
     *
     * @param id 编号
     * @return 动物饲养日志
     */
    Optional<AnimalFeedLog> getAnimalFeedLog(Long id);

    /**
     * 获得动物饲养日志列表
     *
     * @param ids 编号
     * @return 动物饲养日志列表
     */
    List<AnimalFeedLog> getAnimalFeedLogList(Collection<Long> ids);

    /**
     * 获得动物饲养日志分页
     *
     * @param pageReqVO 分页查询
     * @return 动物饲养日志分页
     */
    PageResult<AnimalFeedLog> getAnimalFeedLogPage(AnimalFeedLogPageReqVO pageReqVO, AnimalFeedLogPageOrder orderV0);

    /**
     * 获得动物饲养日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 动物饲养日志列表
     */
    List<AnimalFeedLog> getAnimalFeedLogList(AnimalFeedLogExportReqVO exportReqVO);

}
