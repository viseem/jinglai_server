package cn.iocoder.yudao.module.jl.controller.admin.productuser;

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

import cn.iocoder.yudao.module.jl.controller.admin.productuser.vo.*;
import cn.iocoder.yudao.module.jl.entity.productuser.ProductUser;
import cn.iocoder.yudao.module.jl.mapper.productuser.ProductUserMapper;
import cn.iocoder.yudao.module.jl.service.productuser.ProductUserService;

@Tag(name = "管理后台 - 产品库人员")
@RestController
@RequestMapping("/jl/product-user")
@Validated
public class ProductUserController {

    @Resource
    private ProductUserService productUserService;

    @Resource
    private ProductUserMapper productUserMapper;



    @PostMapping("/create")
    @Operation(summary = "创建产品库人员")
    @PreAuthorize("@ss.hasPermission('jl:product-user:create')")
    public CommonResult<Long> createProductUser(@Valid @RequestBody ProductUserCreateReqVO createReqVO) {
        return success(productUserService.createProductUser(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品库人员")
    @PreAuthorize("@ss.hasPermission('jl:product-user:update')")
    public CommonResult<Boolean> updateProductUser(@Valid @RequestBody ProductUserUpdateReqVO updateReqVO) {
        productUserService.updateProductUser(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除产品库人员")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:product-user:delete')")
    public CommonResult<Boolean> deleteProductUser(@RequestParam("id") Long id) {
        productUserService.deleteProductUser(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得产品库人员")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:product-user:query')")
    public CommonResult<ProductUserRespVO> getProductUser(@RequestParam("id") Long id) {
            Optional<ProductUser> productUser = productUserService.getProductUser(id);
        return success(productUser.map(productUserMapper::toDto).orElseThrow(() -> exception(PRODUCT_USER_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得产品库人员列表")
    @PreAuthorize("@ss.hasPermission('jl:product-user:query')")
    public CommonResult<PageResult<ProductUserRespVO>> getProductUserPage(@Valid ProductUserPageReqVO pageVO, @Valid ProductUserPageOrder orderV0) {
        PageResult<ProductUser> pageResult = productUserService.getProductUserPage(pageVO, orderV0);
        return success(productUserMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品库人员 Excel")
    @PreAuthorize("@ss.hasPermission('jl:product-user:export')")
    @OperateLog(type = EXPORT)
    public void exportProductUserExcel(@Valid ProductUserExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProductUser> list = productUserService.getProductUserList(exportReqVO);
        // 导出 Excel
        List<ProductUserExcelVO> excelData = productUserMapper.toExcelList(list);
        ExcelUtils.write(response, "产品库人员.xls", "数据", ProductUserExcelVO.class, excelData);
    }

}
