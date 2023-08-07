package cn.iocoder.yudao.module.jl.entity.approval;

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

/**
 * 审批 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Approval")
@Table(name = "jl_approval")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Approval extends BaseEntity {

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

    /**
     * 查询审批进度
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "approval_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ApprovalProgressOnly> items = new ArrayList<>();

    @Transient
    private ApprovalProgressOnly lastApprovalProgress;

    public ApprovalProgressOnly getLastApprovalProgress() {
        System.out.println("items:"+items   );

        for(int i = 0; i < items.size(); i++) {
            if(items.get(i).getApprovalStage() == null || items.get(i).getApprovalStage().length()==0){
                return items.get(i);
            }
        }
        return  null;
    }

    public String getApprovalStage() {
        for(int i = items.size() - 1; i >= 0; i--) {
            if(items.get(i).getApprovalStage() != null) {
                if (i==items.size()-1){
                    return items.get(i).getApprovalStage();
                }
                break;
            }
        }
        return  null;
    }

    @Transient
    private String approvalStage;


}
