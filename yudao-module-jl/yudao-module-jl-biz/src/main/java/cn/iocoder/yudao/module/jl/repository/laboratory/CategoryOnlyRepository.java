package cn.iocoder.yudao.module.jl.repository.laboratory;

import cn.iocoder.yudao.module.jl.entity.laboratory.Category;
import cn.iocoder.yudao.module.jl.entity.laboratory.CategoryOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* CategoryRepository
*
*/
public interface CategoryOnlyRepository extends JpaRepository<CategoryOnly, Long>, JpaSpecificationExecutor<CategoryOnly> {

}
