package cn.iocoder.yudao.module.jl.repository.cmsarticle;

import cn.iocoder.yudao.module.jl.entity.cmsarticle.CmsArticle;
import org.springframework.data.jpa.repository.*;

/**
* CmsArticleRepository
*
*/
public interface CmsArticleRepository extends JpaRepository<CmsArticle, Long>, JpaSpecificationExecutor<CmsArticle> {

}
