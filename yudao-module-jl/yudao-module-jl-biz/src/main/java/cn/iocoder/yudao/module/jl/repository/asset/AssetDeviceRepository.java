package cn.iocoder.yudao.module.jl.repository.asset;

import cn.iocoder.yudao.module.jl.entity.asset.AssetDevice;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
* AssetDeviceRepository
*
*/
public interface AssetDeviceRepository extends JpaRepository<AssetDevice, Long>, JpaSpecificationExecutor<AssetDevice> {
    @Transactional
    @Modifying
    @Query("update AssetDevice a set a.cateId = ?1 where a.id in ?2")
    int updateCateIdByIdIn(Long cateId, Collection<Long> ids);
    @Transactional
    @Modifying
    @Query("update AssetDevice a set a.sn = ?1 where a.id = ?2")
    int updateSnById(String sn, Long id);

    @Transactional
    @Modifying
    @Query("update AssetDevice a set a.code = ?1 where a.id = ?2")
    int updateCodeById(String code, Long id);
    @Query("select a from AssetDevice a where a.sn = ?1")
    AssetDevice findBySn(String sn);

    @Query("select a from AssetDevice a where a.code = ?1")
    AssetDevice findByCode(String code);

}
