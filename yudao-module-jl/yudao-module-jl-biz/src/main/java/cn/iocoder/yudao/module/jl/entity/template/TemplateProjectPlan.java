package cn.iocoder.yudao.module.jl.entity.template;

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
 * 项目方案模板 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TemplateProjectPlan")
@Table(name = "jl_template_project_plan")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TemplateProjectPlan extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 模板名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 参考文件
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 参考文件地址
     */
    @Column(name = "file_url")
    private String fileUrl;

    /**
     * 类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 模板：方案富文本
     */
    @Column(name = "content", nullable = false )
    private String content;

}
