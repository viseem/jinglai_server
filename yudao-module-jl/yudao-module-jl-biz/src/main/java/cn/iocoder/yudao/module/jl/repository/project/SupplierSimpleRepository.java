package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.Supplier;
import cn.iocoder.yudao.module.jl.entity.project.SupplierSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
* SupplierRepository
*
*/
public interface SupplierSimpleRepository extends JpaRepository<SupplierSimple, Long>, JpaSpecificationExecutor<SupplierSimple> {
    @Query("select s from SupplierSimple s where s.name = ?1")
    Supplier findByName(String name);

}
