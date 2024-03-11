package cn.iocoder.yudao.module.jl.entity.picollaboration;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.crm.Saleslead;
import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
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
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * PI组协作 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "PiCollaboration")
@Table(name = "jl_pi_collaboration")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PiCollaboration extends BaseEntity {

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
     * 协作的事项id
     */
    @Column(name = "target_id", nullable = false )
    private Long targetId;

    /**
     * 协作的事项类型
     */
    @Column(name = "target_type", nullable = false )
    private String targetType;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Where(clause = "target_type = 'SALESLEAD'")
    @JoinColumn(name = "target_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Saleslead SALESLEAD;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Where(clause = "target_type = 'PROJECT'")
    @JoinColumn(name = "target_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectOnly PROJECT;

}
