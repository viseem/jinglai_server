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
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedCard;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.animal.AnimalFeedCardMapper;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalFeedCardRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 动物饲养鼠牌 Service 实现类
 *
 */
@Service
@Validated
public class AnimalFeedCardServiceImpl implements AnimalFeedCardService {

    @Resource
    private AnimalFeedCardRepository animalFeedCardRepository;

    @Resource
    private AnimalFeedCardMapper animalFeedCardMapper;

    @Override
    public Long createAnimalFeedCard(AnimalFeedCardCreateReqVO createReqVO) {
        // 插入
        AnimalFeedCard animalFeedCard = animalFeedCardMapper.toEntity(createReqVO);
        animalFeedCardRepository.save(animalFeedCard);
        // 返回
        return animalFeedCard.getId();
    }

    @Override
    public void updateAnimalFeedCard(AnimalFeedCardUpdateReqVO updateReqVO) {
        // 校验存在
        validateAnimalFeedCardExists(updateReqVO.getId());
        // 更新
        AnimalFeedCard updateObj = animalFeedCardMapper.toEntity(updateReqVO);
        animalFeedCardRepository.save(updateObj);
    }

    @Override
    public void deleteAnimalFeedCard(Long id) {
        // 校验存在
        validateAnimalFeedCardExists(id);
        // 删除
        animalFeedCardRepository.deleteById(id);
    }

    private void validateAnimalFeedCardExists(Long id) {
        animalFeedCardRepository.findById(id).orElseThrow(() -> exception(ANIMAL_FEED_CARD_NOT_EXISTS));
    }

    @Override
    public Optional<AnimalFeedCard> getAnimalFeedCard(Long id) {
        return animalFeedCardRepository.findById(id);
    }

    @Override
    public List<AnimalFeedCard> getAnimalFeedCardList(Collection<Long> ids) {
        return StreamSupport.stream(animalFeedCardRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<AnimalFeedCard> getAnimalFeedCardPage(AnimalFeedCardPageReqVO pageReqVO, AnimalFeedCardPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<AnimalFeedCard> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getFeedOrderId() != null) {
                predicates.add(cb.equal(root.get("feedOrderId"), pageReqVO.getFeedOrderId()));
            }

            if(pageReqVO.getSeq() != null) {
                predicates.add(cb.equal(root.get("seq"), pageReqVO.getSeq()));
            }

            if(pageReqVO.getBreed() != null) {
                predicates.add(cb.equal(root.get("breed"), pageReqVO.getBreed()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), pageReqVO.getGender()));
            }

            if(pageReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), pageReqVO.getQuantity()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<AnimalFeedCard> page = animalFeedCardRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<AnimalFeedCard> getAnimalFeedCardList(AnimalFeedCardExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<AnimalFeedCard> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getFeedOrderId() != null) {
                predicates.add(cb.equal(root.get("feedOrderId"), exportReqVO.getFeedOrderId()));
            }

            if(exportReqVO.getSeq() != null) {
                predicates.add(cb.equal(root.get("seq"), exportReqVO.getSeq()));
            }

            if(exportReqVO.getBreed() != null) {
                predicates.add(cb.equal(root.get("breed"), exportReqVO.getBreed()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), exportReqVO.getGender()));
            }

            if(exportReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), exportReqVO.getQuantity()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return animalFeedCardRepository.findAll(spec);
    }

    private Sort createSort(AnimalFeedCardPageOrder order) {
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

        if (order.getSeq() != null) {
            orders.add(new Sort.Order(order.getSeq().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "seq"));
        }

        if (order.getBreed() != null) {
            orders.add(new Sort.Order(order.getBreed().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "breed"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getGender() != null) {
            orders.add(new Sort.Order(order.getGender().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "gender"));
        }

        if (order.getQuantity() != null) {
            orders.add(new Sort.Order(order.getQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quantity"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}