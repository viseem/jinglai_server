package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目采购单物流信息 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SupplierSimple")
@Table(name = "jl_project_supplier")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SupplierSimple extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 联系人
     */
    @Column(name = "contact_name")
    private String contactName;


    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 服务目录
     */
    @Column(name = "service_catalog")
    private String serviceCatalog;

    /**
     * 核心优势
     */
    @Column(name = "advantage")
    private String advantage;

    /**
     * 劣势
     */
    @Column(name = "disadvantage")
    private String disadvantage;

    /**
     * 公司负责人
     */
    @Column(name = "company_manager")
    private String companyManager;

    /**
     * 商务负责人
     */
    @Column(name = "business_manager")
    private String businessManager;

    /**
     * 技术负责人
     */
    @Column(name = "tech_manager")
    private String techManager;

    /**
     * 售中负责人
     */
    @Column(name = "manager")
    private String manager;

    /**
     * 售后负责人
     */
    @Column(name = "after_manager")
    private String afterManager;

    /**
     * 公司地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 公司地址
     */
    @Column(name = "tag_ids")
    private String tagIds;

    /**
     * 供应商分类类型
     */
    @Column(name = "cate_type")
    private String cateType;

    /**
     * 供应商分类类型
     */
    @Column(name = "good_at")
    private String goodAt;

}
