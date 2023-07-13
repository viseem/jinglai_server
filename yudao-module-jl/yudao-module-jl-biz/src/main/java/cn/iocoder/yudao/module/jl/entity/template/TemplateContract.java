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
 * 合同模板 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TemplateContract")
@Table(name = "jl_template_contract")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TemplateContract extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 文件名
     */
    @Column(name = "file_name", nullable = false )
    private String fileName;

    /**
     * 文件地址
     */
    @Column(name = "file_url", nullable = false )
    private String fileUrl;

    /**
     * 关键备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 合同类型：项目合同、饲养合同
     */
    @Column(name = "type")
    private String type;

    /**
     * 合同用途
     */
    @Column(name = "use_way")
    private String useWay;

    /**
     * 合同名称
     */
    @Column(name = "name", nullable = false )
    private String name;

}
