package cn.iocoder.yudao.module.jl.service.worktodotag;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.worktodotag.vo.*;
import cn.iocoder.yudao.module.jl.entity.worktodotag.WorkTodoTag;
import cn.iocoder.yudao.module.jl.mapper.worktodotag.WorkTodoTagMapper;
import cn.iocoder.yudao.module.jl.repository.worktodotag.WorkTodoTagRepository;
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
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.WORK_TODO_TAG_NOT_EXISTS;

/**
 * 工作任务 TODO 的标签 Service 实现类
 *
 */
@Service
@Validated
public class WorkTodoTagServiceImpl implements WorkTodoTagService {

    @Resource
    private WorkTodoTagRepository workTodoTagRepository;

    @Resource
    private WorkTodoTagMapper workTodoTagMapper;

    @Override
    public Long createWorkTodoTag(WorkTodoTagCreateReqVO createReqVO) {
        // 插入
        WorkTodoTag workTodoTag = workTodoTagMapper.toEntity(createReqVO);
        workTodoTagRepository.save(workTodoTag);
        // 返回
        return workTodoTag.getId();
    }

    @Override
    public void updateWorkTodoTag(WorkTodoTagUpdateReqVO updateReqVO) {
        // 校验存在
        validateWorkTodoTagExists(updateReqVO.getId());
        // 更新
        WorkTodoTag updateObj = workTodoTagMapper.toEntity(updateReqVO);
        workTodoTagRepository.save(updateObj);
    }

    @Override
    public void deleteWorkTodoTag(Long id) {
        // 校验存在
        validateWorkTodoTagExists(id);
        // 删除
        workTodoTagRepository.deleteById(id);
    }

    private void validateWorkTodoTagExists(Long id) {
        workTodoTagRepository.findById(id).orElseThrow(() -> exception(WORK_TODO_TAG_NOT_EXISTS));
    }

    @Override
    public Optional<WorkTodoTag> getWorkTodoTag(Long id) {
        return workTodoTagRepository.findById(id);
    }

    @Override
    public List<WorkTodoTag> getWorkTodoTagList(Collection<Long> ids) {
        return StreamSupport.stream(workTodoTagRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<WorkTodoTag> getWorkTodoTagPage(WorkTodoTagPageReqVO pageReqVO, WorkTodoTagPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<WorkTodoTag> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<WorkTodoTag> page = workTodoTagRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<WorkTodoTag> getWorkTodoTagList(WorkTodoTagExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<WorkTodoTag> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return workTodoTagRepository.findAll(spec);
    }

    private Sort createSort(WorkTodoTagPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整


        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}