package cn.iocoder.yudao.module.jl.service.visitappointment;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo.*;
import cn.iocoder.yudao.module.jl.entity.visitappointment.VisitAppointment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 晶莱到访预约 Service 接口
 *
 */
public interface VisitAppointmentService {

    /**
     * 创建晶莱到访预约
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVisitAppointment(@Valid VisitAppointmentCreateReqVO createReqVO);

    /**
     * 更新晶莱到访预约
     *
     * @param updateReqVO 更新信息
     */
    void updateVisitAppointment(@Valid VisitAppointmentUpdateReqVO updateReqVO);

    /**
     * 删除晶莱到访预约
     *
     * @param id 编号
     */
    void deleteVisitAppointment(Long id);

    /**
     * 获得晶莱到访预约
     *
     * @param id 编号
     * @return 晶莱到访预约
     */
    Optional<VisitAppointment> getVisitAppointment(Long id);

    /**
     * 获得晶莱到访预约列表
     *
     * @param ids 编号
     * @return 晶莱到访预约列表
     */
    List<VisitAppointment> getVisitAppointmentList(Collection<Long> ids);

    /**
     * 获得晶莱到访预约分页
     *
     * @param pageReqVO 分页查询
     * @return 晶莱到访预约分页
     */
    PageResult<VisitAppointment> getVisitAppointmentPage(VisitAppointmentPageReqVO pageReqVO, VisitAppointmentPageOrder orderV0);

    /**
     * 获得晶莱到访预约列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 晶莱到访预约列表
     */
    List<VisitAppointment> getVisitAppointmentList(VisitAppointmentExportReqVO exportReqVO);

}
