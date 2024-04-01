package cn.iocoder.yudao.module.jl.repository.productdevice;

import cn.iocoder.yudao.module.jl.entity.productdevice.ProductDevice;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProductDeviceRepository
*
*/
public interface ProductDeviceRepository extends JpaRepository<ProductDevice, Long>, JpaSpecificationExecutor<ProductDevice> {
    @Transactional
    @Modifying
    @Query("delete from ProductDevice p where p.productId = ?1")
    int deleteByProductId(Long productId);

}
