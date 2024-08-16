package cn.iocoder.yudao.module.jl.service.projectoutlog;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.enums.ProjectStageEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectOnlyRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectoutlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectoutlog.ProjectOutLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectoutlog.ProjectOutLogMapper;
import cn.iocoder.yudao.module.jl.repository.projectoutlog.ProjectOutLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * example 空 Service 实现类
 *
 */
@Service
@Validated
public class ProjectOutLogServiceImpl implements ProjectOutLogService {

    @Resource
    private ProjectOutLogRepository projectOutLogRepository;

    @Resource
    private ProjectOutLogMapper projectOutLogMapper;

    @Resource
    private ProjectServiceImpl projectService;

    @Resource
    private ProjectOnlyRepository projectOnlyRepository;

    @Override
    @Transactional
    public Long createProjectOutLog(ProjectOutLogCreateReqVO createReqVO) {
        ProjectSimple projectSimple = projectService.validateProjectExists(createReqVO.getProjectId());
        createReqVO.setQuotationId(projectSimple.getCurrentQuotationId());
        createReqVO.setCustomerId(projectSimple.getCustomerId());
        // 插入
        ProjectOutLog projectOutLog = projectOutLogMapper.toEntity(createReqVO);
        projectOutLogRepository.save(projectOutLog);

        projectOnlyRepository.updateOutboundLogIdAndOutboundStepById(projectOutLog.getId(),createReqVO.getStep()+1,createReqVO.getProjectId());

        // 返回
        return projectOutLog.getId();
    }

    @Override
    @Transactional
    public void updateProjectOutLog(ProjectOutLogUpdateReqVO updateReqVO) {
        // 校验存在
        ProjectOutLog projectOutLog = validateProjectOutLogExists(updateReqVO.getId());
        // 更新
        /*ProjectOutLog updateObj = projectOutLogMapper.toEntity(updateReqVO);
        projectOutLogRepository.save(updateObj);*/
        if(!updateReqVO.getNotUpdateStep()){
            projectOnlyRepository.updateOutboundStepById(updateReqVO.getStep()+1, projectOutLog.getProjectId());
        }
        if(updateReqVO.getStep().equals(0)){
            if(updateReqVO.getStepJson()==null){
                throw exception(PROJECT_OUT_LOG_PARAMS_ERROR);
            }
            projectOutLogRepository.updateStep1JsonById(updateReqVO.getStepJson(), updateReqVO.getId());
            projectOnlyRepository.updateStageById(ProjectStageEnums.OUTING.getStatus(), updateReqVO.getProjectId());
        }
        if(updateReqVO.getStep().equals(1)){
            if(updateReqVO.getStepJson()==null){
                throw exception(PROJECT_OUT_LOG_PARAMS_ERROR);
            }
            projectOutLogRepository.updateStep2JsonById(updateReqVO.getStepJson(), updateReqVO.getId());
        }
        if(updateReqVO.getStep().equals(2)){
            if(updateReqVO.getStepJson()==null){
                throw exception(PROJECT_OUT_LOG_PARAMS_ERROR);
            }
            projectOutLogRepository.updateStep3JsonById(updateReqVO.getStepJson(), updateReqVO.getId());
        }
        // 这是最后一步 项目就出库了
        if(updateReqVO.getStep().equals(3)){
            if(updateReqVO.getStepJson()==null){
                throw exception(PROJECT_OUT_LOG_PARAMS_ERROR);
            }
            projectOutLogRepository.updateStep4JsonById(updateReqVO.getStepJson(), updateReqVO.getId());
            // 项目变更为已出库
//            projectOnlyRepository.updateStageById(ProjectStageEnums.OUTED.getStatus(), updateReqVO.getProjectId());
        }
    }


    @Override
    public void deleteProjectOutLog(Long id) {
        // 校验存在
        validateProjectOutLogExists(id);
        // 删除
        projectOutLogRepository.deleteById(id);
    }

    private ProjectOutLog validateProjectOutLogExists(Long id) {
        return projectOutLogRepository.findById(id).orElseThrow(() -> exception(PROJECT_OUT_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectOutLog> getProjectOutLog(Long id) {
        return projectOutLogRepository.findById(id);
    }

    @Override
    public List<ProjectOutLog> getProjectOutLogList(Collection<Long> ids) {
        return StreamSupport.stream(projectOutLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectOutLog> getProjectOutLogPage(ProjectOutLogPageReqVO pageReqVO, ProjectOutLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectOutLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getDataSignJson() != null) {
                predicates.add(cb.equal(root.get("dataSignJson"), pageReqVO.getDataSignJson()));
            }

            if(pageReqVO.getCustomerComment() != null) {
                predicates.add(cb.equal(root.get("customerComment"), pageReqVO.getCustomerComment()));
            }

            if(pageReqVO.getCustomerScoreJson() != null) {
                predicates.add(cb.equal(root.get("customerScoreJson"), pageReqVO.getCustomerScoreJson()));
            }

            if(pageReqVO.getQuotationPrice() != null) {
                predicates.add(cb.equal(root.get("quotationPrice"), pageReqVO.getQuotationPrice()));
            }

            if(pageReqVO.getQuotationMark() != null) {
                predicates.add(cb.equal(root.get("quotationMark"), pageReqVO.getQuotationMark()));
            }

            if(pageReqVO.getReceivedPrice() != null) {
                predicates.add(cb.equal(root.get("receivedPrice"), pageReqVO.getReceivedPrice()));
            }

            if(pageReqVO.getReceivedMark() != null) {
                predicates.add(cb.equal(root.get("receivedMark"), pageReqVO.getReceivedMark()));
            }

            if(pageReqVO.getContractPrice() != null) {
                predicates.add(cb.equal(root.get("contractPrice"), pageReqVO.getContractPrice()));
            }

            if(pageReqVO.getContractMark() != null) {
                predicates.add(cb.equal(root.get("contractMark"), pageReqVO.getContractMark()));
            }

            if(pageReqVO.getSupplyPrice() != null) {
                predicates.add(cb.equal(root.get("supplyPrice"), pageReqVO.getSupplyPrice()));
            }

            if(pageReqVO.getSupplyMark() != null) {
                predicates.add(cb.equal(root.get("supplyMark"), pageReqVO.getSupplyMark()));
            }

            if(pageReqVO.getOutsourcePrice() != null) {
                predicates.add(cb.equal(root.get("outsourcePrice"), pageReqVO.getOutsourcePrice()));
            }

            if(pageReqVO.getOutsourceMark() != null) {
                predicates.add(cb.equal(root.get("outsourceMark"), pageReqVO.getOutsourceMark()));
            }

            if(pageReqVO.getCustomerSignImgUrl() != null) {
                predicates.add(cb.equal(root.get("customerSignImgUrl"), pageReqVO.getCustomerSignImgUrl()));
            }

            if(pageReqVO.getCustoamerSignTime() != null) {
                predicates.add(cb.between(root.get("custoamerSignTime"), pageReqVO.getCustoamerSignTime()[0], pageReqVO.getCustoamerSignTime()[1]));
            } 
            if(pageReqVO.getStepsJsonLog() != null) {
                predicates.add(cb.equal(root.get("stepsJsonLog"), pageReqVO.getStepsJsonLog()));
            }

            if(pageReqVO.getResultPrice() != null) {
                predicates.add(cb.equal(root.get("resultPrice"), pageReqVO.getResultPrice()));
            }

            if(pageReqVO.getResultMark() != null) {
                predicates.add(cb.equal(root.get("resultMark"), pageReqVO.getResultMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectOutLog> page = projectOutLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectOutLog> getProjectOutLogList(ProjectOutLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectOutLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getDataSignJson() != null) {
                predicates.add(cb.equal(root.get("dataSignJson"), exportReqVO.getDataSignJson()));
            }

            if(exportReqVO.getCustomerComment() != null) {
                predicates.add(cb.equal(root.get("customerComment"), exportReqVO.getCustomerComment()));
            }

            if(exportReqVO.getCustomerScoreJson() != null) {
                predicates.add(cb.equal(root.get("customerScoreJson"), exportReqVO.getCustomerScoreJson()));
            }

            if(exportReqVO.getQuotationPrice() != null) {
                predicates.add(cb.equal(root.get("quotationPrice"), exportReqVO.getQuotationPrice()));
            }

            if(exportReqVO.getQuotationMark() != null) {
                predicates.add(cb.equal(root.get("quotationMark"), exportReqVO.getQuotationMark()));
            }

            if(exportReqVO.getReceivedPrice() != null) {
                predicates.add(cb.equal(root.get("receivedPrice"), exportReqVO.getReceivedPrice()));
            }

            if(exportReqVO.getReceivedMark() != null) {
                predicates.add(cb.equal(root.get("receivedMark"), exportReqVO.getReceivedMark()));
            }

            if(exportReqVO.getContractPrice() != null) {
                predicates.add(cb.equal(root.get("contractPrice"), exportReqVO.getContractPrice()));
            }

            if(exportReqVO.getContractMark() != null) {
                predicates.add(cb.equal(root.get("contractMark"), exportReqVO.getContractMark()));
            }

            if(exportReqVO.getSupplyPrice() != null) {
                predicates.add(cb.equal(root.get("supplyPrice"), exportReqVO.getSupplyPrice()));
            }

            if(exportReqVO.getSupplyMark() != null) {
                predicates.add(cb.equal(root.get("supplyMark"), exportReqVO.getSupplyMark()));
            }

            if(exportReqVO.getOutsourcePrice() != null) {
                predicates.add(cb.equal(root.get("outsourcePrice"), exportReqVO.getOutsourcePrice()));
            }

            if(exportReqVO.getOutsourceMark() != null) {
                predicates.add(cb.equal(root.get("outsourceMark"), exportReqVO.getOutsourceMark()));
            }

            if(exportReqVO.getCustomerSignImgUrl() != null) {
                predicates.add(cb.equal(root.get("customerSignImgUrl"), exportReqVO.getCustomerSignImgUrl()));
            }

            if(exportReqVO.getCustoamerSignTime() != null) {
                predicates.add(cb.between(root.get("custoamerSignTime"), exportReqVO.getCustoamerSignTime()[0], exportReqVO.getCustoamerSignTime()[1]));
            } 
            if(exportReqVO.getStepsJsonLog() != null) {
                predicates.add(cb.equal(root.get("stepsJsonLog"), exportReqVO.getStepsJsonLog()));
            }

            if(exportReqVO.getResultPrice() != null) {
                predicates.add(cb.equal(root.get("resultPrice"), exportReqVO.getResultPrice()));
            }

            if(exportReqVO.getResultMark() != null) {
                predicates.add(cb.equal(root.get("resultMark"), exportReqVO.getResultMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectOutLogRepository.findAll(spec);
    }

    private Sort createSort(ProjectOutLogPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getDataSignJson() != null) {
            orders.add(new Sort.Order(order.getDataSignJson().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "dataSignJson"));
        }

        if (order.getCustomerComment() != null) {
            orders.add(new Sort.Order(order.getCustomerComment().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerComment"));
        }

        if (order.getCustomerScoreJson() != null) {
            orders.add(new Sort.Order(order.getCustomerScoreJson().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerScoreJson"));
        }

        if (order.getQuotationPrice() != null) {
            orders.add(new Sort.Order(order.getQuotationPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quotationPrice"));
        }

        if (order.getQuotationMark() != null) {
            orders.add(new Sort.Order(order.getQuotationMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quotationMark"));
        }

        if (order.getReceivedPrice() != null) {
            orders.add(new Sort.Order(order.getReceivedPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receivedPrice"));
        }

        if (order.getReceivedMark() != null) {
            orders.add(new Sort.Order(order.getReceivedMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receivedMark"));
        }

        if (order.getContractPrice() != null) {
            orders.add(new Sort.Order(order.getContractPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "contractPrice"));
        }

        if (order.getContractMark() != null) {
            orders.add(new Sort.Order(order.getContractMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "contractMark"));
        }

        if (order.getSupplyPrice() != null) {
            orders.add(new Sort.Order(order.getSupplyPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplyPrice"));
        }

        if (order.getSupplyMark() != null) {
            orders.add(new Sort.Order(order.getSupplyMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplyMark"));
        }

        if (order.getOutsourcePrice() != null) {
            orders.add(new Sort.Order(order.getOutsourcePrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "outsourcePrice"));
        }

        if (order.getOutsourceMark() != null) {
            orders.add(new Sort.Order(order.getOutsourceMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "outsourceMark"));
        }

        if (order.getCustomerSignImgUrl() != null) {
            orders.add(new Sort.Order(order.getCustomerSignImgUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerSignImgUrl"));
        }

        if (order.getCustoamerSignTime() != null) {
            orders.add(new Sort.Order(order.getCustoamerSignTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "custoamerSignTime"));
        }

        if (order.getStepsJsonLog() != null) {
            orders.add(new Sort.Order(order.getStepsJsonLog().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "stepsJsonLog"));
        }

        if (order.getResultPrice() != null) {
            orders.add(new Sort.Order(order.getResultPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "resultPrice"));
        }

        if (order.getResultMark() != null) {
            orders.add(new Sort.Order(order.getResultMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "resultMark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}