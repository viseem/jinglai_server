package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目款项 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectFundOnly")
@Table(name = "jl_project_fund")
public class ProjectFundOnly extends BaseEntity {

    /**
     * 项目 id
     */
    @Column(name = "contract_id", nullable = false)
    private Long contractId;


    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 资金额度
     */
    @Column(name = "price", nullable = false)
    private Integer price;

    /**
     * 已收款项
     */
    private Integer receivedPrice = 0;

    /**
     * 项目 id
     */
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    /**
     * 客户 id
     */
    @Column(name = "customer_id", nullable = false)
    private Long customerId;


    /**
     * 支付状态(未支付，部分支付，完全支付)
     */
    @Column(name = "status")
    private String status;

    /**
     * 支付时间
     */
    @Column(name = "paid_time")
    private LocalDateTime paidTime;

    /**
     * 支付的截止时间
     */
    @Column(name = "deadline")
    private LocalDate deadline;

    /**
     * 支付凭证上传地址
     */
    @Column(name = "receipt_url")
    private String receiptUrl;

    /**
     * 支付凭证文件名称
     */
    @Column(name = "receipt_name")
    private String receiptName;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 付款方
     */
    @Column(name = "payer")
    private String payer;

    /**
     * 收款方
     */
    @Column(name = "payee")
    private String payee;

    /**
     * 收款备注
     */
    @Column(name = "pay_mark")
    private String payMark;


    @Transient
    private ProjectFundLog latestFundLog;

}
