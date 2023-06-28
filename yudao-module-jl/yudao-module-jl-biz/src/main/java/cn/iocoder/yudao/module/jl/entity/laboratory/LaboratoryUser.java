package cn.iocoder.yudao.module.jl.entity.laboratory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.user.User;
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
 * 实验室人员 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "LaboratoryUser")
@Table(name = "jl_laboratory_user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LaboratoryUser extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 实验室 id
     */
    @Column(name = "lab_id")
    private Long labId;

    /**
     * 人员 id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * JPA 级联出 user
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    //TODO lab还会查出来 user 蛋疼
    /**
     * JPA 级联出 lab
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "lab_id", referencedColumnName = "id", insertable = false, updatable = false)
    private LaboratoryLab lab;

    /**
     * 备注描述
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 实验室人员等级
     */
    @Column(name = "rank")
    private String rank;

}
