package cn.iocoder.yudao.framework.web.core.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.apilog.core.service.ApiErrorLogFrameworkService;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.collection.SetUtils;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.framework.common.util.monitor.TracerUtils;
import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;
import cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils;
import cn.iocoder.yudao.module.infra.api.logger.dto.ApiErrorLogCreateReqDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.*;

/**
 * 全局异常处理器，将 Exception 翻译成 CommonResult + 对应的异常编号
 *
 * @author yun36524
 */
@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 忽略的 ServiceException 错误提示，避免打印过多 logger
     */
    public static final Set<String> IGNORE_ERROR_MESSAGES = SetUtils.asSet("无效的刷新令牌");

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final String applicationName;

    private final ApiErrorLogFrameworkService apiErrorLogFrameworkService;

    /**
     * 处理所有异常，主要是提供给 Filter 使用
     * 因为 Filter 不走 SpringMVC 的流程，但是我们又需要兜底处理异常，所以这里提供一个全量的异常处理过程，保持逻辑统一。
     *
     * @param request 请求
     * @param ex 异常
     * @return 通用返回
     */
    public CommonResult<?> allExceptionHandler(HttpServletRequest request, Throwable ex) {
        if (ex instanceof MissingServletRequestParameterException) {
            return missingServletRequestParameterExceptionHandler((MissingServletRequestParameterException) ex);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return methodArgumentTypeMismatchExceptionHandler((MethodArgumentTypeMismatchException) ex);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return methodArgumentNotValidExceptionExceptionHandler((MethodArgumentNotValidException) ex);
        }
        if (ex instanceof BindException) {
            return bindExceptionHandler((BindException) ex);
        }
        if (ex instanceof ConstraintViolationException) {
            return constraintViolationExceptionHandler((ConstraintViolationException) ex);
        }
        if (ex instanceof ValidationException) {
            return validationException((ValidationException) ex);
        }
        if (ex instanceof NoHandlerFoundException) {
            return noHandlerFoundExceptionHandler(request, (NoHandlerFoundException) ex);
        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return httpRequestMethodNotSupportedExceptionHandler((HttpRequestMethodNotSupportedException) ex);
        }
        if (ex instanceof ServiceException) {
            return serviceExceptionHandler((ServiceException) ex);
        }
        if (ex instanceof AccessDeniedException) {
            return accessDeniedExceptionHandler(request, (AccessDeniedException) ex);
        }
        if (ex instanceof MaxUploadSizeExceededException) {
            return maxUploadSizeExceededExceptionHandler((MaxUploadSizeExceededException) ex);
        }
        return defaultExceptionHandler(request, ex);
    }

    /**
     * 处理 SpringMVC 请求参数缺失
     *
     * 例如说，接口上设置了 @RequestParam("xx") 参数，结果并未传递 xx 参数
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CommonResult<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        log.warn("[missingServletRequestParameterExceptionHandler] {}", ex.getMessage());
        return CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数缺失:%s", ex.getParameterName()));
    }

    /**
     * 处理 SpringMVC 请求参数类型错误
     *
     * 例如说，接口上设置了 @RequestParam("xx") 参数为 Integer，结果传递 xx 参数类型为 String
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public CommonResult<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.warn("[methodArgumentTypeMismatchExceptionHandler] {}{}", ex.getMessage(), getBusinessLocationInfo(ex));
        String message = String.format("请求参数类型错误:%s, 参数名:%s, 期望类型:%s",
                ex.getMessage(), ex.getName(), ex.getRequiredType().getSimpleName());
        return CommonResult.error(BAD_REQUEST.getCode(), message);
    }

    /**
     * 处理 SpringMVC 参数校验不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<?> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null; // 断言，避免告警

        // 获取错误详情，包括类、字段和位置
        String className = fieldError.getObjectName();
        String field = fieldError.getField();
        String defaultMessage = fieldError.getDefaultMessage();
        Object rejectedValue = fieldError.getRejectedValue();

        String errorMessage = String.format("对象[%s], 字段[%s], 值[%s], 原因[%s]",
                className, field, rejectedValue, defaultMessage);

        // 记录日志，包含详细信息
        log.warn("[参数校验失败] {}", errorMessage);

        String resultErrorMessage = String.format("参数校验失败: %s", defaultMessage);

        return CommonResult.error(BAD_REQUEST.getCode(), resultErrorMessage);
    }

    /**
     * 处理 SpringMVC 参数绑定不正确，本质上也是通过 Validator 校验
     */
    @ExceptionHandler(BindException.class)
    public CommonResult<?> bindExceptionHandler(BindException ex) {
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null; // 断言，避免告警

        // 获取错误详情，包括类、字段和位置
        String className = fieldError.getObjectName();
        String field = fieldError.getField();
        String defaultMessage = fieldError.getDefaultMessage();
        Object rejectedValue = fieldError.getRejectedValue();

        String errorMessage = String.format("对象[%s], 字段[%s], 值[%s], 原因[%s]",
                className, field, rejectedValue, defaultMessage);

        // 记录日志，包含详细信息
        log.warn("[参数绑定失败] {}", errorMessage);

        String resultErrorMessage = String.format("参数绑定失败: %s", defaultMessage);

        return CommonResult.error(BAD_REQUEST.getCode(), resultErrorMessage);
    }

    /**
     * 处理 Validator 校验不通过产生的异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResult<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        // 只处理第一个错误
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();

        // 获取错误详情
        String propertyPath = constraintViolation.getPropertyPath().toString();
        String message = constraintViolation.getMessage();
        Object invalidValue = constraintViolation.getInvalidValue();
        String rootBeanClass = constraintViolation.getRootBeanClass().getSimpleName();

        String errorMessage = String.format("对象[%s], 属性[%s], 值[%s], 原因[%s]",
                rootBeanClass, propertyPath, invalidValue, message);

        // 记录日志
        log.warn("[参数校验失败] {}", errorMessage);

        String resultErrorMessage = String.format("参数校验失败: %s", message);

        return CommonResult.error(BAD_REQUEST.getCode(), resultErrorMessage);
    }

    /**
     * 处理 Dubbo Consumer 本地参数校验时，抛出的 ValidationException 异常
     */
    @ExceptionHandler(value = ValidationException.class)
    public CommonResult<?> validationException(ValidationException ex) {
        log.warn("[validationException] {}{}", ex.getMessage(), getBusinessLocationInfo(ex));

        // 无法拼接明细的错误信息，因为 Dubbo Consumer 抛出 ValidationException 异常时，是直接的字符串信息，且人类不可读
        String resultErrorMessage = String.format("参数校验失败: %s", ex.getMessage());

        return CommonResult.error(BAD_REQUEST.getCode(), resultErrorMessage);
    }

    /**
     * 处理 SpringMVC 请求地址不存在
     *
     * 注意，它需要设置如下两个配置项：
     * 1. spring.mvc.throw-exception-if-no-handler-found 为 true
     * 2. spring.mvc.static-path-pattern 为 /statics/**
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonResult<?> noHandlerFoundExceptionHandler(HttpServletRequest req, NoHandlerFoundException ex) {
        log.warn("[noHandlerFoundExceptionHandler] {}", ex.getRequestURL());
        return CommonResult.error(NOT_FOUND.getCode(), String.format("请求地址不存在:%s", ex.getRequestURL()));
    }

    /**
     * 处理 SpringMVC 请求方法不正确
     *
     * 例如说，A 接口的方法为 GET 方式，结果请求方法为 POST 方式，导致不匹配
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        log.warn("[httpRequestMethodNotSupportedExceptionHandler] {}", ex.getMessage());
        return CommonResult.error(METHOD_NOT_ALLOWED.getCode(), String.format("请求方法不正确:%s", ex.getMessage()));
    }

    /**
     * 处理 Spring Security 权限不足的异常
     *
     * 来源是，使用 @PreAuthorize 注解，AOP 进行权限拦截
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public CommonResult<?> accessDeniedExceptionHandler(HttpServletRequest req, AccessDeniedException ex) {
        log.warn("[accessDeniedExceptionHandler][userId({}) 无法访问 url({})] {}",
                WebFrameworkUtils.getLoginUserId(req), req.getRequestURL(), ex.getMessage());
        return CommonResult.error(FORBIDDEN);
    }

    /**
     * 处理业务异常 ServiceException
     *
     * 例如说，商品库存不足，用户手机号已存在。
     */
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult<?> serviceExceptionHandler(ServiceException ex) {
        if (!IGNORE_ERROR_MESSAGES.contains(ex.getMessage())) {
            // 不包含的时候，才进行打印，避免 ex 堆栈过多
            log.info("[serviceExceptionHandler] {}{}", ex.getMessage(), getBusinessLocationInfo(ex));
        }
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 获取业务代码的位置信息
     */
    private String getBusinessLocationInfo(Throwable ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (stackTrace == null || stackTrace.length == 0) {
            return "";
        }

        // 查找第一个业务代码的位置，排除框架代码
        for (StackTraceElement element : stackTrace) {
            String className = element.getClassName();

            // 排除框架代码、工具类和handler包
            if (className.contains("ExceptionUtil") ||
                    className.contains("framework.web.core") ||
                    className.contains("framework.common.exception") ||
                    className.contains("springframework") ||
                    className.contains("apache.catalina") ||
                    className.contains("apache.coyote") ||
                    className.contains("undertow") ||
                    className.contains("tomcat") ||
                    className.contains("netty") ||
                    className.contains("java.") ||
                    className.contains("javax.") ||
                    className.contains("com.fasterxml") ||
                    className.contains("framework.apilog") ||
                    className.contains("sun.reflect")) {
                continue;
            }

            // 仅包含模块代码或业务代码
            if (className.startsWith("cn.iocoder.yudao.module") ||
                    className.contains(".controller") ||
                    className.contains(".service.impl") ||
                    className.contains(".biz") ||
                    className.contains(".dao") ||
                    className.contains(".repository") ||
                    className.startsWith("module.")) {

                return String.format(" at %s.%s(%s:%d)",
                        className,
                        element.getMethodName(),
                        element.getFileName() != null ? element.getFileName() : "<generated>",
                        element.getLineNumber());
            }
        }
        return "";
    }

    /**
     * 处理文件上传大小超过限制的异常
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public CommonResult<?> maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException ex) {
        log.warn("[maxUploadSizeExceededExceptionHandler] {}", ex.getMessage());
        return CommonResult.error(BAD_REQUEST.getCode(), "上传文件大小超出限制");
    }

    /**
     * 处理数据库异常
     */
    @ExceptionHandler(value = {DataIntegrityViolationException.class, BadSqlGrammarException.class, InvalidDataAccessResourceUsageException.class})
    public CommonResult<?> databaseExceptionHandler(Exception ex) {
        // 获取根因错误信息
        String rootMessage = ExceptionUtil.getRootCauseMessage(ex);

        // 获取业务代码位置信息
        String location = getBusinessLocationInfo(ex);

        // 构建用户友好的错误消息
        String userMessage = "数据库错误";
        if (ex instanceof DataIntegrityViolationException) {
            // 数据完整性违规，通常是外键约束或唯一约束
            if (rootMessage.contains("foreign key constraint")) {
                userMessage = "数据关联错误，存在关联数据";
            } else if (rootMessage.contains("Duplicate entry")) {
                userMessage = "数据已存在，请勿重复添加";
            } else {
                userMessage = "数据完整性错误";
            }
        } else if (ex instanceof BadSqlGrammarException) {
            userMessage = "SQL语法错误";
        } else if (ex instanceof InvalidDataAccessResourceUsageException) {
            userMessage = "数据访问资源使用无效";
        }

        // 记录详细的错误信息，包含原始错误和位置
        log.warn("[databaseExceptionHandler] {}: {}{}", userMessage, rootMessage, location);

        // 返回更友好的错误信息给前端
        return CommonResult.error(BAD_REQUEST.getCode(), userMessage);
    }

    /**
     * 处理 JPA 数据库异常
     */
    @ExceptionHandler(value = JpaSystemException.class)
    public CommonResult<?> jpaSystemExceptionHandler(JpaSystemException ex) {
        String message = ExceptionUtil.getRootCauseMessage(ex);
        String location = getBusinessLocationInfo(ex);

        // 构建用户友好的错误消息
        String userMessage = "数据库操作异常";
        if (message.contains("constraint")) {
            userMessage = "数据约束冲突，请检查输入";
        } else if (message.contains("Duplicate")) {
            userMessage = "数据已存在，请勿重复添加";
        } else if (message.contains("could not execute statement")) {
            userMessage = "数据库执行失败，请检查输入";
        }

        log.warn("[jpaSystemExceptionHandler] {}: {}{}", userMessage, message, location);
        return CommonResult.error(BAD_REQUEST.getCode(), userMessage);
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> defaultExceptionHandler(HttpServletRequest req, Throwable ex) {
        // 情况一：处理表不存在的异常
        CommonResult<?> tableNotExistsResult = handleTableNotExists(ex);
        if (tableNotExistsResult != null) {
            return tableNotExistsResult;
        }

        // 提取根本原因的错误消息和用户友好消息
        String rootCauseMessage = ExceptionUtil.getRootCauseMessage(ex);
        String userFriendlyMessage = getUserFriendlyMessage(ex);

        // 获取业务代码位置
        String location = getBusinessLocationInfo(ex);

        // 日志记录根本原因和业务代码位置
        log.error("[defaultExceptionHandler] 原因: {}{}", rootCauseMessage, location);

        // 更详细的堆栈信息记录到debug级别
        if (log.isDebugEnabled()) {
            String detailedStackTrace = getRelevantStackTrace(ex);
            log.debug("[defaultExceptionHandler] 详细堆栈: \n{}", detailedStackTrace);
        }

        // 插入异常日志
        this.createExceptionLog(req, ex);

        // 返回对用户友好的错误信息
        return CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), userFriendlyMessage);
    }

    /**
     * 获取相关的业务代码堆栈信息
     */
    private String getRelevantStackTrace(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        // 添加根本原因错误信息
        sb.append(ExceptionUtil.getRootCauseMessage(ex)).append("\n");

        // 最多显示10行堆栈
        int count = 0;
        int maxLines = 10;

        // 只获取业务相关的堆栈信息
        for (StackTraceElement element : ex.getStackTrace()) {
            String className = element.getClassName();

            // 排除框架代码、工具类和handler包
            if (className.contains("ExceptionUtil") ||
                    className.contains("framework.web.core") ||
                    className.contains("framework.common.exception") ||
                    className.contains("springframework") ||
                    className.contains("apache.catalina") ||
                    className.contains("apache.coyote") ||
                    className.contains("undertow") ||
                    className.contains("tomcat") ||
                    className.contains("netty") ||
                    className.contains("java.") ||
                    className.contains("javax.") ||
                    className.contains("com.fasterxml") ||
                    className.contains("framework.apilog") ||
                    className.contains("sun.reflect")) {
                continue;
            }

            // 仅包含模块代码或业务代码
            if (className.startsWith("cn.iocoder.yudao.module") ||
                    className.contains(".controller") ||
                    className.contains(".service.impl") ||
                    className.contains(".biz") ||
                    className.contains(".dao") ||
                    className.contains(".repository") ||
                    className.startsWith("module.") ||
                    className.contains("$$FastClassBySpringCGLIB$$") ||
                    className.contains("$$EnhancerBySpringCGLIB$$")) {

                String location = element.getFileName() != null ?
                        String.format("(%s:%d)", element.getFileName(), element.getLineNumber()) :
                        "(<generated>:-1)";

                sb.append("    at ").append(className).append(".").append(element.getMethodName())
                        .append(location).append("\n");

                if (++count >= maxLines) {
                    sb.append("    ... ").append(ex.getStackTrace().length - count).append(" more\n");
                    break;
                }
            }
        }
        return sb.toString();
    }

    private void createExceptionLog(HttpServletRequest req, Throwable e) {
        // 插入错误日志
        ApiErrorLogCreateReqDTO errorLog = new ApiErrorLogCreateReqDTO();
        try {
            // 初始化 errorLog
            buildExceptionLog(errorLog, req, e);
            // 执行插入 errorLog
        } catch (Throwable th) {
            log.error("[createExceptionLog][url({}) log({}) 发生异常]", req.getRequestURI(),  JsonUtils.toJsonString(errorLog), th);
        }
    }

    private void buildExceptionLog(ApiErrorLogCreateReqDTO errorLog, HttpServletRequest request, Throwable e) {
        // 处理用户信息
        errorLog.setUserId(WebFrameworkUtils.getLoginUserId(request));
        errorLog.setUserType(WebFrameworkUtils.getLoginUserType(request));
        // 设置异常字段
        errorLog.setExceptionName(e.getClass().getName());
        errorLog.setExceptionMessage(ExceptionUtil.getMessage(e));
        errorLog.setExceptionRootCauseMessage(ExceptionUtil.getRootCauseMessage(e));
        errorLog.setExceptionStackTrace(ExceptionUtils.getStackTrace(e));
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        Assert.notEmpty(stackTraceElements, "异常 stackTraceElements 不能为空");
        StackTraceElement stackTraceElement = stackTraceElements[0];
        errorLog.setExceptionClassName(stackTraceElement.getClassName());
        errorLog.setExceptionFileName(stackTraceElement.getFileName());
        errorLog.setExceptionMethodName(stackTraceElement.getMethodName());
        errorLog.setExceptionLineNumber(stackTraceElement.getLineNumber());
        // 设置其它字段
        errorLog.setTraceId(TracerUtils.getTraceId());
        errorLog.setApplicationName(applicationName);
        errorLog.setRequestUrl(request.getRequestURI());
        Map<String, Object> requestParams = MapUtil.<String, Object>builder()
                .put("query", ServletUtils.getParamMap(request))
                .put("body", ServletUtils.getBody(request)).build();
        errorLog.setRequestParams(JsonUtils.toJsonString(requestParams));
        errorLog.setRequestMethod(request.getMethod());
        errorLog.setUserAgent(ServletUtils.getUserAgent(request));
        errorLog.setUserIp(ServletUtils.getClientIP(request));
        errorLog.setExceptionTime(LocalDateTime.now());
    }

    /**
     * 处理 Table 不存在的异常情况
     *
     * @param ex 异常
     * @return 如果是 Table 不存在的异常，则返回对应的 CommonResult
     */
    private CommonResult<?> handleTableNotExists(Throwable ex) {
        String message = ExceptionUtil.getRootCauseMessage(ex);
        if (!message.contains("doesn't exist")) {
            return null;
        }
        // 1. 数据报表
        if (message.contains("report_")) {
            log.error("[报表模块 yudao-module-report - 表结构未导入][参考 https://doc.iocoder.cn/report/ 开启]");
            return CommonResult.error(NOT_IMPLEMENTED.getCode(),
                    "[报表模块 yudao-module-report - 表结构未导入][参考 https://doc.iocoder.cn/report/ 开启]");
        }
        // 2. 工作流
        if (message.contains("bpm_")) {
            log.error("[工作流模块 yudao-module-bpm - 表结构未导入][参考 https://doc.iocoder.cn/bpm/ 开启]");
            return CommonResult.error(NOT_IMPLEMENTED.getCode(),
                    "[工作流模块 yudao-module-bpm - 表结构未导入][参考 https://doc.iocoder.cn/bpm/ 开启]");
        }
        // 3. 微信公众号
        if (message.contains("mp_")) {
            log.error("[微信公众号 yudao-module-mp - 表结构未导入][参考 https://doc.iocoder.cn/mp/build/ 开启]");
            return CommonResult.error(NOT_IMPLEMENTED.getCode(),
                    "[微信公众号 yudao-module-mp - 表结构未导入][参考 https://doc.iocoder.cn/mp/build/ 开启]");
        }
        // 4. 商城系统
        if (StrUtil.containsAny(message, "product_", "promotion_", "trade_")) {
            log.error("[商城系统 yudao-module-mall - 已禁用][参考 https://doc.iocoder.cn/mall/build/ 开启]");
            return CommonResult.error(NOT_IMPLEMENTED.getCode(),
                    "[商城系统 yudao-module-mall - 已禁用][参考 https://doc.iocoder.cn/mall/build/ 开启]");
        }
        // 5. ERP 系统
        if (message.contains("erp_")) {
            log.error("[ERP 系统 yudao-module-erp - 表结构未导入][参考 https://doc.iocoder.cn/erp/build/ 开启]");
            return CommonResult.error(NOT_IMPLEMENTED.getCode(),
                    "[ERP 系统 yudao-module-erp - 表结构未导入][参考 https://doc.iocoder.cn/erp/build/ 开启]");
        }
        // 6. CRM 系统
        if (message.contains("crm_")) {
            log.error("[CRM 系统 yudao-module-crm - 表结构未导入][参考 https://doc.iocoder.cn/crm/build/ 开启]");
            return CommonResult.error(NOT_IMPLEMENTED.getCode(),
                    "[CRM 系统 yudao-module-crm - 表结构未导入][参考 https://doc.iocoder.cn/crm/build/ 开启]");
        }
        // 7. 支付平台
        if (message.contains("pay_")) {
            log.error("[支付模块 yudao-module-pay - 表结构未导入][参考 https://doc.iocoder.cn/pay/build/ 开启]");
            return CommonResult.error(NOT_IMPLEMENTED.getCode(),
                    "[支付模块 yudao-module-pay - 表结构未导入][参考 https://doc.iocoder.cn/pay/build/ 开启]");
        }
        return null;
    }

    /**
     * 获取对用户友好的错误消息
     */
    private String getUserFriendlyMessage(Throwable ex) {
        String rootMessage = ExceptionUtil.getRootCauseMessage(ex);

        // 先截取 at 之前的部分
        int atIndex = rootMessage.indexOf(" at ");
        if (atIndex > 0) {
            rootMessage = rootMessage.substring(0, atIndex);
        }

        // 常见运行时异常的友好提示
        if (ex instanceof NullPointerException) {
            return "系统处理数据时发生错误: 空指针异常";
        } else if (ex instanceof IllegalArgumentException) {
            return "非法参数: " + rootMessage;
        } else if (ex instanceof IllegalStateException) {
            return "系统状态异常: " + rootMessage;
        } else if (ex instanceof IndexOutOfBoundsException) {
            return "系统处理数据时发生错误: 索引越界";
        } else if (ex instanceof ClassCastException) {
            return "系统处理数据时发生错误: 类型转换异常";
        } else if (ex instanceof NumberFormatException) {
            return "数据格式错误: 无法解析为数字";
        } else if (ex instanceof ArithmeticException) {
            return "系统计算错误";
        } else if (ex instanceof UnsupportedOperationException) {
            return "不支持的操作";
        } else if (ex instanceof IOException) {
            return "IO操作异常";
        }

        // 如果错误信息太长，截取关键部分
        if (rootMessage.length() > 100) {
            rootMessage = rootMessage.substring(0, 100) + "...";
        }

        return rootMessage;
    }

}
