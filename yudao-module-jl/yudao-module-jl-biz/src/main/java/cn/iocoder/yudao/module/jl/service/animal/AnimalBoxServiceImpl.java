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
import cn.iocoder.yudao.module.jl.entity.animal.AnimalBox;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.animal.AnimalBoxMapper;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalBoxRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 动物笼位 Service 实现类
 *
 */
@Service
@Validated
public class AnimalBoxServiceImpl implements AnimalBoxService {

    @Resource
    private AnimalBoxRepository animalBoxRepository;

    @Resource
    private AnimalBoxMapper animalBoxMapper;

    @Override
    public Long createAnimalBox(AnimalBoxCreateReqVO createReqVO) {
        // 插入
        AnimalBox animalBox = animalBoxMapper.toEntity(createReqVO);
        animalBoxRepository.save(animalBox);
        // 返回
        return animalBox.getId();
    }

    @Override
    public void updateAnimalBox(AnimalBoxUpdateReqVO updateReqVO) {
        // 校验存在
        validateAnimalBoxExists(updateReqVO.getId());
        // 更新
        AnimalBox updateObj = animalBoxMapper.toEntity(updateReqVO);
        animalBoxRepository.save(updateObj);
    }

    @Override
    public void deleteAnimalBox(Long id) {
        // 校验存在
        validateAnimalBoxExists(id);
        // 删除
        animalBoxRepository.deleteById(id);
    }

    private void validateAnimalBoxExists(Long id) {
        animalBoxRepository.findById(id).orElseThrow(() -> exception(ANIMAL_BOX_NOT_EXISTS));
    }

    @Override
    public Optional<AnimalBox> getAnimalBox(Long id) {
        return animalBoxRepository.findById(id);
    }

    @Override
    public List<AnimalBox> getAnimalBoxList(Collection<Long> ids) {
        return StreamSupport.stream(animalBoxRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<AnimalBox> getAnimalBoxPage(AnimalBoxPageReqVO pageReqVO, AnimalBoxPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<AnimalBox> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), pageReqVO.getCode()));
            }

            if(pageReqVO.getLocation() != null) {
                predicates.add(cb.equal(root.get("location"), pageReqVO.getLocation()));
            }

            if(pageReqVO.getCapacity() != null) {
                predicates.add(cb.equal(root.get("capacity"), pageReqVO.getCapacity()));
            }

            if(pageReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), pageReqVO.getQuantity()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getShelfId() != null) {
                predicates.add(cb.equal(root.get("shelfId"), pageReqVO.getShelfId()));
            }

            if(pageReqVO.getRoomId() != null) {
                predicates.add(cb.equal(root.get("roomId"), pageReqVO.getRoomId()));
            }

            if(pageReqVO.getRowIndex() != null) {
                predicates.add(cb.equal(root.get("rowIndex"), pageReqVO.getRowIndex()));
            }

            if(pageReqVO.getColIndex() != null) {
                predicates.add(cb.equal(root.get("colIndex"), pageReqVO.getColIndex()));
            }

            if(pageReqVO.getFeedOrderId() != null) {
                predicates.add(cb.equal(root.get("feedOrderId"), pageReqVO.getFeedOrderId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<AnimalBox> page = animalBoxRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<AnimalBox> getAnimalBoxList(AnimalBoxExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<AnimalBox> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), exportReqVO.getCode()));
            }

            if(exportReqVO.getLocation() != null) {
                predicates.add(cb.equal(root.get("location"), exportReqVO.getLocation()));
            }

            if(exportReqVO.getCapacity() != null) {
                predicates.add(cb.equal(root.get("capacity"), exportReqVO.getCapacity()));
            }

            if(exportReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), exportReqVO.getQuantity()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getShelfId() != null) {
                predicates.add(cb.equal(root.get("shelfId"), exportReqVO.getShelfId()));
            }

            if(exportReqVO.getRoomId() != null) {
                predicates.add(cb.equal(root.get("roomId"), exportReqVO.getRoomId()));
            }

            if(exportReqVO.getRowIndex() != null) {
                predicates.add(cb.equal(root.get("rowIndex"), exportReqVO.getRowIndex()));
            }

            if(exportReqVO.getColIndex() != null) {
                predicates.add(cb.equal(root.get("colIndex"), exportReqVO.getColIndex()));
            }

            if(exportReqVO.getFeedOrderId() != null) {
                predicates.add(cb.equal(root.get("feedOrderId"), exportReqVO.getFeedOrderId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return animalBoxRepository.findAll(spec);
    }

    private Sort createSort(AnimalBoxPageOrder order) {
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

        if (order.getLocation() != null) {
            orders.add(new Sort.Order(order.getLocation().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "location"));
        }

        if (order.getCapacity() != null) {
            orders.add(new Sort.Order(order.getCapacity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "capacity"));
        }

        if (order.getQuantity() != null) {
            orders.add(new Sort.Order(order.getQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quantity"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getShelfId() != null) {
            orders.add(new Sort.Order(order.getShelfId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "shelfId"));
        }

        if (order.getRoomId() != null) {
            orders.add(new Sort.Order(order.getRoomId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "roomId"));
        }

        if (order.getRowIndex() != null) {
            orders.add(new Sort.Order(order.getRowIndex().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "rowIndex"));
        }

        if (order.getColIndex() != null) {
            orders.add(new Sort.Order(order.getColIndex().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "colIndex"));
        }

        if (order.getFeedOrderId() != null) {
            orders.add(new Sort.Order(order.getFeedOrderId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "feedOrderId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}