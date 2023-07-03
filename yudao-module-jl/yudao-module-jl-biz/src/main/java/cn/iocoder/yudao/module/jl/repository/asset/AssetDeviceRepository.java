package cn.iocoder.yudao.module.jl.repository.asset;

import cn.iocoder.yudao.module.jl.entity.asset.AssetDevice;
import org.springframework.data.jpa.repository.*;

/**
* AssetDeviceRepository
*
*/
public interface AssetDeviceRepository extends JpaRepository<AssetDevice, Long>, JpaSpecificationExecutor<AssetDevice> {

}
