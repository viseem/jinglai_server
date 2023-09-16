package cn.iocoder.yudao.module.jl.service.project;

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
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectDocument;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectDocumentMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectDocumentRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目文档 Service 实现类
 *
 */
@Service
@Validated
public class ProjectDocumentServiceImpl implements ProjectDocumentService {

    @Resource
    private ProjectDocumentRepository projectDocumentRepository;

    @Resource
    private ProjectDocumentMapper projectDocumentMapper;


    @Override
    public Long createProjectDocumentWithoutReq(Long projectId,String fileName,String fileUrl,String type){
        // 插入
        ProjectDocument  projectDocument = new ProjectDocument();
        projectDocument.setProjectId(projectId);
        projectDocument.setFileName(fileName);
        projectDocument.setFileUrl(fileUrl);
        projectDocument.setType(type);
        projectDocumentRepository.save(projectDocument);
        // 返回
        return projectDocument.getId();
    }

    @Override
    public Long createProjectDocument(ProjectDocumentCreateReqVO createReqVO) {
        // 插入
        ProjectDocument projectDocument = projectDocumentMapper.toEntity(createReqVO);
        projectDocumentRepository.save(projectDocument);
        // 返回
        return projectDocument.getId();
    }

    @Override
    public void updateProjectDocument(ProjectDocumentUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectDocumentExists(updateReqVO.getId());
        // 更新
        ProjectDocument updateObj = projectDocumentMapper.toEntity(updateReqVO);
        projectDocumentRepository.save(updateObj);
    }

    @Override
    public void updateProjectDocumentWithoutReq(Long projectDocumentId,String fileName,String fileUrl) {
        // 校验存在
        ProjectDocument projectDocument = validateProjectDocumentExists(projectDocumentId);
        projectDocument.setFileName(fileName);
        projectDocument.setFileUrl(fileUrl);
        // 更新
        projectDocumentRepository.save(projectDocument);
    }

    @Override
    public void deleteProjectDocument(Long id) {
        // 校验存在
        validateProjectDocumentExists(id);
        // 删除
        projectDocumentRepository.deleteById(id);
    }

    private ProjectDocument validateProjectDocumentExists(Long id) {
        Optional<ProjectDocument> byId = projectDocumentRepository.findById(id);
        if(byId.isEmpty()){
            throw  exception(PROJECT_DOCUMENT_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<ProjectDocument> getProjectDocument(Long id) {
        return projectDocumentRepository.findById(id);
    }

    @Override
    public List<ProjectDocument> getProjectDocumentList(Collection<Long> ids) {
        return StreamSupport.stream(projectDocumentRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectDocument> getProjectDocumentPage(ProjectDocumentPageReqVO pageReqVO, ProjectDocumentPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectDocument> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            if(pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectDocument> page = projectDocumentRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectDocument> getProjectDocumentList(ProjectDocumentExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectDocument> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + exportReqVO.getFileName() + "%"));
            }

            if(exportReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), exportReqVO.getFileUrl()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectDocumentRepository.findAll(spec);
    }

    private Sort createSort(ProjectDocumentPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getFileName() != null) {
            orders.add(new Sort.Order(order.getFileName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileName"));
        }

        if (order.getFileUrl() != null) {
            orders.add(new Sort.Order(order.getFileUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileUrl"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}