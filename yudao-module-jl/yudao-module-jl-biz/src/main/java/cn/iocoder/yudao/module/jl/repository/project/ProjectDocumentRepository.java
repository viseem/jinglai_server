package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectDocument;
import org.springframework.data.jpa.repository.*;

/**
* ProjectDocumentRepository
*
*/
public interface ProjectDocumentRepository extends JpaRepository<ProjectDocument, Long>, JpaSpecificationExecutor<ProjectDocument> {

}
