package cn.iocoder.yudao.module.jl.entity.asset;

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
 * 公司资产（设备） Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AssetDevice")
@Table(name = "jl_asset_device")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AssetDevice extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 设备名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 所属类型：公司、租赁
     */
    @Column(name = "owner_type", nullable = false )
    private String ownerType;

    /**
     * 管理人 id
     */
    @Column(name = "manager_id")
    private Long managerId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "manager_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User manager;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 设备快照名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 设备快照地址
     */
    @Column(name = "file_url")
    private String fileUrl;

    /**
     * 设备类型：细胞仪器、解剖仪器
     */
    @Column(name = "type")
    private String type;

    /**
     * 位置
     */
    @Column(name = "location")
    private String location;

    /**
     * 设备状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 设备编码：后端生成
     */
    @Column(name = "sn")
    private String sn;

    /**
     * 设备编码：后端生成
     */
    @Column(name = "color")
    private String color;

    /**
     * 所属实验室
     */
    @Column(name = "lab_id")
    private Long labId;

    @Transient
    private Boolean busy;
}
