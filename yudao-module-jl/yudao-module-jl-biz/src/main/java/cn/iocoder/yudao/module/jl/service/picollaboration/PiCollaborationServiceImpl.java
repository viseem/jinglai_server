package cn.iocoder.yudao.module.jl.service.picollaboration;

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
import cn.iocoder.yudao.module.jl.controller.admin.picollaboration.vo.*;
import cn.iocoder.yudao.module.jl.entity.picollaboration.PiCollaboration;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.picollaboration.PiCollaborationMapper;
import cn.iocoder.yudao.module.jl.repository.picollaboration.PiCollaborationRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * PI组协作 Service 实现类
 *
 */
@Service
@Validated
public class PiCollaborationServiceImpl implements PiCollaborationService {

    @Resource
    private PiCollaborationRepository piCollaborationRepository;

    @Resource
    private PiCollaborationMapper piCollaborationMapper;

    @Override
    public Long createPiCollaboration(PiCollaborationCreateReqVO createReqVO) {
        // 插入
        PiCollaboration piCollaboration = piCollaborationMapper.toEntity(createReqVO);
        piCollaborationRepository.save(piCollaboration);
        // 返回
        return piCollaboration.getId();
    }

    @Override
    public void updatePiCollaboration(PiCollaborationUpdateReqVO updateReqVO) {
        // 校验存在
        validatePiCollaborationExists(updateReqVO.getId());
        // 更新
        PiCollaboration updateObj = piCollaborationMapper.toEntity(updateReqVO);
        piCollaborationRepository.save(updateObj);
    }

    @Override
    public void deletePiCollaboration(Long id) {
        // 校验存在
        validatePiCollaborationExists(id);
        // 删除
        piCollaborationRepository.deleteById(id);
    }

    private void validatePiCollaborationExists(Long id) {
        piCollaborationRepository.findById(id).orElseThrow(() -> exception(PI_COLLABORATION_NOT_EXISTS));
    }

    @Override
    public Optional<PiCollaboration> getPiCollaboration(Long id) {
        return piCollaborationRepository.findById(id);
    }

    @Override
    public List<PiCollaboration> getPiCollaborationList(Collection<Long> ids) {
        return StreamSupport.stream(piCollaborationRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<PiCollaboration> getPiCollaborationPage(PiCollaborationPageReqVO pageReqVO, PiCollaborationPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<PiCollaboration> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getTargetId() != null) {
                predicates.add(cb.equal(root.get("targetId"), pageReqVO.getTargetId()));
            }

            if(pageReqVO.getTargetType() != null) {
                predicates.add(cb.equal(root.get("targetType"), pageReqVO.getTargetType()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<PiCollaboration> page = piCollaborationRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<PiCollaboration> getPiCollaborationList(PiCollaborationExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<PiCollaboration> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getTargetId() != null) {
                predicates.add(cb.equal(root.get("targetId"), exportReqVO.getTargetId()));
            }

            if(exportReqVO.getTargetType() != null) {
                predicates.add(cb.equal(root.get("targetType"), exportReqVO.getTargetType()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return piCollaborationRepository.findAll(spec);
    }

    private Sort createSort(PiCollaborationPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getTargetId() != null) {
            orders.add(new Sort.Order(order.getTargetId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "targetId"));
        }

        if (order.getTargetType() != null) {
            orders.add(new Sort.Order(order.getTargetType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "targetType"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}