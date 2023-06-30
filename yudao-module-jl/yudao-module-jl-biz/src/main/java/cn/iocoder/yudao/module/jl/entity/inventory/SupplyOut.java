package cn.iocoder.yudao.module.jl.entity.inventory;

import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.SupplyOutItemRespVO;
import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
 * 出库申请 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SupplyOut")
@Table(name = "jl_inventory_supply_out")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SupplyOut extends BaseEntity {

    /**
     * 岗位ID
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
    @Column(name = "project_category_id", nullable = false )
    private Long projectCategoryId;

    /**
     * 说明
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;


    /**
     * 实验名目的审批状态
     */
    @OneToMany(mappedBy = "supplyOut", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    @NotFound(action = NotFoundAction.IGNORE)
    private List<SupplyOutItem> items;
}
