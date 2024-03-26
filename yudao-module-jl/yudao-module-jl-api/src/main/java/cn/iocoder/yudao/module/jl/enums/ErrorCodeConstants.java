package cn.iocoder.yudao.module.jl.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode INSTITUTION_NOT_EXISTS = new ErrorCode(2001000001, "CRM 模块的机构/公司不存在");

    ErrorCode COMPETITOR_NOT_EXISTS = new ErrorCode(2002000001, "友商不存在");

    ErrorCode CUSTOMER_NOT_EXISTS = new ErrorCode(2003000001, "客户不存在");

    ErrorCode FOLLOWUP_NOT_EXISTS = new ErrorCode(2004000001, "销售跟进不存在");

    ErrorCode SALESLEAD_NOT_EXISTS = new ErrorCode(2005000001, "销售线索不存在");

    ErrorCode JOIN_CUSTOMER2SALE_NOT_EXISTS = new ErrorCode(2006000001, "客户所属的销售人员不存在");

    ErrorCode JOIN_SALESLEAD2COMPETITOR_NOT_EXISTS = new ErrorCode(2007000001, "销售线索中竞争对手的报价不存在");

    ErrorCode JOIN_SALESLEAD2CUSTOMERPLAN_NOT_EXISTS = new ErrorCode(2008000001, "销售线索中的客户方案不存在");

    ErrorCode JOIN_SALESLEAD2MANAGER_NOT_EXISTS = new ErrorCode(2009000001, "销售线索中的项目售前支持人员不存在");

    ErrorCode JOIN_SALESLEAD2REPORT_NOT_EXISTS = new ErrorCode(2010000001, "销售线索中的方案不存在");

    ErrorCode PROJECT_NOT_EXISTS = new ErrorCode(2011000001, "项目不存在");

    ErrorCode USER_NOT_EXISTS = new ErrorCode(2012000001, "公司用户不存在");

    ErrorCode CHARGE_ITEM_NOT_EXISTS = new ErrorCode(2013000001, "项目收费项不存在");

    ErrorCode SUPPLY_NOT_EXISTS = new ErrorCode(2014000001, "实验物资不存在");

    ErrorCode PROJECT_FUND_NOT_EXISTS = new ErrorCode(2015000001, "实验物资不存在");

    ErrorCode CATEGORY_REFERENCE_NOT_EXISTS = new ErrorCode(2016000001, "实验名目的参考资料不存在");

    ErrorCode CATEGORY_SUPPLY_NOT_EXISTS = new ErrorCode(2017000001, "实验名目的物资不存在");

    ErrorCode CATEGORY_SOP_NOT_EXISTS = new ErrorCode(2018000001, "实验名目的SOP 不存在");

    ErrorCode CATEGORY_NOT_EXISTS = new ErrorCode(2019000001, "实验名目不存在");

    ErrorCode CATEGORY_CHARGEITEM_NOT_EXISTS = new ErrorCode(2020000001, "实验名目收费项不存在");

    ErrorCode CATEGORY_SKILL_USER_NOT_EXISTS = new ErrorCode(2021000001, "实验名目技术人员不存在");

    ErrorCode PROJECT_SUPPLY_NOT_EXISTS = new ErrorCode(2022000001, "项目中的实验物资不存在");

    ErrorCode PROJECT_QUOTE_NOT_EXISTS = new ErrorCode(2023000001, "报价不存在");

    ErrorCode PROJECT_CHARGEITEM_NOT_EXISTS = new ErrorCode(2024000001, "项目中的收费项不存在");

    ErrorCode PROJECT_SOP_NOT_EXISTS = new ErrorCode(2025000001, "项目中的操作SOP不存在");

    ErrorCode PROJECT_SCHEDULE_NOT_EXISTS = new ErrorCode(2026000001, "安排单不存在");

    ErrorCode PROJECT_CATEGORY_NOT_EXISTS = new ErrorCode(2027000001, "项目中的实验名目不存在");
    ErrorCode PROJECT_CATEGORY_SON_EXISTS = new ErrorCode(2027000001, "该实验包含子实验，请先删除子实验");

    ErrorCode PROJECT_CATEGORY_SUPPLY_EXISTS = new ErrorCode(2027000001, "该实验包含收费项，不可删除");

    ErrorCode SALESLEAD_COMPETITOR_NOT_EXISTS = new ErrorCode(2028000001, "销售线索的竞争报价信息不存在");

    ErrorCode SALESLEAD_CUSTOMER_PLAN_NOT_EXISTS = new ErrorCode(2029000001, "销售线索的竞争报价信息不存在");

    ErrorCode SALESLEAD_MANAGER_NOT_EXISTS = new ErrorCode(2030000001, "销售线索的竞争报价信息不存在");

    ErrorCode PROJECT_CONSTRACT_NOT_EXISTS = new ErrorCode(2031000001, "项目合同不存在");

    ErrorCode COMPANY_SUPPLY_NOT_EXISTS = new ErrorCode(2032000001, "公司实验物资不存在");

    ErrorCode SUPPLY_OUT_NOT_EXISTS = new ErrorCode(2033000001, "出库申请不存在");

    ErrorCode SUPPLY_OUT_ITEM_NOT_EXISTS = new ErrorCode(2034000001, "出库申请的明细项不存在");

    ErrorCode PRODUCT_IN_NOT_EXISTS = new ErrorCode(2038000001, "试验产品入库记录不存在");

    ErrorCode PRODUCT_IN_ITEM_NOT_EXISTS = new ErrorCode(2035000001, "试验产品入库明细不存在");

    ErrorCode PRODUCT_SEND_NOT_EXISTS = new ErrorCode(2037000001, "试验产品寄送单不存在");

    ErrorCode PRODUCT_SEND_ITEM_NOT_EXISTS = new ErrorCode(2036000001, "试验产品寄送单明细不存在");

    ErrorCode INVENTORY_CONTAINER_NOT_EXISTS = new ErrorCode(2037000001, "储存器具不存在");

    ErrorCode INVENTORY_CONTAINER_PLACE_NOT_EXISTS = new ErrorCode(2038000001, "储存器具的位置编号不存在");

    ErrorCode INVENTORY_ROOM_NOT_EXISTS = new ErrorCode(2039000001, "库管房间不存在");

    ErrorCode PROCUREMENT_NOT_EXISTS = new ErrorCode(2040000001, "采购不存在");

    ErrorCode PROCUREMENT_ITEM_NOT_EXISTS = new ErrorCode(2041000001, "采购明细条目不存在");

    ErrorCode PROCUREMENT_PAYMENT_NOT_EXISTS = new ErrorCode(2042000001, "采购支付不存在");

    ErrorCode PROCUREMENT_SHIPMENT_NOT_EXISTS = new ErrorCode(2043000001, "采购物流不存在");

    ErrorCode SUPPLIER_NOT_EXISTS = new ErrorCode(2044000001, "供应商不存在");
    ErrorCode SUPPLIER_EXISTS = new ErrorCode(2044000002, "供应商存在");

    ErrorCode SUPPLY_LOG_ATTACHMENT_NOT_EXISTS = new ErrorCode(2045000001, "物资变更记录的附件不存在");

    ErrorCode SUPPLY_LOG_NOT_EXISTS = new ErrorCode(2046000001, "物资变更记录不存在");

    ErrorCode SUPPLY_PICKUP_NOT_EXISTS = new ErrorCode(2047000001, "物资自取单不存在");

    ErrorCode SUPPLY_PICKUP_ITEM_NOT_EXISTS = new ErrorCode(2048000001, "物资自取单明细条目不存在");

    ErrorCode SUPPLY_SEND_IN_NOT_EXISTS = new ErrorCode(2049000001, "物资寄来单不存在");

    ErrorCode SUPPLY_SEND_IN_ITEM_NOT_EXISTS = new ErrorCode(2050000001, "物资寄来单明细条目不存在");

    ErrorCode PROJECT_FEEDBACK_NOT_EXISTS = new ErrorCode(2051000001, "项目反馈不存在");

    ErrorCode INVENTORY_STORE_IN_NOT_EXISTS = new ErrorCode(2052000001, "入库记录不存在");

    ErrorCode INVENTORY_CHECK_IN_NOT_EXISTS = new ErrorCode(2053000001, "签收记录不存在");

    ErrorCode INVENTORY_OPT_ATTACHMENT_NOT_EXISTS = new ErrorCode(2053400001, "库管操作附近记录不存在");

    ErrorCode LABORATORY_LAB_NOT_EXISTS = new ErrorCode(2053500001, "实验室不存在");
    ErrorCode LABORATORY_USER_NOT_EXISTS = new ErrorCode(2053500002, "实验室人员不存在");

    ErrorCode PROJECT_CATEGORY_LOG_NOT_EXISTS = new ErrorCode(2053600001, "项目实验名目的操作日志不存在");
    ErrorCode PROJECT_CATEGORY_ATTACHMENT_NOT_EXISTS = new ErrorCode(2053600002, "项目实验名目的附件不存在");

    ErrorCode PROJECT_CATEGORY_APPROVAL_NOT_EXISTS = new ErrorCode(2053700001, "项目实验名目的状态变更审批不存在");

    ErrorCode PROJECT_APPROVAL_NOT_EXISTS = new ErrorCode(2053800001, "项目的状态变更记录不存在");

    ErrorCode ASSET_DEVICE_NOT_EXISTS = new ErrorCode(2053900001, "公司资产（设备）不存在");

    ErrorCode PROJECT_FUND_LOG_NOT_EXISTS = new ErrorCode(2054000001, "项目款项不存在");

    ErrorCode PROJECT_CATEGORY_OUTSOURCE_NOT_EXISTS = new ErrorCode(2054100001, "项目实验委外不存在");

    ErrorCode PROJECT_REIMBURSE_NOT_EXISTS = new ErrorCode(2054200001, "项目报销不存在");

    ErrorCode PROJECT_CATEGORY_SUPPLIER_NOT_EXISTS = new ErrorCode(2054300001, "项目实验委外供应商不存在");

    ErrorCode PROJECT_SERVICE_NOT_EXISTS = new ErrorCode(2054400001, "项目售后不存在");
    ErrorCode TEMPLATE_PROJECT_PLAN_NOT_EXISTS = new ErrorCode(2054500001, "项目方案模板不存在");

    ErrorCode TEMPLATE_CONTRACT_NOT_EXISTS = new ErrorCode(2054600001, "合同模板不存在");
    ErrorCode PROJECT_DEVICE_NOT_EXISTS = new ErrorCode(2054700001, "项目设备不存在");

    ErrorCode ASSET_DEVICE_LOG_NOT_EXISTS = new ErrorCode(2054800001, "公司资产（设备）预约不存在");

    ErrorCode ASSET_DEVICE_LOG_EXISTS = new ErrorCode(2054800001, "该时间段已被预约");

    ErrorCode ANIMAL_ROOM_NOT_EXISTS = new ErrorCode(2054900001, "动物饲养室不存在");
    ErrorCode ANIMAL_SHELF_NOT_EXISTS = new ErrorCode(2055000001, "动物饲养笼架不存在");
    ErrorCode UNIQUE_CODE_EXISTS = new ErrorCode(2054900002, "该编码已存在");

    ErrorCode ANIMAL_FEED_ORDER_NOT_EXISTS = new ErrorCode(2055100001, "动物饲养申请单不存在");

    ErrorCode ANIMAL_FEED_CARD_NOT_EXISTS = new ErrorCode(2055200001, "动物饲养鼠牌不存在");

    ErrorCode ANIMAL_FEED_STORE_IN_NOT_EXISTS = new ErrorCode(2055300001, "动物饲养入库不存在");

    ErrorCode ANIMAL_FEED_LOG_NOT_EXISTS = new ErrorCode(2055400001, "动物饲养日志不存在");

    ErrorCode ANIMAL_BOX_NOT_EXISTS = new ErrorCode(2055500001, "动物笼位不存在");

    ErrorCode CRM_RECEIPT_HEAD_NOT_EXISTS = new ErrorCode(2055600001, "客户发票抬头不存在");

    ErrorCode CRM_RECEIPT_NOT_EXISTS = new ErrorCode(2055700001, "客户发票不存在");

    ErrorCode CRM_CONTACT_NOT_EXISTS = new ErrorCode(2055800001, "客户联系人不存在");

    ErrorCode INVENTORY_STORE_OUT_NOT_EXISTS = new ErrorCode(2055900001, "出库记录不存在");

    ErrorCode APPROVAL_NOT_EXISTS = new ErrorCode(2056000001, "审批不存在");
    ErrorCode APPROVAL_PROGRESS_NOT_EXISTS = new ErrorCode(2056000002, "审批流程不存在");
    ErrorCode CONTRACT_APPROVAL_NOT_EXISTS = new ErrorCode(2056100001, "合同状态变更记录不存在");

    ErrorCode FEEDBACK_NOT_EXISTS = new ErrorCode(1002026000, "晶莱反馈不存在");

    ErrorCode PROJECT_FEEDBACK_FOCUS_NOT_EXISTS = new ErrorCode(1002027000, "晶莱项目反馈关注人员不存在");

    ErrorCode PROJECT_PLAN_NOT_EXISTS = new ErrorCode(1002028000, "项目实验方案不存在");

    ErrorCode WORK_DURATION_NOT_EXISTS = new ErrorCode(1002029000, "工时不存在");

    ErrorCode PROJECT_PERSON_NOT_EXISTS = new ErrorCode(1002030000, "项目人员不存在");

    ErrorCode FINANCE_PAYMENT_NOT_EXISTS = new ErrorCode(1002031000, "财务打款不存在");

    ErrorCode PROJECT_DOCUMENT_NOT_EXISTS = new ErrorCode(1002032000, "项目文档不存在");

    ErrorCode AUDIT_CONFIG_NOT_EXISTS = new ErrorCode(1002033000, "审批配置不存在");

    ErrorCode TASK_RELATION_NOT_EXISTS = new ErrorCode(1002034000, "任务关系依赖不存在");

    ErrorCode CMS_ARTICLE_NOT_EXISTS = new ErrorCode(1002034000, "文章不存在");

    ErrorCode PROJECT_FUND_CHANGE_LOG_NOT_EXISTS = new ErrorCode(1002035000, "变更日志不存在");

    ErrorCode SUBJECT_GROUP_NOT_EXISTS = new ErrorCode(1002036000, "专题小组不存在");
    ErrorCode SUBJECT_GROUP_MEMBER_NOT_EXISTS = new ErrorCode(1002036100, "专题小组成员不存在");



    ErrorCode REMINDER_NOT_EXISTS = new ErrorCode(1002037000, "提醒事项不存在");
    ErrorCode CONTRACT_FUND_LOG_NOT_EXISTS = new ErrorCode(1002038000, "合同收款记录不存在");
    ErrorCode CONTRACT_INVOICE_LOG_NOT_EXISTS = new ErrorCode(1002039000, "合同发票记录不存在");

    ErrorCode VISIT_APPOINTMENT_NOT_EXISTS = new ErrorCode(1002040000, "到访预约不存在");

    ErrorCode PROJECT_QUOTATION_NOT_EXISTS = new ErrorCode(1002050000, "项目报价不存在");
    ErrorCode CRM_SUBJECT_GROUP_NOT_EXISTS = new ErrorCode(1002060000, "客户课题组不存在");

    ErrorCode PROJECT_SUPPLIER_INVOICE_NOT_EXISTS = new ErrorCode(1002070000, "供应商发票不存在");
    ErrorCode INVENTORY_PRODUCT_LOG_NOT_EXISTS = new ErrorCode(1002080000, "产品变更日志不存在");
    ErrorCode COMMON_TODO_NOT_EXISTS = new ErrorCode(1002090000, "通用TODO不存在");

    ErrorCode COMMON_TODO_LOG_NOT_EXISTS = new ErrorCode(1002090001, "通用TODO记录不存在");

    ErrorCode SHIP_WAREHOUSE_NOT_EXISTS = new ErrorCode(1002100000, "收货仓库不存在");

    ErrorCode COMMON_ATTACHMENT_NOT_EXISTS = new ErrorCode(1002200000, "通用附件不存在");

    ErrorCode CELL_BASE_NOT_EXISTS = new ErrorCode(1002300000, "细胞数据不存在");

    ErrorCode COLLABORATION_RECORD_NOT_EXISTS = new ErrorCode(1002400000, "通用协作记录不存在");


    // 工作任务模块 1002400000
    ErrorCode WORK_TODO_NOT_EXISTS = new ErrorCode(1002400001, "工作任务 TODO不存在");
    ErrorCode WORK_TODO_TAG_NOT_EXISTS = new ErrorCode(1002400002, "工作任务 TODO 的标签不存在");
    ErrorCode WORK_TODO_TAG_RELATION_NOT_EXISTS = new ErrorCode(1002400003, "工作任务 TODO 与标签的关联不存在");
    ErrorCode COMMON_LOG_NOT_EXISTS = new ErrorCode(1002500000, "管控记录不存在");

    ErrorCode COMMON_CATE_NOT_EXISTS = new ErrorCode(1002600000, "通用分类不存在");

    ErrorCode SALES_GROUP_NOT_EXISTS = new ErrorCode(1002700000, "销售分组不存在");

    ErrorCode SALES_GROUP_MEMBER_NOT_EXISTS = new ErrorCode(1002800000, "销售分组成员不存在");
    ErrorCode PI_COLLABORATION_NOT_EXISTS = new ErrorCode(1002900000, "销售分组成员不存在");

    ErrorCode PI_COLLABORATION_ITEM_NOT_EXISTS = new ErrorCode(1003000000, "pi组协作明细不存在");
    ErrorCode COMMON_OPERATE_LOG_NOT_EXISTS = new ErrorCode(1003100000, "通用变更日志不存在");
    ErrorCode PROJECT_PUSH_LOG_NOT_EXISTS = new ErrorCode(1003200000, "项目推进记录不存在");
    ErrorCode VISIT_LOG_NOT_EXISTS = new ErrorCode(1003300000, "拜访记录不存在");


    // ========== 企业微信模块 ==========
    ErrorCode WX_CP_SEND_MSG_ERROR = new ErrorCode(1009100000, "发送企业微信消息失败");


    // ========== AUTH 模块 1004003000 ==========

    ErrorCode AUTH_WX_APP_PHONE_CODE_ERROR = new ErrorCode(1004003006, "获得手机号失败");


    // ========== BPM ==========

    ErrorCode BPM_PARAMS_ERROR = new ErrorCode(1005003006, "参数错误");

}