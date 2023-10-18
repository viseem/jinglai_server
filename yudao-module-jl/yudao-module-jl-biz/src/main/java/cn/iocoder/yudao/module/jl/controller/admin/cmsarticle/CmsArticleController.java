package cn.iocoder.yudao.module.jl.controller.admin.cmsarticle;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.jl.controller.admin.cmsarticle.vo.*;
import cn.iocoder.yudao.module.jl.entity.cmsarticle.CmsArticle;
import cn.iocoder.yudao.module.jl.mapper.cmsarticle.CmsArticleMapper;
import cn.iocoder.yudao.module.jl.service.cmsarticle.CmsArticleService;

@Tag(name = "管理后台 - 文章")
@RestController
@RequestMapping("/jl/cms-article")
@Validated
public class CmsArticleController {

    @Resource
    private CmsArticleService cmsArticleService;

    @Resource
    private CmsArticleMapper cmsArticleMapper;

    @PostMapping("/create")
    @Operation(summary = "创建文章")
    @PreAuthorize("@ss.hasPermission('jl:cms-article:create')")
    public CommonResult<Long> createCmsArticle(@Valid @RequestBody CmsArticleCreateReqVO createReqVO) {
        return success(cmsArticleService.createCmsArticle(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新文章")
    @PreAuthorize("@ss.hasPermission('jl:cms-article:update')")
    public CommonResult<Boolean> updateCmsArticle(@Valid @RequestBody CmsArticleUpdateReqVO updateReqVO) {
        cmsArticleService.updateCmsArticle(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除文章")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:cms-article:delete')")
    public CommonResult<Boolean> deleteCmsArticle(@RequestParam("id") Long id) {
        cmsArticleService.deleteCmsArticle(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得文章")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:cms-article:query')")
    public CommonResult<CmsArticleRespVO> getCmsArticle(@RequestParam("id") Long id) {
            Optional<CmsArticle> cmsArticle = cmsArticleService.getCmsArticle(id);
        return success(cmsArticle.map(cmsArticleMapper::toDto).orElseThrow(() -> exception(CMS_ARTICLE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得文章列表")
    @PreAuthorize("@ss.hasPermission('jl:cms-article:query')")
    public CommonResult<PageResult<CmsArticleRespVO>> getCmsArticlePage(@Valid CmsArticlePageReqVO pageVO, @Valid CmsArticlePageOrder orderV0) {
        PageResult<CmsArticle> pageResult = cmsArticleService.getCmsArticlePage(pageVO, orderV0);
        return success(cmsArticleMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出文章 Excel")
    @PreAuthorize("@ss.hasPermission('jl:cms-article:export')")
    @OperateLog(type = EXPORT)
    public void exportCmsArticleExcel(@Valid CmsArticleExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CmsArticle> list = cmsArticleService.getCmsArticleList(exportReqVO);
        // 导出 Excel
        List<CmsArticleExcelVO> excelData = cmsArticleMapper.toExcelList(list);
        ExcelUtils.write(response, "文章.xls", "数据", CmsArticleExcelVO.class, excelData);
    }

}
