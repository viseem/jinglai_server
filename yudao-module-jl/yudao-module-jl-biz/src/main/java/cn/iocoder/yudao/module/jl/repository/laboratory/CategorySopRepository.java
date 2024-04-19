package cn.iocoder.yudao.module.jl.repository.laboratory;

import cn.iocoder.yudao.module.jl.entity.laboratory.CategorySop;
import org.springframework.data.jpa.repository.*;

import javax.transaction.Transactional;
import java.util.List;

/**
* CategorySopRepository
*
*/
public interface CategorySopRepository extends JpaRepository<CategorySop, Long>, JpaSpecificationExecutor<CategorySop> {
    @Query("select c from CategorySop c where c.categoryId = ?1")
    List<CategorySop> findByCategoryId(Long categoryId);
    @Modifying
    @Transactional
    @Query("delete from CategorySop sop where sop.categoryId = ?1")
    void deleteByCategoryId(Long categoryId);

}
