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
    NOTIFY_WHEN_INVOICE_IMPORT("NOTIFY_WHEN_INVOICE_IMPORT"),// 发票被导入时，通知销售绑定合同
    NOTIFY_WHEN_SALESLEAD_REPLY("NOTIFY_WHEN_SALESLEAD_REPLY"),// 商机回复时，发送给相关人员：
    NOTIFY_WHEN_EXP_ATTACHMENT("NOTIFY_WHEN_EXP_ATTACHMENT"),// 实验结果附件上传时，发送给相关人员：
    NOTIFY_WHEN_PROJECT_STAGE_CHANGE("NOTIFY_WHEN_PROJECT_STAGE_CHANGE"),// 实验结果附件上传时，发送给相关人员：
    NOTIFY_WHEN_CATEGORY_STAGE_CHANGE("NOTIFY_WHEN_CATEGORY_STAGE_CHANGE"),// 实验结果附件上传时，发送给相关人员：
    NOTIFY_WHEN_SOP_DONE("NOTIFY_WHEN_SOP_DONE"),// 关键节点完成，通知相关人员 项目负责人及项目的实验员
    NOTIFY_WHEN_SALESLEAD_SIGNED("NOTIFY_WHEN_SALESLEAD_SIGNED"),// 实验结果附件上传时，发送给相关人员：
    URGE_DO("URGE_DO"),// 催办通知
    NOTIFY_WHEN_SALESLEAD_CHANGE_VERSION("NOTIFY_WHEN_SALESLEAD_CHANGE_VERSION"),// 催办通知
    NOTIFY_WHEN_QUOTATIONED("NOTIFY_WHEN_QUOTATIONED");// 商机被报价时，发送给商机负责人


    /**
     * 短信模板的标识
     *
     * 关联 SmsTemplateDO 的 code 属性
     */
    private final String templateCode;

}
