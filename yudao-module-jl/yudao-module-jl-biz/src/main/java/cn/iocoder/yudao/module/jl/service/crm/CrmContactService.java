package cn.iocoder.yudao.module.jl.service.crm;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.CrmContact;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 客户联系人 Service 接口
 *
 */
public interface CrmContactService {

    /**
     * 创建客户联系人
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCrmContact(@Valid CrmContactCreateReqVO createReqVO);

    /**
     * 更新客户联系人
     *
     * @param updateReqVO 更新信息
     */
    void updateCrmContact(@Valid CrmContactUpdateReqVO updateReqVO);

    /**
     * 删除客户联系人
     *
     * @param id 编号
     */
    void deleteCrmContact(Long id);

    /**
     * 获得客户联系人
     *
     * @param id 编号
     * @return 客户联系人
     */
    Optional<CrmContact> getCrmContact(Long id);

    /**
     * 获得客户联系人列表
     *
     * @param ids 编号
     * @return 客户联系人列表
     */
    List<CrmContact> getCrmContactList(Collection<Long> ids);

    /**
     * 获得客户联系人分页
     *
     * @param pageReqVO 分页查询
     * @return 客户联系人分页
     */
    PageResult<CrmContact> getCrmContactPage(CrmContactPageReqVO pageReqVO, CrmContactPageOrder orderV0);

    /**
     * 获得客户联系人列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 客户联系人列表
     */
    List<CrmContact> getCrmContactList(CrmContactExportReqVO exportReqVO);

}
