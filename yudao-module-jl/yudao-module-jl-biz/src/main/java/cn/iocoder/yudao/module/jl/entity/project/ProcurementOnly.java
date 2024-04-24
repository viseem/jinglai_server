package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目采购单申请 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProcurementOnly")
@Table(name = "jl_project_procurement")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProcurementOnly extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 实验室id
     */
    @Column(name = "lab_id", nullable = false )
    private Long labId;

    /**
     * 采购类型 项目 实验室 行政
     */
    @Column(name = "procurement_type", nullable = false )
    private Integer procurementType;


    /**
     * 项目 id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;



    /**
     * 实验名目库的名目 id
     */
    @Column(name = "project_category_id")
    private Long projectCategoryId;

    /**
     * 采购单号
     */
    @Column(name = "code")
    private String code;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 采购人员回复
     */
    @Column(name = "reply")
    private String reply;

    /**
     * 采购发起时间
     */
    @Column(name = "start_date")
    private String startDate;

    /**
     * 签收陪审人
     */
    @Column(name = "check_user_id")
    private Long checkUserId;

    /**
     * 收货地址
     */
    @Column(name = "address")
    private String address;
    /**
     * 附件地址
     */
    @Column(name = "file_url")
    private String fileUrl;
    /**
     * 附件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 是否有要签收的
     */
    @Column(name = "wait_check_in")
    private Boolean waitCheckIn;

    /**
     * 是否有要入库的
     */
    @Column(name = "wait_store_in")
    private Boolean waitStoreIn;

    @Column(name = "shipment_codes")
    private String shipmentCodes;

    @Column(name = "schedule_id")
    private Long scheduleId;

    /**
     * 收货人id
     */
    @Column(name = "receiver_user_id")
    private String receiverUserId;

    /**
     * 收货类型
     */
    @Column(name = "receiver_type")
    private String receiverType;

    /**
     * 收货人姓名
     */
    @Column(name = "receiver_name")
    private String receiverName;

    /**
     * 收货人电话
     */
    @Column(name = "receiver_phone")
    private String receiverPhone;

    /**
     * 附件列表
     */
    @Column(name = "attachments")
    private String attachments;

    /**
     * 流程实例id
     */
    @Column(name = "process_instance_id")
    private String processInstanceId;

}
