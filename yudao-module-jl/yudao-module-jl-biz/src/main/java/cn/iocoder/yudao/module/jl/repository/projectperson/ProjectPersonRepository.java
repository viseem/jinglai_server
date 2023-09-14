package cn.iocoder.yudao.module.jl.repository.projectperson;

import cn.iocoder.yudao.module.jl.entity.projectperson.ProjectPerson;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ProjectPersonRepository
*
*/
public interface ProjectPersonRepository extends JpaRepository<ProjectPerson, Long>, JpaSpecificationExecutor<ProjectPerson> {
    @Query("select p from ProjectPerson p where p.projectId = ?1")
    List<ProjectPerson> findByProjectId(Long projectId);
    @Transactional
    @Modifying
    @Query("delete from ProjectPerson p where p.projectId = ?1")
    int deleteByProjectId(Long projectId);

}
