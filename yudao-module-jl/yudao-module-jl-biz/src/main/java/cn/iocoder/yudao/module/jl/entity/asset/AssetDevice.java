package cn.iocoder.yudao.module.jl.entity.asset;

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
 * 公司资产（设备） Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AssetDevice")
@Table(name = "jl_asset_device")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
     * 设备类型：细胞仪器、解剖仪器
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * 位置：字典
     */
    @Column(name = "location", nullable = false )
    private String location;

    /**
     * 管理人 id
     */
    @Column(name = "manager_id")
    private Long managerId;

    /**
     * 设备状态：空闲、忙碌(前端先自己算)
     */
    @Column(name = "status", nullable = false )
    private String status;

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
     * 设备编码：后端生成
     */
    @Column(name = "code")
    private String code;

}
