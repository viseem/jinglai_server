package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.Procurement;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ProcurementRepository
*
*/
public interface ProcurementOnlyRepository extends JpaRepository<ProcurementOnly, Long>, JpaSpecificationExecutor<ProcurementOnly> {


}
