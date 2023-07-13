package cn.iocoder.yudao.module.jl.entity.inventory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 实验产品寄送 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProductSend")
@Table(name = "jl_inventory_product_send")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductSend extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 项目 id
     */
    @Column(name = "project_id", nullable = false)
    private Long projectId;


    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    /**
     * JPA 级联出 project
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectOnly project;

    /**
     * 项目 id
     */
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    /**
     * 实验名目库的名目 id
     */
    @Column(name = "project_category_id")
    private Long projectCategoryId;

    /**
     * 寄送单号
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
     * 寄送时间
     */
    @Column(name = "send_date")
    private String sendDate;

    /**
     * 收货地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 收货人
     */
    @Column(name = "receiver_name")
    private String receiverName;

    /**
     * 收货人电话
     */
    @Column(name = "receiver_phone")
    private String receiverPhone;

    /**
     * send items
     */
    @OneToMany(mappedBy = "productSend", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonManagedReference
    private List<ProductSendItem> items;
}
