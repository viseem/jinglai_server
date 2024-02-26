package cn.iocoder.yudao.module.jl.service.commonlog;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import cn.iocoder.yudao.module.jl.enums.CommonTypeEnums;
import cn.iocoder.yudao.module.jl.repository.commonattachment.CommonAttachmentRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.service.commonattachment.CommonAttachmentServiceImpl;
import org.apache.tomcat.jni.Time;
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
import cn.iocoder.yudao.module.jl.controller.admin.commonlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.commonlog.CommonLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.commonlog.CommonLogMapper;
import cn.iocoder.yudao.module.jl.repository.commonlog.CommonLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 管控记录 Service 实现类
 *
 */
@Service
@Validated
public class CommonLogServiceImpl implements CommonLogService {

    @Resource
    private CommonLogRepository commonLogRepository;

    @Resource
    private CommonLogMapper commonLogMapper;

    @Resource
    private CommonAttachmentServiceImpl commonAttachmentService;

    @Resource
    private CommonAttachmentRepository commonAttachmentRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Override
    @Transactional
    public Long createCommonLog(CommonLogCreateReqVO createReqVO) {
        // 插入
        CommonLog commonLog = commonLogMapper.toEntity(createReqVO);
        commonLogRepository.save(commonLog);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(commonLog.getId(),createReqVO.getType(),createReqVO.getAttachmentList());

        if(Objects.equals(createReqVO.getType(), CommonTypeEnums.PROJECT_COMMON_LOG.getStatus())){
            projectRepository.updateLastFollowTimeById(LocalDateTime.now(),commonLog.getRefId());
        }


        // 返回
        return commonLog.getId();
    }

    @Override
    @Transactional
    public void updateCommonLog(CommonLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateCommonLogExists(updateReqVO.getId());
        // 更新
        CommonLog updateObj = commonLogMapper.toEntity(updateReqVO);
        CommonLog save = commonLogRepository.save(updateObj);

        if(Objects.equals(updateReqVO.getType(), CommonTypeEnums.PROJECT_COMMON_LOG.getStatus())){
            projectRepository.updateLastFollowTimeById(LocalDateTime.now(),save.getRefId());
        }

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(updateReqVO.getId(),updateReqVO.getType(),updateReqVO.getAttachmentList());
    }

    @Override
    @Transactional
    public void deleteCommonLog(Long id) {
        // 校验存在
        validateCommonLogExists(id);
        // 删除
        commonLogRepository.deleteById(id);
        commonAttachmentService.deleteAttachment(id,"PROJECT_COMMON_LOG");
    }

    private void validateCommonLogExists(Long id) {
        commonLogRepository.findById(id).orElseThrow(() -> exception(COMMON_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<CommonLog> getCommonLog(Long id) {
        return commonLogRepository.findById(id);
    }

    @Override
    public List<CommonLog> getCommonLogList(Collection<Long> ids) {
        return StreamSupport.stream(commonLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CommonLog> getCommonLogPage(CommonLogPageReqVO pageReqVO, CommonLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CommonLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getLogTime() != null) {
                predicates.add(cb.between(root.get("logTime"), pageReqVO.getLogTime()[0], pageReqVO.getLogTime()[1]));
            } 
            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), pageReqVO.getRefId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CommonLog> page = commonLogRepository.findAll(spec, pageable);

        page.forEach(log->{
            List<CommonAttachment> byTypeAndRefId = commonAttachmentRepository.findByTypeAndRefId(log.getType(), log.getId());
            if (byTypeAndRefId != null&& !byTypeAndRefId.isEmpty()) {
                log.setAttachmentList(byTypeAndRefId);
            }
        });

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CommonLog> getCommonLogList(CommonLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CommonLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getLogTime() != null) {
                predicates.add(cb.between(root.get("logTime"), exportReqVO.getLogTime()[0], exportReqVO.getLogTime()[1]));
            } 
            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), exportReqVO.getRefId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return commonLogRepository.findAll(spec);
    }

    private Sort createSort(CommonLogPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整
        orders.add(new Sort.Order("asc".equals(order.getLogTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "logTime"));
        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getLogTime() != null) {
            orders.add(new Sort.Order(order.getLogTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "logTime"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getRefId() != null) {
            orders.add(new Sort.Order(order.getRefId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}