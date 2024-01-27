package cn.iocoder.yudao.module.jl.entity.subjectgroup;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 专题小组 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SubjectGroup")
@Table(name = "jl_subject_group")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SubjectGroup extends BaseEntity {

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
     * 备注
     */
    @Column(name = "mark", nullable = false )
    private String mark;

    /**
     * 专题
     */
    @Column(name = "area", nullable = false )
    private String area;

    /**
     * 领域
     */
    @Column(name = "subject", nullable = false )
    private String subject;

    /**
     * 组长
     */
    @Column(name = "leader_id", nullable = false )
    private Long leaderId;

    /**
     * 商户组长
     */
    @Column(name = "business_leader_id", nullable = false )
    private Long businessLeaderId;

    /**
     * 编号
     */
    @Column(name = "code")
    private String code;

    /**
     * 订单金额kpi
     */
    @Column(name = "kpi_order_fund", nullable = false )
    private BigDecimal kpiOrderFund;

    /**
     * 回款金额kpi
     */
    @Column(name = "kpi_return_fund", nullable = false )
    private Long kpiReturnFund;


    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "leader_id",  insertable = false, updatable = false)
    private User leaderUser;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "business_leader_id",  insertable = false, updatable = false)
    private User businessLeaderUser;

}
