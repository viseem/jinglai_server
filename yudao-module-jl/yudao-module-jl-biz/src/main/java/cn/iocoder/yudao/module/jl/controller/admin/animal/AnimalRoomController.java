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
import cn.iocoder.yudao.module.jl.entity.animal.AnimalRoom;
import cn.iocoder.yudao.module.jl.mapper.animal.AnimalRoomMapper;
import cn.iocoder.yudao.module.jl.service.animal.AnimalRoomService;

@Tag(name = "管理后台 - 动物饲养室")
@RestController
@RequestMapping("/jl/animal-room")
@Validated
public class AnimalRoomController {

    @Resource
    private AnimalRoomService animalRoomService;

    @Resource
    private AnimalRoomMapper animalRoomMapper;

    @PostMapping("/create")
    @Operation(summary = "创建动物饲养室")
    @PreAuthorize("@ss.hasPermission('jl:animal-room:create')")
    public CommonResult<Long> createAnimalRoom(@Valid @RequestBody AnimalRoomCreateReqVO createReqVO) {
        return success(animalRoomService.createAnimalRoom(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新动物饲养室")
    @PreAuthorize("@ss.hasPermission('jl:animal-room:update')")
    public CommonResult<Boolean> updateAnimalRoom(@Valid @RequestBody AnimalRoomUpdateReqVO updateReqVO) {
        animalRoomService.updateAnimalRoom(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除动物饲养室")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:animal-room:delete')")
    public CommonResult<Boolean> deleteAnimalRoom(@RequestParam("id") Long id) {
        animalRoomService.deleteAnimalRoom(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得动物饲养室")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:animal-room:query')")
    public CommonResult<AnimalRoomRespVO> getAnimalRoom(@RequestParam("id") Long id) {
            Optional<AnimalRoom> animalRoom = animalRoomService.getAnimalRoom(id);
        return success(animalRoom.map(animalRoomMapper::toDto).orElseThrow(() -> exception(ANIMAL_ROOM_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得动物饲养室列表")
    @PreAuthorize("@ss.hasPermission('jl:animal-room:query')")
    public CommonResult<PageResult<AnimalRoomRespVO>> getAnimalRoomPage(@Valid AnimalRoomPageReqVO pageVO, @Valid AnimalRoomPageOrder orderV0) {
        PageResult<AnimalRoom> pageResult = animalRoomService.getAnimalRoomPage(pageVO, orderV0);
        return success(animalRoomMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出动物饲养室 Excel")
    @PreAuthorize("@ss.hasPermission('jl:animal-room:export')")
    @OperateLog(type = EXPORT)
    public void exportAnimalRoomExcel(@Valid AnimalRoomExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<AnimalRoom> list = animalRoomService.getAnimalRoomList(exportReqVO);
        // 导出 Excel
        List<AnimalRoomExcelVO> excelData = animalRoomMapper.toExcelList(list);
        ExcelUtils.write(response, "动物饲养室.xls", "数据", AnimalRoomExcelVO.class, excelData);
    }

}
