package cn.iocoder.yudao.module.jl.repository.asset;

import cn.iocoder.yudao.module.jl.entity.asset.AssetDeviceLog;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDateTime;

/**
* AssetDeviceLogRepository
*
*/
public interface AssetDeviceLogRepository extends JpaRepository<AssetDeviceLog, Long>, JpaSpecificationExecutor<AssetDeviceLog> {
    @Query("select a from AssetDeviceLog a where a.deviceId = ?1 and a.startDate < ?2 and a.endDate > ?2")
    AssetDeviceLog findByDeviceIdAndStartDateLessThanAndEndDateGreaterThan(Long deviceId,LocalDateTime CurrentDateTime);

    @Query("SELECT COUNT(e) FROM AssetDeviceLog e " +
            "WHERE e.deviceId = ?3 " +
            "AND ((e.startDate < ?2 AND e.endDate > ?1) " +
            "OR (e.startDate < ?1 AND e.endDate > ?2))")
    Long countOverlappingTimeRanges(LocalDateTime startDate,LocalDateTime endDate,Long deviceId);

}
