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
import cn.iocoder.yudao.module.jl.entity.animal.AnimalBox;
import cn.iocoder.yudao.module.jl.mapper.animal.AnimalBoxMapper;
import cn.iocoder.yudao.module.jl.service.animal.AnimalBoxService;

@Tag(name = "管理后台 - 动物笼位")
@RestController
@RequestMapping("/jl/animal-box")
@Validated
public class AnimalBoxController {

    @Resource
    private AnimalBoxService animalBoxService;

    @Resource
    private AnimalBoxMapper animalBoxMapper;

    @PostMapping("/create")
    @Operation(summary = "创建动物笼位")
    @PreAuthorize("@ss.hasPermission('jl:animal-box:create')")
    public CommonResult<Long> createAnimalBox(@Valid @RequestBody AnimalBoxCreateReqVO createReqVO) {
        return success(animalBoxService.createAnimalBox(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新动物笼位")
    @PreAuthorize("@ss.hasPermission('jl:animal-box:update')")
    public CommonResult<Boolean> updateAnimalBox(@Valid @RequestBody AnimalBoxUpdateReqVO updateReqVO) {
        animalBoxService.updateAnimalBox(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除动物笼位")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:animal-box:delete')")
    public CommonResult<Boolean> deleteAnimalBox(@RequestParam("id") Long id) {
        animalBoxService.deleteAnimalBox(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得动物笼位")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:animal-box:query')")
    public CommonResult<AnimalBoxRespVO> getAnimalBox(@RequestParam("id") Long id) {
            Optional<AnimalBox> animalBox = animalBoxService.getAnimalBox(id);
        return success(animalBox.map(animalBoxMapper::toDto).orElseThrow(() -> exception(ANIMAL_BOX_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得动物笼位列表")
    @PreAuthorize("@ss.hasPermission('jl:animal-box:query')")
    public CommonResult<PageResult<AnimalBoxRespVO>> getAnimalBoxPage(@Valid AnimalBoxPageReqVO pageVO, @Valid AnimalBoxPageOrder orderV0) {
        PageResult<AnimalBox> pageResult = animalBoxService.getAnimalBoxPage(pageVO, orderV0);
        return success(animalBoxMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出动物笼位 Excel")
    @PreAuthorize("@ss.hasPermission('jl:animal-box:export')")
    @OperateLog(type = EXPORT)
    public void exportAnimalBoxExcel(@Valid AnimalBoxExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<AnimalBox> list = animalBoxService.getAnimalBoxList(exportReqVO);
        // 导出 Excel
        List<AnimalBoxExcelVO> excelData = animalBoxMapper.toExcelList(list);
        ExcelUtils.write(response, "动物笼位.xls", "数据", AnimalBoxExcelVO.class, excelData);
    }

}
