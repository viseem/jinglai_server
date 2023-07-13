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
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 项目设备 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectDevice")
@Table(name = "jl_project_device")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectDevice extends BaseEntity {

    /**
     * 岗位ID
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

    /**
     * 设备名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 开始时间
     */
    @Column(name = "start_date")
    private LocalDateTime startDate;

    /**
     * 结束时间
     */
    @Column(name = "end_date")
    private LocalDateTime endDate;

}
