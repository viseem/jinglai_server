package cn.iocoder.yudao.module.system.enums;

/**
 * System 字典类型的枚举类
 *
 * @author 芋道源码
 */
public interface DictTypeConstants {

    String USER_TYPE = "user_type"; // 用户类型
    String COMMON_STATUS = "common_status"; // 系统状态

    // ========== SYSTEM 模块 ==========

    String USER_SEX = "system_user_sex"; // 用户性别

    String OPERATE_TYPE = "system_operate_type"; // 操作类型

    String LOGIN_TYPE = "system_login_type"; // 登录日志的类型
    String LOGIN_RESULT = "system_login_result"; // 登录结果

    String ERROR_CODE_TYPE = "system_error_code_type"; // 错误码的类型枚举

    String SMS_CHANNEL_CODE = "system_sms_channel_code"; // 短信渠道编码
    String SMS_TEMPLATE_TYPE = "system_sms_template_type"; // 短信模板类型
    String SMS_SEND_STATUS = "system_sms_send_status"; // 短信发送状态
    String SMS_RECEIVE_STATUS = "system_sms_receive_status"; // 短信接收状态


    // =================采购
    String SUPPLIER_CHANNEL_TYPE = "SUPPLIER_CHANNEL_TYPE"; // 供应商渠道类型
    String SUPPLIER_BILL_WAY_TYPE = "SUPPLIER_BILL_WAY_TYPE"; // 供应商结算方式类型
    String SUPPLIER_PAYMENT_CYCLE = "SUPPLIER_PAYMENT_CYCLE"; // 供应商付款周期类型

    // ======== 财务
    String RECEIPT_TYPE = "RECEIPT_TYPE"; // 发票种类：全电专票 全电普票

    // ====== 饲养
    String FEED_BILL_RULES = "FEED_BILL_RULES"; // 发票种类：全电专票 全电普票

    // ====  项目
    // PROJECT_TYPE = 'projectType'
    String PROJECT_TYPE = "projectType";

}
