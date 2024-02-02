package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.project.*;
import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import cn.iocoder.yudao.module.jl.enums.*;
import cn.iocoder.yudao.module.jl.repository.contractfundlog.ContractFundLogRepository;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogRepository;
import cn.iocoder.yudao.module.jl.repository.crm.CustomerRepository;
import cn.iocoder.yudao.module.jl.repository.crm.CustomerSimpleRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractSimpleRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectDocumentRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.repository.projectfundlog.ProjectFundLogRepository;
import cn.iocoder.yudao.module.jl.service.statistic.StatisticUtils;
import cn.iocoder.yudao.module.jl.utils.DateAttributeGenerator;
import cn.iocoder.yudao.module.jl.utils.UniqCodeGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Predicate;

import java.util.*;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectConstractMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.*;

/**
 * 项目合同 Service 实现类
 */
@Service
@Validated
public class ProjectConstractServiceImpl implements ProjectConstractService {
    @Resource
    private DateAttributeGenerator dateAttributeGenerator;
    private final String uniqCodeKey = AUTO_INCREMENT_KEY_PROJECT_CONTRACT_CODE.getKeyTemplate();
    private final String uniqCodePrefixKey = PREFIX_PROJECT_CONTRACT_CODE.getKeyTemplate();
    @Resource
    private ProjectConstractRepository projectConstractRepository;
    @Resource
    private ProjectConstractSimpleRepository projectConstractSimpleRepository;
    @Resource
    private UniqCodeGenerator uniqCodeGenerator;

    @Resource
    private ProjectFundLogRepository projectFundLogRepository;

    @Resource
    private ContractFundLogRepository contractFundLogRepository;
    @Resource
    private ContractInvoiceLogRepository contractInvoiceLogRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private ProjectDocumentServiceImpl projectDocumentService;

/*    @PostConstruct
    public void ProjectConstractServiceImpl() {
        ProjectConstract firstByOrderByIdDesc = projectConstractRepository.findFirstByOrderByIdDesc();
        uniqCodeGenerator.setInitUniqUid(firstByOrderByIdDesc != null ? firstByOrderByIdDesc.getId() : 0L,uniqCodeKey,uniqCodePrefixKey, PROJECT_CONTRACT_CODE_DEFAULT_PREFIX);
    }*/


    public String generateCode() {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return String.format("%s%s%04d", "contractx", dateStr, uniqCodeGenerator.generateUniqUid());
    }


    @Resource
    private CustomerSimpleRepository customerSimpleRepository;
    @Resource
    private ProjectConstractMapper projectConstractMapper;

    @Resource
    private ProjectServiceImpl projectService;

    @Override
    @Transactional
    public Long createProjectConstract(ProjectConstractCreateReqVO createReqVO) {

        // 如果有项目id，则校验项目是否存在
        if(createReqVO.getProjectId()!=null&& createReqVO.getProjectId()>0){
            ProjectSimple projectSimple = projectService.validateProjectExists(createReqVO.getProjectId());
            createReqVO.setCustomerId(projectSimple.getCustomerId());
            createReqVO.setSalesId(projectSimple.getSalesId());
        }else{
            createReqVO.setProjectId(null);
        }

        if(createReqVO.getCustomerId()!=null){
            Optional<CustomerOnly> customerOnlyOptional = customerSimpleRepository.findById(createReqVO.getCustomerId());
            if (customerOnlyOptional.isEmpty()) {
                throw exception(CUSTOMER_NOT_EXISTS);
            }
            createReqVO.setSalesId(customerOnlyOptional.get().getSalesId());
        }



        ProjectConstract bySn = projectConstractRepository.findBySn(createReqVO.getSn());
        if (bySn != null) {
            return 0L;
        }

        // 插入
//        createReqVO.setSn(generateCode());
        ProjectConstract projectConstract = projectConstractMapper.toEntity(createReqVO);
        if (projectConstract.getRealPrice() == null) {
            projectConstract.setRealPrice(projectConstract.getPrice() == null ? BigDecimal.valueOf(0) : projectConstract.getPrice());
        }
        projectConstract.setStatus(ProjectContractStatusEnums.SIGNED.getStatus());
//        projectConstract.setStatus(ProjectContractStatusEnums.SIGNED.getStatus());
//        projectConstract.setStampFileUrl(createReqVO.getFileUrl());
//        projectConstract.setStampFileName(createReqVO.getFileName());
        projectConstract.setCustomerId(createReqVO.getCustomerId());
        projectConstract.setSalesId(createReqVO.getSalesId());
        ProjectConstract projectContractSave = projectConstractRepository.save(projectConstract);

        //projectDocument存一份
//        Long projectDocumentId = projectDocumentService.createProjectDocumentWithoutReq(byId.get().getId(), createReqVO.getFileName(), createReqVO.getFileUrl(), ProjectDocumentTypeEnums.CONTRACT.getStatus());

        //更新document id
//        projectConstractRepository.updateProjectDocumentIdById(projectDocumentId, projectContractSave.getId());

        // 返回
        return projectConstract.getId();
    }

    public void processContractReceivedPrice(Long contractId) {
        List<ProjectFundLog> projectFundLogs = projectFundLogRepository.findAllByContractId(contractId);
        BigDecimal priceSum = BigDecimal.ZERO;

        for (ProjectFundLog log : projectFundLogs) {
//            priceSum += log.getPrice();
            priceSum=priceSum.add(log.getPrice());

        }
        projectConstractRepository.updateReceivedPriceById(priceSum, contractId);
    }

    public void processContractReceivedPrice2(Long contractId) {
        List<ContractFundLog> projectFundLogs = contractFundLogRepository.findAllByContractId(contractId);
        BigDecimal priceSum = BigDecimal.ZERO;

        for (ContractFundLog log : projectFundLogs) {
            if (Objects.equals(log.getStatus(), ContractFundStatusEnums.AUDITED.getStatus()) && log.getReceivedPrice() != null) {
//                priceSum += log.getReceivedPrice();
                priceSum=priceSum.add(log.getReceivedPrice());

            }
        }
        projectConstractRepository.updateReceivedPriceById(priceSum, contractId);
    }

    public void processContractInvoicedPrice2(Long contractId) {
        List<ContractInvoiceLog> list = contractInvoiceLogRepository.findByContractId(contractId);
        BigDecimal priceSum = BigDecimal.ZERO;
        for (ContractInvoiceLog log : list) {
            if (Objects.equals(log.getStatus(), ContractInvoiceStatusEnums.INVOICED.getStatus()) && log.getReceivedPrice() != null) {
//                priceSum += log.getReceivedPrice();
                priceSum=priceSum.add(log.getReceivedPrice());
            }
        }
        projectConstractRepository.updateInvoicedPriceById(priceSum, contractId);
    }

    @Override
    public void updateProjectConstract(ProjectConstractUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectConstractExists(updateReqVO.getId());
        // 更新
        ProjectConstract updateObj = projectConstractMapper.toEntity(updateReqVO);
        projectConstractRepository.save(updateObj);
    }

    @Override
    public void updateProjectConstractPayStatus(ProjectConstractUpdatePayStatusReqVO updateReqVO) {
        projectConstractRepository.updatePayStatusById(updateReqVO.getPayStatus(), updateReqVO.getId());
    }

    @Override
    public void updateFieldProjectConstract(ProjectConstractUpdateFieldReqVO updateReqVO) {
        // 校验存在
        ProjectConstract projectConstract = validateProjectConstractExists(updateReqVO.getId());
        // 更新

        ProjectConstract updateObj = projectConstractMapper.toEntity(updateReqVO);
        // Copy non-null properties from updateObj to projectConstract
        copyNonNullProperties(updateObj, projectConstract);
        projectConstractRepository.save(projectConstract);
    }


    private <T> void copyNonNullProperties(T source, T destination) {
        try {
            Class<?> sourceClass = source.getClass();
            Class<?> destinationClass = destination.getClass();

            for (Field sourceField : sourceClass.getDeclaredFields()) {
                sourceField.setAccessible(true);
                Object sourceValue = sourceField.get(source);

                // Only copy non-null properties
                if (sourceValue != null) {
                    Field destinationField = destinationClass.getDeclaredField(sourceField.getName());
                    destinationField.setAccessible(true);
                    destinationField.set(destination, sourceValue);
                }
            }
        } catch (Exception e) {
            // Handle any exceptions that might occur during the copy process
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProjectConstract(Long id) {
        // 校验存在
        validateProjectConstractExists(id);
        // 删除
        projectConstractRepository.deleteById(id);
    }

    public ProjectConstract validateProjectConstractExists(Long id) {
        Optional<ProjectConstract> byId = projectConstractRepository.findById(id);
        if (byId.isEmpty()) {
            throw exception(PROJECT_CONSTRACT_NOT_EXISTS);
        }
        return byId.orElse(null);
    }

    @Override
    public Optional<ProjectConstract> getProjectConstract(Long id) {
        Optional<ProjectConstract> byId = projectConstractRepository.findById(id);
        return byId;
    }

    @Override
    public List<ProjectConstract> getProjectConstractList(Collection<Long> ids) {
        return StreamSupport.stream(projectConstractRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectConstract> getProjectConstractPage(ProjectConstractPageReqVO pageReqVO, ProjectConstractPageOrder orderV0) {



        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectConstract> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if(pageReqVO.getCreatorIds()==null){
                if (pageReqVO.getCustomerId() != null) {
                    predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
                } else {

                    if (!pageReqVO.getAttribute().equals(DataAttributeTypeEnums.ANY.getStatus())) {
                        Long[] users = pageReqVO.getSalesId()!=null?dateAttributeGenerator.processAttributeUsersWithUserId(pageReqVO.getAttribute(), pageReqVO.getSalesId()):dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
                        predicates.add(root.get("salesId").in(Arrays.stream(users).toArray()));
                    }
                }
            }else{
                predicates.add(root.get("creator").in(Arrays.stream(pageReqVO.getCreatorIds()).toArray()));
            }

            if(pageReqVO.getTimeRange()!=null){
                predicates.add(cb.between(root.get("signedTime"), StatisticUtils.getStartTimeByTimeRange(pageReqVO.getTimeRange()), LocalDateTime.now()));
            }

            if (pageReqVO.getReceivedStatus() != null) {
                switch (pageReqVO.getReceivedStatus()) {
                    case "ALL_PAY":
                        predicates.add(cb.greaterThanOrEqualTo(root.get("receivedPrice"), root.get("price")));
                        break;
                    case "PART":
                        predicates.add(cb.and(
                                cb.lessThan(root.get("receivedPrice"), root.get("price")),
                                cb.greaterThan(root.get("receivedPrice"), 0)
                        ));
                        break;
                    case "NO":
                        predicates.add(cb.or(
                                cb.equal(root.get("receivedPrice"), 0),
                                cb.isNull(root.get("receivedPrice"))
                        ));
                        break;
                    // Add more cases if needed
                    case "NOT_ALL_PAY":
                    predicates.add(cb.or(
                            cb.lessThan(root.get("receivedPrice"), root.get("price")),
                            cb.equal(root.get("receivedPrice"), 0),
                            cb.isNull(root.get("receivedPrice"))
                    ));
                    break;
                    default:
                        break;
                }
            }

            if (pageReqVO.getInvoicedStatus() != null) {
                switch (pageReqVO.getInvoicedStatus()) {
                    case "ALL_PAY":
                        predicates.add(cb.greaterThanOrEqualTo(root.get("invoicedPrice"), root.get("receivedPrice")));
                        break;
                    case "PART":
                        predicates.add(cb.and(
                                cb.lessThan(root.get("invoicedPrice"), root.get("receivedPrice")),
                                cb.greaterThan(root.get("invoicedPrice"), 0)
                        ));
                        break;
                    case "NO":
                        predicates.add(cb.or(
                                cb.equal(root.get("invoicedPrice"), 0),
                                cb.isNull(root.get("invoicedPrice"))
                        ));
                        break;
                    // Add more cases if needed
                    default:
                        break;
                }
            }

            if (pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if (pageReqVO.getKeyword() != null) {
                Predicate namePredicate = cb.like(root.get("name"), "%" + pageReqVO.getKeyword() + "%");
                Predicate snPredicate = cb.like(root.get("sn"), "%" + pageReqVO.getKeyword() + "%");

                // Combine the predicates with 'or' (or 'and', depending on your needs)
                predicates.add(cb.or(namePredicate, snPredicate));
            }

            //如果创建时间不为空，查询创建时间在开始时间和结束时间之间的数据
            if (pageReqVO.getCreateTime() != null) {
                predicates.add(cb.between(root.get("createTime"), pageReqVO.getCreateTime()[0], pageReqVO.getCreateTime()[1]));
            }

            if (pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if (pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }

            if (pageReqVO.getIsOuted() != null) {
                predicates.add(cb.equal(root.get("isOuted"), pageReqVO.getIsOuted()));
            }

            if (pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if (pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if (pageReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), pageReqVO.getPrice()));
            }

            if (pageReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), pageReqVO.getSalesId()));
            }

            if (pageReqVO.getSn() != null) {
                predicates.add(cb.like(root.get("sn"), "%" + pageReqVO.getSn() + "%"));
            }

            if (pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };


        // 执行查询
        Page<ProjectConstract> page = projectConstractRepository.findAll(spec, pageable);

/*        page.getContent().forEach(contract -> {
            if (contract.getApprovalList().size() > 0) {
                contract.setLatestApproval(contract.getApprovalList().get(0));
            }
        });*/


        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public PageResult<ProjectConstractOnly> getProjectConstractSimplePage(ProjectConstractPageReqVO pageReqVO, ProjectConstractPageOrder orderV0) {


        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectConstractOnly> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if (pageReqVO.getKeyword() != null) {
                Predicate namePredicate = cb.like(root.get("name"), "%" + pageReqVO.getKeyword() + "%");
                Predicate snPredicate = cb.like(root.get("sn"), "%" + pageReqVO.getKeyword() + "%");

                // Combine the predicates with 'or' (or 'and', depending on your needs)
                predicates.add(cb.or(namePredicate, snPredicate));
            }

            if (pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if (pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }

            if (pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }


            if (pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if (pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if (pageReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), pageReqVO.getPrice()));
            }

            if (pageReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), pageReqVO.getSalesId()));
            }

            if (pageReqVO.getSn() != null) {
                predicates.add(cb.like(root.get("sn"), "%" + pageReqVO.getSn() + "%"));
            }

            if (pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };


        // 执行查询
        Page<ProjectConstractOnly> page = projectConstractSimpleRepository.findAll(spec, pageable);

//        List<ProjectConstractOnly> contracts = page.getContent();

        //计算已收金额

/*        if (contracts.size() > 0) {
            contracts.forEach(item -> {
                Integer receivedPrice = 0;
                if (item.getFundLogs().size() > 0) {
                    receivedPrice = item.getFundLogs().stream()
                            .mapToInt(ProjectFundLog::getPrice)
                            .sum();
                }

                item.setReceivedPrice(receivedPrice);
            });
        }*/


        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectConstract> getProjectConstractList(ProjectConstractExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectConstract> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if (exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if (exportReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), exportReqVO.getFileUrl()));
            }

            if (exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if (exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if (exportReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), exportReqVO.getPrice()));
            }

            if (exportReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), exportReqVO.getSalesId()));
            }

            if (exportReqVO.getSn() != null) {
                predicates.add(cb.equal(root.get("sn"), exportReqVO.getSn()));
            }

            if (exportReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + exportReqVO.getFileName() + "%"));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectConstractRepository.findAll(spec);
    }

    private Sort createSort(ProjectConstractPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getFileUrl() != null) {
            orders.add(new Sort.Order(order.getFileUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileUrl"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getPrice() != null) {
            orders.add(new Sort.Order(order.getPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "price"));
        }

        if (order.getSalesId() != null) {
            orders.add(new Sort.Order(order.getSalesId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salesId"));
        }

        if (order.getSn() != null) {
            orders.add(new Sort.Order(order.getSn().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sn"));
        }

        if (order.getFileName() != null) {
            orders.add(new Sort.Order(order.getFileName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileName"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}