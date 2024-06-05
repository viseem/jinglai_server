package cn.iocoder.yudao.module.jl.entity.crm;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 机构/公司 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Institution")
@Table(name = "jl_crm_institution")
public class Institution extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 省份
     */
    @Column(name = "province", nullable = false )
    private String province;

    /**
     * 城市
     */
    @Column(name = "city", nullable = false )
    private String city;

    /**
     * 名字
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 详细地址
     */
    @Column(name = "address", nullable = false )
    private String address;

    /**
     * 备注信息
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 机构类型枚举值
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * 发票抬头
     */
    @Column(name = "bill_title", nullable = false )
    private String billTitle;

    /**
     * 开票方式
     */
    @Column(name = "bill_way", nullable = false )
    private String billWay;

    /**
     * 发票要求
     */
    @Column(name = "bill_request", nullable = false )
    private String billRequest;

    /**
     * 银行账号
     */
    @Column(name = "bank_account", nullable = false )
    private String bankAccount;

    /**
     * 开户行
     */
    @Column(name = "bank_branch", nullable = false )
    private String bankBranch;

    /**
     * 科室
     */
    @Column(name = "department", nullable = false )
    private String department;

    /**
     * 企业码
     */
    @Column(name = "code", nullable = false )
    private String code;


    /**
     * 资料内容
     */
    @Column(name = "data_content", nullable = false )
    private String dataContent;

    /**
     * 法定代表人
     */
    @Column(name = "legal_representative", nullable = false )
    private String legalRepresentative;

    /**
     * 注册基本
     */
    @Column(name = "register_capital", nullable = false )
    private String registerCapital;

    /**
     * 成立日期
     */
    @Column(name = "establish_date", nullable = false )
    private String establishDate;

    /**
     * 信用代码
     */
    @Column(name = "credit_code", nullable = false )
    private String creditCode;

    /**
     * 注册地址
     */
    @Column(name = "register_address", nullable = false )
    private String registerAddress;

    /**
     * 电话
     */
    @Column(name = "phone", nullable = false )
    private String phone;

    /**
     * 邮箱
     */
    @Column(name = "email", nullable = false )
    private String email;

    /**
     * 纳税人识别号
     */
    @Column(name = "bill_code", nullable = false )
    private String billCode;

    /**
     * 网址
     */
    @Column(name = "website", nullable = false )
    private String website;

    /**
     * 简介
     */
    @Column(name = "profile", nullable = false )
    private String profile;

    /**
     * 经营范围
     */
    @Column(name = "business_scope", nullable = false )
    private String businessScope;

    /**
     * 发票寄送地址
     */
    @Column(name = "invoice_address", nullable = false )
    private String invoiceAddress;

    /*
     * 级联附件
     * */
    @OneToMany(fetch = FetchType.EAGER)
    @Where(clause = "type = 'INSTITUTION'")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "ref_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CommonAttachment> attachmentList = new ArrayList<>();
}
