package cn.iocoder.yudao.module.jl.entity.subjectgroupmember;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.subjectgroup.SubjectGroup;
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

/**
 * 专题小组成员 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SubjectGroupMember")
@Table(name = "jl_subject_group_member")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SubjectGroupMember extends BaseEntity {

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
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "group_id",  insertable = false, updatable = false)
    private SubjectGroup group;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id",  insertable = false, updatable = false)
    private User user;

}
