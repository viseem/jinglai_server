package cn.iocoder.yudao.module.jl.service.projectcategory;

import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategorySimple;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryAttachmentEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectDocumentTypeEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectFundEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategorySimpleRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectDocumentServiceImpl;
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
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryAttachment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategoryAttachmentMapper;
import cn.iocoder.yudao.module.jl.repository.projectcategory.ProjectCategoryAttachmentRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目实验名目的附件 Service 实现类
 *
 */
@Service
@Validated
public class ProjectCategoryAttachmentServiceImpl implements ProjectCategoryAttachmentService {

    @Resource
    private ProjectCategoryAttachmentRepository projectCategoryAttachmentRepository;

    @Resource
    private ProjectCategoryAttachmentMapper projectCategoryAttachmentMapper;
    private final ProjectCategoryRepository projectCategoryRepository;

    @Resource
    private ProjectCategorySimpleRepository projectCategorySimpleRepository;

    @Resource
    private ProjectDocumentServiceImpl projectDocumentService;

    public ProjectCategoryAttachmentServiceImpl(ProjectCategoryRepository projectCategoryRepository) {
        this.projectCategoryRepository = projectCategoryRepository;
    }

    @Override
    @Transactional
    public Long createProjectCategoryAttachment(ProjectCategoryAttachmentCreateReqVO createReqVO) {

        Optional<ProjectCategorySimple> byId = projectCategorySimpleRepository.findById(createReqVO.getProjectCategoryId());
        if (byId.isEmpty()){
            throw exception(PROJECT_CATEGORY_NOT_EXISTS);
        }

        // 插入
        ProjectCategoryAttachment projectCategoryAttachment = projectCategoryAttachmentMapper.toEntity(createReqVO);
        projectCategoryAttachment.setProjectId(byId.get().getProjectId());
        projectCategoryAttachmentRepository.save(projectCategoryAttachment);

        // 如果是实验类型的数据，则存一份到projectDocument
        if(Objects.equals(createReqVO.getType(), ProjectCategoryAttachmentEnums.EXP.getStatus())){
            projectDocumentService.createProjectDocumentWithoutReq(byId.get().getProjectId(),createReqVO.getFileName(),createReqVO.getFileUrl(), ProjectDocumentTypeEnums.EXP_DATA.getStatus());
        }

        // 返回
        return projectCategoryAttachment.getId();
    }

    @Override
    public void updateProjectCategoryAttachment(ProjectCategoryAttachmentUpdateReqVO updateReqVO) {

        // 校验存在
        validateProjectCategoryAttachmentExists(updateReqVO.getId());

        Optional<ProjectCategorySimple> byId = projectCategorySimpleRepository.findById(updateReqVO.getProjectCategoryId());
        if (byId.isEmpty()){
            throw exception(PROJECT_CATEGORY_NOT_EXISTS);
        }

        // 更新
        ProjectCategoryAttachment updateObj = projectCategoryAttachmentMapper.toEntity(updateReqVO);
        updateObj.setProjectId(byId.get().getProjectId());
        projectCategoryAttachmentRepository.save(updateObj);
    }

    @Override
    @Transactional
    public void deleteProjectCategoryAttachment(Long id) {
        // 校验存在
        ProjectCategoryAttachment projectCategoryAttachment = validateProjectCategoryAttachmentExists(id);

        //删除projectDocument的数据
        if(projectCategoryAttachment.getProjectDocumentId()!=null){
            projectDocumentService.deleteProjectDocument(projectCategoryAttachment.getProjectDocumentId());
        }

        // 删除
        projectCategoryAttachmentRepository.deleteById(id);
    }

    private ProjectCategoryAttachment validateProjectCategoryAttachmentExists(Long id) {
        Optional<ProjectCategoryAttachment> byId = projectCategoryAttachmentRepository.findById(id);
        if(byId.isEmpty()){
            throw exception(PROJECT_CATEGORY_ATTACHMENT_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<ProjectCategoryAttachment> getProjectCategoryAttachment(Long id) {
        return projectCategoryAttachmentRepository.findById(id);
    }

    @Override
    public List<ProjectCategoryAttachment> getProjectCategoryAttachmentList(Collection<Long> ids) {
        return StreamSupport.stream(projectCategoryAttachmentRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectCategoryAttachment> getProjectCategoryAttachmentPage(ProjectCategoryAttachmentPageReqVO pageReqVO, ProjectCategoryAttachmentPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectCategoryAttachment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(pageReqVO.getCreator() != null) {
                predicates.add(cb.equal(root.get("creator"), pageReqVO.getCreator()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if(pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            if(pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }

            if(pageReqVO.getExpType() != null) {
                predicates.add(cb.equal(root.get("expType"), pageReqVO.getExpType()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectCategoryAttachment> page = projectCategoryAttachmentRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectCategoryAttachment> getProjectCategoryAttachmentList(ProjectCategoryAttachmentExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectCategoryAttachment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if(exportReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + exportReqVO.getFileName() + "%"));
            }

            if(exportReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), exportReqVO.getFileUrl()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectCategoryAttachmentRepository.findAll(spec);
    }

    private Sort createSort(ProjectCategoryAttachmentPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getFileName() != null) {
            orders.add(new Sort.Order(order.getFileName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileName"));
        }

        if (order.getFileUrl() != null) {
            orders.add(new Sort.Order(order.getFileUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileUrl"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}