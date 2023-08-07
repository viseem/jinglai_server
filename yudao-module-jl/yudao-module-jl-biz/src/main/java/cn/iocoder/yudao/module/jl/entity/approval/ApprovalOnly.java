package cn.iocoder.yudao.module.jl.entity.approval;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 审批 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ApprovalOnly")
@Table(name = "jl_approval")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ApprovalOnly extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 内容
     */
    @Column(name = "content", nullable = false )
    private String content;

    /**
     * 内容详情id
     */
    @Column(name = "ref_id", nullable = false )
    private Long refId;

    /**
     * 类型
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 当前审批人
     */
    @Column(name = "current_approval_user")
    private String currentApprovalUser;

    /**
     * 当前审批状态
     */
    @Column(name = "current_approval_stage")
    private String currentApprovalStage;


}
