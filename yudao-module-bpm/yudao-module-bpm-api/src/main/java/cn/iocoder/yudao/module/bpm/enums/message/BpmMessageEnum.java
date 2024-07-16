package cn.iocoder.yudao.module.bpm.enums.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bpm 消息的枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum BpmMessageEnum {

/*    SMS_WHEN_APPROVAL("bpm_process_instance_approve"), // 流程任务被审批通过时，发送给申请人
    SMS_WHEN_REJECT("bpm_process_instance_reject"), // 流程任务被审批不通过时，发送给申请人
    SMS_WHEN_ASSIGNED("bpm_task_assigned"), // 任务被分配时，发送给审批人

    EMAIL_WHEN_APPROVAL("EMAIL_WHEN_BPM_APPROVAL"), // 流程任务被审批通过时，发送给申请人
    EMAIL_WHEN_REJECT("EMAIL_WHEN_BPM_REJECT"), // 流程任务被审批不通过时，发送给申请人
    EMAIL_WHEN_ASSIGNED("EMAIL_WHEN_BPM_ASSIGNED"), // 任务被分配时，发送给审批人*/

    NOTIFY_WHEN_APPROVAL("NOTIFY_WHEN_BPM_APPROVAL"), // 流程任务被审批通过时，发送给申请人
    NOTIFY_WHEN_REJECT("NOTIFY_WHEN_BPM_REJECT"), // 流程任务被审批不通过时，发送给申请人
    NOTIFY_WHEN_ASSIGNED("NOTIFY_WHEN_BPM_ASSIGNED"), // 任务被分配时，发送给审批人
    NOTIFY_WHEN_QUOTATION("NOTIFY_WHEN_QUOTATION"),// 商机申请报价时，发给商机负责人
    NOTIFY_WHEN_FUND_IMPORT("NOTIFY_WHEN_FUND_IMPORT"),// 款项被导入时，通知销售绑定合同
    NOTIFY_WHEN_FUND_BIND("NOTIFY_WHEN_FUND_BIND"),// 款项被导入时，通知销售绑定合同
    NOTIFY_WHEN_INVOICE_IMPORT("NOTIFY_WHEN_INVOICE_IMPORT"),// 发票被导入时，通知销售绑定合同
    NOTIFY_WHEN_SALESLEAD_REPLY("NOTIFY_WHEN_SALESLEAD_REPLY"),// 商机回复时，发送给相关人员：
    NOTIFY_WHEN_PROJECT_REPLY("NOTIFY_WHEN_PROJECT_REPLY"),// 项目沟通时，通知相关人员
    NOTIFY_WHEN_CONTRACT_REPLY("NOTIFY_WHEN_CONTRACT_REPLY"),// 合同沟通时，通知相关人员

    NOTIFY_WHEN_QUOTATION_REPLY("NOTIFY_WHEN_QUOTATION_REPLY"),// 商机回复时，发送给相关人员：

    NOTIFY_WHEN_EXP_ATTACHMENT("NOTIFY_WHEN_EXP_ATTACHMENT"),// 实验结果附件上传时，发送给相关人员：
    NOTIFY_WHEN_PROJECT_STAGE_CHANGE("NOTIFY_WHEN_PROJECT_STAGE_CHANGE"),// 实验结果附件上传时，发送给相关人员：
    NOTIFY_WHEN_CATEGORY_STAGE_CHANGE("NOTIFY_WHEN_CATEGORY_STAGE_CHANGE"),// 实验结果附件上传时，发送给相关人员：
    NOTIFY_WHEN_SOP_DONE("NOTIFY_WHEN_SOP_DONE"),// 关键节点完成，通知相关人员 项目负责人及项目的实验员
    NOTIFY_WHEN_SALESLEAD_SIGNED("NOTIFY_WHEN_SALESLEAD_SIGNED"),// 商机签订合同，发送给相关人员：
    NOTIFY_WHEN_CONTRACT_CREATE("NOTIFY_WHEN_CONTRACT_CREATE"),// 合同创建时，发送给相关人员：
    URGE_DO("URGE_DO"),// 催办通知
    NOTIFY_WHEN_SALESLEAD_CHANGE_VERSION("NOTIFY_WHEN_SALESLEAD_CHANGE_VERSION"),// 催办通知
    NOTIFY_WHEN_GOODS_SIGN_IN("NOTIFY_WHEN_GOODS_SIGN_IN"),// 物资签收入库通知
    NOTIFY_WHEN_COMMON_TASK_SEND("NOTIFY_WHEN_COMMON_TASK_SEND"),// 任务下发时，发给任务负责人
    NOTIFY_WHEN_COMMON_TASK_WAIT_DO("NOTIFY_WHEN_COMMON_TASK_WAIT_DO"),// 待办任务
    NOTIFY_WHEN_PROJECT_COMMON_TASK_WAIT_DO("NOTIFY_WHEN_PROJECT_COMMON_TASK_WAIT_DO"),// 项目的待办任务，这里只发送一个项目名称的通知，点击进去的是项目
    NOTIFY_WHEN_ANIMAL_FEED_APPLY("NOTIFY_WHEN_ANIMAL_FEED_APPLY"),// 饲养单申请 发送通知给饲养部负责人
    NOTIFY_WHEN_TASK_ARRANGE_CUSTOMER_SIGN("NOTIFY_WHEN_TASK_ARRANGE_CUSTOMER_SIGN"),// 安排单客户签字 发送通知
    NOTIFY_WHEN_INVOICE_APPLY_VOID("NOTIFY_WHEN_INVOICE_APPLY_VOID"),// 发票申请作废时 发送通知
    NOTIFY_WHEN_QUOTATIONED("NOTIFY_WHEN_QUOTATIONED");// 商机被报价时，发送给商机负责人


    /**
     * 短信模板的标识
     *
     * 关联 SmsTemplateDO 的 code 属性
     */
    private final String templateCode;

}
