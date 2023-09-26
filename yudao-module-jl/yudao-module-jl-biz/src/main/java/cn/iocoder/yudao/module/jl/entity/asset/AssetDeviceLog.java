package cn.iocoder.yudao.module.jl.entity.asset;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 公司资产（设备）预约 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AssetDeviceLog")
@Table(name = "jl_asset_device_log")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AssetDeviceLog extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 设备id
     */
    @Column(name = "device_id", nullable = false )
    private Long deviceId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "device_id",  insertable = false, updatable = false)
    private AssetDevice device;

    /**
     * 预约说明
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 开始时间
     */
    @Column(name = "start_date", nullable = false )
    private String startDate;

    /**
     * 结束时间
     */
    @Column(name = "end_date", nullable = false )
    private String endDate;

    /**
     * 项目id
     */
    @Column(name = "project_id")
    private Long projectId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectSimple project;

    /**
     * 用途分类：项目
     */
    @Column(name = "use_type")
    private String useType;

    /**
     * 项目设备id
     */
    @Column(name = "project_device_id")
    private Long projectDeviceId;

}
