package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.Supplier;
import org.springframework.data.jpa.repository.*;

/**
* SupplierRepository
*
*/
public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {
    @Query("select s from Supplier s where s.name = ?1")
    Supplier findByName(String name);

}
