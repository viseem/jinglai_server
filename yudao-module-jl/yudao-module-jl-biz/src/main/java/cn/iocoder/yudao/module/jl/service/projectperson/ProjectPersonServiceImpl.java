package cn.iocoder.yudao.module.jl.service.projectperson;

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
import cn.iocoder.yudao.module.jl.controller.admin.projectperson.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectperson.ProjectPerson;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectperson.ProjectPersonMapper;
import cn.iocoder.yudao.module.jl.repository.projectperson.ProjectPersonRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目人员 Service 实现类
 *
 */
@Service
@Validated
public class ProjectPersonServiceImpl implements ProjectPersonService {

    @Resource
    private ProjectPersonRepository projectPersonRepository;

    @Resource
    private ProjectPersonMapper projectPersonMapper;

    @Override
    public Long createProjectPerson(ProjectPersonCreateReqVO createReqVO) {
        // 插入
        ProjectPerson projectPerson = projectPersonMapper.toEntity(createReqVO);
        projectPersonRepository.save(projectPerson);
        // 返回
        return projectPerson.getId();
    }

    @Override
    public void updateProjectPerson(ProjectPersonUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectPersonExists(updateReqVO.getId());
        // 更新
        ProjectPerson updateObj = projectPersonMapper.toEntity(updateReqVO);
        projectPersonRepository.save(updateObj);
    }

    @Override
    public void deleteProjectPerson(Long id) {
        // 校验存在
        validateProjectPersonExists(id);
        // 删除
        projectPersonRepository.deleteById(id);
    }

    private void validateProjectPersonExists(Long id) {
        projectPersonRepository.findById(id).orElseThrow(() -> exception(PROJECT_PERSON_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectPerson> getProjectPerson(Long id) {
        return projectPersonRepository.findById(id);
    }

    @Override
    public List<ProjectPerson> getProjectPersonList(Collection<Long> ids) {
        return StreamSupport.stream(projectPersonRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectPerson> getProjectPersonPage(ProjectPersonPageReqVO pageReqVO, ProjectPersonPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectPerson> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectPerson> page = projectPersonRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectPerson> getProjectPersonList(ProjectPersonExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectPerson> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectPersonRepository.findAll(spec);
    }

    private Sort createSort(ProjectPersonPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
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

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}