package cn.iocoder.yudao.module.jl.controller.admin.commoncate;

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

import cn.iocoder.yudao.module.jl.controller.admin.commoncate.vo.*;
import cn.iocoder.yudao.module.jl.entity.commoncate.CommonCate;
import cn.iocoder.yudao.module.jl.mapper.commoncate.CommonCateMapper;
import cn.iocoder.yudao.module.jl.service.commoncate.CommonCateService;

@Tag(name = "管理后台 - 通用分类")
@RestController
@RequestMapping("/jl/common-cate")
@Validated
public class CommonCateController {

    @Resource
    private CommonCateService commonCateService;

    @Resource
    private CommonCateMapper commonCateMapper;

    @PostMapping("/create")
    @Operation(summary = "创建通用分类")
    @PreAuthorize("@ss.hasPermission('jl:common-cate:create')")
    public CommonResult<Long> createCommonCate(@Valid @RequestBody CommonCateCreateReqVO createReqVO) {
        return success(commonCateService.createCommonCate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新通用分类")
    @PreAuthorize("@ss.hasPermission('jl:common-cate:update')")
    public CommonResult<Boolean> updateCommonCate(@Valid @RequestBody CommonCateUpdateReqVO updateReqVO) {
        commonCateService.updateCommonCate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除通用分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:common-cate:delete')")
    public CommonResult<Boolean> deleteCommonCate(@RequestParam("id") Long id) {
        commonCateService.deleteCommonCate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得通用分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:common-cate:query')")
    public CommonResult<CommonCateRespVO> getCommonCate(@RequestParam("id") Long id) {
            Optional<CommonCate> commonCate = commonCateService.getCommonCate(id);
        return success(commonCate.map(commonCateMapper::toDto).orElseThrow(() -> exception(COMMON_CATE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得通用分类列表")
    @PreAuthorize("@ss.hasPermission('jl:common-cate:query')")
    public CommonResult<PageResult<CommonCateRespVO>> getCommonCatePage(@Valid CommonCatePageReqVO pageVO, @Valid CommonCatePageOrder orderV0) {
        PageResult<CommonCate> pageResult = commonCateService.getCommonCatePage(pageVO, orderV0);
        return success(commonCateMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出通用分类 Excel")
    @PreAuthorize("@ss.hasPermission('jl:common-cate:export')")
    @OperateLog(type = EXPORT)
    public void exportCommonCateExcel(@Valid CommonCateExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CommonCate> list = commonCateService.getCommonCateList(exportReqVO);
        // 导出 Excel
        List<CommonCateExcelVO> excelData = commonCateMapper.toExcelList(list);
        ExcelUtils.write(response, "通用分类.xls", "数据", CommonCateExcelVO.class, excelData);
    }

}
