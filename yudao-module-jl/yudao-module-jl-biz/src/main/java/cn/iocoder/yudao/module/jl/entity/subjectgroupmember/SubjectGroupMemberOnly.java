package cn.iocoder.yudao.module.jl.entity.subjectgroupmember;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.subjectgroup.SubjectGroup;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 专题小组成员 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SubjectGroupMemberOnly")
@Table(name = "jl_subject_group_member")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SubjectGroupMemberOnly extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 分组id
     */
    @Column(name = "group_id", nullable = false )
    private Long groupId;

    /**
     * 用户id
     */
    @Column(name = "user_id", nullable = false )
    private Long userId;

    /**
     * 类型
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * 角色
     */
    @Column(name = "role", nullable = false )
    private String role;

    /**
     * 昵称
     */
    @Column(name = "nickname", nullable = false )
    private String nickname;

    /**
     * 类型
     */
    @Column(name = "post", nullable = false )
    private String post;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

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


}
