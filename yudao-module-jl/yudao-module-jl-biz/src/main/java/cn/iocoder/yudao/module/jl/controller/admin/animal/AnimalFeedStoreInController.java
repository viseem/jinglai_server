package cn.iocoder.yudao.module.jl.controller.admin.animal;

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

import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedStoreIn;
import cn.iocoder.yudao.module.jl.mapper.animal.AnimalFeedStoreInMapper;
import cn.iocoder.yudao.module.jl.service.animal.AnimalFeedStoreInService;

@Tag(name = "管理后台 - 动物饲养入库")
@RestController
@RequestMapping("/jl/animal-feed-store-in")
@Validated
public class AnimalFeedStoreInController {

    @Resource
    private AnimalFeedStoreInService animalFeedStoreInService;

    @Resource
    private AnimalFeedStoreInMapper animalFeedStoreInMapper;

    @PostMapping("/create")
    @Operation(summary = "创建动物饲养入库")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-store-in:create')")
    public CommonResult<Long> createAnimalFeedStoreIn(@Valid @RequestBody AnimalFeedStoreInCreateReqVO createReqVO) {
        return success(animalFeedStoreInService.createAnimalFeedStoreIn(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新动物饲养入库")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-store-in:update')")
    public CommonResult<Boolean> updateAnimalFeedStoreIn(@Valid @RequestBody AnimalFeedStoreInUpdateReqVO updateReqVO) {
        animalFeedStoreInService.updateAnimalFeedStoreIn(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除动物饲养入库")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-store-in:delete')")
    public CommonResult<Boolean> deleteAnimalFeedStoreIn(@RequestParam("id") Long id) {
        animalFeedStoreInService.deleteAnimalFeedStoreIn(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得动物饲养入库")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-store-in:query')")
    public CommonResult<AnimalFeedStoreInRespVO> getAnimalFeedStoreIn(@RequestParam("id") Long id) {
            Optional<AnimalFeedStoreIn> animalFeedStoreIn = animalFeedStoreInService.getAnimalFeedStoreIn(id);
        return success(animalFeedStoreIn.map(animalFeedStoreInMapper::toDto).orElseThrow(() -> exception(ANIMAL_FEED_STORE_IN_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得动物饲养入库列表")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-store-in:query')")
    public CommonResult<PageResult<AnimalFeedStoreInRespVO>> getAnimalFeedStoreInPage(@Valid AnimalFeedStoreInPageReqVO pageVO, @Valid AnimalFeedStoreInPageOrder orderV0) {
        PageResult<AnimalFeedStoreIn> pageResult = animalFeedStoreInService.getAnimalFeedStoreInPage(pageVO, orderV0);
        return success(animalFeedStoreInMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出动物饲养入库 Excel")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-store-in:export')")
    @OperateLog(type = EXPORT)
    public void exportAnimalFeedStoreInExcel(@Valid AnimalFeedStoreInExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<AnimalFeedStoreIn> list = animalFeedStoreInService.getAnimalFeedStoreInList(exportReqVO);
        // 导出 Excel
        List<AnimalFeedStoreInExcelVO> excelData = animalFeedStoreInMapper.toExcelList(list);
        ExcelUtils.write(response, "动物饲养入库.xls", "数据", AnimalFeedStoreInExcelVO.class, excelData);
    }

}
