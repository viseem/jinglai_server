package cn.iocoder.yudao.module.jl.service.dept;

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

import javax.persistence.criteria.Predicate;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.dept.vo.*;
import cn.iocoder.yudao.module.jl.entity.dept.Dept;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.dept.XDeptMapper;
import cn.iocoder.yudao.module.jl.repository.dept.DeptRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 部门 Service 实现类
 *
 */
@Service
@Validated
public class XDeptServiceImpl implements XDeptService {

    @Resource
    private DeptRepository deptRepository;

    @Resource
    private XDeptMapper deptMapper;

    @Override
    public Long createDept(DeptCreateReqVO createReqVO) {
        // 插入
        Dept dept = deptMapper.toEntity(createReqVO);
        deptRepository.save(dept);
        // 返回
        return dept.getId();
    }

    @Override
    public void updateDept(DeptUpdateReqVO updateReqVO) {
        // 校验存在
        validateDeptExists(updateReqVO.getId());
        // 更新
        Dept updateObj = deptMapper.toEntity(updateReqVO);
        deptRepository.save(updateObj);
    }

    @Override
    public void deleteDept(Long id) {
        // 校验存在
        validateDeptExists(id);
        // 删除
        deptRepository.deleteById(id);
    }

    private Dept validateDeptExists(Long id) {
        return deptRepository.findById(id).orElseThrow(() -> exception(DEPT_NOT_EXISTS));
    }

    @Override
    public Optional<Dept> getDept(Long id) {
        return deptRepository.findById(id);
    }

    @Override
    public List<Dept> getDeptList(Collection<Long> ids) {
        return StreamSupport.stream(deptRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Dept> getDeptPage(DeptPageReqVO pageReqVO, DeptPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Dept> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getParentId() != null) {
                predicates.add(cb.equal(root.get("parentId"), pageReqVO.getParentId()));
            }

            if(pageReqVO.getSort() != null) {
                predicates.add(cb.equal(root.get("sort"), pageReqVO.getSort()));
            }

            if(pageReqVO.getLeaderUserId() != null) {
                predicates.add(cb.equal(root.get("leaderUserId"), pageReqVO.getLeaderUserId()));
            }

            if(pageReqVO.getPhone() != null) {
                predicates.add(cb.equal(root.get("phone"), pageReqVO.getPhone()));
            }

            if(pageReqVO.getEmail() != null) {
                predicates.add(cb.equal(root.get("email"), pageReqVO.getEmail()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<Dept> page = deptRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<Dept> getDeptList(DeptExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Dept> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getParentId() != null) {
                predicates.add(cb.equal(root.get("parentId"), exportReqVO.getParentId()));
            }

            if(exportReqVO.getSort() != null) {
                predicates.add(cb.equal(root.get("sort"), exportReqVO.getSort()));
            }

            if(exportReqVO.getLeaderUserId() != null) {
                predicates.add(cb.equal(root.get("leaderUserId"), exportReqVO.getLeaderUserId()));
            }

            if(exportReqVO.getPhone() != null) {
                predicates.add(cb.equal(root.get("phone"), exportReqVO.getPhone()));
            }

            if(exportReqVO.getEmail() != null) {
                predicates.add(cb.equal(root.get("email"), exportReqVO.getEmail()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return deptRepository.findAll(spec);
    }

    private Sort createSort(DeptPageOrder order) {
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

        if (order.getParentId() != null) {
            orders.add(new Sort.Order(order.getParentId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "parentId"));
        }

        if (order.getSort() != null) {
            orders.add(new Sort.Order(order.getSort().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sort"));
        }

        if (order.getLeaderUserId() != null) {
            orders.add(new Sort.Order(order.getLeaderUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "leaderUserId"));
        }

        if (order.getPhone() != null) {
            orders.add(new Sort.Order(order.getPhone().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "phone"));
        }

        if (order.getEmail() != null) {
            orders.add(new Sort.Order(order.getEmail().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "email"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }

    // 获取饲养部负责人
    public Long getAnimalFeedDeptManagerId(){
        Dept dept = validateDeptExists(118L);
        return dept.getLeaderUserId();
    }
}