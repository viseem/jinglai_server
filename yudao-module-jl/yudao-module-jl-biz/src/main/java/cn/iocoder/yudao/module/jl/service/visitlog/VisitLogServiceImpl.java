package cn.iocoder.yudao.module.jl.service.visitlog;

import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.visitlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.visitlog.VisitLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.visitlog.VisitLogMapper;
import cn.iocoder.yudao.module.jl.repository.visitlog.VisitLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 拜访记录 Service 实现类
 *
 */
@Service
@Validated
public class VisitLogServiceImpl implements VisitLogService {
    @Resource
    private DateAttributeGenerator dateAttributeGenerator;

    @Resource
    private VisitLogRepository visitLogRepository;

    @Resource
    private VisitLogMapper visitLogMapper;

    @Override
    public Long createVisitLog(VisitLogCreateReqVO createReqVO) {
        // 插入
        VisitLog visitLog = visitLogMapper.toEntity(createReqVO);
        visitLog.setSalesId(getLoginUserId());
        visitLogRepository.save(visitLog);
        // 返回
        return visitLog.getId();
    }

    @Override
    public void updateVisitLog(VisitLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateVisitLogExists(updateReqVO.getId());
        // 更新
        VisitLog updateObj = visitLogMapper.toEntity(updateReqVO);
        visitLogRepository.save(updateObj);
    }

    @Override
    public void deleteVisitLog(Long id) {
        // 校验存在
        validateVisitLogExists(id);
        // 删除
        visitLogRepository.deleteById(id);
    }

    private void validateVisitLogExists(Long id) {
        visitLogRepository.findById(id).orElseThrow(() -> exception(VISIT_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<VisitLog> getVisitLog(Long id) {
        return visitLogRepository.findById(id);
    }

    @Override
    public List<VisitLog> getVisitLogList(Collection<Long> ids) {
        return StreamSupport.stream(visitLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<VisitLog> getVisitLogPage(VisitLogPageReqVO pageReqVO, VisitLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<VisitLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(!Objects.equals(pageReqVO.getAttribute(), DataAttributeTypeEnums.ANY.getStatus())){
                Long[] users = pageReqVO.getSalesId()!=null?dateAttributeGenerator.processAttributeUsersWithUserId(pageReqVO.getAttribute(), pageReqVO.getSalesId()):dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
                pageReqVO.setCreators(users);
                predicates.add(root.get("creator").in(Arrays.stream(pageReqVO.getCreators()).toArray()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), pageReqVO.getSalesId()));
            }

            if(pageReqVO.getTime() != null) {
                predicates.add(cb.between(root.get("time"), pageReqVO.getTime()[0], pageReqVO.getTime()[1]));
            } 
            if(pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }

            if(pageReqVO.getGoal() != null) {
                predicates.add(cb.equal(root.get("goal"), pageReqVO.getGoal()));
            }

            if(pageReqVO.getVisitType() != null) {
                predicates.add(cb.equal(root.get("visitType"), pageReqVO.getVisitType()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getFeedback() != null) {
                predicates.add(cb.equal(root.get("feedback"), pageReqVO.getFeedback()));
            }

            if(pageReqVO.getScore() != null) {
                predicates.add(cb.equal(root.get("score"), pageReqVO.getScore()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<VisitLog> page = visitLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<VisitLog> getVisitLogList(VisitLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<VisitLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), exportReqVO.getSalesId()));
            }

            if(exportReqVO.getTime() != null) {
                predicates.add(cb.between(root.get("time"), exportReqVO.getTime()[0], exportReqVO.getTime()[1]));
            } 
            if(exportReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), exportReqVO.getAddress()));
            }

            if(exportReqVO.getGoal() != null) {
                predicates.add(cb.equal(root.get("goal"), exportReqVO.getGoal()));
            }

            if(exportReqVO.getVisitType() != null) {
                predicates.add(cb.equal(root.get("visitType"), exportReqVO.getVisitType()));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getFeedback() != null) {
                predicates.add(cb.equal(root.get("feedback"), exportReqVO.getFeedback()));
            }

            if(exportReqVO.getScore() != null) {
                predicates.add(cb.equal(root.get("score"), exportReqVO.getScore()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return visitLogRepository.findAll(spec);
    }

    private Sort createSort(VisitLogPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getSalesId() != null) {
            orders.add(new Sort.Order(order.getSalesId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salesId"));
        }

        if (order.getTime() != null) {
            orders.add(new Sort.Order(order.getTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "time"));
        }

        if (order.getAddress() != null) {
            orders.add(new Sort.Order(order.getAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        }

        if (order.getGoal() != null) {
            orders.add(new Sort.Order(order.getGoal().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "goal"));
        }

        if (order.getVisitType() != null) {
            orders.add(new Sort.Order(order.getVisitType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "visitType"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getFeedback() != null) {
            orders.add(new Sort.Order(order.getFeedback().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "feedback"));
        }

        if (order.getScore() != null) {
            orders.add(new Sort.Order(order.getScore().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "score"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}