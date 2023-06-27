package cn.iocoder.yudao.module.jl.repository.projectcategory;

import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryAttachment;
import org.springframework.data.jpa.repository.*;

/**
* ProjectCategoryAttachmentRepository
*
*/
public interface ProjectCategoryAttachmentRepository extends JpaRepository<ProjectCategoryAttachment, Long>, JpaSpecificationExecutor<ProjectCategoryAttachment> {

}
