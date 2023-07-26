package cn.iocoder.yudao.module.jl.entity.animal;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
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
 * 动物饲养室 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AnimalRoom")
@Table(name = "jl_animal_room")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AnimalRoom extends BaseEntity {

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
     * 编号
     */
    @Column(name = "code")
    private String code;

    /**
     * 管理人id
     */
    @Column(name = "manager_id", nullable = false )
    private Long managerId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "manager_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User manager;

    /**
     * 描述说明
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 位置
     */
    @Column(name = "location")
    private String location;

    /**
     * 排序
     */
    @Column(name = "weight", nullable = false )
    private Integer weight;

    /**
     * 缩略图名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 缩略图地址
     */
    @Column(name = "file_url")
    private String fileUrl;

}
