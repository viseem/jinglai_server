package cn.iocoder.yudao.module.jl.controller.open.dict;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.commontask.vo.CommonTaskPageOrder;
import cn.iocoder.yudao.module.jl.controller.admin.commontask.vo.CommonTaskPageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.commontask.vo.CommonTaskRespVO;
import cn.iocoder.yudao.module.jl.entity.commontask.CommonTask;
import cn.iocoder.yudao.module.jl.mapper.commontask.CommonTaskMapper;
import cn.iocoder.yudao.module.jl.service.commontask.CommonTaskService;
import cn.iocoder.yudao.module.system.controller.admin.dict.vo.data.DictDataSimpleRespVO;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreGetReqVO;
import cn.iocoder.yudao.module.system.convert.dict.DictDataConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.system.service.dict.DictDataService;
import cn.iocoder.yudao.module.system.service.util.UtilServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.COMMON_TASK_NOT_EXISTS;

@Tag(name = "管理后台 - 报价开放接口")
@RestController
@RequestMapping("/dict-data")
@Validated
public class JLOpenDictDataController {
    @Resource
    private UtilServiceImpl utilService;

    @Resource
    private DictDataService dictDataService;

    @PostMapping("/list-all-simple")
    public CommonResult<List<DictDataSimpleRespVO>> getList() {
        List<DictDataDO> list = dictDataService.getDictDataList();
        return success(DictDataConvert.INSTANCE.convertList(list));
    }

}
