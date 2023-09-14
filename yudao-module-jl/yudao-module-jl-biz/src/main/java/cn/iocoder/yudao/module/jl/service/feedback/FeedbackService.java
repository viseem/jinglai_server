package cn.iocoder.yudao.module.jl.service.feedback;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.feedback.vo.*;
import cn.iocoder.yudao.module.jl.entity.feedback.Feedback;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 晶莱反馈 Service 接口
 *
 */
public interface FeedbackService {

    /**
     * 创建晶莱反馈
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFeedback(@Valid FeedbackCreateReqVO createReqVO);

    /**
     * 更新晶莱反馈
     *
     * @param updateReqVO 更新信息
     */
    void updateFeedback(@Valid FeedbackUpdateReqVO updateReqVO);

    /**
     * 删除晶莱反馈
     *
     * @param id 编号
     */
    void deleteFeedback(Long id);

    /**
     * 获得晶莱反馈
     *
     * @param id 编号
     * @return 晶莱反馈
     */
    Optional<Feedback> getFeedback(Long id);

    /**
     * 获得晶莱反馈列表
     *
     * @param ids 编号
     * @return 晶莱反馈列表
     */
    List<Feedback> getFeedbackList(Collection<Long> ids);

    /**
     * 获得晶莱反馈分页
     *
     * @param pageReqVO 分页查询
     * @return 晶莱反馈分页
     */
    PageResult<Feedback> getFeedbackPage(FeedbackPageReqVO pageReqVO, FeedbackPageOrder orderV0);

    /**
     * 获得晶莱反馈列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 晶莱反馈列表
     */
    List<Feedback> getFeedbackList(FeedbackExportReqVO exportReqVO);

}
