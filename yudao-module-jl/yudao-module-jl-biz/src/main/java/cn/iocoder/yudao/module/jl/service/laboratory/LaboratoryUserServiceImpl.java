package cn.iocoder.yudao.module.jl.service.laboratory;

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
import cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo.*;
import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryUser;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.laboratory.LaboratoryUserMapper;
import cn.iocoder.yudao.module.jl.repository.laboratory.LaboratoryUserRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 实验室人员 Service 实现类
 *
 */
@Service
@Validated
public class LaboratoryUserServiceImpl implements LaboratoryUserService {

    @Resource
    private LaboratoryUserRepository laboratoryUserRepository;

    @Resource
    private LaboratoryUserMapper laboratoryUserMapper;

    @Override
    public Long createLaboratoryUser(LaboratoryUserCreateReqVO createReqVO) {
        // 插入
        LaboratoryUser laboratoryUser = laboratoryUserMapper.toEntity(createReqVO);
        laboratoryUserRepository.save(laboratoryUser);
        // 返回
        return laboratoryUser.getId();
    }

    @Override
    public void updateLaboratoryUser(LaboratoryUserUpdateReqVO updateReqVO) {
        // 校验存在
        validateLaboratoryUserExists(updateReqVO.getId());
        // 更新
        LaboratoryUser updateObj = laboratoryUserMapper.toEntity(updateReqVO);
        laboratoryUserRepository.save(updateObj);
    }

    @Override
    public void deleteLaboratoryUser(Long id) {
        // 校验存在
        validateLaboratoryUserExists(id);
        // 删除
        laboratoryUserRepository.deleteById(id);
    }

    private void validateLaboratoryUserExists(Long id) {
        laboratoryUserRepository.findById(id).orElseThrow(() -> exception(LABORATORY_USER_NOT_EXISTS));
    }

    @Override
    public Optional<LaboratoryUser> getLaboratoryUser(Long id) {
        return laboratoryUserRepository.findById(id);
    }

    @Override
    public List<LaboratoryUser> getLaboratoryUserList(Collection<Long> ids) {
        return StreamSupport.stream(laboratoryUserRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<LaboratoryUser> getLaboratoryUserPage(LaboratoryUserPageReqVO pageReqVO, LaboratoryUserPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<LaboratoryUser> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getLabId() != null) {
                predicates.add(cb.equal(root.get("labId"), pageReqVO.getLabId()));
            }

            if(pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getRank() != null) {
                predicates.add(cb.equal(root.get("rank"), pageReqVO.getRank()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<LaboratoryUser> page = laboratoryUserRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<LaboratoryUser> getLaboratoryUserList(LaboratoryUserExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<LaboratoryUser> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getLabId() != null) {
                predicates.add(cb.equal(root.get("labId"), exportReqVO.getLabId()));
            }

            if(exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getRank() != null) {
                predicates.add(cb.equal(root.get("rank"), exportReqVO.getRank()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return laboratoryUserRepository.findAll(spec);
    }

    private Sort createSort(LaboratoryUserPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getLabId() != null) {
            orders.add(new Sort.Order(order.getLabId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "labId"));
        }

        if (order.getUserId() != null) {
            orders.add(new Sort.Order(order.getUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "userId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getRank() != null) {
            orders.add(new Sort.Order(order.getRank().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "rank"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}