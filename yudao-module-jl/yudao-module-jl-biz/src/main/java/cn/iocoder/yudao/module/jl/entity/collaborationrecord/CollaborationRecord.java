package cn.iocoder.yudao.module.jl.entity.collaborationrecord;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
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
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 通用协作记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CollaborationRecord")
@Table(name = "jl_collaboration_record")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CollaborationRecord extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 外键id
     */
    @Column(name = "ref_id", nullable = false )
    private Long refId;

    /**
     * 类型
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * 内容
     */
    @Column(name = "content", nullable = false )
    private String content;

    /**
     * 父级id
     */
    @Column(name = "pid")
    private Long pid;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /*
     * 级联附件
     * */
    @OneToMany(fetch = FetchType.EAGER)
    @Where(clause = "type = 'COLLABORATION_RECORD'")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "ref_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CommonAttachment> attachmentList = new ArrayList<>();

    /*
    * 级联创建者
    * */
    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}
