package cn.iocoder.yudao.module.jl.service.commonoperatelog;

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
import cn.iocoder.yudao.module.jl.controller.admin.commonoperatelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.commonoperatelog.CommonOperateLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.commonoperatelog.CommonOperateLogMapper;
import cn.iocoder.yudao.module.jl.repository.commonoperatelog.CommonOperateLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 通用操作记录 Service 实现类
 *
 */
@Service
@Validated
public class CommonOperateLogServiceImpl implements CommonOperateLogService {

    @Resource
    private CommonOperateLogRepository commonOperateLogRepository;

    @Resource
    private CommonOperateLogMapper commonOperateLogMapper;

    @Override
    public Long createCommonOperateLog(CommonOperateLogCreateReqVO createReqVO) {
        // 插入
        CommonOperateLog commonOperateLog = commonOperateLogMapper.toEntity(createReqVO);
        commonOperateLogRepository.save(commonOperateLog);
        // 返回
        return commonOperateLog.getId();
    }

    @Override
    public void updateCommonOperateLog(CommonOperateLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateCommonOperateLogExists(updateReqVO.getId());
        // 更新
        CommonOperateLog updateObj = commonOperateLogMapper.toEntity(updateReqVO);
        commonOperateLogRepository.save(updateObj);
    }

    @Override
    public void deleteCommonOperateLog(Long id) {
        // 校验存在
        validateCommonOperateLogExists(id);
        // 删除
        commonOperateLogRepository.deleteById(id);
    }

    private void validateCommonOperateLogExists(Long id) {
        commonOperateLogRepository.findById(id).orElseThrow(() -> exception(COMMON_OPERATE_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<CommonOperateLog> getCommonOperateLog(Long id) {
        return commonOperateLogRepository.findById(id);
    }

    @Override
    public List<CommonOperateLog> getCommonOperateLogList(Collection<Long> ids) {
        return StreamSupport.stream(commonOperateLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CommonOperateLog> getCommonOperateLogPage(CommonOperateLogPageReqVO pageReqVO, CommonOperateLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CommonOperateLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getOldContent() != null) {
                predicates.add(cb.equal(root.get("oldContent"), pageReqVO.getOldContent()));
            }

            if(pageReqVO.getNewContent() != null) {
                predicates.add(cb.equal(root.get("newContent"), pageReqVO.getNewContent()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getSubType() != null) {
                predicates.add(cb.equal(root.get("subType"), pageReqVO.getSubType()));
            }

            if(pageReqVO.getEventType() != null) {
                predicates.add(cb.equal(root.get("eventType"), pageReqVO.getEventType()));
            }

            if(pageReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), pageReqVO.getRefId()));
            }

            if(pageReqVO.getSubRefId() != null) {
                predicates.add(cb.equal(root.get("subRefId"), pageReqVO.getSubRefId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CommonOperateLog> page = commonOperateLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CommonOperateLog> getCommonOperateLogList(CommonOperateLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CommonOperateLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getOldContent() != null) {
                predicates.add(cb.equal(root.get("oldContent"), exportReqVO.getOldContent()));
            }

            if(exportReqVO.getNewContent() != null) {
                predicates.add(cb.equal(root.get("newContent"), exportReqVO.getNewContent()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getSubType() != null) {
                predicates.add(cb.equal(root.get("subType"), exportReqVO.getSubType()));
            }

            if(exportReqVO.getEventType() != null) {
                predicates.add(cb.equal(root.get("eventType"), exportReqVO.getEventType()));
            }

            if(exportReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), exportReqVO.getRefId()));
            }

            if(exportReqVO.getSubRefId() != null) {
                predicates.add(cb.equal(root.get("subRefId"), exportReqVO.getSubRefId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return commonOperateLogRepository.findAll(spec);
    }

    private Sort createSort(CommonOperateLogPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getOldContent() != null) {
            orders.add(new Sort.Order(order.getOldContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "oldContent"));
        }

        if (order.getNewContent() != null) {
            orders.add(new Sort.Order(order.getNewContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "newContent"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getSubType() != null) {
            orders.add(new Sort.Order(order.getSubType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "subType"));
        }

        if (order.getEventType() != null) {
            orders.add(new Sort.Order(order.getEventType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "eventType"));
        }

        if (order.getRefId() != null) {
            orders.add(new Sort.Order(order.getRefId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refId"));
        }

        if (order.getSubRefId() != null) {
            orders.add(new Sort.Order(order.getSubRefId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "subRefId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}