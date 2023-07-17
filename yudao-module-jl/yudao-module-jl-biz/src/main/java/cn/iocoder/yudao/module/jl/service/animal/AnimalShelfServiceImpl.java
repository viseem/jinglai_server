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
import cn.iocoder.yudao.module.jl.entity.animal.AnimalShelf;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.animal.AnimalShelfMapper;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalShelfRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 动物饲养笼架 Service 实现类
 *
 */
@Service
@Validated
public class AnimalShelfServiceImpl implements AnimalShelfService {

    @Resource
    private AnimalShelfRepository animalShelfRepository;

    @Resource
    private AnimalShelfMapper animalShelfMapper;

    @Override
    public Long createAnimalShelf(AnimalShelfCreateReqVO createReqVO) {
        // 插入
        AnimalShelf animalShelf = animalShelfMapper.toEntity(createReqVO);
        animalShelfRepository.save(animalShelf);
        // 返回
        return animalShelf.getId();
    }

    @Override
    public void updateAnimalShelf(AnimalShelfUpdateReqVO updateReqVO) {
        // 校验存在
        validateAnimalShelfExists(updateReqVO.getId());
        // 更新
        AnimalShelf updateObj = animalShelfMapper.toEntity(updateReqVO);
        animalShelfRepository.save(updateObj);
    }

    @Override
    public void deleteAnimalShelf(Long id) {
        // 校验存在
        validateAnimalShelfExists(id);
        // 删除
        animalShelfRepository.deleteById(id);
    }

    private void validateAnimalShelfExists(Long id) {
        animalShelfRepository.findById(id).orElseThrow(() -> exception(ANIMAL_SHELF_NOT_EXISTS));
    }

    @Override
    public Optional<AnimalShelf> getAnimalShelf(Long id) {
        return animalShelfRepository.findById(id);
    }

    @Override
    public List<AnimalShelf> getAnimalShelfList(Collection<Long> ids) {
        return StreamSupport.stream(animalShelfRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<AnimalShelf> getAnimalShelfPage(AnimalShelfPageReqVO pageReqVO, AnimalShelfPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<AnimalShelf> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getCode() != null) {
                predicates.add(cb.like(root.get("code"), "%" + pageReqVO.getCode() + "%"));
            }

            if(pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            if(pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }

            if(pageReqVO.getLocation() != null) {
                predicates.add(cb.like(root.get("location"), "%" + pageReqVO.getLocation() + "%"));
            }

            if(pageReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), pageReqVO.getManagerId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.like(root.get("mark"), "%" + pageReqVO.getMark() + "%"));
            }

            if(pageReqVO.getRoomId() != null) {
                predicates.add(cb.equal(root.get("roomId"), pageReqVO.getRoomId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<AnimalShelf> page = animalShelfRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<AnimalShelf> getAnimalShelfList(AnimalShelfExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<AnimalShelf> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getCode() != null) {
                predicates.add(cb.like(root.get("code"), "%" + exportReqVO.getCode() + "%"));
            }

            if(exportReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + exportReqVO.getFileName() + "%"));
            }

            if(exportReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), exportReqVO.getFileUrl()));
            }

            if(exportReqVO.getLocation() != null) {
                predicates.add(cb.like(root.get("location"), "%" + exportReqVO.getLocation() + "%"));
            }

            if(exportReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), exportReqVO.getManagerId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.like(root.get("mark"), "%" + exportReqVO.getMark() + "%"));
            }

            if(exportReqVO.getRoomId() != null) {
                predicates.add(cb.equal(root.get("roomId"), exportReqVO.getRoomId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return animalShelfRepository.findAll(spec);
    }

    private Sort createSort(AnimalShelfPageOrder order) {
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

        if (order.getCode() != null) {
            orders.add(new Sort.Order(order.getCode().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "code"));
        }

        if (order.getFileName() != null) {
            orders.add(new Sort.Order(order.getFileName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileName"));
        }

        if (order.getFileUrl() != null) {
            orders.add(new Sort.Order(order.getFileUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileUrl"));
        }

        if (order.getLocation() != null) {
            orders.add(new Sort.Order(order.getLocation().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "location"));
        }

        if (order.getManagerId() != null) {
            orders.add(new Sort.Order(order.getManagerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "managerId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getRoomId() != null) {
            orders.add(new Sort.Order(order.getRoomId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "roomId"));
        }

        if (order.getOrder() != null) {
            orders.add(new Sort.Order(order.getOrder().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "order"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}