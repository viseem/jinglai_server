package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.SalesleadDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* SalesleadRepository
*
*/
public interface SalesleadDetailRepository extends JpaRepository<SalesleadDetail, Long>, JpaSpecificationExecutor<SalesleadDetail> {

}
