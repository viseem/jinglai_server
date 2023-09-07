package cn.iocoder.yudao.module.jl.service.crm;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectConstractItemVO;
import cn.iocoder.yudao.module.jl.entity.crm.Customer;
import cn.iocoder.yudao.module.jl.entity.crm.SalesleadCompetitor;
import cn.iocoder.yudao.module.jl.entity.crm.SalesleadCustomerPlan;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import cn.iocoder.yudao.module.jl.enums.SalesLeadStatusEnums;
import cn.iocoder.yudao.module.jl.mapper.crm.SalesleadCompetitorMapper;
import cn.iocoder.yudao.module.jl.mapper.crm.SalesleadCustomerPlanMapper;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectConstractMapper;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectMapper;
import cn.iocoder.yudao.module.jl.repository.crm.*;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectServiceImpl;
import cn.iocoder.yudao.module.jl.utils.DateAttributeGenerator;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Predicate;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.Saleslead;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.crm.SalesleadMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 销售线索 Service 实现类
 *
 */
@Service
@Validated
public class SalesleadServiceImpl implements SalesleadService {

    @Resource
    private DateAttributeGenerator dateAttributeGenerator;

    @Resource
    private SalesleadRepository salesleadRepository;

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private SalesleadMapper salesleadMapper;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ProjectConstractRepository projectConstractRepository;

    @Resource
    private ProjectConstractMapper projectConstractMapper;

    @Resource
    private SalesleadCustomerPlanRepository salesleadCustomerPlanRepository;

    @Resource
    private SalesleadCustomerPlanMapper salesleadCustomerPlanMapper;

    @Resource
    private SalesleadCompetitorRepository salesleadCompetitorRepository;

    @Resource
    private SalesleadCompetitorMapper salesleadCompetitorMapper;

    @Resource
    private ProjectServiceImpl projectService;

    @Override
    public Long createSaleslead(SalesleadCreateReqVO createReqVO) {
        // 插入
        Saleslead saleslead = salesleadMapper.toEntity(createReqVO);
        salesleadRepository.save(saleslead);
        // 返回
        return saleslead.getId();
    }

    @Override
    public SalesleadCountStatsRespVO getSalesleadCountStats(){
        //获取未转化为项目的销售线索数量
        Integer notToProjectCount = salesleadRepository.countByStatusNot(Integer.valueOf(SalesLeadStatusEnums.NotToProject.getStatus()));
        SalesleadCountStatsRespVO salesleadCountStatsRespVO = new SalesleadCountStatsRespVO();
        salesleadCountStatsRespVO.setNotToProjectCount(notToProjectCount);
        return salesleadCountStatsRespVO;
    }

    @Override
    public void updateSaleslead(SalesleadUpdateReqVO updateReqVO) {

        if(updateReqVO.getId() != null) {
            // 校验存在
            Saleslead saleslead = validateSalesleadExists(updateReqVO.getId());
            updateReqVO.setProjectId(saleslead.getProjectId());
        }

        // 更新线索
        Saleslead updateObj = salesleadMapper.toEntity(updateReqVO);
        salesleadRepository.save(updateObj);
        Long salesleadId = updateObj.getId();

                              // 更新竞争对手的报价
        // ``               删除原有的
        salesleadCompetitorRepository.deleteBySalesleadId(salesleadId);
        // 再插入
        List<SalesleadCompetitorItemVO> competitorQuotations = updateReqVO.getCompetitorQuotations();
        if(competitorQuotations != null && competitorQuotations.size() > 0) {
            // 遍历 competitorQuotations，将它的 salesleadId 字段设置为 updateObj.getId()
            competitorQuotations.forEach(competitorQuotation -> competitorQuotation.setSalesleadId(salesleadId));
            List<SalesleadCompetitor> quotations = salesleadCompetitorMapper.toEntityList(competitorQuotations);
            salesleadCompetitorRepository.saveAll(quotations);
        }

        // 更新客户方案
        // 删除原有的
        salesleadCustomerPlanRepository.deleteBySalesleadId(salesleadId);
        // 再插入
        List<SalesleadCustomerPlanItemVO> customerPlans = updateReqVO.getCustomerPlans();
        if(customerPlans != null && customerPlans.size() > 0) {
            // 遍历 customerPlans，将它的 salesleadId 字段设置为 updateObj.getId()
            customerPlans.forEach(customerPlan -> customerPlan.setSalesleadId(salesleadId));
            List<SalesleadCustomerPlan> plans = salesleadCustomerPlanMapper.toEntityList(customerPlans);
            salesleadCustomerPlanRepository.saveAll(plans);
        }


       if(updateReqVO.getStatus().equals(SalesLeadStatusEnums.ToProject.getStatus()) || updateReqVO.getStatus().equals(SalesLeadStatusEnums.QUOTATION.getStatus())){
           // 1. 创建项目
           Project project = new Project();
           project.setSalesleadId(salesleadId);
           project.setCustomerId(updateReqVO.getCustomerId());
           project.setName(updateReqVO.getProjectName());
           project.setStage("1");
           project.setStatus(updateReqVO.getStatus());
           project.setType(updateReqVO.getType());
           project.setSalesId(getLoginUserId()); // 线索的销售人员 id

           if(updateReqVO.getStatus().equals(SalesLeadStatusEnums.QUOTATION.getStatus())){
               //如果客户不存在，则抛出异常
               Customer customer = customerRepository.findById(updateReqVO.getCustomerId()).orElseThrow(() -> exception(CUSTOMER_NOT_EXISTS));
               project.setName(customer.getName()+"的报价");

           }

//           projectRepository.save(project);
           if(updateReqVO.getProjectId()!=null){
                project.setId(updateReqVO.getProjectId());
                projectRepository.save(project);
           }else{
               Long projectId = projectService.createProject(projectMapper.toCreateDto(project));
               // 销售线索中保存项目 id
               updateObj.setProjectId(projectId);
               salesleadRepository.save(updateObj);
           }




           //不按照类型区分合同传不传，有就传没有不传
           // 2. 保存合同
           // 遍历 updateReqVO.getProjectConstracts(), 创建合同
           List<ProjectConstractItemVO> projectConstracts = updateReqVO.getProjectConstracts();
           if(projectConstracts != null && projectConstracts.size() > 0) {
               // 遍历 projectConstracts，将它的 projectId 字段设置为 project.getId()
               projectConstracts.forEach(projectConstract -> {
                   projectConstract.setProjectId(project.getId());
                   projectConstract.setCustomerId(updateReqVO.getCustomerId());
                   projectConstract.setSalesId(updateObj.getCreator()); // 线索的销售人员 id
                   projectConstract.setName(project.getName());
               });
               List<ProjectConstract> contracts = projectConstractMapper.toEntityList(projectConstracts);
               projectConstractRepository.saveAll(contracts);
           }
       }


        //设置customer updateLastSalesleadIdById
        customerRepository.updateLastSalesleadIdById(updateReqVO.getCustomerId(),updateObj.getId());

    }

    @Override
    public void deleteSaleslead(Long id) {
        // 校验存在
        validateSalesleadExists(id);
        // 删除
        salesleadRepository.deleteById(id);
    }

    private Saleslead validateSalesleadExists(Long id) {
        Optional<Saleslead> byId = salesleadRepository.findById(id);
        if(byId.isEmpty()) {
            throw exception(SALESLEAD_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<Saleslead> getSaleslead(Long id) {
        return salesleadRepository.findById(id);
    }

    @Override
    public List<Saleslead> getSalesleadList(Collection<Long> ids) {
        return StreamSupport.stream(salesleadRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Saleslead> getSalesleadPage(SalesleadPageReqVO pageReqVO, SalesleadPageOrder orderV0) {

        Long[] users = dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
        pageReqVO.setCreators(users);

        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Saleslead> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getSource() != null) {
                predicates.add(cb.equal(root.get("source"), pageReqVO.getSource()));
            }

            if(pageReqVO.getRequirement() != null) {
                predicates.add(cb.equal(root.get("requirement"), pageReqVO.getRequirement()));
            }

            if(pageReqVO.getBudget() != null) {
                predicates.add(cb.equal(root.get("budget"), pageReqVO.getBudget()));
            }

/*            if(Objects.equals(pageReqVO.getQuotationStatus(), "1")) {
                // 待报价的
                predicates.add(cb.isNull(root.get("quotation")));
            } else if (Objects.equals(pageReqVO.getQuotationStatus(), "2")) {
                // 已报价的
                predicates.add(cb.isNotNull(root.get("quotation")));
            }*/

            if(pageReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("creator"), pageReqVO.getSalesId()));
            }

            if(pageReqVO.getQuotation() != null) {
                predicates.add(cb.equal(root.get("quotation"), pageReqVO.getQuotation()));
            }
            if(pageReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"),getLoginUserId()));
                if(pageReqVO.getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
                }
            }else{
                predicates.add(root.get("creator").in(Arrays.stream(pageReqVO.getCreators()).toArray()));

                if(pageReqVO.getStatus() != null) {
                    //查询未转项目的
                    if(pageReqVO.getStatus().toString().equals(SalesLeadStatusEnums.NotToProject.getStatus())){
                        predicates.add(cb.notEqual(root.get("status"), SalesLeadStatusEnums.ToProject.getStatus()));
                    }else{
                        predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
                    }

                }
            }

//                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));


            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getBusinessType() != null) {
                predicates.add(cb.equal(root.get("businessType"), pageReqVO.getBusinessType()));
            }

            if(pageReqVO.getLostNote() != null) {
                predicates.add(cb.equal(root.get("lostNote"), pageReqVO.getLostNote()));
            }




            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<Saleslead> page = salesleadRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }


    @Override
    public List<Saleslead> getSalesleadList(SalesleadExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Saleslead> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getSource() != null) {
                predicates.add(cb.equal(root.get("source"), exportReqVO.getSource()));
            }

            if(exportReqVO.getRequirement() != null) {
                predicates.add(cb.equal(root.get("requirement"), exportReqVO.getRequirement()));
            }

            if(exportReqVO.getBudget() != null) {
                predicates.add(cb.equal(root.get("budget"), exportReqVO.getBudget()));
            }

            if(exportReqVO.getQuotation() != null) {
                predicates.add(cb.equal(root.get("quotation"), exportReqVO.getQuotation()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getBusinessType() != null) {
                predicates.add(cb.equal(root.get("businessType"), exportReqVO.getBusinessType()));
            }

            if(exportReqVO.getLostNote() != null) {
                predicates.add(cb.equal(root.get("lostNote"), exportReqVO.getLostNote()));
            }

            if(exportReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), exportReqVO.getManagerId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return salesleadRepository.findAll(spec);
    }

    private Sort createSort(SalesleadPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getSource() != null) {
            orders.add(new Sort.Order(order.getSource().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "source"));
        }

        if (order.getRequirement() != null) {
            orders.add(new Sort.Order(order.getRequirement().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "requirement"));
        }

        if (order.getBudget() != null) {
            orders.add(new Sort.Order(order.getBudget().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "budget"));
        }

        if (order.getQuotation() != null) {
            orders.add(new Sort.Order(order.getQuotation().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quotation"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getBusinessType() != null) {
            orders.add(new Sort.Order(order.getBusinessType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "businessType"));
        }

        if (order.getLostNote() != null) {
            orders.add(new Sort.Order(order.getLostNote().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "lostNote"));
        }

        if (order.getManagerId() != null) {
            orders.add(new Sort.Order(order.getManagerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "managerId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}