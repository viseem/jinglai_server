package cn.iocoder.yudao.module.jl.service.picollaboration;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.picollaboration.vo.*;
import cn.iocoder.yudao.module.jl.entity.picollaboration.PiCollaboration;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * PI组协作 Service 接口
 *
 */
public interface PiCollaborationService {

    /**
     * 创建PI组协作
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPiCollaboration(@Valid PiCollaborationCreateReqVO createReqVO);

    /**
     * 更新PI组协作
     *
     * @param updateReqVO 更新信息
     */
    void updatePiCollaboration(@Valid PiCollaborationUpdateReqVO updateReqVO);

    /**
     * 删除PI组协作
     *
     * @param id 编号
     */
    void deletePiCollaboration(Long id);

    /**
     * 获得PI组协作
     *
     * @param id 编号
     * @return PI组协作
     */
    Optional<PiCollaboration> getPiCollaboration(Long id);

    /**
     * 获得PI组协作列表
     *
     * @param ids 编号
     * @return PI组协作列表
     */
    List<PiCollaboration> getPiCollaborationList(Collection<Long> ids);

    /**
     * 获得PI组协作分页
     *
     * @param pageReqVO 分页查询
     * @return PI组协作分页
     */
    PageResult<PiCollaboration> getPiCollaborationPage(PiCollaborationPageReqVO pageReqVO, PiCollaborationPageOrder orderV0);

    /**
     * 获得PI组协作列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return PI组协作列表
     */
    List<PiCollaboration> getPiCollaborationList(PiCollaborationExportReqVO exportReqVO);

}
