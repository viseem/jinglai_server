package cn.iocoder.yudao.module.jl.entity.projectoutlog;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * example 空 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectOutLog")
@Table(name = "jl_project_out_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectOutLog extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 项目id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 报价id
     */
    @Column(name = "quotation_id", nullable = false )
    private Long quotationId;

    /**
     * 客户id
     */
    @Column(name = "customer_id", nullable = false )
    private Long customerId;

    /**
     * 内部数据评审json
     */
    @Column(name = "data_sign_json")
    private String dataSignJson;

    /**
     * 客户评价
     */
    @Column(name = "customer_comment")
    private String customerComment;

    /**
     * 客户打分
     */
    @Column(name = "customer_score_json")
    private String customerScoreJson;

    /**
     * 报价金额
     */
    @Column(name = "quotation_price")
    private BigDecimal quotationPrice;

    /**
     * 报价备注
     */
    @Column(name = "quotation_mark")
    private String quotationMark;

    /**
     * 已收金额
     */
    @Column(name = "received_price")
    private BigDecimal receivedPrice;

    /**
     * 已收备注
     */
    @Column(name = "received_mark")
    private String receivedMark;

    /**
     * 合同金额
     */
    @Column(name = "contract_price")
    private BigDecimal contractPrice;

    /**
     * 合同备注
     */
    @Column(name = "contract_mark")
    private String contractMark;

    /**
     * 材料成本
     */
    @Column(name = "supply_price")
    private BigDecimal supplyPrice;

    /**
     * 材料备注
     */
    @Column(name = "supply_mark")
    private String supplyMark;

    /**
     * 委外成本
     */
    @Column(name = "outsource_price")
    private BigDecimal outsourcePrice;

    /**
     * 委外备注
     */
    @Column(name = "outsource_mark")
    private String outsourceMark;

    /**
     * 客户签字链接
     */
    @Column(name = "customer_sign_img_url")
    private String customerSignImgUrl;

    /**
     * 客户签字时间
     */
    @Column(name = "custoamer_sign_time")
    private LocalDateTime custoamerSignTime;

    /**
     * 步骤的确认日志
     */
    @Column(name = "steps_json_log")
    private String stepsJsonLog;

    /**
     * 出库金额，理论上等于已收金额
     */
    @Column(name = "result_price")
    private BigDecimal resultPrice;

    /**
     * 出库金额备注
     */
    @Column(name = "result_mark")
    private String resultMark;

    /**
     * 数据链接
     */
    @Column(name = "data_link")
    private String dataLink;

    /**
     * 数据备注
     */
    @Column(name = "data_mark")
    private String dataMark;
}
