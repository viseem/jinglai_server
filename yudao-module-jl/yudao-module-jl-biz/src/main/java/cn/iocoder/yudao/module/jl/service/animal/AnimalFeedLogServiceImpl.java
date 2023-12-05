package cn.iocoder.yudao.module.jl.service.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedOrder;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalFeedOrderRepository;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalFeedStoreInRepository;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
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
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.animal.AnimalFeedLogMapper;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalFeedLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 动物饲养日志 Service 实现类
 *
 */
@Service
@Validated
public class AnimalFeedLogServiceImpl implements AnimalFeedLogService {

    @Resource
    private AnimalFeedLogRepository animalFeedLogRepository;

    @Resource
    private AnimalFeedOrderRepository animalFeedOrderRepository;

    @Resource
    private AnimalFeedStoreInRepository animalFeedStoreInRepository;

    @Resource
    private AnimalFeedLogMapper animalFeedLogMapper;

    @Override
    public Long createAnimalFeedLog(AnimalFeedLogCreateReqVO createReqVO) {
        // 插入
        AnimalFeedLog animalFeedLog = animalFeedLogMapper.toEntity(createReqVO);
        animalFeedLogRepository.save(animalFeedLog);
        // 返回
        return animalFeedLog.getId();
    }

    @Override
    public void updateAnimalFeedLog(AnimalFeedLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateAnimalFeedLogExists(updateReqVO.getId());
        // 更新
        AnimalFeedLog updateObj = animalFeedLogMapper.toEntity(updateReqVO);
        animalFeedLogRepository.save(updateObj);
    }

    @Override
    @Transactional
    public void saveAnimalFeedLog(AnimalFeedLogSaveReqVO saveReqVO) {
        // 校验存在
//        validateAnimalFeedLogExists(saveReqVO.getId());
        // 更新
        AnimalFeedLog updateObj = animalFeedLogMapper.toEntity(saveReqVO);
        AnimalFeedLog save = animalFeedLogRepository.save(updateObj);
        Long feedOrderId = updateObj.getFeedOrderId();

        //更新入库信息
        animalFeedStoreInRepository.saveAll(saveReqVO.getStoreList().stream().map(item->{
            item.setFeedOrderId(feedOrderId);
            item.setLogId(save.getId());
            return item;
        }).collect(Collectors.toList()));

        //更新饲养单的location和locationCode
        animalFeedOrderRepository.updateLocationAndLocationCodeById(saveReqVO.getLocation(),saveReqVO.getLocationCode(),feedOrderId);
    }

    @Override
    public void deleteAnimalFeedLog(Long id) {
        // 校验存在
        validateAnimalFeedLogExists(id);
        // 删除
        animalFeedLogRepository.deleteById(id);
    }

    private void validateAnimalFeedLogExists(Long id) {
        animalFeedLogRepository.findById(id).orElseThrow(() -> exception(ANIMAL_FEED_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<AnimalFeedLog> getAnimalFeedLog(Long id) {
        return animalFeedLogRepository.findById(id);
    }

    @Override
    public List<AnimalFeedLog> getAnimalFeedLogList(Collection<Long> ids) {
        return StreamSupport.stream(animalFeedLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<AnimalFeedLog> getAnimalFeedLogPage(AnimalFeedLogPageReqVO pageReqVO, AnimalFeedLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<AnimalFeedLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getBoxId() != null) {
                predicates.add(cb.equal(root.get("boxId"), pageReqVO.getBoxId()));
            }

            if(pageReqVO.getFeedOrderId() != null) {
                predicates.add(cb.equal(root.get("feedOrderId"), pageReqVO.getFeedOrderId()));
            }

            if(pageReqVO.getChangeQuantity() != null) {
                predicates.add(cb.equal(root.get("changeQuantity"), pageReqVO.getChangeQuantity()));
            }

            if(pageReqVO.getChangeCageQuantity() != null) {
                predicates.add(cb.equal(root.get("changeCageQuantity"), pageReqVO.getChangeCageQuantity()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getFiles() != null) {
                predicates.add(cb.equal(root.get("files"), pageReqVO.getFiles()));
            }

            if(pageReqVO.getStores() != null) {
                predicates.add(cb.equal(root.get("stores"), pageReqVO.getStores()));
            }

            if(pageReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), pageReqVO.getQuantity()));
            }

            if(pageReqVO.getCageQuantity() != null) {
                predicates.add(cb.equal(root.get("cageQuantity"), pageReqVO.getCageQuantity()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<AnimalFeedLog> page = animalFeedLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<AnimalFeedLog> getAnimalFeedLogList(AnimalFeedLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<AnimalFeedLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getFeedOrderId() != null) {
                predicates.add(cb.equal(root.get("feedOrderId"), exportReqVO.getFeedOrderId()));
            }

            if(exportReqVO.getChangeQuantity() != null) {
                predicates.add(cb.equal(root.get("changeQuantity"), exportReqVO.getChangeQuantity()));
            }

            if(exportReqVO.getChangeCageQuantity() != null) {
                predicates.add(cb.equal(root.get("changeCageQuantity"), exportReqVO.getChangeCageQuantity()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getFiles() != null) {
                predicates.add(cb.equal(root.get("files"), exportReqVO.getFiles()));
            }

            if(exportReqVO.getStores() != null) {
                predicates.add(cb.equal(root.get("stores"), exportReqVO.getStores()));
            }

            if(exportReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), exportReqVO.getQuantity()));
            }

            if(exportReqVO.getCageQuantity() != null) {
                predicates.add(cb.equal(root.get("cageQuantity"), exportReqVO.getCageQuantity()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return animalFeedLogRepository.findAll(spec);
    }

    private Sort createSort(AnimalFeedLogPageOrder order) {
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

        if (order.getChangeQuantity() != null) {
            orders.add(new Sort.Order(order.getChangeQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "changeQuantity"));
        }

        if (order.getChangeCageQuantity() != null) {
            orders.add(new Sort.Order(order.getChangeCageQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "changeCageQuantity"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getFiles() != null) {
            orders.add(new Sort.Order(order.getFiles().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "files"));
        }

        if (order.getStores() != null) {
            orders.add(new Sort.Order(order.getStores().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "stores"));
        }

        if (order.getQuantity() != null) {
            orders.add(new Sort.Order(order.getQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quantity"));
        }

        if (order.getCageQuantity() != null) {
            orders.add(new Sort.Order(order.getCageQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "cageQuantity"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}