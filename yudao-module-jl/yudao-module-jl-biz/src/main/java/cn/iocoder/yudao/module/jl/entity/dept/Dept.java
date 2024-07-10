package cn.iocoder.yudao.module.jl.entity.dept;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 部门 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Dept")
@Table(name = "system_dept")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Dept extends BaseEntity {

    /**
     * 部门id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 部门名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 父部门id
     */
    @Column(name = "parent_id", nullable = false )
    private Long parentId;

    /**
     * 显示顺序
     */
    @Column(name = "sort", nullable = false )
    private Integer sort;

    /**
     * 负责人
     */
    @Column(name = "leader_user_id")
    private Long leaderUserId;

    /**
     * 联系电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 部门状态（0正常 1停用）
     */
    @Column(name = "status", nullable = false )
    private Byte status;

}
