package cn.iocoder.yudao.module.jl.repository.asset;

import cn.iocoder.yudao.module.jl.entity.asset.AssetDeviceLog;
import org.springframework.data.jpa.repository.*;

/**
* AssetDeviceLogRepository
*
*/
public interface AssetDeviceLogRepository extends JpaRepository<AssetDeviceLog, Long>, JpaSpecificationExecutor<AssetDeviceLog> {

}
