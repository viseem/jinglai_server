package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 项目采购单物流信息 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Supplier")
@Table(name = "jl_project_supplier")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Supplier extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 联系人
     */
    @Column(name = "contact_name")
    private String contactName;

    /**
     * 联系电话
     */
    @Column(name = "contact_phone")
    private String contactPhone;

    /**
     * 结算周期
     */
    @Column(name = "payment_cycle")
    private String paymentCycle;

    /**
     * 银行卡号
     */
    @Column(name = "bank_account")
    private String bankAccount;

    /**
     * 税号
     */
    @Column(name = "tax_number")
    private String taxNumber;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

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
     * 所属部门
     */
    @Column(name = "contactDepartment", nullable = false )
    private String contact_department;

    /**
     * 产品
     */
    @Column(name = "product", nullable = false )
    private String product;

    /**
     * 服务折扣
     */
    @Column(name = "discount", nullable = false )
    private String discount;

    /**
     * 联系人的职位
     */
    @Column(name = "contact_level", nullable = false )
    private String contactLevel;

}
