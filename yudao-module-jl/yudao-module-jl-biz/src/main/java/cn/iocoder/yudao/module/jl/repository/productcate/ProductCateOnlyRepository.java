package cn.iocoder.yudao.module.jl.repository.productcate;

import cn.iocoder.yudao.module.jl.entity.productcate.ProductCate;
import cn.iocoder.yudao.module.jl.entity.productcate.ProductCateOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* ProductCateRepository
*
*/
public interface ProductCateOnlyRepository extends JpaRepository<ProductCateOnly, Long>, JpaSpecificationExecutor<ProductCateOnly> {
    @Query("select count(p) from ProductCateOnly p where p.parentId = ?1")
    long countByParentId(Long parentId);
    @Query("select p from ProductCateOnly p where p.parentId = ?1")
    List<ProductCateOnly> findByParentId(Long parentId);

}
