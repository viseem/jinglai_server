package cn.iocoder.yudao.module.jl.entity.visitlog;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
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
 * 拜访记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "VisitLog")
@Table(name = "jl_visit_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class VisitLog extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 客户id
     */
    @Column(name = "customer_id", nullable = false )
    private Long customerId;

    /**
     * 销售id
     */
    @Column(name = "sales_id", nullable = false )
    private Long salesId;

    /**
     * 拜访时间
     */
    @Column(name = "time")
    private LocalDateTime time;

    /**
     * 拜访地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 拜访目的
     */
    @Column(name = "goal")
    private String goal;

    /**
     * 拜访途径
     */
    @Column(name = "visit_type", nullable = false )
    private String visitType;

    /**
     * 拜访内容
     */
    @Column(name = "content", nullable = false )
    private String content;

    /**
     * 反馈
     */
    @Column(name = "feedback")
    private String feedback;

    /**
     * 评分
     */
    @Column(name = "score")
    private Integer score;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "sales_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User sales;
}
