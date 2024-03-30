package cn.iocoder.yudao.module.jl.entity.productdevice;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDevice;
import cn.iocoder.yudao.module.jl.entity.product.Product;
import cn.iocoder.yudao.module.jl.entity.product.ProductOnly;
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

/**
 * 产品库设备 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProductDevice")
@Table(name = "jl_product_device")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductDevice extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 产品
     */
    @Column(name = "product_id", nullable = false )
    private Long productId;

    /**
     * 设备
     */
    @Column(name = "device_id", nullable = false )
    private Long deviceId;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 级联产品
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductOnly product;

    /**
     * 级联设备
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "device_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AssetDevice device;
}
