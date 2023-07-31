package cn.iocoder.yudao.module.jl.controller.admin.crm;

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

import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.CrmContact;
import cn.iocoder.yudao.module.jl.mapper.crm.CrmContactMapper;
import cn.iocoder.yudao.module.jl.service.crm.CrmContactService;

@Tag(name = "管理后台 - 客户联系人")
@RestController
@RequestMapping("/jl/crm-contact")
@Validated
public class CrmContactController {

    @Resource
    private CrmContactService crmContactService;

    @Resource
    private CrmContactMapper crmContactMapper;

    @PostMapping("/create")
    @Operation(summary = "创建客户联系人")
    @PreAuthorize("@ss.hasPermission('jl:crm-contact:create')")
    public CommonResult<Long> createCrmContact(@Valid @RequestBody CrmContactCreateReqVO createReqVO) {
        return success(crmContactService.createCrmContact(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户联系人")
    @PreAuthorize("@ss.hasPermission('jl:crm-contact:update')")
    public CommonResult<Boolean> updateCrmContact(@Valid @RequestBody CrmContactUpdateReqVO updateReqVO) {
        crmContactService.updateCrmContact(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除客户联系人")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:crm-contact:delete')")
    public CommonResult<Boolean> deleteCrmContact(@RequestParam("id") Long id) {
        crmContactService.deleteCrmContact(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得客户联系人")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:crm-contact:query')")
    public CommonResult<CrmContactRespVO> getCrmContact(@RequestParam("id") Long id) {
            Optional<CrmContact> crmContact = crmContactService.getCrmContact(id);
        return success(crmContact.map(crmContactMapper::toDto).orElseThrow(() -> exception(CRM_CONTACT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得客户联系人列表")
    @PreAuthorize("@ss.hasPermission('jl:crm-contact:query')")
    public CommonResult<PageResult<CrmContactRespVO>> getCrmContactPage(@Valid CrmContactPageReqVO pageVO, @Valid CrmContactPageOrder orderV0) {
        PageResult<CrmContact> pageResult = crmContactService.getCrmContactPage(pageVO, orderV0);
        return success(crmContactMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客户联系人 Excel")
    @PreAuthorize("@ss.hasPermission('jl:crm-contact:export')")
    @OperateLog(type = EXPORT)
    public void exportCrmContactExcel(@Valid CrmContactExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CrmContact> list = crmContactService.getCrmContactList(exportReqVO);
        // 导出 Excel
        List<CrmContactExcelVO> excelData = crmContactMapper.toExcelList(list);
        ExcelUtils.write(response, "客户联系人.xls", "数据", CrmContactExcelVO.class, excelData);
    }

}
