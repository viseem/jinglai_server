package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.CompanySupply;
import org.springframework.data.jpa.repository.*;

/**
* CompanySupplyRepository
*
*/
public interface CompanySupplyRepository extends JpaRepository<CompanySupply, Long>, JpaSpecificationExecutor<CompanySupply> {

}
