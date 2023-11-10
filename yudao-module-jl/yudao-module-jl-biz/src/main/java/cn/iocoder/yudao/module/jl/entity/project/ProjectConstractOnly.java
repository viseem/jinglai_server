package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 项目合同 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectConstractOnly")
@Table(name = "jl_project_constract")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectConstractOnly extends BaseEntity {

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
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 合同名字
     */
    @Column(name = "name")
    private String name;

    /**
     * 公司主体名称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;


    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 合同文件 URL
     */
    @Column(name = "file_url", nullable = false )
    private String fileUrl;

    /**
     * 盖章合同文件 URL
     */
    @Column(name = "stamp_file_url", nullable = false)
    private String stampFileUrl;

    /**
     * 盖章合同文件 NAME
     */
    @Column(name = "stamp_file_name", nullable = false)
    private String stampFileName;

    /**
     * 合同状态：起效、失效、其它
     */
    @Column(name = "status")
    private String status;

    /**
     * 合同类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 合同金额
     */
    @Column(name = "price")
    private Long price;

    /**
     * 已收金额
     */
    @Column(name = "received_price")
    private Long receivedPrice;

    /**
     * 已开票
     */
    @Column(name = "invoiced_price")
    private Long invoicedPrice;
    /**
     * 结算金额
     */
    @Column(name = "real_price")
    private Integer realPrice;

    /**
     * 签订销售人员
     */
    @Column(name = "sales_id")
    private Long salesId;

    /**
     * 合同编号
     */
    @Column(name = "sn")
    private String sn;

    /**
     * 合同文件名
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 是否出库
     */
    @Column(name = "is_outed")
    private Integer isOuted;
}
