package cn.iocoder.yudao.module.jl.service.contract;

import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.iocoder.yudao.module.jl.enums.ProjectStageEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectDocumentRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectDocumentServiceImpl;
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
import cn.iocoder.yudao.module.jl.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.jl.entity.contract.ContractApproval;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.contract.ContractApprovalMapper;
import cn.iocoder.yudao.module.jl.repository.contract.ContractApprovalRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 合同状态变更记录 Service 实现类
 *
 */
@Service
@Validated
public class ContractApprovalServiceImpl implements ContractApprovalService {


    /**
     * OA 对应的流程定义 KEY
     */
    public static final String PROCESS_KEY = "PROJECT_CONTRACT_CHANGE_APPROVAL";

    @Resource
    private ContractApprovalRepository contractApprovalRepository;

    @Resource
    private ContractApprovalMapper contractApprovalMapper;

    @Resource
    private BpmProcessInstanceApi processInstanceApi;
    private final ProjectConstractRepository projectConstractRepository;
    @Resource
    private ProjectDocumentServiceImpl projectDocumentService;

    public ContractApprovalServiceImpl(ProjectConstractRepository projectConstractRepository) {
        this.projectConstractRepository = projectConstractRepository;
    }

    @Override
    @Transactional
    public Long createContractApproval(ContractApprovalCreateReqVO createReqVO) {
        // 插入
        String bpmProcess = BpmProcessInstanceResultEnum.PROCESS.getResult().toString();

        ContractApproval contractApproval = contractApprovalMapper.toEntity(createReqVO);
        contractApproval.setApprovalStage(bpmProcess);
        ContractApproval save = contractApprovalRepository.save(contractApproval);

        // 发起 BPM 流程
        Map<String, Object> processInstanceVariables = new HashMap<>();
        String processInstanceId = processInstanceApi.createProcessInstance(getLoginUserId(),
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(save.getId())));

        // 更新流程实例编号
        contractApprovalRepository.updateProcessInstanceIdById(processInstanceId, save.getId());

        //更新合同的签订文件
        projectConstractRepository.findById(save.getContractId()).ifPresent(contract -> {
            contract.setStampFileName(createReqVO.getStampFileName());
            contract.setStampFileUrl(createReqVO.getStampFileUrl());
            projectConstractRepository.save(contract);
        });

        // 返回
        return contractApproval.getId();
    }

    @Override
    @Transactional
    public void updateContractApproval(ContractApprovalUpdateReqVO updateReqVO) {
        // 校验存在
        ContractApproval contractApproval = validateContractApprovalExists(updateReqVO.getId());
        contractApproval.setApprovalStage(updateReqVO.getApprovalStage());

        // 批准该条申请 ： 1. 如果是开展前审批，则变更为开展中
        if (Objects.equals(updateReqVO.getApprovalStage(), BpmProcessInstanceResultEnum.APPROVE.getResult().toString())) {
            // 校验是否存在,并修改状态
            projectConstractRepository.findById(contractApproval.getContractId()).ifPresentOrElse(contract -> {
                if(contract.getProjectDocumentId()!=null){
                    projectDocumentService.updateProjectDocumentWithoutReq(contract.getProjectDocumentId(),contract.getStampFileName(),contract.getStampFileUrl());
                }
                contract.setStatus(contractApproval.getStage());
                System.out.println("--------"+contractApproval.getStage());
                if(contractApproval.getPrice()!=null){
                    contract.setPrice(contractApproval.getPrice());
                }
//                contract.setRealPrice(contractApproval.getRealPrice());
                projectConstractRepository.save(contract);
            },()->{
                throw exception(PROJECT_NOT_EXISTS);
            });
            //修改文档库的文件

        }

        // 更新

        contractApprovalRepository.save(contractApproval);
    }

    @Override
    public void deleteContractApproval(Long id) {
        // 校验存在
        validateContractApprovalExists(id);
        // 删除
        contractApprovalRepository.deleteById(id);
    }

    private ContractApproval validateContractApprovalExists(Long id) {
        Optional<ContractApproval> byId = contractApprovalRepository.findById(id);
        if (byId.isEmpty()) {
            throw exception(CONTRACT_APPROVAL_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<ContractApproval> getContractApproval(Long id) {
        return contractApprovalRepository.findById(id);
    }

    @Override
    public List<ContractApproval> getContractApprovalList(Collection<Long> ids) {
        return StreamSupport.stream(contractApprovalRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ContractApproval> getContractApprovalPage(ContractApprovalPageReqVO pageReqVO, ContractApprovalPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ContractApproval> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), pageReqVO.getStage()));
            }

            if(pageReqVO.getStageMark() != null) {
                predicates.add(cb.equal(root.get("stageMark"), pageReqVO.getStageMark()));
            }

            if(pageReqVO.getApprovalMark() != null) {
                predicates.add(cb.equal(root.get("approvalMark"), pageReqVO.getApprovalMark()));
            }

            if(pageReqVO.getApprovalStage() != null) {
                predicates.add(cb.equal(root.get("approvalStage"), pageReqVO.getApprovalStage()));
            }

            if(pageReqVO.getContractId() != null) {
                predicates.add(cb.equal(root.get("contractId"), pageReqVO.getContractId()));
            }

            if(pageReqVO.getOriginStage() != null) {
                predicates.add(cb.equal(root.get("originStage"), pageReqVO.getOriginStage()));
            }

            if(pageReqVO.getProcessInstanceId() != null) {
                predicates.add(cb.equal(root.get("processInstanceId"), pageReqVO.getProcessInstanceId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ContractApproval> page = contractApprovalRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ContractApproval> getContractApprovalList(ContractApprovalExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ContractApproval> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), exportReqVO.getStage()));
            }

            if(exportReqVO.getStageMark() != null) {
                predicates.add(cb.equal(root.get("stageMark"), exportReqVO.getStageMark()));
            }

            if(exportReqVO.getApprovalMark() != null) {
                predicates.add(cb.equal(root.get("approvalMark"), exportReqVO.getApprovalMark()));
            }

            if(exportReqVO.getApprovalStage() != null) {
                predicates.add(cb.equal(root.get("approvalStage"), exportReqVO.getApprovalStage()));
            }

            if(exportReqVO.getContractId() != null) {
                predicates.add(cb.equal(root.get("contractId"), exportReqVO.getContractId()));
            }

            if(exportReqVO.getOriginStage() != null) {
                predicates.add(cb.equal(root.get("originStage"), exportReqVO.getOriginStage()));
            }

            if(exportReqVO.getProcessInstanceId() != null) {
                predicates.add(cb.equal(root.get("processInstanceId"), exportReqVO.getProcessInstanceId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return contractApprovalRepository.findAll(spec);
    }

    private Sort createSort(ContractApprovalPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整


        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getStage() != null) {
            orders.add(new Sort.Order(order.getStage().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "stage"));
        }

        if (order.getStageMark() != null) {
            orders.add(new Sort.Order(order.getStageMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "stageMark"));
        }

        if (order.getApprovalMark() != null) {
            orders.add(new Sort.Order(order.getApprovalMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "approvalMark"));
        }

        if (order.getApprovalStage() != null) {
            orders.add(new Sort.Order(order.getApprovalStage().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "approvalStage"));
        }

        if (order.getContractId() != null) {
            orders.add(new Sort.Order(order.getContractId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "contractId"));
        }

        if (order.getOriginStage() != null) {
            orders.add(new Sort.Order(order.getOriginStage().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "originStage"));
        }

        if (order.getProcessInstanceId() != null) {
            orders.add(new Sort.Order(order.getProcessInstanceId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "processInstanceId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}