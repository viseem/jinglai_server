package cn.iocoder.yudao.module.jl.controller.admin.jlbpm.service;

import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskReturnReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.jlbpm.vo.JLBpmTaskReqVO;

import javax.validation.Valid;

/**
 * 项目的实验名目 Service 接口
 *
 */
public interface JLBpmService {

   void approveTask(@Valid JLBpmTaskReqVO approveReqVO);

   void returnTask(@Valid BpmTaskReturnReqVO approveReqVO);

}
