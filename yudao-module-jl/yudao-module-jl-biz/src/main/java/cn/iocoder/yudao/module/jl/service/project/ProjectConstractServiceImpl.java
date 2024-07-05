package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.module.bpm.enums.message.BpmMessageEnum;
import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerSimple;
import cn.iocoder.yudao.module.jl.entity.project.*;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.enums.*;
import cn.iocoder.yudao.module.jl.repository.contractfundlog.ContractFundLogRepository;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogRepository;
import cn.iocoder.yudao.module.jl.repository.crm.CustomerSimpleRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractSimpleRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.repository.projectfundlog.ProjectFundLogRepository;
import cn.iocoder.yudao.module.jl.repository.user.UserRepository;
import cn.iocoder.yudao.module.jl.service.statistic.StatisticUtils;
import cn.iocoder.yudao.module.jl.service.subjectgroupmember.SubjectGroupMemberServiceImpl;
import cn.iocoder.yudao.module.jl.service.user.UserServiceImpl;
import cn.iocoder.yudao.module.jl.utils.CommonPageSortUtils;
import cn.iocoder.yudao.module.jl.utils.DateAttributeGenerator;
import cn.iocoder.yudao.module.jl.utils.UniqCodeGenerator;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.*;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.jl.utils.JLSqlUtils.mysqlFindInSet;
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
    private ProjectConstractOnlyRepository projectConstractOnlyRepository;

    @Resource
    private UniqCodeGenerator uniqCodeGenerator;

    @Resource
    private ContractFundLogRepository contractFundLogRepository;
    @Resource
    private ContractInvoiceLogRepository contractInvoiceLogRepository;


    @Resource
    private SubjectGroupMemberServiceImpl subjectGroupMemberService;

    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserServiceImpl userService;

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
      //暂存一下salesId
        Long salesId = createReqVO.getSalesId();
        ProjectSimple projectSimple=null;
        CustomerSimple customerSimple= null;
        // 如果有项目id，则校验项目是否存在
        if(createReqVO.getProjectId()!=null&& createReqVO.getProjectId()>0){
            projectSimple= projectService.validateProjectExists(createReqVO.getProjectId());
            createReqVO.setCustomerId(projectSimple.getCustomerId());
            createReqVO.setSalesId(projectSimple.getSalesId());
            customerSimple=projectSimple.getCustomer();
        }else{
            createReqVO.setProjectId(null);
        }

        if(createReqVO.getCustomerId()!=null){
            Optional<CustomerSimple> customerOnlyOptional = customerSimpleRepository.findById(createReqVO.getCustomerId());
            if (customerOnlyOptional.isEmpty()) {
                throw exception(CUSTOMER_NOT_EXISTS);
            }
            customerSimple= customerOnlyOptional.get();
            createReqVO.setSalesId(customerSimple.getSalesId());
        }


        ProjectConstract bySn = projectConstractRepository.findBySn(createReqVO.getSn());
        if (bySn != null) {
            return 0L;
        }

        //如果传递了销售id，则重新设置一下传递的销售id
        if(salesId!=null){
            createReqVO.setSalesId(salesId);
        }

        ProjectConstract projectConstract = projectConstractMapper.toEntity(createReqVO);
        if (projectConstract.getRealPrice() == null) {
            projectConstract.setRealPrice(projectConstract.getPrice() == null ? BigDecimal.valueOf(0) : projectConstract.getPrice());
        }
        projectConstract.setStatus(ProjectContractStatusEnums.SIGNED.getStatus());
        projectConstract.setCustomerId(createReqVO.getCustomerId());
        projectConstract.setSalesId(createReqVO.getSalesId());
        projectConstractRepository.save(projectConstract);

        // 查询销售
        User user = userService.validateUserExists(createReqVO.getSalesId());

        // 发送通知
        List<Long> userIds = new ArrayList<>();
        Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("id",projectConstract.getId());
        String format = createReqVO.getSignedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String content = String.format("%s与客户(%s)新增签订合同(%s),金额：%s,日期：%s",user.getNickname(),customerSimple!=null?customerSimple.getName():createReqVO.getCustomerId(),createReqVO.getName(),createReqVO.getPrice(),format);
        templateParams.put("content",content);
        System.out.println("content---"+content);
        // 总经理
        userIds.add(getSuperUserId());
        // 销售总监
        userIds.addAll(List.of(getSalesManagerIds()));
        List<User> financeUsers = userRepository.findFinanceUsers();
        // 商务组长
        if(createReqVO.getSalesId()!=null){
            List<Long> bizManagerIdByUserId = userService.findBizManagerIdByUserId(createReqVO.getSalesId());
            userIds.addAll(bizManagerIdByUserId);
        }

        // 通知所有财务人员
        if(financeUsers!=null){
            List<Long> collect = financeUsers.stream().map(User::getId).collect(Collectors.toList());
            userIds.addAll(collect);
        }
        // 通知项目人员
        if(projectSimple!=null&&projectSimple.getManagerId()!=null){
            userIds.add(projectSimple.getManagerId());
        }
        // userIds去重
        userIds = userIds.stream().distinct().collect(Collectors.toList());
        for (Long userId : userIds) {
            if(userId==null){
                continue;
            }
            System.out.println("contract userid---"+userId);
          notifyMessageSendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO(
                    userId,
                    BpmMessageEnum.NOTIFY_WHEN_CONTRACT_CREATE.getTemplateCode(), templateParams
            ));
        }
        // 返回
        return projectConstract.getId();
    }

    @Transactional
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

    @Transactional
    public void processContractInvoicedPrice2(Long contractId) {
        List<ContractInvoiceLog> list = contractInvoiceLogRepository.findByContractId(contractId);
        BigDecimal priceSum = BigDecimal.ZERO;
        for (ContractInvoiceLog log : list) {
/*            if (Objects.equals(log.getStatus(), ContractInvoiceStatusEnums.INVOICED.getStatus()) && log.getReceivedPrice() != null) {
                priceSum=priceSum.add(log.getReceivedPrice());
            }*/
            if(log.getPrice()!=null&&(Objects.equals(log.getStatus(), ContractInvoiceStatusEnums.RECEIVED_NO.getStatus())||Objects.equals(log.getStatus(), ContractInvoiceStatusEnums.RECEIVED.getStatus()))){
                priceSum=priceSum.add(log.getPrice());
            }

        }
        projectConstractRepository.updateInvoicedPriceById(priceSum, contractId);
    }

    @Override
    @Transactional
    public void updateProjectConstract(ProjectConstractUpdateReqVO updateReqVO) {
        // 暂存一下salesId
        Long salesId = updateReqVO.getSalesId();
        // 校验存在
        ProjectConstract projectConstract = validateProjectConstractExists(updateReqVO.getId());

        // 如果salesId是null
        if(salesId==null){
            updateReqVO.setSalesId(projectConstract.getSalesId());
        }
        // 更新
        ProjectConstract updateObj = projectConstractMapper.toEntity(updateReqVO);
        projectConstractRepository.save(updateObj);

        processContractInvoicedPrice2(updateReqVO.getId());
        processContractReceivedPrice2(updateReqVO.getId());
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

    @Transactional
    public Boolean refreshContractPriceData() {

        List<ProjectConstractOnly> all1 = projectConstractOnlyRepository.findAll();
        all1.forEach(contract->{
            processContractReceivedPrice2(contract.getId());
            processContractInvoicedPrice2(contract.getId());
            System.out.println("成功--"+contract.getName());
        });

        return true;
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
        Specification<ProjectConstract> spec = getSpecification(pageReqVO);


        // 执行查询
        Page<ProjectConstract> page = projectConstractRepository.findAll(spec, pageable);


        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @NotNull
    private <T>Specification<T> getSpecification(ProjectConstractPageReqVO pageReqVO) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            //高级查询

            if (pageReqVO.getSignedTimeDayEnd() != null) {
                // 查询签订日期 到现在 在getSignedTimeDayEnd天内的数据，需要换算天数
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime minus = now.minusDays(pageReqVO.getSignedTimeDayEnd());
                predicates.add(cb.lessThanOrEqualTo(root.get("signedTime"), minus));
            }

            if (pageReqVO.getReceivedPercentEnd() != null) {
                predicates.add(cb.or(
                        cb.isNull(root.get("receivedPercent")),
                        cb.lessThanOrEqualTo(root.get("receivedPercent"), pageReqVO.getReceivedPercentEnd() / 100)
                ));
            }


            if (pageReqVO.getCreatorIds() == null) {
                if (pageReqVO.getCustomerId() != null) {
                    predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
                } else {

                    if (!pageReqVO.getAttribute().equals(DataAttributeTypeEnums.ANY.getStatus()) && pageReqVO.getPiGroupId() == null) {
                        Long[] users = pageReqVO.getSalesId() != null ? dateAttributeGenerator.processAttributeUsersWithUserId(pageReqVO.getAttribute(), pageReqVO.getSalesId()) : dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
//                        predicates.add(root.get("salesId").in(Arrays.stream(users).toArray()));
                        predicates.add(cb.or(
                                root.get("salesId").in(Arrays.asList(users)),
                                root.get("projectManagerId").in(Arrays.asList(users))
                        ));
                    }
                }
            }

            // 这个是PI组看板传过来的，查的就是销售的
            if (pageReqVO.getCreatorIds() != null) {
                // 都是用salesId查询，但是前端这里之前传的是getCreatorIds，先不改，创建者其实没有意义
                predicates.add(root.get("salesId").in(Arrays.stream(pageReqVO.getCreatorIds()).toArray()));
//                predicates.add(cb.or(
//                        root.get("salesId").in(Arrays.asList(pageReqVO.getCreatorIds())),
//                        root.get("projectManagerId").in(Arrays.asList(pageReqVO.getCreatorIds()))
//                ));
            }

            if (Objects.equals(pageReqVO.getAttribute(), DataAttributeTypeEnums.MY.getStatus())) {
//                predicates.add(root.get("salesId").in(Collections.singletonList(getLoginUserId())));
                predicates.add(cb.or(
                        root.get("salesId").in(Collections.singletonList(getLoginUserId())),
                        root.get("projectManagerId").in(Collections.singletonList(getLoginUserId()))
                ));
            }

            if (pageReqVO.getPiGroupId() != null) {
                Long[] membersUserIdsByGroupId = subjectGroupMemberService.findMembersUserIdsByGroupId(pageReqVO.getPiGroupId());
//                predicates.add(root.get("salesId").in(Arrays.stream(membersUserIdsByGroupId).toArray()));
                predicates.add(cb.or(
                        root.get("salesId").in(Arrays.stream(membersUserIdsByGroupId).toArray()),
                        root.get("projectManagerId").in(Arrays.stream(membersUserIdsByGroupId).toArray())
                ));
            }


            if (pageReqVO.getProjectTagId() != null) {
                mysqlFindInSet(pageReqVO.getProjectTagId(), "projectTagIds", root, cb, predicates);
            }

            if (pageReqVO.getProjectStage() != null) {
                predicates.add(cb.equal(root.get("projectStage"), pageReqVO.getProjectStage()));
            }

            if (pageReqVO.getMonth() != null) {
                LocalDateTime[] startAndEndTimeByMonth = StatisticUtils.getStartAndEndTimeByMonth(pageReqVO.getMonth());
                predicates.add(cb.between(root.get("signedTime"), startAndEndTimeByMonth[0], startAndEndTimeByMonth[1]));
            }

            if (pageReqVO.getSignedTime() != null) {
                predicates.add(cb.between(root.get("signedTime"), pageReqVO.getSignedTime()[0], pageReqVO.getSignedTime()[1]));
            }

            if (pageReqVO.getTimeRange() != null) {
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
                                cb.notEqual(root.get("price"), root.get("receivedPrice")),
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

            if (pageReqVO.getProjectManagerId() != null) {
                predicates.add(cb.equal(root.get("projectManagerId"), pageReqVO.getSalesId()));
            }

            if (pageReqVO.getSn() != null) {
                predicates.add(cb.like(root.get("sn"), "%" + pageReqVO.getSn() + "%"));
            }

            if (pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public PageResult<ProjectConstractOnly> getProjectConstractSimplePage(ProjectConstractPageReqVO pageReqVO, ProjectConstractPageOrder orderV0) {


        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectConstractOnly> spec = getSpecification(pageReqVO);


        // 执行查询
        Page<ProjectConstractOnly> page = projectConstractSimpleRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectConstract> getProjectConstractList(ProjectConstractExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectConstract> spec = getSpecification(exportReqVO);

        // 执行查询
        return projectConstractRepository.findAll(spec);
    }

    private Sort createSort(ProjectConstractPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整
        CommonPageSortUtils.parseAndAddSort(orders, order.getSortFields());

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