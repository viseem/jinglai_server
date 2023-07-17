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
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedOrder;
import cn.iocoder.yudao.module.jl.mapper.animal.AnimalFeedOrderMapper;
import cn.iocoder.yudao.module.jl.service.animal.AnimalFeedOrderService;

@Tag(name = "管理后台 - 动物饲养申请单")
@RestController
@RequestMapping("/jl/animal-feed-order")
@Validated
public class AnimalFeedOrderController {

    @Resource
    private AnimalFeedOrderService animalFeedOrderService;

    @Resource
    private AnimalFeedOrderMapper animalFeedOrderMapper;

    @PostMapping("/create")
    @Operation(summary = "创建动物饲养申请单")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-order:create')")
    public CommonResult<Long> createAnimalFeedOrder(@Valid @RequestBody AnimalFeedOrderCreateReqVO createReqVO) {
        return success(animalFeedOrderService.createAnimalFeedOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新动物饲养申请单")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-order:update')")
    public CommonResult<Boolean> updateAnimalFeedOrder(@Valid @RequestBody AnimalFeedOrderUpdateReqVO updateReqVO) {
        animalFeedOrderService.updateAnimalFeedOrder(updateReqVO);
        return success(true);
    }

    @PutMapping("/save")
    @Operation(summary = "更新动物饲养申请单")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-order:update')")
    public CommonResult<Boolean> saveAnimalFeedOrder(@Valid @RequestBody AnimalFeedOrderSaveReqVO saveReqVO) {
        animalFeedOrderService.saveAnimalFeedOrder(saveReqVO);
        return success(true);
    }

    @PutMapping("/store-in")
    @Operation(summary = "更新动物饲养申请单")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-order:update')")
    public CommonResult<Boolean> storeAnimalFeedOrder(@Valid @RequestBody AnimalFeedOrderStoreReqVO storeReqVO) {
        animalFeedOrderService.storeAnimalFeedOrder(storeReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除动物饲养申请单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-order:delete')")
    public CommonResult<Boolean> deleteAnimalFeedOrder(@RequestParam("id") Long id) {
        animalFeedOrderService.deleteAnimalFeedOrder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得动物饲养申请单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-order:query')")
    public CommonResult<AnimalFeedOrderRespVO> getAnimalFeedOrder(@RequestParam("id") Long id) {
            Optional<AnimalFeedOrder> animalFeedOrder = animalFeedOrderService.getAnimalFeedOrder(id);
        return success(animalFeedOrder.map(animalFeedOrderMapper::toDto).orElseThrow(() -> exception(ANIMAL_FEED_ORDER_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得动物饲养申请单列表")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-order:query')")
    public CommonResult<PageResult<AnimalFeedOrderRespVO>> getAnimalFeedOrderPage(@Valid AnimalFeedOrderPageReqVO pageVO, @Valid AnimalFeedOrderPageOrder orderV0) {
        PageResult<AnimalFeedOrder> pageResult = animalFeedOrderService.getAnimalFeedOrderPage(pageVO, orderV0);
        return success(animalFeedOrderMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出动物饲养申请单 Excel")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-order:export')")
    @OperateLog(type = EXPORT)
    public void exportAnimalFeedOrderExcel(@Valid AnimalFeedOrderExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<AnimalFeedOrder> list = animalFeedOrderService.getAnimalFeedOrderList(exportReqVO);
        // 导出 Excel
        List<AnimalFeedOrderExcelVO> excelData = animalFeedOrderMapper.toExcelList(list);
        ExcelUtils.write(response, "动物饲养申请单.xls", "数据", AnimalFeedOrderExcelVO.class, excelData);
    }

}
