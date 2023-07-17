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
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedCard;
import cn.iocoder.yudao.module.jl.mapper.animal.AnimalFeedCardMapper;
import cn.iocoder.yudao.module.jl.service.animal.AnimalFeedCardService;

@Tag(name = "管理后台 - 动物饲养鼠牌")
@RestController
@RequestMapping("/jl/animal-feed-card")
@Validated
public class AnimalFeedCardController {

    @Resource
    private AnimalFeedCardService animalFeedCardService;

    @Resource
    private AnimalFeedCardMapper animalFeedCardMapper;

    @PostMapping("/create")
    @Operation(summary = "创建动物饲养鼠牌")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-card:create')")
    public CommonResult<Long> createAnimalFeedCard(@Valid @RequestBody AnimalFeedCardCreateReqVO createReqVO) {
        return success(animalFeedCardService.createAnimalFeedCard(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新动物饲养鼠牌")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-card:update')")
    public CommonResult<Boolean> updateAnimalFeedCard(@Valid @RequestBody AnimalFeedCardUpdateReqVO updateReqVO) {
        animalFeedCardService.updateAnimalFeedCard(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除动物饲养鼠牌")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-card:delete')")
    public CommonResult<Boolean> deleteAnimalFeedCard(@RequestParam("id") Long id) {
        animalFeedCardService.deleteAnimalFeedCard(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得动物饲养鼠牌")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-card:query')")
    public CommonResult<AnimalFeedCardRespVO> getAnimalFeedCard(@RequestParam("id") Long id) {
            Optional<AnimalFeedCard> animalFeedCard = animalFeedCardService.getAnimalFeedCard(id);
        return success(animalFeedCard.map(animalFeedCardMapper::toDto).orElseThrow(() -> exception(ANIMAL_FEED_CARD_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得动物饲养鼠牌列表")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-card:query')")
    public CommonResult<PageResult<AnimalFeedCardRespVO>> getAnimalFeedCardPage(@Valid AnimalFeedCardPageReqVO pageVO, @Valid AnimalFeedCardPageOrder orderV0) {
        PageResult<AnimalFeedCard> pageResult = animalFeedCardService.getAnimalFeedCardPage(pageVO, orderV0);
        return success(animalFeedCardMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出动物饲养鼠牌 Excel")
    @PreAuthorize("@ss.hasPermission('jl:animal-feed-card:export')")
    @OperateLog(type = EXPORT)
    public void exportAnimalFeedCardExcel(@Valid AnimalFeedCardExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<AnimalFeedCard> list = animalFeedCardService.getAnimalFeedCardList(exportReqVO);
        // 导出 Excel
        List<AnimalFeedCardExcelVO> excelData = animalFeedCardMapper.toExcelList(list);
        ExcelUtils.write(response, "动物饲养鼠牌.xls", "数据", AnimalFeedCardExcelVO.class, excelData);
    }

}
