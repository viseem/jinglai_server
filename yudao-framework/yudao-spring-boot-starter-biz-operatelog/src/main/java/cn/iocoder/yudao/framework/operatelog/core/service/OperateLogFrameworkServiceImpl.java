package cn.iocoder.yudao.framework.operatelog.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.yudao.module.system.api.logger.OperateLogApi;
import cn.iocoder.yudao.module.system.api.logger.dto.OperateLogCreateReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * 操作日志 Framework Service 实现类
 *
 * 基于 {@link OperateLogApi} 实现，记录操作日志
 *
 * @author 芋道源码
 */
@RequiredArgsConstructor
@Slf4j
public class OperateLogFrameworkServiceImpl implements OperateLogFrameworkService {

    private final OperateLogApi operateLogApi;

    @Override
    @Async
    public void createOperateLog(OperateLog operateLog) {
        try {
            // 参数校验，如果userId为空，则跳过日志记录
            if (operateLog == null) {
                log.warn("[createOperateLog] 操作日志为空，忽略记录");
                return;
            }
            if (operateLog.getUserId() == null) {
                log.warn("[createOperateLog] 用户编号为空，忽略记录");
                return;
            }
            
            // 转换并记录日志
            OperateLogCreateReqDTO reqDTO = BeanUtil.copyProperties(operateLog, OperateLogCreateReqDTO.class);
            operateLogApi.createOperateLog(reqDTO);
        } catch (Exception ex) {
            // 捕获所有异常，避免异步方法执行失败导致线程中断
            log.error("[createOperateLog] 记录操作日志异常", ex);
        }
    }

}
