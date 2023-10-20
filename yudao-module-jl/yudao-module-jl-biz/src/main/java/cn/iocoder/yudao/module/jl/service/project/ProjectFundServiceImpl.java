package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.module.jl.entity.crm.CrmReceipt;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import cn.iocoder.yudao.module.jl.entity.projectfundchangelog.ProjectFundChangeLog;
import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import cn.iocoder.yudao.module.jl.mapper.projectfundlog.ProjectFundLogMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractRepository;
import cn.iocoder.yudao.module.jl.repository.projectfundchangelog.ProjectFundChangeLogRepository;
import cn.iocoder.yudao.module.jl.repository.projectfundlog.ProjectFundLogRepository;
import cn.iocoder.yudao.module.jl.utils.DateAttributeGenerator;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;
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
import cn.iocoder.yudao.module.jl.entity.project.ProjectFund;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectFundMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectFundRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目款项 Service 实现类
 *
 */
@Service
@Validated
public class ProjectFundServiceImpl implements ProjectFundService {
    @Resource
    private ProjectConstractRepository projectConstractRepository;

    @Resource
    private DateAttributeGenerator dateAttributeGenerator;

    @Resource
    private ProjectFundRepository projectFundRepository;

    @Resource
    private ProjectFundLogRepository projectFundLogRepository;

    @Resource
    private ProjectFundMapper projectFundMapper;

    @Resource
    private ProjectConstractServiceImpl projectConstractService;

    @Resource
    private ProjectFundChangeLogRepository projectFundChangeLogRepository;

    @Override
    public Long createProjectFund(ProjectFundCreateReqVO createReqVO) {

        //校验合同是否存在
        Optional<ProjectConstract> byId = projectConstractRepository.findById(createReqVO.getContractId());
        ProjectConstract projectConstract = byId.orElse(null);
        if (projectConstract==null){
            throw exception(PROJECT_CONSTRACT_NOT_EXISTS);
        }
        createReqVO.setProjectId(projectConstract.getProjectId());
        createReqVO.setCustomerId(projectConstract.getCustomerId());

        // 插入
        ProjectFund projectFund = projectFundMapper.toEntity(createReqVO);
        projectFundRepository.save(projectFund);
        // 返回
        return projectFund.getId();
    }

    @Override
    @Transactional
    public void updateProjectFund(ProjectFundUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectFundExists(updateReqVO.getId());
        // 更新
        ProjectFund updateObj = projectFundMapper.toEntity(updateReqVO);
        projectFundRepository.save(updateObj);

        // 写入变更日志 projectFundChangeLog
//        ProjectFundChangeLog changeEntity = projectFundMapper.toChangeEntity(updateReqVO);
//        projectFundChangeLogRepository.save();
    }

    @Override
    @Transactional
    public void saveProjectFund(ProjectFundSaveReqVO saveReqVO) {
        // 校验存在
        validateProjectFundExists(saveReqVO.getId());

        //创建items明细 ProjectFundLog
/*        List<ProjectFundLog> sops = projectFundLogMapper.toEntity(saveReqVO.getItems());
        projectFundLogRepository.saveAll(sops);*/
        AtomicReference<Integer> receivedPrice = new AtomicReference<>(0);
        projectFundLogRepository.saveAll(saveReqVO.getItems().stream().peek(item -> {
            item.setCustomerId(saveReqVO.getCustomerId());
            item.setContractId(saveReqVO.getContractId());
            item.setProjectId(saveReqVO.getProjectId());
            item.setProjectFundId(saveReqVO.getId());
            receivedPrice.updateAndGet(v -> v + item.getPrice());
        }).collect(Collectors.toList()));

        //更新合同已收金额
        projectConstractService.processContractReceivedPrice(saveReqVO.getContractId());

        // 更新
        ProjectFund updateObj = projectFundMapper.toEntity(saveReqVO);
        updateObj.setReceivedPrice(receivedPrice.get());
        projectFundRepository.save(updateObj);
    }

    @Override
    @Transactional
    public void updateProjectFundStatus(ProjectFundPaymentUpdateReqVO updateReqVO) {
//        projectFundRepository.updateStatusAndPayMarkById(updateReqVO.getStatus(),updateReqVO.getMark(),updateReqVO.getId());
//        projectFundRepository.updateActualPaymentTimeById(updateReqVO.getActualPaymentTime(),updateReqVO.getId());
        // 校验存在
        ProjectFund projectFund = validateProjectFundExists(updateReqVO.getId());
        projectFund.setStatus(updateReqVO.getStatus());
        projectFund.setPayMark(updateReqVO.getMark());
        projectFund.setActualPaymentTime(updateReqVO.getActualPaymentTime());
        projectFundRepository.save(projectFund);
        // 写入变更日志 projectFundChangeLog
        /*ProjectFund projectFund = validateProjectFundExists(updateReqVO.getId());
        ProjectFundChangeLog changeEntity = projectFundMapper.entityToChangeEntity(projectFund);
        changeEntity.setProjectFundId(updateReqVO.getId());
        changeEntity.setStatus(updateReqVO.getStatus());
        changeEntity.setActualPaymentTime(updateReqVO.getActualPaymentTime());
        // 默认实际支付金额为计划金额
        changeEntity.setActualPaymentAmount(projectFund.getPrice());

        changeEntity.setOriginStatus(projectFund.getStatus());
        changeEntity.setChangeType("1");
        changeEntity.setMark(updateReqVO.getMark());
        changeEntity.setSalesId(projectFund.getCreator());
        projectFundChangeLogRepository.save(changeEntity);*/
    }


    @Override
    public void deleteProjectFund(Long id) {
        // 校验存在
        validateProjectFundExists(id);
        // 删除
        projectFundRepository.deleteById(id);
    }

    private ProjectFund validateProjectFundExists(Long id) {
        Optional<ProjectFund> byId = projectFundRepository.findById(id);
        if (!byId.isPresent()) {
            throw exception(PROJECT_FUND_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<ProjectFund> getProjectFund(Long id) {
        Optional<ProjectFund> byId = projectFundRepository.findById(id);
        if (byId.isPresent()){
            ProjectFund projectFund = byId.orElse(null);
            processFundLogs(projectFund);
        }
        return byId;
    }

    @Override
    public List<ProjectFund> getProjectFundList(Collection<Long> ids) {
        return StreamSupport.stream(projectFundRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectFund> getProjectFundPage(ProjectFundPageReqVO pageReqVO, ProjectFundPageOrder orderV0) {

        Long[] users = dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
        pageReqVO.setCreators(users);

        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectFund> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getAttribute()!=null&& !pageReqVO.getAttribute().equals(DataAttributeTypeEnums.ANY.getStatus())){
                predicates.add(root.get("creator").in(Arrays.stream(pageReqVO.getCreators()).toArray()));
            }

/*            if(pageReqVO.getStatus() != null) {
                //如果status="ALL_PAY"  判断root.get("receivedPrice") >= root.get("price")
                //如果status="PART" 判断root.get("receivedPrice") < root.get("price") && root.get("receivedPrice") > 0
                //如果status="NO" 判断root.get("receivedPrice") == 0 || root.get("receivedPrice") == null
                predicates.add();
            }*/

            if (pageReqVO.getPayStatus() != null) {
                switch (pageReqVO.getPayStatus()) {
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
                    default:
                        break;
                }
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }
            if(pageReqVO.getName() != null) {
                predicates.add(cb.equal(root.get("name"), pageReqVO.getName()));
            }

            if(pageReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), pageReqVO.getPrice()));
            }

            if(pageReqVO.getContractId() != null) {
                predicates.add(cb.equal(root.get("contractId"), pageReqVO.getContractId()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }


            if(pageReqVO.getPaidTime() != null) {
                predicates.add(cb.between(root.get("paidTime"), pageReqVO.getPaidTime()[0], pageReqVO.getPaidTime()[1]));
            } 
            if(pageReqVO.getDeadline() != null) {
                predicates.add(cb.between(root.get("deadline"), pageReqVO.getDeadline()[0], pageReqVO.getDeadline()[1]));
            } 
            if(pageReqVO.getReceiptUrl() != null) {
                predicates.add(cb.equal(root.get("receiptUrl"), pageReqVO.getReceiptUrl()));
            }

            if(pageReqVO.getReceiptName() != null) {
                predicates.add(cb.like(root.get("receiptName"), "%" + pageReqVO.getReceiptName() + "%"));
            }

            if(pageReqVO.getSort() != null) {
                predicates.add(cb.equal(root.get("sort"), pageReqVO.getSort()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectFund> page = projectFundRepository.findAll(spec, pageable);
        List<ProjectFund> projectFunds = page.getContent();

        //计算已收金额
        if (projectFunds.size() > 0) {
            projectFunds.forEach(ProjectFundServiceImpl::processFundLogs);
        }



        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    private static void processFundLogs(ProjectFund item) {
        if (item.getItems().size() > 0) {
            item.setLatestFundLog(item.getItems().get(0));
        }
        //计算已开票的金额,通过item.getReceipts()获取所有的发票，然后计算所有已开发票(actualDate不为空)的金额
        BigDecimal totalReceiptPrice = item.getReceipts().stream().filter(receipt -> receipt.getActualDate() != null).map(CrmReceipt::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        item.setReceiptPrice(totalReceiptPrice);
    }

    @Override
    public List<ProjectFund> getProjectFundList(ProjectFundExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectFund> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.equal(root.get("name"), exportReqVO.getName()));
            }

            if(exportReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), exportReqVO.getPrice()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getPaidTime() != null) {
                predicates.add(cb.between(root.get("paidTime"), exportReqVO.getPaidTime()[0], exportReqVO.getPaidTime()[1]));
            } 
            if(exportReqVO.getDeadline() != null) {
                predicates.add(cb.between(root.get("deadline"), exportReqVO.getDeadline()[0], exportReqVO.getDeadline()[1]));
            } 
            if(exportReqVO.getReceiptUrl() != null) {
                predicates.add(cb.equal(root.get("receiptUrl"), exportReqVO.getReceiptUrl()));
            }

            if(exportReqVO.getReceiptName() != null) {
                predicates.add(cb.like(root.get("receiptName"), "%" + exportReqVO.getReceiptName() + "%"));
            }

            if(exportReqVO.getSort() != null) {
                predicates.add(cb.equal(root.get("sort"), exportReqVO.getSort()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectFundRepository.findAll(spec);
    }

    private Sort createSort(ProjectFundPageOrder order) {
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

        if (order.getPrice() != null) {
            orders.add(new Sort.Order(order.getPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "price"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getPaidTime() != null) {
            orders.add(new Sort.Order(order.getPaidTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "paidTime"));
        }

        if (order.getDeadline() != null) {
            orders.add(new Sort.Order(order.getDeadline().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "deadline"));
        }

        if (order.getReceiptUrl() != null) {
            orders.add(new Sort.Order(order.getReceiptUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receiptUrl"));
        }

        if (order.getReceiptName() != null) {
            orders.add(new Sort.Order(order.getReceiptName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receiptName"));
        }

        if (order.getSort() != null) {
            orders.add(new Sort.Order(order.getSort().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sort"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}