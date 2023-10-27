package cn.iocoder.yudao.module.member.controller.app.user;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.AssetDevicePageOrder;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.AssetDevicePageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.AssetDeviceRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.cmsarticle.vo.CmsArticlePageOrder;
import cn.iocoder.yudao.module.jl.controller.admin.cmsarticle.vo.CmsArticlePageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.cmsarticle.vo.CmsArticleRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.AppCustomerUpdateReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.appcustomer.CustomerProjectPageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo.VisitAppointmentCreateReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo.VisitAppointmentPageOrder;
import cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo.VisitAppointmentPageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo.VisitAppointmentRespVO;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDevice;
import cn.iocoder.yudao.module.jl.entity.cmsarticle.CmsArticle;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.project.*;
import cn.iocoder.yudao.module.jl.entity.visitappointment.VisitAppointment;
import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.mapper.asset.AssetDeviceMapper;
import cn.iocoder.yudao.module.jl.mapper.cmsarticle.CmsArticleMapper;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectFeedbackMapper;
import cn.iocoder.yudao.module.jl.mapper.visitappointment.VisitAppointmentMapper;
import cn.iocoder.yudao.module.jl.repository.cmsarticle.CmsArticleRepository;
import cn.iocoder.yudao.module.jl.repository.project.AppProjectRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategorySimpleRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectFeedbackRepository;
import cn.iocoder.yudao.module.jl.repository.visitappointment.VisitAppointmentRepository;
import cn.iocoder.yudao.module.jl.service.asset.AssetDeviceServiceImpl;
import cn.iocoder.yudao.module.jl.service.cmsarticle.CmsArticleService;
import cn.iocoder.yudao.module.jl.service.crm.CustomerService;
import cn.iocoder.yudao.module.jl.service.project.*;
import cn.iocoder.yudao.module.jl.service.visitappointment.VisitAppointmentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import liquibase.pro.packaged.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

@Tag(name = "用户 APP - 用户个人中心")
@RestController
@RequestMapping("/member/user")
@Validated
@Slf4j
public class JLAppUserController {

    @Resource
    ProjectServiceImpl projectService;

    @Resource
    ProjectCategoryService projectCategoryService;

    @Resource
    AppProjectRepository appProjectRepository;

    @Resource
    ProjectCategoryRepository projectCategoryRepository;

    @Resource
    ProjectConstractService projectConstractService;

    @Resource
    CustomerService customerService;

    @Resource
    CmsArticleService cmsArticleService;

    @Resource
    CmsArticleRepository cmsArticleRepository;

    @Resource
    private CmsArticleMapper cmsArticleMapper;

    @Resource
    private ProjectFeedbackServiceImpl projectFeedbackService;

    @Resource
    private ProjectFeedbackRepository projectFeedbackRepository;

    @Resource
    private ProjectFeedbackMapper projectFeedbackMapper;

    @Resource
    private VisitAppointmentServiceImpl visitAppointmentService;

    @Resource
    private VisitAppointmentRepository visitAppointmentRepository;

    @Resource
    private VisitAppointmentMapper visitAppointmentMapper;

    @Resource
    private AssetDeviceServiceImpl assetDeviceService;

    @Resource
    private AssetDeviceMapper assetDeviceMapper;

    private Long validLoginUser(){
        Long loginUserId = WebFrameworkUtils.getLoginUserId();
        if (loginUserId == null) {
            throw exception(UNAUTHORIZED);
        }
        return loginUserId;
    }

    @PostMapping("/project-page")
    @Operation(summary = "客户 项目列表")
    public CommonResult<PageResult<ProjectSimple>> getProjectPage(@Valid @RequestBody CustomerProjectPageReqVO pageVO, @Valid @RequestBody ProjectPageOrder orderV0) {
        Long loginUserId = validLoginUser();
        pageVO.setCustomerId(loginUserId);

        PageResult<ProjectSimple> pageResult = projectService.getCustomerProjectPage(pageVO, orderV0);
        return success(pageResult);
    }

    @PostMapping("/project-category-page")
    @Operation(summary = "客户 项目列表")
    public CommonResult<PageResult<ProjectCategorySimple>> getProjectCategoryPage(@Valid @RequestBody ProjectCategoryPageReqVO pageVO, @Valid @RequestBody ProjectCategoryPageOrder orderV0) {
        PageResult<ProjectCategorySimple> projectCategoryPage = projectCategoryService.getProjectCategoryPage(pageVO, orderV0);
        return success(projectCategoryPage);
    }

    @GetMapping("/project-detail")
    @Operation(summary = "APP 项目详情")
    public CommonResult<AppProject> getProjectDetail(@RequestParam("id") Long id) {
        AppProject appProject = appProjectRepository.findById(id).orElseThrow(() -> exception(PROJECT_NOT_EXISTS));

        // 计算categoryList的完成个数,通过categoryList的status来判断，等于COMPLETE的个数
        int completeCount = 0;
        int allCount = appProject.getCategoryList().size();

        for (ProjectCategoryOnly category : appProject.getCategoryList()) {
            if (category.getStage().equals(ProjectCategoryStatusEnums.COMPLETE.getStatus())) {
                completeCount++;
            }
        }

        appProject.setCompleteCount(completeCount);
        appProject.setAllCount(appProject.getCategoryList().size());
        if(allCount!=0){
            appProject.setCompletePercent((completeCount*100/allCount));
        }

        return success(appProject);
    }


    @PostMapping("/contract-page")
    @Operation(summary = "客户 合同列表")
    public CommonResult<PageResult<ProjectConstract>> getContractPage(@Valid @RequestBody ProjectConstractPageReqVO pageVO, @Valid @RequestBody ProjectConstractPageOrder orderV0) {
        Long loginUserId = validLoginUser();
        pageVO.setCustomerId(loginUserId);
        PageResult<ProjectConstract> projectConstractPage = projectConstractService.getProjectConstractPage(pageVO, orderV0);
        return success(projectConstractPage);
    }

    @PostMapping("/update")
    //实际上更新的是customer表的信息
    @Operation(summary = "更新信息")
    public CommonResult<CustomerOnly> updateCustomer(@Valid @RequestBody AppCustomerUpdateReqVO updateReqVO) {
        Long loginUserId = validLoginUser();
        updateReqVO.setId(loginUserId);
        CustomerOnly customerOnly = customerService.updateAppCustomer(updateReqVO);
        return success(customerOnly);
    }

    @GetMapping("/category-detail")
    @Operation(summary = "APP 实验详情")
    public CommonResult<ProjectCategory> getProjectCategoryDetail(@RequestParam("id") Long id) {
        ProjectCategory projectCategory = projectCategoryRepository.findById(id).orElseThrow(() -> exception(PROJECT_CATEGORY_NOT_EXISTS));
        return success(projectCategory);
    }

    @PostMapping("/article-page")
    @Operation(summary = "APP 文章列表")
    public CommonResult<PageResult<CmsArticleRespVO>> getArticlePage(@Valid @RequestBody CmsArticlePageReqVO pageVO, @Valid @RequestBody CmsArticlePageOrder orderV0) {
        PageResult<CmsArticle> pageResult = cmsArticleService.getCmsArticlePage(pageVO, orderV0);
        return success(cmsArticleMapper.toPage(pageResult));
    }

    @GetMapping("/article-detail")
    @Operation(summary = "APP 文章详情")
    public CommonResult<CmsArticle> getArticleDetail(@RequestParam("id") Long id) {
        CmsArticle cmsArticle = cmsArticleRepository.findById(id).orElseThrow(() -> exception(PROJECT_NOT_EXISTS));
        return success(cmsArticle);
    }

    /*
    * 反馈
    * */
    @PostMapping("/feedback")
    @Operation(summary = "客户 发起反馈")
    public CommonResult<Long> getFeedbackPage(@Valid @RequestBody AppProjectFeedbackBaseVO reqVO) {
        Long loginUserId = validLoginUser();
        Project project = projectService.validateProjectExists(reqVO.getProjectId());
        ProjectFeedbackCreateReqVO createReqVO = new ProjectFeedbackCreateReqVO();
        BeanUtils.copyProperties(reqVO,createReqVO);
        createReqVO.setCustomerId(loginUserId);
        //设置负责人为销售人员
        createReqVO.setUserId(project.getManagerId());
        Long projectFeedback = projectFeedbackService.createProjectFeedback(createReqVO);
        return success(projectFeedback);
    }

    @PostMapping("/feedback-page")
    @Operation(summary = "APP 反馈列表")
    public CommonResult<PageResult<ProjectFeedbackRespVO>> getArticlePage(@Valid @RequestBody ProjectFeedbackPageReqVO pageVO, @Valid @RequestBody ProjectFeedbackPageOrder orderV0) {
        pageVO.setAttribute(DataAttributeTypeEnums.ANY.getStatus());
        PageResult<ProjectFeedback> projectFeedbackPage = projectFeedbackService.getProjectFeedbackPage(pageVO, orderV0);
        return success(projectFeedbackMapper.toPage(projectFeedbackPage));
    }

    @GetMapping("/feedback-detail")
    @Operation(summary = "APP 反馈详情")
    public CommonResult<ProjectFeedback> getFeedbackDetail(@RequestParam("id") Long id) {
        ProjectFeedback projectFeedback = projectFeedbackRepository.findById(id).orElseThrow(() -> exception(PROJECT_FEEDBACK_NOT_EXISTS));
        return success(projectFeedback);
    }

    /*
    * 到访预约
    * */
    @PostMapping("/visit")
    @Operation(summary = "客户 发起到访")
    public CommonResult<Long> getVisitPage(@Valid @RequestBody VisitAppointmentCreateReqVO reqVO) {
        Long loginUserId = validLoginUser();
        reqVO.setCustomerId(loginUserId);
        Long id = visitAppointmentService.createVisitAppointment(reqVO);
        return success(id);
    }

    @PostMapping("/visit-page")
    @Operation(summary = "客户 到访记录")
    public CommonResult<PageResult<VisitAppointmentRespVO>> getVisitPage(@Valid @RequestBody VisitAppointmentPageReqVO pageVO, @Valid @RequestBody VisitAppointmentPageOrder orderV0) {
        PageResult<VisitAppointment> visitAppointmentPage = visitAppointmentService.getVisitAppointmentPage(pageVO, orderV0);
        return success(visitAppointmentMapper.toPage(visitAppointmentPage));
    }

    @GetMapping("/visit-detail")
    @Operation(summary = "APP 到访详情")
    public CommonResult<VisitAppointment> getVisitDetail(@RequestParam("id") Long id) {
        VisitAppointment visitAppointment = visitAppointmentRepository.findById(id).orElseThrow(() -> exception(VISIT_APPOINTMENT_NOT_EXISTS));
        return success(visitAppointment);
    }

    /*
    * 设备
    * */
    @PostMapping("/device-page")
    @Operation(summary = "客户 公司设备列表")
    public CommonResult<PageResult<AssetDeviceRespVO>> getDevicePage(@Valid @RequestBody AssetDevicePageReqVO pageVO, @Valid @RequestBody AssetDevicePageOrder orderV0) {
        PageResult<AssetDevice> assetDevicePage = assetDeviceService.getAssetDevicePage(pageVO, orderV0);
        return success(assetDeviceMapper.toPage(assetDevicePage));
    }

}

