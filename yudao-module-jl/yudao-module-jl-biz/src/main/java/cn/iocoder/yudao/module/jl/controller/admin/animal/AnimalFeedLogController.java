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
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedLog;
import cn.iocoder.yudao.module.jl.mapper.animal.AnimalFeedLogMapper;
import cn.iocoder.yudao.module.jl.service.animal.AnimalFeedLogService;

@Tag(name = "管理后台 - 动物饲养日志")
@RestController
@RequestMapping("/jl/animal-feed-log")
@Validated
public class AnimalFeedLogController {

    @Resource
    private AnimalFeedLogService animalFeedLogService;

    @Resource
    private AnimalFeedLogMapper animalFeedLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建动物饲养日志")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-log:create')")
    public CommonResult<Long> createAnimalFeedLog(@Valid @RequestBody AnimalFeedLogCreateReqVO createReqVO) {
        return success(animalFeedLogService.createAnimalFeedLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新动物饲养日志")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-log:update')")
    public CommonResult<Boolean> updateAnimalFeedLog(@Valid @RequestBody AnimalFeedLogUpdateReqVO updateReqVO) {
        animalFeedLogService.updateAnimalFeedLog(updateReqVO);
        return success(true);
    }

    @PutMapping("/save")
    @Operation(summary = "save更新动物饲养日志")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-log:update')")
    public CommonResult<Boolean> saveAnimalFeedLog(@Valid @RequestBody AnimalFeedLogSaveReqVO saveReqVO) {
        animalFeedLogService.saveAnimalFeedLog(saveReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除动物饲养日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-log:delete')")
    public CommonResult<Boolean> deleteAnimalFeedLog(@RequestParam("id") Long id) {
        animalFeedLogService.deleteAnimalFeedLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得动物饲养日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-log:query')")
    public CommonResult<AnimalFeedLogRespVO> getAnimalFeedLog(@RequestParam("id") Long id) {
            Optional<AnimalFeedLog> animalFeedLog = animalFeedLogService.getAnimalFeedLog(id);
        return success(animalFeedLog.map(animalFeedLogMapper::toDto).orElseThrow(() -> exception(ANIMAL_FEED_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得动物饲养日志列表")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-log:query')")
    public CommonResult<PageResult<AnimalFeedLogRespVO>> getAnimalFeedLogPage(@Valid AnimalFeedLogPageReqVO pageVO, @Valid AnimalFeedLogPageOrder orderV0) {
        PageResult<AnimalFeedLog> pageResult = animalFeedLogService.getAnimalFeedLogPage(pageVO, orderV0);
        return success(animalFeedLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出动物饲养日志 Excel")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-log:export')")
    @OperateLog(type = EXPORT)
    public void exportAnimalFeedLogExcel(@Valid AnimalFeedLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<AnimalFeedLog> list = animalFeedLogService.getAnimalFeedLogList(exportReqVO);
        // 导出 Excel
        List<AnimalFeedLogExcelVO> excelData = animalFeedLogMapper.toExcelList(list);
        ExcelUtils.write(response, "动物饲养日志.xls", "数据", AnimalFeedLogExcelVO.class, excelData);
    }

}
