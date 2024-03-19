package cn.iocoder.yudao.module.jl.controller.admin.weixin.service.WXCP;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.commontodo.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontodo.CommonTodo;
import cn.iocoder.yudao.module.jl.entity.commontodolog.CommonTodoLog;
import cn.iocoder.yudao.module.jl.enums.CommonTodoEnums;
import cn.iocoder.yudao.module.jl.mapper.commontodo.CommonTodoMapper;
import cn.iocoder.yudao.module.jl.repository.commontodo.CommonTodoRepository;
import cn.iocoder.yudao.module.jl.repository.commontodolog.CommonTodoLogRepository;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpMessageServiceImpl;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.COMMON_TODO_NOT_EXISTS;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.WX_CP_SEND_MSG_ERROR;

/**
 * 通用TODO Service 实现类
 *
 */
@Service
@Validated
public class WXCPServiceImpl implements WXCPService {

    @Resource
    private WxCpService wxCpService;

    public void sendTextMessage(String userCPId,String title, String content,Long msgId) {
        WxCpMessageServiceImpl wxCpMessageService = new WxCpMessageServiceImpl(wxCpService);
        WxCpMessage wxCpMessage = new WxCpMessage();
        wxCpMessage.setSafe("0");
        wxCpMessage.setMsgType("textcard");  // 设置消息形式
        wxCpMessage.setToUser(userCPId); // u.getWxCpId()
        wxCpMessage.setTitle(title);
        wxCpMessage.setDescription(content);
        wxCpMessage.setUrl("https://szhpt.genelb.cn/index?msgId="+msgId);
        wxCpMessage.setBtnTxt("点击查看");
        try {
            wxCpMessageService.send(wxCpMessage);
        } catch (WxErrorException e) {
            throw exception(WX_CP_SEND_MSG_ERROR);
        }
    }


}