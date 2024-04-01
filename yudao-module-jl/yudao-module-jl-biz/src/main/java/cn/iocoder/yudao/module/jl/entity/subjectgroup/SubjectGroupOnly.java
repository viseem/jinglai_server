package cn.iocoder.yudao.module.jl.entity.subjectgroup;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.subjectgroupmember.SubjectGroupMember;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 专题小组 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SubjectGroupOnly")
@Table(name = "jl_subject_group")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SubjectGroupOnly extends BaseEntity {

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
    private BigDecimal kpiReturnFund;

    /**
     * 出库金额kpi
     */
    @Column(name = "kpi_out_fund", nullable = false )
    private BigDecimal kpiOutFund;


}
