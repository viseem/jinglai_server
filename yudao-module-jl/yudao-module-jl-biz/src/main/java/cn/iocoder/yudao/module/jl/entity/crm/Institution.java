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
