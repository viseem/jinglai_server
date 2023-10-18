package cn.iocoder.yudao.module.jl.entity.cmsarticle;

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
 * 文章 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CmsArticle")
@Table(name = "jl_cms_article")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CmsArticle extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 标题
     */
    @Column(name = "title", nullable = false )
    private String title;

    /**
     * 副标题
     */
    @Column(name = "sub_title")
    private String subTitle;

    /**
     * 内容
     */
    @Column(name = "content", nullable = false )
    private String content;

    /**
     * 浏览次数
     */
    @Column(name = "look_count")
    private Integer lookCount;

    /**
     * 排序
     */
    @Column(name = "sort", nullable = false )
    private Integer sort;

    /**
     * 封面
     */
    @Column(name = "cover_url", nullable = false )
    private String coverUrl;
}
