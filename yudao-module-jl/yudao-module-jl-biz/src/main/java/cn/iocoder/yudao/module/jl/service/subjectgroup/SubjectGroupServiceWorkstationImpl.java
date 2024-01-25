package cn.iocoder.yudao.module.jl.service.subjectgroup;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.*;
import cn.iocoder.yudao.module.jl.entity.subjectgroup.SubjectGroup;
import cn.iocoder.yudao.module.jl.mapper.subjectgroup.SubjectGroupMapper;
import cn.iocoder.yudao.module.jl.repository.subjectgroup.SubjectGroupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.SUBJECT_GROUP_NOT_EXISTS;

/**
 * 专题小组 Service 实现类
 *
 */
@Service
@Validated
public class SubjectGroupServiceWorkstationImpl implements SubjectGroupWorkstationService {

    @Resource
    private SubjectGroupRepository subjectGroupRepository;

    @Resource
    private SubjectGroupMapper subjectGroupMapper;

    @Override
    public Long createSubjectGroup(SubjectGroupCreateReqVO createReqVO) {
        // 插入
        SubjectGroup subjectGroup = subjectGroupMapper.toEntity(createReqVO);
        subjectGroupRepository.save(subjectGroup);
        // 返回
        return subjectGroup.getId();
    }

    @Override
    public void updateSubjectGroup(SubjectGroupUpdateReqVO updateReqVO) {
        // 校验存在
        validateSubjectGroupExists(updateReqVO.getId());
        // 更新
        SubjectGroup updateObj = subjectGroupMapper.toEntity(updateReqVO);
        subjectGroupRepository.save(updateObj);
    }

    @Override
    public void deleteSubjectGroup(Long id) {
        // 校验存在
        validateSubjectGroupExists(id);
        // 删除
        subjectGroupRepository.deleteById(id);
    }

    private void validateSubjectGroupExists(Long id) {
        subjectGroupRepository.findById(id).orElseThrow(() -> exception(SUBJECT_GROUP_NOT_EXISTS));
    }

    @Override
    public Optional<SubjectGroup> getSubjectGroup(Long id) {
        return subjectGroupRepository.findById(id);
    }

    @Override
    public List<SubjectGroup> getSubjectGroupList(Collection<Long> ids) {
        return StreamSupport.stream(subjectGroupRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<SubjectGroup> getSubjectGroupPage(SubjectGroupPageReqVO pageReqVO, SubjectGroupPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<SubjectGroup> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getArea() != null) {
                predicates.add(cb.equal(root.get("area"), pageReqVO.getArea()));
            }

            if(pageReqVO.getSubject() != null) {
                predicates.add(cb.equal(root.get("subject"), pageReqVO.getSubject()));
            }

            if(pageReqVO.getLeaderId() != null) {
                predicates.add(cb.equal(root.get("leaderId"), pageReqVO.getLeaderId()));
            }

            if(pageReqVO.getBusinessLeaderId() != null) {
                predicates.add(cb.equal(root.get("businessLeaderId"), pageReqVO.getBusinessLeaderId()));
            }

            if(pageReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), pageReqVO.getCode()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<SubjectGroup> page = subjectGroupRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<SubjectGroup> getSubjectGroupList(SubjectGroupExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<SubjectGroup> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getArea() != null) {
                predicates.add(cb.equal(root.get("area"), exportReqVO.getArea()));
            }

            if(exportReqVO.getSubject() != null) {
                predicates.add(cb.equal(root.get("subject"), exportReqVO.getSubject()));
            }

            if(exportReqVO.getLeaderId() != null) {
                predicates.add(cb.equal(root.get("leaderId"), exportReqVO.getLeaderId()));
            }

            if(exportReqVO.getBusinessLeaderId() != null) {
                predicates.add(cb.equal(root.get("businessLeaderId"), exportReqVO.getBusinessLeaderId()));
            }

            if(exportReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), exportReqVO.getCode()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return subjectGroupRepository.findAll(spec);
    }

    private Sort createSort(SubjectGroupPageOrder order) {
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

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getArea() != null) {
            orders.add(new Sort.Order(order.getArea().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "area"));
        }

        if (order.getSubject() != null) {
            orders.add(new Sort.Order(order.getSubject().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "subject"));
        }

        if (order.getLeaderId() != null) {
            orders.add(new Sort.Order(order.getLeaderId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "leaderId"));
        }

        if (order.getBusinessLeaderId() != null) {
            orders.add(new Sort.Order(order.getBusinessLeaderId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "businessLeaderId"));
        }

        if (order.getCode() != null) {
            orders.add(new Sort.Order(order.getCode().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "code"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}