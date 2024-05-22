package cn.iocoder.yudao.module.jl.repository.productuser;

import cn.iocoder.yudao.module.jl.entity.productuser.ProductUserOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ProductUserRepository
*
*/
public interface ProductUserOnlyRepository extends JpaRepository<ProductUserOnly, Long>, JpaSpecificationExecutor<ProductUserOnly> {
    @Query("select p from ProductUserOnly p where p.userId = ?1")
    List<ProductUserOnly> findByUserId(Long userId);

}
