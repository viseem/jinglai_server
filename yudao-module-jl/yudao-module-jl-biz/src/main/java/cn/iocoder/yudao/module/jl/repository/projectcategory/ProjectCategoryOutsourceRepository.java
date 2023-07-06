package cn.iocoder.yudao.module.jl.repository.projectcategory;

import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryOutsource;
import org.springframework.data.jpa.repository.*;

/**
* ProjectCategoryOutsourceRepository
*
*/
public interface ProjectCategoryOutsourceRepository extends JpaRepository<ProjectCategoryOutsource, Long>, JpaSpecificationExecutor<ProjectCategoryOutsource> {

}
