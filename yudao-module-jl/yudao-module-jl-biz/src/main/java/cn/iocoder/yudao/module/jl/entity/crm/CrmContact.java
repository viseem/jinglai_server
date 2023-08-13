package cn.iocoder.yudao.module.jl.entity.crm;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 客户联系人 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CrmContact")
@Table(name = "jl_crm_contact")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CrmContact extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 姓名
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 性别
     */
    @Column(name = "gender")
    private Byte gender;

    /**
     * 客户id
     */
    @Column(name = "customer_id", nullable = false )
    private Integer customerId;

    /**
     * 手机号
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 电话
     */
    @Column(name = "tel")
    private String tel;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 职务
     */
    @Column(name = "position")
    private String position;

    /**
     * 是否决策者
     */
    @Column(name = "is_maker")
    private Boolean isMaker;

    /**
     * 详细地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 省
     */
    @Column(name = "province")
    private String province;

    /**
     * 市
     */
    @Column(name = "city")
    private String city;

    /**
     * 区
     */
    @Column(name = "area")
    private String area;

    /**
     * 下次联系时间
     */
    @Column(name = "next_contact_time")
    private String nextContactTime;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerOnly customer;

}
