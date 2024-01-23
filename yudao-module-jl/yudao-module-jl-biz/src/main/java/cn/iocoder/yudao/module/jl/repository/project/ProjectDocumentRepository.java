package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProjectDocumentRepository
*
*/
public interface ProjectDocumentRepository extends JpaRepository<ProjectDocument, Long>, JpaSpecificationExecutor<ProjectDocument> {
    @Transactional
    @Modifying
    @Query("delete from ProjectDocument p where p.type = ?1 and p.projectId = ?2")
    int deleteByTypeAndProjectId(String type, Long projectId);

}
