package cn.iocoder.yudao.module.jl.controller.admin.crm;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.SupplierImportRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.SupplierImportVO;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerSimple;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.Customer;
import cn.iocoder.yudao.module.jl.mapper.crm.CustomerMapper;
import cn.iocoder.yudao.module.jl.service.crm.CustomerService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 客户")
@RestController
@RequestMapping("/jl/customer")
//------------------客户先当成用户
@Validated
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @Resource
    private CustomerMapper customerMapper;

    //计算客户的成交次数、成交总额、款项总额、未付款项总额
    @GetMapping("/statistics")
    @Operation(summary = "计算客户的成交次数、成交总额、款项总额、未付款项总额")
    @PreAuthorize("@ss.hasPermission('jl:customer:query')")
    public CommonResult<CustomerStatisticsRespVO> getCustomerStatistics(@RequestParam("id") Long id) {
        CustomerStatisticsRespVO customerStatisticsRespVO = customerService.getCustomerStatistics(id);
        return success(customerStatisticsRespVO);
    }

    @PostMapping("/create")
    @Operation(summary = "创建客户")
    @PreAuthorize("@ss.hasPermission('jl:customer:create')")
    public CommonResult<Long> createCustomer(@Valid @RequestBody CustomerCreateReqVO createReqVO) {
        Long customer = customerService.createCustomer(createReqVO);
        String msg="";
        if(customer == null || customer<=0){
            msg="该手机号已存在";
        }
        return success(customer,msg);
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户")
    @PreAuthorize("@ss.hasPermission('jl:customer:update')")
    public CommonResult<Boolean> updateCustomer(@Valid @RequestBody CustomerUpdateReqVO updateReqVO) {
        customerService.updateCustomer(updateReqVO);
        return success(true);
    }

    @PutMapping("/to-customer")
    @Operation(summary = "转客户")
    @PreAuthorize("@ss.hasPermission('jl:customer:update')")
    public CommonResult<Boolean> updateCustomer(@Valid @RequestBody ClueToCustomerReqVO updateReqVO) {
        customerService.toCustomer(updateReqVO);
        return success(true);
    }

    @PutMapping("/assign")
    @Operation(summary = "指派给销售")
    @PreAuthorize("@ss.hasPermission('jl:customer:update')")
    public CommonResult<Boolean> updateCustomer(@Valid @RequestBody CustomerAssignToSalesReqVO updateReqVO) {
        customerService.customerAssign2Sales(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除客户")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:customer:delete')")
    public CommonResult<Boolean> deleteCustomer(@RequestParam("id") Long id) {
        customerService.deleteCustomer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得客户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:customer:query')")
    public CommonResult<CustomerRespVO> getCustomer(@RequestParam("id") Long id) {
            Optional<Customer> customer = customerService.getCustomer(id);
        return success(customer.map(customerMapper::toDto).orElseThrow(() -> exception(CUSTOMER_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得客户列表")
    @PreAuthorize("@ss.hasPermission('jl:customer:query')")
    public CommonResult<PageResult<CustomerRespVO>> getCustomerPage(@Valid CustomerPageReqVO pageVO, @Valid CustomerPageOrder orderV0) {
        PageResult<Customer> pageResult = customerService.getCustomerPage(pageVO, orderV0);
        return success(customerMapper.toPage(pageResult));
    }

    @GetMapping("/simple-page")
    @Operation(summary = "(分页)获得客户列表 simple")
    @PreAuthorize("@ss.hasPermission('jl:customer:query')")
    public CommonResult<PageResult<CustomerRespVO>> getCustomerSimplePage(@Valid CustomerPageReqVO pageVO, @Valid CustomerPageOrder orderV0) {
        PageResult<CustomerSimple> pageResult = customerService.getCustomerSimplePage(pageVO, orderV0);
        return success(customerMapper.toPageSimple(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客户 Excel")
    @PreAuthorize("@ss.hasPermission('jl:customer:export')")
    @OperateLog(type = EXPORT)
    public void exportCustomerExcel(@Valid CustomerExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<Customer> list = customerService.getCustomerList(exportReqVO);
        // 导出 Excel
        List<CustomerExcelVO> excelData = customerMapper.toExcelList(list);
        ExcelUtils.write(response, "客户.xls", "数据", CustomerExcelVO.class, excelData);
    }

    //excel导入
    @PostMapping("/import-excel")
    @Operation(summary = "导入用户")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('system:user:import')")
    public CommonResult<CustomerImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                          @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<CustomerImportVO> list = ExcelUtils.read(file, CustomerImportVO.class);
        return success(customerService.importList(list, updateSupport));
    }

}
