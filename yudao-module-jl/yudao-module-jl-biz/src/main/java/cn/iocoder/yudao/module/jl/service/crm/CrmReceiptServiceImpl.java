package cn.iocoder.yudao.module.jl.service.crm;

import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import cn.iocoder.yudao.module.jl.utils.UniqCodeGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
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
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.CrmReceipt;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.crm.CrmReceiptMapper;
import cn.iocoder.yudao.module.jl.repository.crm.CrmReceiptRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.*;

/**
 * 客户发票 Service 实现类
 *
 */
@Service
@Validated
public class CrmReceiptServiceImpl implements CrmReceiptService {

    @Resource
    private CrmReceiptRepository crmReceiptRepository;

    @Resource
    private CrmReceiptMapper crmReceiptMapper;

    @Resource
    private UniqCodeGenerator uniqCodeGenerator;

/*    @PostConstruct
    public void CrmReceiptServiceImpl() {
        CrmReceipt firstByOrderByIdDesc = crmReceiptRepository.findFirstByOrderByIdDesc();
        uniqCodeGenerator.setInitUniqUid(firstByOrderByIdDesc != null ? firstByOrderByIdDesc.getId() : 0L, AUTO_INCREMENT_KEY_CUSTOMER_RECEIPT.getKeyTemplate(), PREFIX_CUSTOMER_RECEIPT.getKeyTemplate(), CUSTOMER_RECEIPT_DEFAULT_PREFIX);
    }*/
    public String generateCode() {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return  String.format("%s%s%07d",uniqCodeGenerator.getUniqCodePrefix(),dateStr, uniqCodeGenerator.generateUniqUid());
    }


    @Override
    public Long createCrmReceipt(CrmReceiptCreateReqVO createReqVO) {
        createReqVO.setCode(generateCode());
        // 插入
        CrmReceipt crmReceipt = crmReceiptMapper.toEntity(createReqVO);
        crmReceiptRepository.save(crmReceipt);
        // 返回
        return crmReceipt.getId();
    }

    @Override
    public void updateCrmReceipt(CrmReceiptUpdateReqVO updateReqVO) {
        // 校验存在
        validateCrmReceiptExists(updateReqVO.getId());

        // 更新
        CrmReceipt updateObj = crmReceiptMapper.toEntity(updateReqVO);
        crmReceiptRepository.save(updateObj);
    }

    @Override
    public void deleteCrmReceipt(Long id) {
        // 校验存在
        validateCrmReceiptExists(id);
        // 删除
        crmReceiptRepository.deleteById(id);
    }

    private void validateCrmReceiptExists(Long id) {
        crmReceiptRepository.findById(id).orElseThrow(() -> exception(CRM_RECEIPT_NOT_EXISTS));
    }

    @Override
    public Optional<CrmReceipt> getCrmReceipt(Long id) {
        return crmReceiptRepository.findById(id);
    }

    @Override
    public List<CrmReceipt> getCrmReceiptList(Collection<Long> ids) {
        return StreamSupport.stream(crmReceiptRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CrmReceipt> getCrmReceiptPage(CrmReceiptPageReqVO pageReqVO, CrmReceiptPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CrmReceipt> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), pageReqVO.getCode()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getContractId() != null) {
                predicates.add(cb.equal(root.get("contractId"), pageReqVO.getContractId()));
            }

            if(pageReqVO.getFundId() != null) {
                predicates.add(cb.equal(root.get("fundId"), pageReqVO.getFundId()));
            }

            if(pageReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), pageReqVO.getPrice()));
            }

            if(pageReqVO.getDate() != null) {
                predicates.add(cb.between(root.get("date"), pageReqVO.getDate()[0], pageReqVO.getDate()[1]));
            } 
            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getNumber() != null) {
                predicates.add(cb.equal(root.get("number"), pageReqVO.getNumber()));
            }

            if(pageReqVO.getActualDate() != null) {
                predicates.add(cb.between(root.get("actualDate"), pageReqVO.getActualDate()[0], pageReqVO.getActualDate()[1]));
            } 
            if(pageReqVO.getShipmentNumber() != null) {
                predicates.add(cb.equal(root.get("shipmentNumber"), pageReqVO.getShipmentNumber()));
            }

            if(pageReqVO.getWay() != null) {
                predicates.add(cb.equal(root.get("way"), pageReqVO.getWay()));
            }

            if(pageReqVO.getReceiptHeadId() != null) {
                predicates.add(cb.equal(root.get("receiptHeadId"), pageReqVO.getReceiptHeadId()));
            }

            if(pageReqVO.getTitle() != null) {
                predicates.add(cb.equal(root.get("title"), pageReqVO.getTitle()));
            }

            if(pageReqVO.getTaxerNumber() != null) {
                predicates.add(cb.equal(root.get("taxerNumber"), pageReqVO.getTaxerNumber()));
            }

            if(pageReqVO.getBankName() != null) {
                predicates.add(cb.like(root.get("bankName"), "%" + pageReqVO.getBankName() + "%"));
            }

            if(pageReqVO.getBankAccount() != null) {
                predicates.add(cb.equal(root.get("bankAccount"), pageReqVO.getBankAccount()));
            }

            if(pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }

            if(pageReqVO.getPhone() != null) {
                predicates.add(cb.equal(root.get("phone"), pageReqVO.getPhone()));
            }

            if(pageReqVO.getHeadType() != null) {
                predicates.add(cb.equal(root.get("headType"), pageReqVO.getHeadType()));
            }

            if(pageReqVO.getSendContact() != null) {
                predicates.add(cb.equal(root.get("sendContact"), pageReqVO.getSendContact()));
            }

            if(pageReqVO.getSendPhone() != null) {
                predicates.add(cb.equal(root.get("sendPhone"), pageReqVO.getSendPhone()));
            }

            if(pageReqVO.getSendAddress() != null) {
                predicates.add(cb.equal(root.get("sendAddress"), pageReqVO.getSendAddress()));
            }

            if(pageReqVO.getSendProvince() != null) {
                predicates.add(cb.equal(root.get("sendProvince"), pageReqVO.getSendProvince()));
            }

            if(pageReqVO.getSendCity() != null) {
                predicates.add(cb.equal(root.get("sendCity"), pageReqVO.getSendCity()));
            }

            if(pageReqVO.getSendArea() != null) {
                predicates.add(cb.equal(root.get("sendArea"), pageReqVO.getSendArea()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CrmReceipt> page = crmReceiptRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CrmReceipt> getCrmReceiptList(CrmReceiptExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CrmReceipt> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), exportReqVO.getCode()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getContractId() != null) {
                predicates.add(cb.equal(root.get("contractId"), exportReqVO.getContractId()));
            }

            if(exportReqVO.getFundId() != null) {
                predicates.add(cb.equal(root.get("fundId"), exportReqVO.getFundId()));
            }

            if(exportReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), exportReqVO.getPrice()));
            }

            if(exportReqVO.getDate() != null) {
                predicates.add(cb.between(root.get("date"), exportReqVO.getDate()[0], exportReqVO.getDate()[1]));
            } 
            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getNumber() != null) {
                predicates.add(cb.equal(root.get("number"), exportReqVO.getNumber()));
            }

            if(exportReqVO.getActualDate() != null) {
                predicates.add(cb.between(root.get("actualDate"), exportReqVO.getActualDate()[0], exportReqVO.getActualDate()[1]));
            } 
            if(exportReqVO.getShipmentNumber() != null) {
                predicates.add(cb.equal(root.get("shipmentNumber"), exportReqVO.getShipmentNumber()));
            }

            if(exportReqVO.getWay() != null) {
                predicates.add(cb.equal(root.get("way"), exportReqVO.getWay()));
            }

            if(exportReqVO.getReceiptHeadId() != null) {
                predicates.add(cb.equal(root.get("receiptHeadId"), exportReqVO.getReceiptHeadId()));
            }

            if(exportReqVO.getTitle() != null) {
                predicates.add(cb.equal(root.get("title"), exportReqVO.getTitle()));
            }

            if(exportReqVO.getTaxerNumber() != null) {
                predicates.add(cb.equal(root.get("taxerNumber"), exportReqVO.getTaxerNumber()));
            }

            if(exportReqVO.getBankName() != null) {
                predicates.add(cb.like(root.get("bankName"), "%" + exportReqVO.getBankName() + "%"));
            }

            if(exportReqVO.getBankAccount() != null) {
                predicates.add(cb.equal(root.get("bankAccount"), exportReqVO.getBankAccount()));
            }

            if(exportReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), exportReqVO.getAddress()));
            }

            if(exportReqVO.getPhone() != null) {
                predicates.add(cb.equal(root.get("phone"), exportReqVO.getPhone()));
            }

            if(exportReqVO.getHeadType() != null) {
                predicates.add(cb.equal(root.get("headType"), exportReqVO.getHeadType()));
            }

            if(exportReqVO.getSendContact() != null) {
                predicates.add(cb.equal(root.get("sendContact"), exportReqVO.getSendContact()));
            }

            if(exportReqVO.getSendPhone() != null) {
                predicates.add(cb.equal(root.get("sendPhone"), exportReqVO.getSendPhone()));
            }

            if(exportReqVO.getSendAddress() != null) {
                predicates.add(cb.equal(root.get("sendAddress"), exportReqVO.getSendAddress()));
            }

            if(exportReqVO.getSendProvince() != null) {
                predicates.add(cb.equal(root.get("sendProvince"), exportReqVO.getSendProvince()));
            }

            if(exportReqVO.getSendCity() != null) {
                predicates.add(cb.equal(root.get("sendCity"), exportReqVO.getSendCity()));
            }

            if(exportReqVO.getSendArea() != null) {
                predicates.add(cb.equal(root.get("sendArea"), exportReqVO.getSendArea()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return crmReceiptRepository.findAll(spec);
    }

    private Sort createSort(CrmReceiptPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getCode() != null) {
            orders.add(new Sort.Order(order.getCode().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "code"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getContractId() != null) {
            orders.add(new Sort.Order(order.getContractId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "contractId"));
        }

        if (order.getFundId() != null) {
            orders.add(new Sort.Order(order.getFundId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fundId"));
        }

        if (order.getPrice() != null) {
            orders.add(new Sort.Order(order.getPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "price"));
        }

        if (order.getDate() != null) {
            orders.add(new Sort.Order(order.getDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "date"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getNumber() != null) {
            orders.add(new Sort.Order(order.getNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "number"));
        }

        if (order.getActualDate() != null) {
            orders.add(new Sort.Order(order.getActualDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "actualDate"));
        }

        if (order.getShipmentNumber() != null) {
            orders.add(new Sort.Order(order.getShipmentNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "shipmentNumber"));
        }

        if (order.getWay() != null) {
            orders.add(new Sort.Order(order.getWay().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "way"));
        }

        if (order.getReceiptHeadId() != null) {
            orders.add(new Sort.Order(order.getReceiptHeadId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receiptHeadId"));
        }

        if (order.getTitle() != null) {
            orders.add(new Sort.Order(order.getTitle().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "title"));
        }

        if (order.getTaxerNumber() != null) {
            orders.add(new Sort.Order(order.getTaxerNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "taxerNumber"));
        }

        if (order.getBankName() != null) {
            orders.add(new Sort.Order(order.getBankName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "bankName"));
        }

        if (order.getBankAccount() != null) {
            orders.add(new Sort.Order(order.getBankAccount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "bankAccount"));
        }

        if (order.getAddress() != null) {
            orders.add(new Sort.Order(order.getAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        }

        if (order.getPhone() != null) {
            orders.add(new Sort.Order(order.getPhone().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "phone"));
        }

        if (order.getHeadType() != null) {
            orders.add(new Sort.Order(order.getHeadType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "headType"));
        }

        if (order.getSendContact() != null) {
            orders.add(new Sort.Order(order.getSendContact().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sendContact"));
        }

        if (order.getSendPhone() != null) {
            orders.add(new Sort.Order(order.getSendPhone().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sendPhone"));
        }

        if (order.getSendAddress() != null) {
            orders.add(new Sort.Order(order.getSendAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sendAddress"));
        }

        if (order.getSendProvince() != null) {
            orders.add(new Sort.Order(order.getSendProvince().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sendProvince"));
        }

        if (order.getSendCity() != null) {
            orders.add(new Sort.Order(order.getSendCity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sendCity"));
        }

        if (order.getSendArea() != null) {
            orders.add(new Sort.Order(order.getSendArea().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sendArea"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}