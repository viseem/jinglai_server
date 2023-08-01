package cn.iocoder.yudao.module.jl.service.crm;

import cn.iocoder.yudao.module.jl.utils.DateAttributeGenerator;
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
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.CrmContact;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.crm.CrmContactMapper;
import cn.iocoder.yudao.module.jl.repository.crm.CrmContactRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 客户联系人 Service 实现类
 *
 */
@Service
@Validated
public class CrmContactServiceImpl implements CrmContactService {

    @Resource
    private DateAttributeGenerator dateAttributeGenerator;

    @Resource
    private CrmContactRepository crmContactRepository;

    @Resource
    private CrmContactMapper crmContactMapper;

    @Override
    public Long createCrmContact(CrmContactCreateReqVO createReqVO) {
        // 插入
        CrmContact crmContact = crmContactMapper.toEntity(createReqVO);
        crmContactRepository.save(crmContact);
        // 返回
        return crmContact.getId();
    }

    @Override
    public void updateCrmContact(CrmContactUpdateReqVO updateReqVO) {
        // 校验存在
        validateCrmContactExists(updateReqVO.getId());
        // 更新
        CrmContact updateObj = crmContactMapper.toEntity(updateReqVO);
        crmContactRepository.save(updateObj);
    }

    @Override
    public void deleteCrmContact(Long id) {
        // 校验存在
        validateCrmContactExists(id);
        // 删除
        crmContactRepository.deleteById(id);
    }

    private void validateCrmContactExists(Long id) {
        crmContactRepository.findById(id).orElseThrow(() -> exception(CRM_CONTACT_NOT_EXISTS));
    }

    @Override
    public Optional<CrmContact> getCrmContact(Long id) {
        return crmContactRepository.findById(id);
    }

    @Override
    public List<CrmContact> getCrmContactList(Collection<Long> ids) {
        return StreamSupport.stream(crmContactRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CrmContact> getCrmContactPage(CrmContactPageReqVO pageReqVO, CrmContactPageOrder orderV0) {

        Long[] users = dateAttributeGenerator.processAttributeUsers(pageReqVO.getAttribute());
        pageReqVO.setCreators(users);

        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CrmContact> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(root.get("creator").in(Arrays.stream(pageReqVO.getCreators()).toArray()));

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), pageReqVO.getGender()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getPhone() != null) {
                predicates.add(cb.equal(root.get("phone"), pageReqVO.getPhone()));
            }

            if(pageReqVO.getTel() != null) {
                predicates.add(cb.equal(root.get("tel"), pageReqVO.getTel()));
            }

            if(pageReqVO.getEmail() != null) {
                predicates.add(cb.equal(root.get("email"), pageReqVO.getEmail()));
            }

            if(pageReqVO.getPosition() != null) {
                predicates.add(cb.equal(root.get("position"), pageReqVO.getPosition()));
            }

            if(pageReqVO.getIsMaker() != null) {
                predicates.add(cb.equal(root.get("isMaker"), pageReqVO.getIsMaker()));
            }

            if(pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }

            if(pageReqVO.getProvince() != null) {
                predicates.add(cb.equal(root.get("province"), pageReqVO.getProvince()));
            }

            if(pageReqVO.getCity() != null) {
                predicates.add(cb.equal(root.get("city"), pageReqVO.getCity()));
            }

            if(pageReqVO.getArea() != null) {
                predicates.add(cb.equal(root.get("area"), pageReqVO.getArea()));
            }

            if(pageReqVO.getNextContactTime() != null) {
                predicates.add(cb.between(root.get("nextContactTime"), pageReqVO.getNextContactTime()[0], pageReqVO.getNextContactTime()[1]));
            } 
            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CrmContact> page = crmContactRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CrmContact> getCrmContactList(CrmContactExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CrmContact> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), exportReqVO.getGender()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getPhone() != null) {
                predicates.add(cb.equal(root.get("phone"), exportReqVO.getPhone()));
            }

            if(exportReqVO.getTel() != null) {
                predicates.add(cb.equal(root.get("tel"), exportReqVO.getTel()));
            }

            if(exportReqVO.getEmail() != null) {
                predicates.add(cb.equal(root.get("email"), exportReqVO.getEmail()));
            }

            if(exportReqVO.getPosition() != null) {
                predicates.add(cb.equal(root.get("position"), exportReqVO.getPosition()));
            }

            if(exportReqVO.getIsMaker() != null) {
                predicates.add(cb.equal(root.get("isMaker"), exportReqVO.getIsMaker()));
            }

            if(exportReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), exportReqVO.getAddress()));
            }

            if(exportReqVO.getProvince() != null) {
                predicates.add(cb.equal(root.get("province"), exportReqVO.getProvince()));
            }

            if(exportReqVO.getCity() != null) {
                predicates.add(cb.equal(root.get("city"), exportReqVO.getCity()));
            }

            if(exportReqVO.getArea() != null) {
                predicates.add(cb.equal(root.get("area"), exportReqVO.getArea()));
            }

            if(exportReqVO.getNextContactTime() != null) {
                predicates.add(cb.between(root.get("nextContactTime"), exportReqVO.getNextContactTime()[0], exportReqVO.getNextContactTime()[1]));
            } 
            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return crmContactRepository.findAll(spec);
    }

    private Sort createSort(CrmContactPageOrder order) {
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

        if (order.getGender() != null) {
            orders.add(new Sort.Order(order.getGender().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "gender"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getPhone() != null) {
            orders.add(new Sort.Order(order.getPhone().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "phone"));
        }

        if (order.getTel() != null) {
            orders.add(new Sort.Order(order.getTel().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "tel"));
        }

        if (order.getEmail() != null) {
            orders.add(new Sort.Order(order.getEmail().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "email"));
        }

        if (order.getPosition() != null) {
            orders.add(new Sort.Order(order.getPosition().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "position"));
        }

        if (order.getIsMaker() != null) {
            orders.add(new Sort.Order(order.getIsMaker().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "isMaker"));
        }

        if (order.getAddress() != null) {
            orders.add(new Sort.Order(order.getAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        }

        if (order.getProvince() != null) {
            orders.add(new Sort.Order(order.getProvince().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "province"));
        }

        if (order.getCity() != null) {
            orders.add(new Sort.Order(order.getCity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "city"));
        }

        if (order.getArea() != null) {
            orders.add(new Sort.Order(order.getArea().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "area"));
        }

        if (order.getNextContactTime() != null) {
            orders.add(new Sort.Order(order.getNextContactTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "nextContactTime"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}