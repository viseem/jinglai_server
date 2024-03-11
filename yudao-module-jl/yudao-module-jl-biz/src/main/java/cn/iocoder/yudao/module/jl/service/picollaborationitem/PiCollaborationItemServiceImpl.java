package cn.iocoder.yudao.module.jl.service.picollaborationitem;

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
import cn.iocoder.yudao.module.jl.controller.admin.picollaborationitem.vo.*;
import cn.iocoder.yudao.module.jl.entity.picollaborationitem.PiCollaborationItem;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.picollaborationitem.PiCollaborationItemMapper;
import cn.iocoder.yudao.module.jl.repository.picollaborationitem.PiCollaborationItemRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * pi组协作明细 Service 实现类
 *
 */
@Service
@Validated
public class PiCollaborationItemServiceImpl implements PiCollaborationItemService {

    @Resource
    private PiCollaborationItemRepository piCollaborationItemRepository;


    @Resource
    private PiCollaborationItemMapper piCollaborationItemMapper;

    @Override
    public Long createPiCollaborationItem(PiCollaborationItemCreateReqVO createReqVO) {
        // 插入
        PiCollaborationItem piCollaborationItem = piCollaborationItemMapper.toEntity(createReqVO);
        piCollaborationItemRepository.save(piCollaborationItem);
        // 返回
        return piCollaborationItem.getId();
    }

    @Override
    public void updatePiCollaborationItem(PiCollaborationItemUpdateReqVO updateReqVO) {
        // 校验存在
        validatePiCollaborationItemExists(updateReqVO.getId());
        // 更新
        PiCollaborationItem updateObj = piCollaborationItemMapper.toEntity(updateReqVO);
        piCollaborationItemRepository.save(updateObj);
    }

    @Override
    public void deletePiCollaborationItem(Long id) {
        // 校验存在
        validatePiCollaborationItemExists(id);
        // 删除
        piCollaborationItemRepository.deleteById(id);
    }

    private void validatePiCollaborationItemExists(Long id) {
        piCollaborationItemRepository.findById(id).orElseThrow(() -> exception(PI_COLLABORATION_ITEM_NOT_EXISTS));
    }

    @Override
    public Optional<PiCollaborationItem> getPiCollaborationItem(Long id) {
        return piCollaborationItemRepository.findById(id);
    }

    @Override
    public List<PiCollaborationItem> getPiCollaborationItemList(Collection<Long> ids) {
        return StreamSupport.stream(piCollaborationItemRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<PiCollaborationItem> getPiCollaborationItemPage(PiCollaborationItemPageReqVO pageReqVO, PiCollaborationItemPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<PiCollaborationItem> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getCollaborationId() != null) {
                predicates.add(cb.equal(root.get("collaborationId"), pageReqVO.getCollaborationId()));
            }

            if(pageReqVO.getPiId() != null) {
                predicates.add(cb.equal(root.get("piId"), pageReqVO.getPiId()));
            }

            if(pageReqVO.getPercent() != null) {
                predicates.add(cb.equal(root.get("percent"), pageReqVO.getPercent()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<PiCollaborationItem> page = piCollaborationItemRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<PiCollaborationItem> getPiCollaborationItemList(PiCollaborationItemExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<PiCollaborationItem> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getCollaborationId() != null) {
                predicates.add(cb.equal(root.get("collaborationId"), exportReqVO.getCollaborationId()));
            }

            if(exportReqVO.getPiId() != null) {
                predicates.add(cb.equal(root.get("piId"), exportReqVO.getPiId()));
            }

            if(exportReqVO.getPercent() != null) {
                predicates.add(cb.equal(root.get("percent"), exportReqVO.getPercent()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return piCollaborationItemRepository.findAll(spec);
    }

    private Sort createSort(PiCollaborationItemPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getCollaborationId() != null) {
            orders.add(new Sort.Order(order.getCollaborationId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "collaborationId"));
        }

        if (order.getPiId() != null) {
            orders.add(new Sort.Order(order.getPiId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "piId"));
        }

        if (order.getPercent() != null) {
            orders.add(new Sort.Order(order.getPercent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "percent"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}