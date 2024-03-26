package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategorySimple;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ProjectCategoryRepository
*
*/
public interface ProjectCategoryOnlyRepository extends JpaRepository<ProjectCategoryOnly, Long>, JpaSpecificationExecutor<ProjectCategoryOnly> {
    @Query("select p from ProjectCategoryOnly p where p.parentId = ?1")
    List<ProjectCategoryOnly> findByParentId(Long parentId);

}
