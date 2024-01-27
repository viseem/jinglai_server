package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

/**
* ProjectConstractRepository
*
*/
public interface ProjectConstractOnlyRepository extends JpaRepository<ProjectConstractOnly, Long>, JpaSpecificationExecutor<ProjectConstractOnly> {
    List<ProjectConstractOnly> findByCreatorInAndStatus(Collection<Long> creators, String status);


}
