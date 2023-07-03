package cn.iocoder.yudao.module.jl.entity.projectfundlog;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFund;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 项目款项 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectFundLog")
@Table(name = "jl_project_fund_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectFundLog extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 收款金额
     */
    @Column(name = "price", nullable = false )
    private Long price;

    /**
     * 项目 id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 支付凭证上传地址
     */
    @Column(name = "receipt_url", nullable = false )
    private String receiptUrl;

    /**
     * 支付时间
     */
    @Column(name = "paid_time")
    private LocalDateTime paidTime;

    /**
     * 支付凭证文件名称
     */
    @Column(name = "receipt_name", nullable = false )
    private String receiptName;

    /**
     * 付款方
     */
    @Column(name = "payer", nullable = false )
    private String payer;

    /**
     * 收款方
     */
    @Column(name = "payee", nullable = false )
    private String payee;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 项目款项主表id
     */
    @Column(name = "project_fund_id", nullable = false )
    private Long projectFundId;

    @ManyToOne
    @JoinColumn(name="project_fund_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonBackReference
    private ProjectFund projectFund;

}
