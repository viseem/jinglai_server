package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 项目采购单物流信息 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProcurementShipment")
@Table(name = "jl_project_procurement_shipment")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProcurementShipment extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 采购单id
     */
    @Column(name = "procurement_id", nullable = false )
    private Long procurementId;

    /**
     * 物流单号
     */
    @Column(name = "shipment_number")
    private String shipmentNumber;

    /**
     * 附件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 附件地址
     */
    @Column(name = "file_url")
    private String fileUrl;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 预计送达日期
     */
    @Column(name = "expect_arrival_time")
    private String expectArrivalTime;

}
