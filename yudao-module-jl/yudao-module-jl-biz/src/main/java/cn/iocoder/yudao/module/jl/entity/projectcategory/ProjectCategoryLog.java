package cn.iocoder.yudao.module.jl.entity.projectcategory;

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
 * 实验名目的操作记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectCategoryLog")
@Table(name = "jl_project_category_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectCategoryLog extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 实验名目 id
     */
    @Column(name = "project_category_id", nullable = false )
    private Long projectCategoryId;

    /**
     * 文件名
     */
    @Column(name = "file_name", nullable = false )
    private String fileName;

    /**
     * 文件地址
     */
    @Column(name = "file_url")
    private String fileUrl;

    /**
     * 文件类型
     */
    @Column(name = "type")
    private byte[] type;

}
