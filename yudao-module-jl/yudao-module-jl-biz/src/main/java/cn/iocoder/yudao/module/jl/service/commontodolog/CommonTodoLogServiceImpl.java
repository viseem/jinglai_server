package cn.iocoder.yudao.module.jl.service.commontodolog;

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
import cn.iocoder.yudao.module.jl.controller.admin.commontodolog.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontodolog.CommonTodoLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.commontodolog.CommonTodoLogMapper;
import cn.iocoder.yudao.module.jl.repository.commontodolog.CommonTodoLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 通用TODO记录 Service 实现类
 *
 */
@Service
@Validated
public class CommonTodoLogServiceImpl implements CommonTodoLogService {

    @Resource
    private CommonTodoLogRepository commonTodoLogRepository;

    @Resource
    private CommonTodoLogMapper commonTodoLogMapper;

    @Override
    public Long createCommonTodoLog(CommonTodoLogCreateReqVO createReqVO) {
        // 插入
        CommonTodoLog commonTodoLog = commonTodoLogMapper.toEntity(createReqVO);
        commonTodoLogRepository.save(commonTodoLog);
        // 返回
        return commonTodoLog.getId();
    }

    @Override
    public void updateCommonTodoLog(CommonTodoLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateCommonTodoLogExists(updateReqVO.getId());
        // 更新
        CommonTodoLog updateObj = commonTodoLogMapper.toEntity(updateReqVO);
        commonTodoLogRepository.save(updateObj);
    }

    @Override
    public void deleteCommonTodoLog(Long id) {
        // 校验存在
        validateCommonTodoLogExists(id);
        // 删除
        commonTodoLogRepository.deleteById(id);
    }

    private void validateCommonTodoLogExists(Long id) {
        commonTodoLogRepository.findById(id).orElseThrow(() -> exception(COMMON_TODO_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<CommonTodoLog> getCommonTodoLog(Long id) {
        return commonTodoLogRepository.findById(id);
    }

    @Override
    public List<CommonTodoLog> getCommonTodoLogList(Collection<Long> ids) {
        return StreamSupport.stream(commonTodoLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CommonTodoLog> getCommonTodoLogPage(CommonTodoLogPageReqVO pageReqVO, CommonTodoLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CommonTodoLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), pageReqVO.getRefId()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CommonTodoLog> page = commonTodoLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CommonTodoLog> getCommonTodoLogList(CommonTodoLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CommonTodoLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), exportReqVO.getRefId()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return commonTodoLogRepository.findAll(spec);
    }

    private Sort createSort(CommonTodoLogPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getRefId() != null) {
            orders.add(new Sort.Order(order.getRefId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refId"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}