package cn.iocoder.yudao.module.jl.service.contractfundlog;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.SupplierImportRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.SupplierImportVO;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import cn.iocoder.yudao.module.jl.entity.project.Supplier;
import cn.iocoder.yudao.module.jl.enums.ContractFundStatusEnums;
import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import cn.iocoder.yudao.module.jl.repository.commonattachment.CommonAttachmentRepository;
import cn.iocoder.yudao.module.jl.service.commonattachment.CommonAttachmentServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProjectConstractServiceImpl;
import cn.iocoder.yudao.module.jl.service.statistic.StatisticUtils;
import cn.iocoder.yudao.module.jl.utils.DateAttributeGenerator;
import com.xingyuv.captcha.util.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
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
import cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.contractfundlog.ContractFundLogMapper;
import cn.iocoder.yudao.module.jl.repository.contractfundlog.ContractFundLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.USER_IMPORT_LIST_IS_EMPTY;

/**
 * 合同收款记录 Service 实现类
 *
 */
@Service
@Validated
public class ContractFundLogServiceImpl implements ContractFundLogService {

    @Resource
    private ContractFundLogRepository contractFundLogRepository;

    @Resource
    private ContractFundLogMapper contractFundLogMapper;

    @Resource
    private ProjectConstractServiceImpl projectConstractService;

    @Resource
    private DateAttributeGenerator dateAttributeGenerator;

    @Resource
    private CommonAttachmentServiceImpl commonAttachmentService;


    @Override
    @Transactional
    public Long createContractFundLog(ContractFundLogCreateReqVO createReqVO) {
        //查询合同是否存在
        ProjectConstract projectConstract = projectConstractService.validateProjectConstractExists(createReqVO.getContractId());

        // 如果status不为空，则记录auditId为当前登录用户
        if(Objects.equals(createReqVO.getStatus(), ContractFundStatusEnums.AUDITED.getStatus())){
            createReqVO.setAuditId(getLoginUserId());
        }





        // 插入
        ContractFundLog contractFundLog = contractFundLogMapper.toEntity(createReqVO);
        contractFundLog.setProjectId(projectConstract.getProjectId());
        contractFundLog.setCustomerId(projectConstract.getCustomerId());
        contractFundLog.setSalesId(projectConstract.getSalesId());

        contractFundLogRepository.save(contractFundLog);

        projectConstractService.processContractReceivedPrice2(createReqVO.getContractId());

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(contractFundLog.getId(),"CONTRACT_FUND_LOG",createReqVO.getAttachmentList());

        // 返回
        return contractFundLog.getId();
    }

    @Override
    @Transactional
    public void updateContractFundLog(ContractFundLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateContractFundLogExists(updateReqVO.getId());


        // 如果status不为空，则记录auditId为当前登录用户
        if(Objects.equals(updateReqVO.getStatus(), ContractFundStatusEnums.AUDITED.getStatus())){
            updateReqVO.setAuditId(getLoginUserId());
        }

        // 更新
        ContractFundLog updateObj = contractFundLogMapper.toEntity(updateReqVO);
        contractFundLogRepository.save(updateObj);

        projectConstractService.processContractReceivedPrice2(updateReqVO.getContractId());

        commonAttachmentService.saveAttachmentList(updateReqVO.getId(),"CONTRACT_FUND_LOG",updateReqVO.getAttachmentList());
    }

    @Override
    @Transactional
    public void deleteContractFundLog(Long id) {
        // 校验存在
        ContractFundLog contractFundLog = validateContractFundLogExists(id);
        // 删除
        contractFundLogRepository.deleteById(id);

        projectConstractService.processContractReceivedPrice2(contractFundLog.getContractId());
    }

    private ContractFundLog validateContractFundLogExists(Long id) {
        Optional<ContractFundLog> byId = contractFundLogRepository.findById(id);

        if (byId.isEmpty()){
            throw exception(CONTRACT_FUND_LOG_NOT_EXISTS);
        }
        return byId.orElse(null);
    }

    @Override
    public Optional<ContractFundLog> getContractFundLog(Long id) {
        return contractFundLogRepository.findById(id);
    }

    @Override
    public List<ContractFundLog> getContractFundLogList(Collection<Long> ids) {
        return StreamSupport.stream(contractFundLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ContractFundLog> getContractFundLogPage(ContractFundLogPageReqVO pageReqVO, ContractFundLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);



        // 创建 Specification
        Specification<ContractFundLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getContractIds()==null&&pageReqVO.getUserIds()==null){
                if(pageReqVO.getCustomerId() != null) {
                    predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
                }else{
                    //如果不是any，则都是in查询
                    if(!pageReqVO.getAttribute().equals(DataAttributeTypeEnums.ANY.getStatus())&&pageReqVO.getContractId()==null){
                        Long[] users = pageReqVO.getSalesId()!=null?dateAttributeGenerator.processAttributeUsersWithUserId(pageReqVO.getAttribute(), pageReqVO.getSalesId()):dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
                        //                Long[] users = dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
                        predicates.add(root.get("salesId").in(Arrays.stream(users).toArray()));
                    }
                }
            }else{
            }

            if(pageReqVO.getContractIds()!=null){
                predicates.add(root.get("contractId").in(Arrays.stream(pageReqVO.getContractIds()).toArray()));
            }

            if(pageReqVO.getUserIds()!=null){
                predicates.add(root.get("salesId").in(Arrays.stream(pageReqVO.getUserIds()).toArray()));
            }

            if(pageReqVO.getTimeRange()!=null){
                predicates.add(cb.between(root.get("paidTime"), StatisticUtils.getStartTimeByTimeRange(pageReqVO.getTimeRange()), LocalDateTime.now()));
            }

            if(pageReqVO.getMonth()!=null){
                LocalDateTime[] startAndEndTimeByMonth = StatisticUtils.getStartAndEndTimeByMonth(pageReqVO.getMonth());
                predicates.add(cb.between(root.get("paidTime"), startAndEndTimeByMonth[0], startAndEndTimeByMonth[1]));
            }

            if(pageReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), pageReqVO.getSalesId()));
            }

            if(pageReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), pageReqVO.getPrice()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }

            if(pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getPaidTime() != null) {
                predicates.add(cb.between(root.get("paidTime"), pageReqVO.getPaidTime()[0], pageReqVO.getPaidTime()[1]));
            } 
            if(pageReqVO.getPayer() != null) {
                predicates.add(cb.equal(root.get("payer"), pageReqVO.getPayer()));
            }

            if(pageReqVO.getPayee() != null) {
                predicates.add(cb.equal(root.get("payee"), pageReqVO.getPayee()));
            }

            if(pageReqVO.getContractId() != null) {
                predicates.add(cb.equal(root.get("contractId"), pageReqVO.getContractId()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }




            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ContractFundLog> page = contractFundLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ContractFundLog> getContractFundLogList(ContractFundLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ContractFundLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), exportReqVO.getPrice()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), exportReqVO.getFileUrl()));
            }

            if(exportReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + exportReqVO.getFileName() + "%"));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getPaidTime() != null) {
                predicates.add(cb.between(root.get("paidTime"), exportReqVO.getPaidTime()[0], exportReqVO.getPaidTime()[1]));
            } 
            if(exportReqVO.getPayer() != null) {
                predicates.add(cb.equal(root.get("payer"), exportReqVO.getPayer()));
            }

            if(exportReqVO.getPayee() != null) {
                predicates.add(cb.equal(root.get("payee"), exportReqVO.getPayee()));
            }

            if(exportReqVO.getContractId() != null) {
                predicates.add(cb.equal(root.get("contractId"), exportReqVO.getContractId()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return contractFundLogRepository.findAll(spec);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public ContractFundLogImportRespVO importList(List<ContractFundLogImportVO> importUsers, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importUsers)) {
            throw exception(USER_IMPORT_LIST_IS_EMPTY);
        }
        ContractFundLogImportRespVO respVO = ContractFundLogImportRespVO.builder().createNames(new ArrayList<>())
                .updateNames(new ArrayList<>()).failureNames(new LinkedHashMap<>()).build();
        importUsers.forEach(item -> {

            // entity.
            ContractFundLog entity = contractFundLogMapper.toEntity(item);

            contractFundLogRepository.save(entity);



            respVO.getCreateNames().add(item.getPayCompanyName());


        });
        return respVO;
    }

    private Sort createSort(ContractFundLogPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));


        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getPrice() != null) {
            orders.add(new Sort.Order(order.getPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "price"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getFileUrl() != null) {
            orders.add(new Sort.Order(order.getFileUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileUrl"));
        }

        if (order.getFileName() != null) {
            orders.add(new Sort.Order(order.getFileName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileName"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getPaidTime() != null) {
            orders.add(new Sort.Order(order.getPaidTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "paidTime"));
        }

        if (order.getPayer() != null) {
            orders.add(new Sort.Order(order.getPayer().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "payer"));
        }

        if (order.getPayee() != null) {
            orders.add(new Sort.Order(order.getPayee().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "payee"));
        }

        if (order.getContractId() != null) {
            orders.add(new Sort.Order(order.getContractId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "contractId"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}