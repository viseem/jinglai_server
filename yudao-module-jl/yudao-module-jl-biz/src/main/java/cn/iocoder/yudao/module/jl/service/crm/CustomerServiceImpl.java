package cn.iocoder.yudao.module.jl.service.crm;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import cn.iocoder.yudao.module.jl.entity.crm.Customer;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerSimple;
import cn.iocoder.yudao.module.jl.entity.customerchangelog.CustomerChangeLog;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import cn.iocoder.yudao.module.jl.enums.ContractFundStatusEnums;
import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectStageEnums;
import cn.iocoder.yudao.module.jl.mapper.crm.CustomerMapper;
import cn.iocoder.yudao.module.jl.mapper.user.UserMapper;
import cn.iocoder.yudao.module.jl.repository.contractfundlog.ContractFundLogRepository;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogRepository;
import cn.iocoder.yudao.module.jl.repository.crm.CustomerRepository;
import cn.iocoder.yudao.module.jl.repository.crm.CustomerSimpleRepository;
import cn.iocoder.yudao.module.jl.repository.crm.SalesleadRepository;
import cn.iocoder.yudao.module.jl.repository.crmsubjectgroup.CrmSubjectGroupRepository;
import cn.iocoder.yudao.module.jl.repository.customerchangelog.CustomerChangeLogRepository;
import cn.iocoder.yudao.module.jl.repository.invoiceapplication.InvoiceApplicationRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectSimpleRepository;
import cn.iocoder.yudao.module.jl.repository.user.UserRepository;
import cn.iocoder.yudao.module.jl.utils.DateAttributeGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.CUSTOMER_NOT_EXISTS;
import static cn.iocoder.yudao.module.jl.utils.JLSqlUtils.idsString2QueryList;
import static cn.iocoder.yudao.module.jl.utils.JLSqlUtils.mysqlFindInSet;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.USER_IMPORT_LIST_IS_EMPTY;

/**
 * 客户 Service 实现类
 *
 */
@Service
@Validated
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private ProjectConstractRepository projectConstractRepository;

    @Resource
    private ContractFundLogRepository contractFundLogRepository;

    @Resource
    private ContractInvoiceLogRepository contractInvoiceLogRepository;

    @Resource
    private DateAttributeGenerator dateAttributeGenerator;

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private CustomerSimpleRepository customerSimpleRepository;

    @Resource
    private ProjectSimpleRepository projectSimpleRepository;

    @Resource
    private CrmSubjectGroupRepository crmSubjectGroupRepository;

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private SalesleadRepository salesleadRepository;

    @Resource
    private ProjectOnlyRepository projectOnlyRepository;

    @Resource
    private ProjectConstractOnlyRepository projectConstractOnlyRepository;

    @Resource
    private InvoiceApplicationRepository invoiceApplicationRepository;

    @Resource
    private CustomerChangeLogRepository customerChangeLogRepository;


    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CustomerServiceImpl(UserRepository userRepository,
                               UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Long createCustomer(CustomerCreateReqVO createReqVO) {
        //暂存一下salesId
        Long salesId = createReqVO.getSalesId();

        // 查询手机号是否存在
        if(createReqVO.getPhone()!=null&& !createReqVO.getPhone().isEmpty()){
            CustomerSimple byPhone = customerSimpleRepository.findByPhone(createReqVO.getPhone());
            if(byPhone!=null){
                return 0L;
            }
        }

        if(!createReqVO.getIsSeas()){
            createReqVO.setSalesId(getLoginUserId());
        }

        if(createReqVO.getIsSeas()){
            createReqVO.setToCustomer(false);
        }

        //如果传递了销售id，则重新设置一下传递的销售id
        if(salesId!=null){
            createReqVO.setSalesId(salesId);
        }

        // 插入
        Customer customer = customerMapper.toEntity(createReqVO);
        customerRepository.save(customer);
        // 返回
        return customer.getId();
    }

    @Override
    public void updateCustomer(CustomerUpdateReqVO updateReqVO) {
        // 暂存一下salesId
        Long salesId = updateReqVO.getSalesId();
        // 校验存在
        Customer customer = validateCustomerExists(updateReqVO.getId());
        // 如果salesId是null
        if(salesId==null){
            updateReqVO.setSalesId(customer.getSalesId());
        }
        // 更新
        Customer updateObj = customerMapper.toEntity(updateReqVO);
        customerRepository.save(updateObj);
    }

    @Override
    public CustomerSimple updateAppCustomer(AppCustomerUpdateReqVO updateReqVO) {
        // 校验存在
        CustomerSimple customer = validateSimpleCustomerExists(updateReqVO.getId());
        //copy对象
        BeanUtils.copyProperties(updateReqVO,customer);
        // 更新
        return customerSimpleRepository.save(customer);
    }

    @Override
    public void toCustomer(ClueToCustomerReqVO updateReqVO) {
        // 校验存在
        validateCustomerExists(updateReqVO.getId());
        // 更新
        customerRepository.updateToCustomerById(true,updateReqVO.getId());
    }

    @Override
    public void transferCustomer(TransferCustomerReqVO vo) {
        // 校验存在
        Customer customer = validateCustomerExists(vo.getId());
        // 写变更记录
        CustomerChangeLog log = new CustomerChangeLog();
        log.setCustomerId(vo.getId());
        log.setType(vo.getType());
        log.setToOwnerId(vo.getToOwnerId());
        log.setFromOwnerId(customer.getSalesId());
        log.setMark(vo.getMark());
        customerChangeLogRepository.save(log);
        //客户
        customerRepository.updateSalesIdById(vo.getToOwnerId(),vo.getId());
        //商机
        salesleadRepository.updateCreatorByCustomerId(vo.getToOwnerId(),vo.getId());
        projectOnlyRepository.updateSalesIdByCustomerId(vo.getToOwnerId(),vo.getId());
        //合同、发票、回款、申请开票记录
        projectConstractOnlyRepository.updateSalesIdByCustomerId(vo.getToOwnerId(),vo.getId());
        contractInvoiceLogRepository.updateSalesIdByCustomerId(vo.getToOwnerId(),vo.getId());
        contractFundLogRepository.updateSalesIdByCustomerId(vo.getToOwnerId(),vo.getId());

        invoiceApplicationRepository.updateSalesIdByCustomerId(vo.getToOwnerId(),vo.getId());
    }

    @Override
    public void customerAssign2Sales(CustomerAssignToSalesReqVO updateReqVO) {
        // 校验存在
        Customer customer = validateCustomerExists(updateReqVO.getId());
        customer.setSalesId(updateReqVO.getSalesId());
        customer.setMark(updateReqVO.getMark());
        customer.setToCustomer(true);
        // 更新
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        // 校验存在
        validateCustomerExists(id);
        // 删除
        customerRepository.deleteById(id);
    }

    private Customer validateCustomerExists(Long id) {

        Optional<Customer> byId = customerRepository.findById(id);
        if(!byId.isPresent()){
            throw exception(CUSTOMER_NOT_EXISTS);
        }
        return byId.get();
    }

    private CustomerSimple validateSimpleCustomerExists(Long id) {

        Optional<CustomerSimple> byId = customerSimpleRepository.findById(id);
        if(!byId.isPresent()){
            throw exception(CUSTOMER_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<Customer> getCustomer(Long id) {
        Optional<Customer> byId = customerRepository.findById(id);
        Customer customer = byId.get();
        if(customer.getSubjectGroupIds() != null&&!customer.getSubjectGroupIds().isEmpty()){
            customer.setSubjectGroupList(idsString2QueryList(customer.getSubjectGroupIds(),crmSubjectGroupRepository));
        }
        return byId;
    }

    @Override
    public List<Customer> getCustomerList(Collection<Long> ids) {
        return StreamSupport.stream(customerRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Customer> getCustomerPage(CustomerPageReqVO pageReqVO, CustomerPageOrder orderV0) {



        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Customer> spec = getCustomerSpecification(pageReqVO);

        // 执行查询
        Page<Customer> page = customerRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @NotNull
    private <T>Specification<T> getCustomerSpecification(CustomerPageReqVO pageReqVO) {
        Specification<T> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            //获取attributeUsers


                //如果不是any，则都是in查询
            if(!pageReqVO.getAttribute().equals(DataAttributeTypeEnums.ANY.getStatus())&&!pageReqVO.getAttribute().equals(DataAttributeTypeEnums.SEAS.getStatus())){
                Long[] users = pageReqVO.getSalesId()!=null?dateAttributeGenerator.processAttributeUsersWithUserId(pageReqVO.getAttribute(), pageReqVO.getSalesId()):dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
                pageReqVO.setCreators(users);
                predicates.add(root.get("salesId").in(Arrays.stream(pageReqVO.getCreators()).toArray()));
            }

            if(pageReqVO.getAttribute().equals(DataAttributeTypeEnums.SEAS.getStatus())) {
                predicates.add(root.get("salesId").isNull());
            }else{
                predicates.add(cb.greaterThan(root.get("salesId"), 0));
            }


            if(pageReqVO.getSubjectGroupId() != null) {
                mysqlFindInSet(pageReqVO.getSubjectGroupId(),"subjectGroupIds", root, cb, predicates);
            }

/*           if(!pageReqVO.getAttribute().equals(DataAttributeTypeEnums.ANY.getStatus())){
               if(!pageReqVO.getAttribute().equals(DataAttributeTypeEnums.SEAS.getStatus())) {
                   predicates.add(root.get("salesId").in(Arrays.stream(pageReqVO.getCreators()).toArray()));
               }

               if(pageReqVO.getAttribute().equals(DataAttributeTypeEnums.SEAS.getStatus())) {
                   predicates.add(root.get("salesId").isNull());
               }
           }*/

            // hospital_id or university_id or company_id or research_id
            if(pageReqVO.getInstitutionId()!=null){
                predicates.add(cb.or(
                        cb.equal(root.get("hospitalId"), pageReqVO.getInstitutionId()),
                        cb.equal(root.get("universityId"), pageReqVO.getInstitutionId()),
                        cb.equal(root.get("companyId"), pageReqVO.getInstitutionId()),
                        cb.equal(root.get("researchId"), pageReqVO.getInstitutionId())
                ));
            }



            if(pageReqVO.getToCustomer() != null) {
                predicates.add(cb.equal(root.get("toCustomer"), pageReqVO.getToCustomer()));
            }

            if(pageReqVO.getInstitutionMark() != null) {
                predicates.add(cb.like(root.get("institutionMark"), "%" + pageReqVO.getInstitutionMark() + "%"));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getSource() != null) {
                predicates.add(cb.equal(root.get("source"), pageReqVO.getSource()));
            }

            if(pageReqVO.getPhone() != null) {
                predicates.add(cb.like(root.get("phone"), "%" + pageReqVO.getPhone() + "%"));
            }

            if(pageReqVO.getEmail() != null) {
                predicates.add(cb.equal(root.get("email"), pageReqVO.getEmail()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getWechat() != null) {
                predicates.add(cb.equal(root.get("wechat"), pageReqVO.getWechat()));
            }

            if(pageReqVO.getDoctorProfessionalRank() != null) {
                predicates.add(cb.equal(root.get("doctorProfessionalRank"), pageReqVO.getDoctorProfessionalRank()));
            }

            if(pageReqVO.getHospitalDepartment() != null) {
                predicates.add(cb.equal(root.get("hospitalDepartment"), pageReqVO.getHospitalDepartment()));
            }

            if(pageReqVO.getAcademicTitle() != null) {
                predicates.add(cb.equal(root.get("academicTitle"), pageReqVO.getAcademicTitle()));
            }

            if(pageReqVO.getAcademicCredential() != null) {
                predicates.add(cb.equal(root.get("academicCredential"), pageReqVO.getAcademicCredential()));
            }

            if(pageReqVO.getHospitalId() != null) {
                predicates.add(cb.equal(root.get("hospitalId"), pageReqVO.getHospitalId()));
            }

            if(pageReqVO.getUniversityId() != null) {
                predicates.add(cb.equal(root.get("universityId"), pageReqVO.getUniversityId()));
            }

            if(pageReqVO.getCompanyId() != null) {
                predicates.add(cb.equal(root.get("companyId"), pageReqVO.getCompanyId()));
            }

            if(pageReqVO.getProvince() != null) {
                predicates.add(cb.equal(root.get("province"), pageReqVO.getProvince()));
            }

            if(pageReqVO.getCity() != null) {
                predicates.add(cb.equal(root.get("city"), pageReqVO.getCity()));
            }

            if(pageReqVO.getArea() != null) {
                predicates.add(cb.equal(root.get("area"), pageReqVO.getArea()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getDealCount() != null) {
                predicates.add(cb.equal(root.get("dealCount"), pageReqVO.getDealCount()));
            }

            if(pageReqVO.getDealTotalAmount() != null) {
                predicates.add(cb.equal(root.get("dealTotalAmount"), pageReqVO.getDealTotalAmount()));
            }

            if(pageReqVO.getArrears() != null) {
                predicates.add(cb.equal(root.get("arrears"), pageReqVO.getArrears()));
            }

            if(pageReqVO.getLastFollowupTime() != null) {
                predicates.add(cb.between(root.get("lastFollowupTime"), pageReqVO.getLastFollowupTime()[0], pageReqVO.getLastFollowupTime()[1]));
            }


            if(pageReqVO.getLastFollowupId() != null) {
                predicates.add(cb.equal(root.get("lastFollowupId"), pageReqVO.getLastFollowupId()));
            }

            if(pageReqVO.getLastSalesleadId() != null) {
                predicates.add(cb.equal(root.get("lastSalesleadId"), pageReqVO.getLastSalesleadId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return spec;
    }


    public PageResult<CustomerSimple> getCustomerSimplePage(CustomerPageReqVO pageReqVO, CustomerPageOrder orderV0) {

        //获取attributeUsers
//        Long[] users = dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
//        pageReqVO.setCreators(users);

        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CustomerSimple> spec = getCustomerSpecification(pageReqVO);

        // 执行查询
        Page<CustomerSimple> page = customerSimpleRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<Customer> getCustomerList(CustomerExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Customer> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getSource() != null) {
                predicates.add(cb.equal(root.get("source"), exportReqVO.getSource()));
            }

            if(exportReqVO.getPhone() != null) {
                predicates.add(cb.like(root.get("phone"), "%" + exportReqVO.getPhone() + "%"));
            }

            if(exportReqVO.getEmail() != null) {
                predicates.add(cb.equal(root.get("email"), exportReqVO.getEmail()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getWechat() != null) {
                predicates.add(cb.equal(root.get("wechat"), exportReqVO.getWechat()));
            }

            if(exportReqVO.getDoctorProfessionalRank() != null) {
                predicates.add(cb.equal(root.get("doctorProfessionalRank"), exportReqVO.getDoctorProfessionalRank()));
            }

            if(exportReqVO.getHospitalDepartment() != null) {
                predicates.add(cb.equal(root.get("hospitalDepartment"), exportReqVO.getHospitalDepartment()));
            }

            if(exportReqVO.getAcademicTitle() != null) {
                predicates.add(cb.equal(root.get("academicTitle"), exportReqVO.getAcademicTitle()));
            }

            if(exportReqVO.getAcademicCredential() != null) {
                predicates.add(cb.equal(root.get("academicCredential"), exportReqVO.getAcademicCredential()));
            }

            if(exportReqVO.getHospitalId() != null) {
                predicates.add(cb.equal(root.get("hospitalId"), exportReqVO.getHospitalId()));
            }

            if(exportReqVO.getUniversityId() != null) {
                predicates.add(cb.equal(root.get("universityId"), exportReqVO.getUniversityId()));
            }

            if(exportReqVO.getCompanyId() != null) {
                predicates.add(cb.equal(root.get("companyId"), exportReqVO.getCompanyId()));
            }

            if(exportReqVO.getProvince() != null) {
                predicates.add(cb.equal(root.get("province"), exportReqVO.getProvince()));
            }

            if(exportReqVO.getCity() != null) {
                predicates.add(cb.equal(root.get("city"), exportReqVO.getCity()));
            }

            if(exportReqVO.getArea() != null) {
                predicates.add(cb.equal(root.get("area"), exportReqVO.getArea()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getDealCount() != null) {
                predicates.add(cb.equal(root.get("dealCount"), exportReqVO.getDealCount()));
            }

            if(exportReqVO.getDealTotalAmount() != null) {
                predicates.add(cb.equal(root.get("dealTotalAmount"), exportReqVO.getDealTotalAmount()));
            }

            if(exportReqVO.getArrears() != null) {
                predicates.add(cb.equal(root.get("arrears"), exportReqVO.getArrears()));
            }

            if(exportReqVO.getLastFollowupTime() != null) {
                predicates.add(cb.between(root.get("lastFollowupTime"), exportReqVO.getLastFollowupTime()[0], exportReqVO.getLastFollowupTime()[1]));
            } 
            if(exportReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), exportReqVO.getSalesId()));
            }

            if(exportReqVO.getLastFollowupId() != null) {
                predicates.add(cb.equal(root.get("lastFollowupId"), exportReqVO.getLastFollowupId()));
            }

            if(exportReqVO.getLastSalesleadId() != null) {
                predicates.add(cb.equal(root.get("lastSalesleadId"), exportReqVO.getLastSalesleadId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return customerRepository.findAll(spec);
    }

    /**
     * 为客户绑定最新的销售线索
     *
     * @param customerId
     * @param salesleadId
     * @return Boolean
     */
    @Override
    public Boolean bindLastSaleslead(Long customerId, Long salesleadId) {
        customerRepository.findById(customerId).map(customer -> {
            customer.setLastSalesleadId(salesleadId);
            return customerRepository.save(customer);
        });

        return null;
    }

    @Override
    public CustomerStatisticsRespVO getCustomerStatistics(Long id) {
        //查询已经签订的合同
        List<ProjectConstract> byCustomerIdAndStatus = projectConstractRepository.findByCustomerIdAndStatus(id, ProjectContractStatusEnums.SIGNED.getStatus());

        // 项目个数
        Integer dealCount = projectSimpleRepository.countByCodeNotNullAndCustomerId(id);
        Integer projectDoingCount = projectSimpleRepository.countByCodeNotNullAndStageAndCustomerId(ProjectStageEnums.DOING.getStatus(),id);
        Integer projectOutedCount = projectSimpleRepository.countByCodeNotNullAndStageAndCustomerId(ProjectStageEnums.OUTED.getStatus(),id);

        //计算客户的成交总金额、已开票金额
        BigDecimal dealTotalAmount = new BigDecimal(0);
        for (ProjectConstract projectConstract : byCustomerIdAndStatus) {
            dealTotalAmount = dealTotalAmount.add(projectConstract.getPrice());
        }
        //查询客户的款项记录
        List<ContractFundLog> fundLogs = contractFundLogRepository.findByCustomerId(id);
        //计算客户的已付款项总额
        BigDecimal paidAmount = new BigDecimal(0);
        for (ContractFundLog fundLog : fundLogs) {
            if (Objects.equals(fundLog.getStatus(),ContractFundStatusEnums.AUDITED.getStatus())){
                paidAmount = paidAmount.add(fundLog.getReceivedPrice());
            }
        }

        //开票记录
        BigDecimal invoiceAmount = new BigDecimal(0);
        List<ContractInvoiceLog> invoiceLogList = contractInvoiceLogRepository.findByCustomerId(id);
        for (ContractInvoiceLog contractInvoiceLog : invoiceLogList) {
//            if(Objects.equals(contractInvoiceLog.getStatus(), ContractInvoiceStatusEnums.INVOICED.getStatus())){
//            }
            invoiceAmount = invoiceAmount.add(contractInvoiceLog.getPrice());
        }

        //赋值返回值
        CustomerStatisticsRespVO customerStatisticsRespVO = new CustomerStatisticsRespVO();
        customerStatisticsRespVO.setDealCount(dealCount);
        customerStatisticsRespVO.setDealAmount(dealTotalAmount);
        customerStatisticsRespVO.setPaidAmount(paidAmount);
        customerStatisticsRespVO.setInvoiceAmount(invoiceAmount);
        customerStatisticsRespVO.setReceivableAmount(dealTotalAmount.subtract(paidAmount));
//        customerStatisticsRespVO.setFundAmount(dealTotalAmount.subtract(paidAmount));
//        customerStatisticsRespVO.setArrearsAmount(dealTotalAmount.subtract(paidAmount));
        customerStatisticsRespVO.setProjectDoingCount(new BigDecimal(projectDoingCount));
        customerStatisticsRespVO.setProjectOutedCount(new BigDecimal(projectOutedCount));
        return customerStatisticsRespVO;
    }


    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public CustomerImportRespVO importList(List<CustomerImportVO> importUsers, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importUsers)) {
            throw exception(USER_IMPORT_LIST_IS_EMPTY);
        }
        CustomerImportRespVO respVO = CustomerImportRespVO.builder().createNames(new ArrayList<>())
                .updateNames(new ArrayList<>()).failureNames(new ArrayList<>()).build();

        importUsers.forEach(item -> {
            if(item.getPhone()!=null){
                item.setPhone(item.getPhone().trim());
            }
            CustomerSimple byPhone = customerSimpleRepository.findByPhone(item.getPhone());
            if(byPhone==null){
                Customer customer = customerMapper.toEntity(item);

                if (item.getType() != null) {
                    switch (item.getType()) {
                        case "在校学生":
                            customer.setType("1");
                            break;
                        case "科研院校":
                            customer.setType("3");
                            break;
                        case "医院":
                            customer.setType("4");
                            break;
                        case "药企":
                            customer.setType("5");
                            break;
                        case "中间商":
                            customer.setType("6");
                            break;
                        default:
                            customer.setType("2");
                    }
                } else {
                    customer.setType("2");
                }

                customer.setInstitutionMark(item.getInstitutionStr());
                customer.setCreator(getLoginUserId());
                customer.setSource("EXCEL导入");
                customer.setToCustomer(false);
                customerRepository.save(customer);
                respVO.getCreateNames().add(item.getName()+":"+item.getInstitutionStr()+":"+item.getPhone());
            }else{
                respVO.getFailureNames().add(item.getName()+":"+item.getInstitutionStr()+":"+item.getPhone());
            }

        });

        return respVO;
    }

    private Sort createSort(CustomerPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getSource() != null) {
            orders.add(new Sort.Order(order.getSource().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "source"));
        }

        if (order.getPhone() != null) {
            orders.add(new Sort.Order(order.getPhone().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "phone"));
        }

        if (order.getEmail() != null) {
            orders.add(new Sort.Order(order.getEmail().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "email"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getWechat() != null) {
            orders.add(new Sort.Order(order.getWechat().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "wechat"));
        }

        if (order.getDoctorProfessionalRank() != null) {
            orders.add(new Sort.Order(order.getDoctorProfessionalRank().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "doctorProfessionalRank"));
        }

        if (order.getHospitalDepartment() != null) {
            orders.add(new Sort.Order(order.getHospitalDepartment().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "hospitalDepartment"));
        }

        if (order.getAcademicTitle() != null) {
            orders.add(new Sort.Order(order.getAcademicTitle().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "academicTitle"));
        }

        if (order.getAcademicCredential() != null) {
            orders.add(new Sort.Order(order.getAcademicCredential().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "academicCredential"));
        }

        if (order.getHospitalId() != null) {
            orders.add(new Sort.Order(order.getHospitalId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "hospitalId"));
        }

        if (order.getUniversityId() != null) {
            orders.add(new Sort.Order(order.getUniversityId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "universityId"));
        }

        if (order.getCompanyId() != null) {
            orders.add(new Sort.Order(order.getCompanyId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "companyId"));
        }

        if (order.getProvince() != null) {
            orders.add(new Sort.Order(order.getProvince().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "province"));
        }

        if (order.getCity() != null) {
            orders.add(new Sort.Order(order.getCity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "city"));
        }

        if (order.getArea() != null) {
            orders.add(new Sort.Order(order.getArea().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "area"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getDealCount() != null) {
            orders.add(new Sort.Order(order.getDealCount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "dealCount"));
        }

        if (order.getDealTotalAmount() != null) {
            orders.add(new Sort.Order(order.getDealTotalAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "dealTotalAmount"));
        }

        if (order.getArrears() != null) {
            orders.add(new Sort.Order(order.getArrears().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "arrears"));
        }

        if (order.getLastFollowupTime() != null) {
            orders.add(new Sort.Order(order.getLastFollowupTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "lastFollowupTime"));
        }

        if (order.getSalesId() != null) {
            orders.add(new Sort.Order(order.getSalesId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salesId"));
        }

        if (order.getLastFollowupId() != null) {
            orders.add(new Sort.Order(order.getLastFollowupId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "lastFollowupId"));
        }

        if (order.getLastSalesleadId() != null) {
            orders.add(new Sort.Order(order.getLastSalesleadId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "lastSalesleadId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}