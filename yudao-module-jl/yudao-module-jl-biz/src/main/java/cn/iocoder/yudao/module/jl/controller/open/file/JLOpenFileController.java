package cn.iocoder.yudao.module.jl.controller.open.file;

import cn.hutool.core.io.IoUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.infra.controller.admin.file.vo.file.FileUploadReqVO;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreGetReqVO;
import cn.iocoder.yudao.module.system.service.util.UtilServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 报价开放接口")
@RestController
@RequestMapping("/infra/file")
@Validated
public class JLOpenFileController {
    @Resource
    private FileService fileService;

    @Resource
    private UtilServiceImpl utilService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    @OperateLog(logArgs = false) // 上传文件，没有记录操作日志的必要
    public CommonResult<String> uploadFile(FileUploadReqVO uploadReqVO) throws Exception {
        UtilStoreGetReqVO storeVo= new UtilStoreGetReqVO();
        storeVo.setStorePwd(uploadReqVO.getStorePwd());
        storeVo.setStoreId(uploadReqVO.getStoreId());
        utilService.validStoreWithException(storeVo);

        MultipartFile file = uploadReqVO.getFile();
            String path = uploadReqVO.getPath();
        return success(fileService.createFile(file.getOriginalFilename(), path, IoUtil.readBytes(file.getInputStream())));
    }

}
