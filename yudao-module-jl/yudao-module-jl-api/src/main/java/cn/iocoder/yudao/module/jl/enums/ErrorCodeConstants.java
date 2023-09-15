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

}