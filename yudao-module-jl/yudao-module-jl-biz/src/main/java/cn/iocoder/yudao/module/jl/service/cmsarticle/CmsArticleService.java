package cn.iocoder.yudao.module.jl.service.cmsarticle;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.cmsarticle.vo.*;
import cn.iocoder.yudao.module.jl.entity.cmsarticle.CmsArticle;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 文章 Service 接口
 *
 */
public interface CmsArticleService {

    /**
     * 创建文章
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCmsArticle(@Valid CmsArticleCreateReqVO createReqVO);

    /**
     * 更新文章
     *
     * @param updateReqVO 更新信息
     */
    void updateCmsArticle(@Valid CmsArticleUpdateReqVO updateReqVO);

    /**
     * 删除文章
     *
     * @param id 编号
     */
    void deleteCmsArticle(Long id);

    /**
     * 获得文章
     *
     * @param id 编号
     * @return 文章
     */
    Optional<CmsArticle> getCmsArticle(Long id);

    /**
     * 获得文章列表
     *
     * @param ids 编号
     * @return 文章列表
     */
    List<CmsArticle> getCmsArticleList(Collection<Long> ids);

    /**
     * 获得文章分页
     *
     * @param pageReqVO 分页查询
     * @return 文章分页
     */
    PageResult<CmsArticle> getCmsArticlePage(CmsArticlePageReqVO pageReqVO, CmsArticlePageOrder orderV0);

    /**
     * 获得文章列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 文章列表
     */
    List<CmsArticle> getCmsArticleList(CmsArticleExportReqVO exportReqVO);

}
