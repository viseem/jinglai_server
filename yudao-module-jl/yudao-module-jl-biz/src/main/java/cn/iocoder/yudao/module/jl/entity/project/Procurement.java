package cn.iocoder.yudao.module.jl.entity.project;

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
 * 项目采购单申请 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Procurement")
@Table(name = "jl_project_procurement")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Procurement extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 项目 id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 实验名目库的名目 id
     */
    @Column(name = "project_category_id")
    private Long projectCategoryId;

    /**
     * 采购单号
     */
    @Column(name = "code")
    private String code;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 采购发起时间
     */
    @Column(name = "start_date")
    private String startDate;

    /**
     * 签收陪审人
     */
    @Column(name = "check_user_id", nullable = false )
    private Long checkUserId;

    /**
     * 收货地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 收货人id
     */
    @Column(name = "receiver_user_id")
    private String receiverUserId;

}
