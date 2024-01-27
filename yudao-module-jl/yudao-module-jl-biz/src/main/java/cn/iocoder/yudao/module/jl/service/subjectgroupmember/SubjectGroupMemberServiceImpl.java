package cn.iocoder.yudao.module.jl.service.subjectgroupmember;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember.vo.*;
import cn.iocoder.yudao.module.jl.entity.subjectgroupmember.SubjectGroupMember;
import cn.iocoder.yudao.module.jl.mapper.subjectgroupmember.SubjectGroupMemberMapper;
import cn.iocoder.yudao.module.jl.repository.subjectgroupmember.SubjectGroupMemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.SUBJECT_GROUP_MEMBER_NOT_EXISTS;

/**
 * 专题小组成员 Service 实现类
 *
 */
@Service
@Validated
public class SubjectGroupMemberServiceImpl implements SubjectGroupMemberService {

    @Resource
    private SubjectGroupMemberRepository subjectGroupMemberRepository;

    @Resource
    private SubjectGroupMemberMapper subjectGroupMemberMapper;

    @Override
    public Long createSubjectGroupMember(SubjectGroupMemberCreateReqVO createReqVO) {
        // 插入
        SubjectGroupMember subjectGroupMember = subjectGroupMemberMapper.toEntity(createReqVO);
        subjectGroupMemberRepository.save(subjectGroupMember);
        // 返回
        return subjectGroupMember.getId();
    }

    public List<SubjectGroupMember> findMembersByGroupId(@Valid Long groupId) {
        return subjectGroupMemberRepository.findByGroupId(groupId);
    }

    @Override
    public void updateSubjectGroupMember(SubjectGroupMemberUpdateReqVO updateReqVO) {
        // 校验存在
        validateSubjectGroupMemberExists(updateReqVO.getId());
        // 更新
        SubjectGroupMember updateObj = subjectGroupMemberMapper.toEntity(updateReqVO);
        subjectGroupMemberRepository.save(updateObj);
    }

    @Override
    public void deleteSubjectGroupMember(Long id) {
        // 校验存在
        validateSubjectGroupMemberExists(id);
        // 删除
        subjectGroupMemberRepository.deleteById(id);
    }

    private void validateSubjectGroupMemberExists(Long id) {
        subjectGroupMemberRepository.findById(id).orElseThrow(() -> exception(SUBJECT_GROUP_MEMBER_NOT_EXISTS));
    }

    @Override
    public Optional<SubjectGroupMember> getSubjectGroupMember(Long id) {
        return subjectGroupMemberRepository.findById(id);
    }

    @Override
    public List<SubjectGroupMember> getSubjectGroupMemberList(Collection<Long> ids) {
        return StreamSupport.stream(subjectGroupMemberRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<SubjectGroupMember> getSubjectGroupMemberPage(SubjectGroupMemberPageReqVO pageReqVO, SubjectGroupMemberPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<SubjectGroupMember> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getGroupId() != null) {
                predicates.add(cb.equal(root.get("groupId"), pageReqVO.getGroupId()));
            }

            if(pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<SubjectGroupMember> page = subjectGroupMemberRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<SubjectGroupMember> getSubjectGroupMemberList(SubjectGroupMemberExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<SubjectGroupMember> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getGroupId() != null) {
                predicates.add(cb.equal(root.get("groupId"), exportReqVO.getGroupId()));
            }

            if(exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return subjectGroupMemberRepository.findAll(spec);
    }

    private Sort createSort(SubjectGroupMemberPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getGroupId() != null) {
            orders.add(new Sort.Order(order.getGroupId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "groupId"));
        }

        if (order.getUserId() != null) {
            orders.add(new Sort.Order(order.getUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "userId"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}