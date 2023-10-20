package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ProjectConstractRepository
*
*/
public interface ProjectConstractOnlyRepository extends JpaRepository<ProjectConstractOnly, Long>, JpaSpecificationExecutor<ProjectConstractOnly> {

}
