package cn.iocoder.yudao.module.jl.repository.projectcategory;

import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategorySupplier;
import org.springframework.data.jpa.repository.*;

/**
* ProjectCategorySupplierRepository
*
*/
public interface ProjectCategorySupplierRepository extends JpaRepository<ProjectCategorySupplier, Long>, JpaSpecificationExecutor<ProjectCategorySupplier> {

}
