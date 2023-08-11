package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

/**
 * 项目安排单 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectScheduleOnly")
@Table(name = "jl_project_schedule")
public class ProjectScheduleOnly extends BaseEntity {

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
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 报价单的名字
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 状态, 待审批、已审批
     */
    @Column(name = "status", nullable = false )
    private String status;

    /**
     * 方案状态
     */
    @Column(name = "plan_text", nullable = false )
    private String planText;

}
