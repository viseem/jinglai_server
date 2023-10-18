package cn.iocoder.yudao.module.jl.mapper.cmsarticle;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.cmsarticle.vo.*;
import cn.iocoder.yudao.module.jl.entity.cmsarticle.CmsArticle;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CmsArticleMapper {
    CmsArticle toEntity(CmsArticleCreateReqVO dto);

    CmsArticle toEntity(CmsArticleUpdateReqVO dto);

    CmsArticleRespVO toDto(CmsArticle entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CmsArticle partialUpdate(CmsArticleUpdateReqVO dto, @MappingTarget CmsArticle entity);

    List<CmsArticleExcelVO> toExcelList(List<CmsArticle> list);

    PageResult<CmsArticleRespVO> toPage(PageResult<CmsArticle> page);
}