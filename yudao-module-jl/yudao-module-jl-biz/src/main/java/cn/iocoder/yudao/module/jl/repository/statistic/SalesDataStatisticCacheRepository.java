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
     * 根据统计时间查询（查询当天的所有记录）
     */
    @Query(value = "select s from SalesDataStatisticCache s where FUNCTION('DATE', s.statisticDate) = FUNCTION('DATE', ?1) and s.deleted = false")
    List<SalesDataStatisticCache> findByStatisticDate(LocalDateTime statisticDate);

    /**
     * 根据统计时间和用户ID查询
     */
    @Query("select s from SalesDataStatisticCache s where FUNCTION('DATE', s.statisticDate) = FUNCTION('DATE', ?1) and s.userId = ?2 and s.deleted = false")
    SalesDataStatisticCache findByStatisticDateAndUserId(LocalDateTime statisticDate, Long userId);

    /**
     * 删除指定日期的缓存数据（删除当天的所有记录）
     */
    @Modifying
    @Transactional
    @Query("update SalesDataStatisticCache s set s.deleted = true where FUNCTION('DATE', s.statisticDate) = FUNCTION('DATE', ?1)")
    void deleteByStatisticDate(LocalDateTime statisticDate);
}

