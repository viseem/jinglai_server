package cn.iocoder.yudao.module.jl.repository.productdevice;

import cn.iocoder.yudao.module.jl.entity.productdevice.ProductDevice;
import org.springframework.data.jpa.repository.*;

/**
* ProductDeviceRepository
*
*/
public interface ProductDeviceRepository extends JpaRepository<ProductDevice, Long>, JpaSpecificationExecutor<ProductDevice> {

}
