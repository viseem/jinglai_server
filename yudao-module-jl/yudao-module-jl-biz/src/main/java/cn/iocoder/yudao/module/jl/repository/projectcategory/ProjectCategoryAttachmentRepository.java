package cn.iocoder.yudao.module.jl.repository.projectcategory;

import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryAttachment;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
* ProjectCategoryAttachmentRepository
*
*/
public interface ProjectCategoryAttachmentRepository extends JpaRepository<ProjectCategoryAttachment, Long>, JpaSpecificationExecutor<ProjectCategoryAttachment> {
    @Transactional
    @Modifying
    @Query("delete from ProjectCategoryAttachment p where p.projectCategoryId = ?1")
    int deleteByProjectCategoryId(Long projectCategoryId);
    @Transactional
    @Modifying
    @Query("delete from ProjectCategoryAttachment p where p.projectCategoryLogId = ?1")
    int deleteByProjectCategoryLogId(Long projectCategoryLogId);
    @Transactional
    @Modifying
    @Query("delete from ProjectCategoryAttachment p where p.projectCategoryId in ?1")
    int deleteByProjectCategoryIdIn(Collection<Long> projectCategoryIds);
}
