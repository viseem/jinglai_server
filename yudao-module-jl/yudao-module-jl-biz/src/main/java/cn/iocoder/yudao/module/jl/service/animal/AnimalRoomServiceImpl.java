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
import javax.transaction.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalRoom;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.animal.AnimalRoomMapper;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalRoomRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 动物饲养室 Service 实现类
 *
 */
@Service
@Validated
public class AnimalRoomServiceImpl implements AnimalRoomService {

    @Transactional
    public synchronized String generateUniqueCode(String prefix) {
        long count = animalRoomRepository.count();
        return String.format("%s%d", prefix,count+1);
    }

    @Resource
    private AnimalRoomRepository animalRoomRepository;

    @Resource
    private AnimalRoomMapper animalRoomMapper;

    private String generateCodeById(Long id){
        return "R"+id;
    }

    @Override
    public Long createAnimalRoom(AnimalRoomCreateReqVO createReqVO) {
        //校验一下code是否存在
        validateAnimalRoomExistsByCode(createReqVO.getCode());
        // 插入
        AnimalRoom animalRoom = animalRoomMapper.toEntity(createReqVO);
        animalRoomRepository.save(animalRoom);

        Long id = animalRoom.getId();
        if(createReqVO.getCode()==null||createReqVO.getCode().equals("")){
            animalRoomRepository.updateCodeById(generateCodeById(id),id);
        }
        // 返回
        return animalRoom.getId();
    }

    @Override
    public void updateAnimalRoom(AnimalRoomUpdateReqVO updateReqVO) {
        // 校验存在
        AnimalRoom animalRoom = validateAnimalRoomExists(updateReqVO.getId());

        // 如果是空 设置一个
        if(updateReqVO.getCode()==null||updateReqVO.getCode().equals("")){
            updateReqVO.setCode(generateCodeById(updateReqVO.getId()));
        } else if (!Objects.equals(animalRoom.getCode(), updateReqVO.getCode())) {
            //如果不空 并且跟原先的code不一样 校验一下存在
            validateAnimalRoomExistsByCode(updateReqVO.getCode());
        }
        // 更新
        AnimalRoom updateObj = animalRoomMapper.toEntity(updateReqVO);
        animalRoomRepository.save(updateObj);
    }

    @Override
    public void deleteAnimalRoom(Long id) {
        // 校验存在
        validateAnimalRoomExists(id);
        // 删除
        animalRoomRepository.deleteById(id);
    }

    private AnimalRoom validateAnimalRoomExists(Long id) {
        Optional<AnimalRoom> byId = animalRoomRepository.findById(id);
        if(byId.isEmpty()){
            throw exception(ANIMAL_ROOM_NOT_EXISTS);
        }
        return byId.orElse(null);
    }

    private void validateAnimalRoomExistsByCode(String code) {
        AnimalRoom byCode = animalRoomRepository.findByCode(code);
        if (byCode!=null){
            throw exception(ANIMAL_CODE_EXISTS);
        }
    }

    @Override
    public Optional<AnimalRoom> getAnimalRoom(Long id) {
        return animalRoomRepository.findById(id);
    }

    @Override
    public List<AnimalRoom> getAnimalRoomList(Collection<Long> ids) {
        return StreamSupport.stream(animalRoomRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<AnimalRoom> getAnimalRoomPage(AnimalRoomPageReqVO pageReqVO, AnimalRoomPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<AnimalRoom> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getCode() != null) {
                predicates.add(cb.like(root.get("code"), "%" + pageReqVO.getCode() + "%"));
            }

            if(pageReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), pageReqVO.getManagerId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.like(root.get("mark"), "%" + pageReqVO.getMark() + "%"));
            }

            if(pageReqVO.getLocation() != null) {
                predicates.add(cb.like(root.get("location"), "%" + pageReqVO.getLocation() + "%"));
            }


            if(pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            if(pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<AnimalRoom> page = animalRoomRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<AnimalRoom> getAnimalRoomList(AnimalRoomExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<AnimalRoom> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getCode() != null) {
                predicates.add(cb.like(root.get("code"), "%" + exportReqVO.getCode() + "%"));
            }

            if(exportReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), exportReqVO.getManagerId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.like(root.get("mark"), "%" + exportReqVO.getMark() + "%"));
            }

            if(exportReqVO.getLocation() != null) {
                predicates.add(cb.like(root.get("location"), "%" + exportReqVO.getLocation() + "%"));
            }


            if(exportReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + exportReqVO.getFileName() + "%"));
            }

            if(exportReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), exportReqVO.getFileUrl()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return animalRoomRepository.findAll(spec);
    }

    private Sort createSort(AnimalRoomPageOrder order) {
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

        if (order.getManagerId() != null) {
            orders.add(new Sort.Order(order.getManagerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "managerId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getLocation() != null) {
            orders.add(new Sort.Order(order.getLocation().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "location"));
        }

        if (order.getOrder() != null) {
            orders.add(new Sort.Order(order.getOrder().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "order"));
        }

        if (order.getFileName() != null) {
            orders.add(new Sort.Order(order.getFileName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileName"));
        }

        if (order.getFileUrl() != null) {
            orders.add(new Sort.Order(order.getFileUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileUrl"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}