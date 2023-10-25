package cn.iocoder.yudao.module.jl.entity.reminder;

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
 * 晶莱提醒事项 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Reminder")
@Table(name = "jl_reminder")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Reminder extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 合同id
     */
    @Column(name = "contract_id")
    private Long contractId;

    /**
     * 项目id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 内容
     */
    @Column(name = "content", nullable = false )
    private String content;

    /**
     * 截止日期
     */
    @Column(name = "deadline", nullable = false )
    private String deadline;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

}
