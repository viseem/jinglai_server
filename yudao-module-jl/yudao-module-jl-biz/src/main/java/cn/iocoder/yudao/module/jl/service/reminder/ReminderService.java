package cn.iocoder.yudao.module.jl.service.reminder;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.reminder.vo.*;
import cn.iocoder.yudao.module.jl.entity.reminder.Reminder;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 晶莱提醒事项 Service 接口
 *
 */
public interface ReminderService {

    /**
     * 创建晶莱提醒事项
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReminder(@Valid ReminderCreateReqVO createReqVO);

    /**
     * 更新晶莱提醒事项
     *
     * @param updateReqVO 更新信息
     */
    void updateReminder(@Valid ReminderUpdateReqVO updateReqVO);

    /**
     * 删除晶莱提醒事项
     *
     * @param id 编号
     */
    void deleteReminder(Long id);

    /**
     * 获得晶莱提醒事项
     *
     * @param id 编号
     * @return 晶莱提醒事项
     */
    Optional<Reminder> getReminder(Long id);

    /**
     * 获得晶莱提醒事项列表
     *
     * @param ids 编号
     * @return 晶莱提醒事项列表
     */
    List<Reminder> getReminderList(Collection<Long> ids);

    /**
     * 获得晶莱提醒事项分页
     *
     * @param pageReqVO 分页查询
     * @return 晶莱提醒事项分页
     */
    PageResult<Reminder> getReminderPage(ReminderPageReqVO pageReqVO, ReminderPageOrder orderV0);

    /**
     * 获得晶莱提醒事项列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 晶莱提醒事项列表
     */
    List<Reminder> getReminderList(ReminderExportReqVO exportReqVO);

}
