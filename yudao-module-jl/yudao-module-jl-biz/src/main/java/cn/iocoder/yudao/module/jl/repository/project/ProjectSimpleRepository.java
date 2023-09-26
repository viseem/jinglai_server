package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* ProjectRepository
*
*/
public interface ProjectSimpleRepository extends JpaRepository<ProjectSimple, Long>, JpaSpecificationExecutor<ProjectSimple> {



}
