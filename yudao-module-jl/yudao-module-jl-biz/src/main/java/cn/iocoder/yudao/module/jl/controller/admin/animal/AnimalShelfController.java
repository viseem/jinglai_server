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
import cn.iocoder.yudao.module.jl.entity.animal.AnimalShelf;
import cn.iocoder.yudao.module.jl.mapper.animal.AnimalShelfMapper;
import cn.iocoder.yudao.module.jl.service.animal.AnimalShelfService;

@Tag(name = "管理后台 - 动物饲养笼架")
@RestController
@RequestMapping("/jl/animal-shelf")
@Validated
public class AnimalShelfController {

    @Resource
    private AnimalShelfService animalShelfService;

    @Resource
    private AnimalShelfMapper animalShelfMapper;

    @PostMapping("/create")
    @Operation(summary = "创建动物饲养笼架")
    @PreAuthorize("@ss.hasPermission('jl:animal-shelf:create')")
    public CommonResult<Long> createAnimalShelf(@Valid @RequestBody AnimalShelfCreateReqVO createReqVO) {
        return success(animalShelfService.createAnimalShelf(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新动物饲养笼架")
    @PreAuthorize("@ss.hasPermission('jl:animal-shelf:update')")
    public CommonResult<Boolean> updateAnimalShelf(@Valid @RequestBody AnimalShelfUpdateReqVO updateReqVO) {
        animalShelfService.updateAnimalShelf(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除动物饲养笼架")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:animal-shelf:delete')")
    public CommonResult<Boolean> deleteAnimalShelf(@RequestParam("id") Long id) {
        animalShelfService.deleteAnimalShelf(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得动物饲养笼架")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:animal-shelf:query')")
    public CommonResult<AnimalShelfRespVO> getAnimalShelf(@RequestParam("id") Long id) {
            Optional<AnimalShelf> animalShelf = animalShelfService.getAnimalShelf(id);
        return success(animalShelf.map(animalShelfMapper::toDto).orElseThrow(() -> exception(ANIMAL_SHELF_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得动物饲养笼架列表")
    @PreAuthorize("@ss.hasPermission('jl:animal-shelf:query')")
    public CommonResult<PageResult<AnimalShelfRespVO>> getAnimalShelfPage(@Valid AnimalShelfPageReqVO pageVO, @Valid AnimalShelfPageOrder orderV0) {
        PageResult<AnimalShelf> pageResult = animalShelfService.getAnimalShelfPage(pageVO, orderV0);
        return success(animalShelfMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出动物饲养笼架 Excel")
    @PreAuthorize("@ss.hasPermission('jl:animal-shelf:export')")
    @OperateLog(type = EXPORT)
    public void exportAnimalShelfExcel(@Valid AnimalShelfExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<AnimalShelf> list = animalShelfService.getAnimalShelfList(exportReqVO);
        // 导出 Excel
        List<AnimalShelfExcelVO> excelData = animalShelfMapper.toExcelList(list);
        ExcelUtils.write(response, "动物饲养笼架.xls", "数据", AnimalShelfExcelVO.class, excelData);
    }

}
