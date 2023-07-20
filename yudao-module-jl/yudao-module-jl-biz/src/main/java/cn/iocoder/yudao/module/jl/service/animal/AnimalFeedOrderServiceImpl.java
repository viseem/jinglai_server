package cn.iocoder.yudao.module.jl.service.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedLog;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedStoreIn;
import cn.iocoder.yudao.module.jl.enums.AnimalFeedBillRulesEnums;
import cn.iocoder.yudao.module.jl.enums.AnimalFeedStageEnums;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalFeedCardRepository;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalFeedStoreInRepository;
import cn.iocoder.yudao.module.jl.utils.UniqCodeGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Predicate;

import java.util.*;

import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedOrder;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.animal.AnimalFeedOrderMapper;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalFeedOrderRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.*;

/**
 * 动物饲养申请单 Service 实现类
 */
@Service
@Validated
public class AnimalFeedOrderServiceImpl implements AnimalFeedOrderService {

    private final String uniqCodeKey = AUTO_INCREMENT_KEY_ANIMAL_FEED_ORDER.getKeyTemplate();
    private final String uniqCodePrefixKey = PREFIX_ANIMAL_FEED_ORDER.getKeyTemplate();


    @Resource
    private UniqCodeGenerator uniqCodeGenerator;

    @PostConstruct
    public void ProjectServiceImpl(){
        AnimalFeedOrder firstByOrderByIdDesc = animalFeedOrderRepository.findFirstByOrderByIdDesc();
        uniqCodeGenerator.setInitUniqUid(firstByOrderByIdDesc != null ? firstByOrderByIdDesc.getId() : 0L,uniqCodeKey,uniqCodePrefixKey, ANIMAL_FEED_ORDER_DEFAULT_PREFIX);
    }


    public String generateCode() {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return  String.format("%s%s%07d",uniqCodeGenerator.getUniqCodePrefix(),dateStr, uniqCodeGenerator.generateUniqUid());
    }

    @Resource
    private AnimalFeedOrderRepository animalFeedOrderRepository;

    @Resource
    private AnimalFeedCardRepository animalFeedCardRepository;

    @Resource
    private AnimalFeedStoreInRepository animalFeedStoreInRepository;

    @Resource
    private AnimalFeedOrderMapper animalFeedOrderMapper;

    @Override
    public Long createAnimalFeedOrder(AnimalFeedOrderCreateReqVO createReqVO) {
        // 插入
        createReqVO.setCode(generateCode());
        AnimalFeedOrder animalFeedOrder = animalFeedOrderMapper.toEntity(createReqVO);
        animalFeedOrderRepository.save(animalFeedOrder);
        // 返回
        return animalFeedOrder.getId();
    }

    @Override
    public void updateAnimalFeedOrder(AnimalFeedOrderUpdateReqVO updateReqVO) {
        // 校验存在
        validateAnimalFeedOrderExists(updateReqVO.getId());
        // 更新
        AnimalFeedOrder updateObj = animalFeedOrderMapper.toEntity(updateReqVO);
        animalFeedOrderRepository.save(updateObj);
    }

    @Override
    public void saveAnimalFeedOrder(AnimalFeedOrderSaveReqVO saveReqVO) {
        // 校验存在
        // 更新
        if (saveReqVO.getId()==null||saveReqVO.getId()<=0){
            saveReqVO.setCode(generateCode());
        }
        AnimalFeedOrder saveObj = animalFeedOrderMapper.toEntity(saveReqVO);
        AnimalFeedOrder animalFeedOrder = animalFeedOrderRepository.save(saveObj);
        Long id = animalFeedOrder.getId();

        animalFeedCardRepository.saveAll(saveReqVO.getCards().stream().map(item -> {
            item.setFeedOrderId(id);
            item.setProjectId(animalFeedOrder.getProjectId());
            item.setCustomerId(animalFeedOrder.getCustomerId());
            item.setBreed(saveObj.getBreed());
            return item;
        }).collect(Collectors.toList()));

    }

    @Override
    public void storeAnimalFeedOrder(AnimalFeedOrderStoreReqVO storeReqVO) {
        // 校验存在
        // 更新
        storeReqVO.setStage(AnimalFeedStageEnums.FEEDING.getStatus());
        AnimalFeedOrder saveObj = animalFeedOrderMapper.toEntity(storeReqVO);
        AnimalFeedOrder animalFeedOrder = animalFeedOrderRepository.save(saveObj);
        Long id = animalFeedOrder.getId();

        //更新鼠牌
        animalFeedCardRepository.saveAll(storeReqVO.getCards().stream().map(item -> {
            item.setFeedOrderId(id);
            item.setProjectId(animalFeedOrder.getProjectId());
            item.setCustomerId(animalFeedOrder.getCustomerId());
            item.setBreed(saveObj.getBreed());
            return item;
        }).collect(Collectors.toList()));

        //更新入库信息
        animalFeedStoreInRepository.saveAll(storeReqVO.getStores().stream().map(item -> {
            item.setFeedOrderId(id);
            return item;
        }).collect(Collectors.toList()));

    }

    @Override
    public void deleteAnimalFeedOrder(Long id) {
        // 校验存在
        validateAnimalFeedOrderExists(id);
        // 删除
        animalFeedOrderRepository.deleteById(id);
    }

    private void validateAnimalFeedOrderExists(Long id) {
        animalFeedOrderRepository.findById(id).orElseThrow(() -> exception(ANIMAL_FEED_ORDER_NOT_EXISTS));
    }

    @Override
    public Optional<AnimalFeedOrder> getAnimalFeedOrder(Long id) {
        Optional<AnimalFeedOrder> byId = animalFeedOrderRepository.findById(id);


        if (byId.isPresent()) {
            AnimalFeedOrder animalFeedOrder = byId.get();
            processLatestFeedLog(animalFeedOrder);
        }
        return byId;
    }

    private void processLatestFeedLog(AnimalFeedOrder animalFeedOrder) {
        List<AnimalFeedLog> logs = animalFeedOrder.getLogs();
        if (logs != null&&logs.size()>0) {
            Map<String, Integer> dateStrToRowAmountMap = new HashMap<>();
            AtomicInteger totalAmount = new AtomicInteger();
            logs.forEach(log -> {
                String dateStr = log.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Integer count = log.getCageQuantity();
                if (Objects.equals(animalFeedOrder.getBillRules(), AnimalFeedBillRulesEnums.ONE.getStatus())) {
                    count = log.getQuantity();
                }
                Integer price = 0;
                if (animalFeedOrder.getUnitFee() != null && animalFeedOrder.getUnitFee() > 0) {
                    price = animalFeedOrder.getUnitFee();
                }
                int rowAmount = count * price;

                if (dateStrToRowAmountMap.containsKey(dateStr)) {
                    // If dateStr already exists in the map, get the rowAmount from the map
                    rowAmount = dateStrToRowAmountMap.get(dateStr);
                } else {
                    totalAmount.addAndGet(rowAmount);
                    // If dateStr is encountered for the first time, add it to the map with the rowAmount value
                    dateStrToRowAmountMap.put(dateStr, rowAmount);
                }

                log.setDateStr(dateStr);
                log.setTimeStr(log.getCreateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                log.setRowAmount(rowAmount);
            });
            animalFeedOrder.setAmount(totalAmount.get());
            animalFeedOrder.setLatestLog(logs.get(0));
        }
    }

    private void processLatestFeedStore(AnimalFeedOrder animalFeedOrder){
        List<AnimalFeedStoreIn> stores = animalFeedOrder.getStores();
        if(stores!=null&&stores.size()>0){
            animalFeedOrder.setLatestStore(stores.get(0));
        }
    }

    @Override
    public List<AnimalFeedOrder> getAnimalFeedOrderList(Collection<Long> ids) {
        return StreamSupport.stream(animalFeedOrderRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<AnimalFeedOrder> getAnimalFeedOrderPage(AnimalFeedOrderPageReqVO pageReqVO, AnimalFeedOrderPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<AnimalFeedOrder> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if (pageReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), pageReqVO.getCode()));
            }

            if (pageReqVO.getBreed() != null) {
                predicates.add(cb.equal(root.get("breed"), pageReqVO.getBreed()));
            }

            if (pageReqVO.getAge() != null) {
                predicates.add(cb.equal(root.get("age"), pageReqVO.getAge()));
            }

            if (pageReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), pageReqVO.getQuantity()));
            }

            if (pageReqVO.getFemaleCount() != null) {
                predicates.add(cb.equal(root.get("femaleCount"), pageReqVO.getFemaleCount()));
            }

            if (pageReqVO.getMaleCount() != null) {
                predicates.add(cb.equal(root.get("maleCount"), pageReqVO.getMaleCount()));
            }

            if (pageReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), pageReqVO.getSupplierId()));
            }

            if (pageReqVO.getSupplierName() != null) {
                predicates.add(cb.like(root.get("supplierName"), "%" + pageReqVO.getSupplierName() + "%"));
            }

            if (pageReqVO.getCertificateNumber() != null) {
                predicates.add(cb.like(root.get("certificateNumber"), "%" + pageReqVO.getCertificateNumber() + "%"));
            }

            if (pageReqVO.getLicenseNumber() != null) {
                predicates.add(cb.like(root.get("licenseNumber"), "%" + pageReqVO.getLicenseNumber() + "%"));
            }
            if (pageReqVO.getHasDanger() != null) {
                predicates.add(cb.equal(root.get("hasDanger"), pageReqVO.getHasDanger()));
            }

            if (pageReqVO.getFeedType() != null) {
                predicates.add(cb.equal(root.get("feedType"), pageReqVO.getFeedType()));
            }

            if (pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if (pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if (pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if (pageReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), pageReqVO.getStage()));
            }

            if (pageReqVO.getReply() != null) {
                predicates.add(cb.equal(root.get("reply"), pageReqVO.getReply()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<AnimalFeedOrder> page = animalFeedOrderRepository.findAll(spec, pageable);

        List<AnimalFeedOrder> animalFeedOrders = page.getContent();

        animalFeedOrders.forEach(animalFeedOrder -> {
            processLatestFeedLog(animalFeedOrder);
            processLatestFeedStore(animalFeedOrder);
        });

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<AnimalFeedOrder> getAnimalFeedOrderList(AnimalFeedOrderExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<AnimalFeedOrder> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if (exportReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), exportReqVO.getCode()));
            }

            if (exportReqVO.getBreed() != null) {
                predicates.add(cb.equal(root.get("breed"), exportReqVO.getBreed()));
            }

            if (exportReqVO.getAge() != null) {
                predicates.add(cb.equal(root.get("age"), exportReqVO.getAge()));
            }

            if (exportReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), exportReqVO.getQuantity()));
            }

            if (exportReqVO.getFemaleCount() != null) {
                predicates.add(cb.equal(root.get("femaleCount"), exportReqVO.getFemaleCount()));
            }

            if (exportReqVO.getMaleCount() != null) {
                predicates.add(cb.equal(root.get("maleCount"), exportReqVO.getMaleCount()));
            }

            if (exportReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), exportReqVO.getSupplierId()));
            }

            if (exportReqVO.getSupplierName() != null) {
                predicates.add(cb.like(root.get("supplierName"), "%" + exportReqVO.getSupplierName() + "%"));
            }

            if (exportReqVO.getCertificateNumber() != null) {
                predicates.add(cb.like(root.get("certificateNumber"), "%" + exportReqVO.getCertificateNumber() + "%"));
            }

            if (exportReqVO.getLicenseNumber() != null) {
                predicates.add(cb.like(root.get("licenseNumber"), "%" + exportReqVO.getLicenseNumber() + "%"));
            }

/*            if(exportReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), exportReqVO.getStartDate()[0], exportReqVO.getStartDate()[1]));
            } 
            if(exportReqVO.getEndDate() != null) {
                predicates.add(cb.between(root.get("endDate"), exportReqVO.getEndDate()[0], exportReqVO.getEndDate()[1]));
            } */
            if (exportReqVO.getHasDanger() != null) {
                predicates.add(cb.equal(root.get("hasDanger"), exportReqVO.getHasDanger()));
            }

            if (exportReqVO.getFeedType() != null) {
                predicates.add(cb.equal(root.get("feedType"), exportReqVO.getFeedType()));
            }

            if (exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if (exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if (exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if (exportReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), exportReqVO.getStage()));
            }

            if (exportReqVO.getReply() != null) {
                predicates.add(cb.equal(root.get("reply"), exportReqVO.getReply()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return animalFeedOrderRepository.findAll(spec);
    }

    private Sort createSort(AnimalFeedOrderPageOrder order) {
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

        if (order.getBreed() != null) {
            orders.add(new Sort.Order(order.getBreed().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "breed"));
        }

        if (order.getAge() != null) {
            orders.add(new Sort.Order(order.getAge().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "age"));
        }

        if (order.getQuantity() != null) {
            orders.add(new Sort.Order(order.getQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quantity"));
        }

        if (order.getFemaleCount() != null) {
            orders.add(new Sort.Order(order.getFemaleCount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "femaleCount"));
        }

        if (order.getMaleCount() != null) {
            orders.add(new Sort.Order(order.getMaleCount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "maleCount"));
        }

        if (order.getSupplierId() != null) {
            orders.add(new Sort.Order(order.getSupplierId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplierId"));
        }

        if (order.getSupplierName() != null) {
            orders.add(new Sort.Order(order.getSupplierName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplierName"));
        }

        if (order.getCertificateNumber() != null) {
            orders.add(new Sort.Order(order.getCertificateNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "certificateNumber"));
        }

        if (order.getLicenseNumber() != null) {
            orders.add(new Sort.Order(order.getLicenseNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "licenseNumber"));
        }

        if (order.getStartDate() != null) {
            orders.add(new Sort.Order(order.getStartDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "startDate"));
        }

        if (order.getEndDate() != null) {
            orders.add(new Sort.Order(order.getEndDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "endDate"));
        }

        if (order.getHasDanger() != null) {
            orders.add(new Sort.Order(order.getHasDanger().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "hasDanger"));
        }

        if (order.getFeedType() != null) {
            orders.add(new Sort.Order(order.getFeedType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "feedType"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getStage() != null) {
            orders.add(new Sort.Order(order.getStage().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "stage"));
        }

        if (order.getReply() != null) {
            orders.add(new Sort.Order(order.getReply().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "reply"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}