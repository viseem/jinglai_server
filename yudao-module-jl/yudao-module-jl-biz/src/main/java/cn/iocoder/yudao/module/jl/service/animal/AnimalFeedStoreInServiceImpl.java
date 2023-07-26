package cn.iocoder.yudao.module.jl.service.animal;

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
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedStoreIn;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.animal.AnimalFeedStoreInMapper;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalFeedStoreInRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 动物饲养入库 Service 实现类
 *
 */
@Service
@Validated
public class AnimalFeedStoreInServiceImpl implements AnimalFeedStoreInService {

    @Resource
    private AnimalFeedStoreInRepository animalFeedStoreInRepository;

    @Resource
    private AnimalFeedStoreInMapper animalFeedStoreInMapper;

    @Override
    public Long createAnimalFeedStoreIn(AnimalFeedStoreInCreateReqVO createReqVO) {
        // 插入
        AnimalFeedStoreIn animalFeedStoreIn = animalFeedStoreInMapper.toEntity(createReqVO);
        animalFeedStoreInRepository.save(animalFeedStoreIn);
        // 返回
        return animalFeedStoreIn.getId();
    }

    @Override
    public void updateAnimalFeedStoreIn(AnimalFeedStoreInUpdateReqVO updateReqVO) {
        // 校验存在
        validateAnimalFeedStoreInExists(updateReqVO.getId());
        // 更新
        AnimalFeedStoreIn updateObj = animalFeedStoreInMapper.toEntity(updateReqVO);
        animalFeedStoreInRepository.save(updateObj);
    }

    @Override
    public void deleteAnimalFeedStoreIn(Long id) {
        // 校验存在
        validateAnimalFeedStoreInExists(id);
        // 删除
        animalFeedStoreInRepository.deleteById(id);
    }

    private void validateAnimalFeedStoreInExists(Long id) {
        animalFeedStoreInRepository.findById(id).orElseThrow(() -> exception(ANIMAL_FEED_STORE_IN_NOT_EXISTS));
    }

    @Override
    public Optional<AnimalFeedStoreIn> getAnimalFeedStoreIn(Long id) {
        return animalFeedStoreInRepository.findById(id);
    }

    @Override
    public List<AnimalFeedStoreIn> getAnimalFeedStoreInList(Collection<Long> ids) {
        return StreamSupport.stream(animalFeedStoreInRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<AnimalFeedStoreIn> getAnimalFeedStoreInPage(AnimalFeedStoreInPageReqVO pageReqVO, AnimalFeedStoreInPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<AnimalFeedStoreIn> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getFeedOrderId() != null) {
                predicates.add(cb.equal(root.get("feedOrderId"), pageReqVO.getFeedOrderId()));
            }

            if(pageReqVO.getRoomId() != null) {
                predicates.add(cb.equal(root.get("roomId"), pageReqVO.getRoomId()));
            }

            if(pageReqVO.getShelfIds() != null) {
                predicates.add(cb.equal(root.get("shelfIds"), pageReqVO.getShelfIds()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getLocation() != null) {
                predicates.add(cb.like(root.get("location"), "%" + pageReqVO.getLocation() + "%"));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<AnimalFeedStoreIn> page = animalFeedStoreInRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<AnimalFeedStoreIn> getAnimalFeedStoreInList(AnimalFeedStoreInExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<AnimalFeedStoreIn> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getFeedOrderId() != null) {
                predicates.add(cb.equal(root.get("feedOrderId"), exportReqVO.getFeedOrderId()));
            }

            if(exportReqVO.getRoomId() != null) {
                predicates.add(cb.equal(root.get("roomId"), exportReqVO.getRoomId()));
            }

            if(exportReqVO.getShelfIds() != null) {
                predicates.add(cb.equal(root.get("shelfIds"), exportReqVO.getShelfIds()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getLocation() != null) {
                predicates.add(cb.like(root.get("location"), "%" + exportReqVO.getLocation() + "%"));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return animalFeedStoreInRepository.findAll(spec);
    }

    private Sort createSort(AnimalFeedStoreInPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getFeedOrderId() != null) {
            orders.add(new Sort.Order(order.getFeedOrderId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "feedOrderId"));
        }

        if (order.getRoomId() != null) {
            orders.add(new Sort.Order(order.getRoomId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "roomId"));
        }

        if (order.getShelfIds() != null) {
            orders.add(new Sort.Order(order.getShelfIds().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "shelfIds"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getLocation() != null) {
            orders.add(new Sort.Order(order.getLocation().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "location"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}