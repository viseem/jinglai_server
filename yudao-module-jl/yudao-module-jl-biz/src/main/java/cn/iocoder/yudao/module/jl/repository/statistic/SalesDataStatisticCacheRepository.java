package cn.iocoder.yudao.module.jl.repository.statistic;

import cn.iocoder.yudao.module.jl.entity.statistic.SalesDataStatisticCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 销售数据统计缓存 Repository
 */
public interface SalesDataStatisticCacheRepository extends JpaRepository<SalesDataStatisticCache, Long>, JpaSpecificationExecutor<SalesDataStatisticCache> {

    /**
     * 根据时间范围类型查询缓存
     */
    @Query("select s from SalesDataStatisticCache s where s.timeRangeType = ?1 and s.deleted = false")
    List<SalesDataStatisticCache> findByTimeRangeType(String timeRangeType);

    /**
     * 根据时间范围类型和用户ID查询缓存
     */
    @Query("select s from SalesDataStatisticCache s where s.timeRangeType = ?1 and s.userId = ?2 and s.deleted = false")
    SalesDataStatisticCache findByTimeRangeTypeAndUserId(String timeRangeType, Long userId);

    /**
     * 根据精确时间范围查询缓存
     */
    @Query("select s from SalesDataStatisticCache s where s.startTime = ?1 and s.endTime = ?2 and s.deleted = false")
    List<SalesDataStatisticCache> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据精确时间范围和用户ID查询缓存
     */
    @Query("select s from SalesDataStatisticCache s where s.startTime = ?1 and s.endTime = ?2 and s.userId = ?3 and s.deleted = false")
    SalesDataStatisticCache findByTimeRangeAndUserId(LocalDateTime startTime, LocalDateTime endTime, Long userId);

    /**
     * 删除指定时间范围类型的缓存数据
     */
    @Modifying
    @Transactional
    @Query("update SalesDataStatisticCache s set s.deleted = true where s.timeRangeType = ?1")
    void deleteByTimeRangeType(String timeRangeType);

    /**
     * 删除指定精确时间范围的缓存数据
     */
    @Modifying
    @Transactional
    @Query("update SalesDataStatisticCache s set s.deleted = true where s.startTime = ?1 and s.endTime = ?2")
    void deleteByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 查询过期的缓存数据（缓存更新时间早于指定时间）
     */
    @Query("select s from SalesDataStatisticCache s where s.cacheUpdateTime < ?1 and s.deleted = false")
    List<SalesDataStatisticCache> findExpiredCache(LocalDateTime expiredTime);
}

