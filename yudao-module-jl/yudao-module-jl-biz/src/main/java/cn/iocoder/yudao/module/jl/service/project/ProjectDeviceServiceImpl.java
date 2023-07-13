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
import cn.iocoder.yudao.module.jl.entity.project.ProjectDevice;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectDeviceMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectDeviceRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目设备 Service 实现类
 *
 */
@Service
@Validated
public class ProjectDeviceServiceImpl implements ProjectDeviceService {

    @Resource
    private ProjectDeviceRepository projectDeviceRepository;

    @Resource
    private ProjectDeviceMapper projectDeviceMapper;

    @Override
    public Long createProjectDevice(ProjectDeviceCreateReqVO createReqVO) {
        // 插入
        ProjectDevice projectDevice = projectDeviceMapper.toEntity(createReqVO);
        projectDeviceRepository.save(projectDevice);
        // 返回
        return projectDevice.getId();
    }

    @Override
    public void updateProjectDevice(ProjectDeviceUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectDeviceExists(updateReqVO.getId());
        // 更新
        ProjectDevice updateObj = projectDeviceMapper.toEntity(updateReqVO);
        projectDeviceRepository.save(updateObj);
    }

    @Override
    public void deleteProjectDevice(Long id) {
        // 校验存在
        validateProjectDeviceExists(id);
        // 删除
        projectDeviceRepository.deleteById(id);
    }

    private void validateProjectDeviceExists(Long id) {
        projectDeviceRepository.findById(id).orElseThrow(() -> exception(PROJECT_DEVICE_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectDevice> getProjectDevice(Long id) {
        return projectDeviceRepository.findById(id);
    }

    @Override
    public List<ProjectDevice> getProjectDeviceList(Collection<Long> ids) {
        return StreamSupport.stream(projectDeviceRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectDevice> getProjectDevicePage(ProjectDevicePageReqVO pageReqVO, ProjectDevicePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectDevice> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getDeviceId() != null) {
                predicates.add(cb.equal(root.get("deviceId"), pageReqVO.getDeviceId()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), pageReqVO.getStartDate()[0], pageReqVO.getStartDate()[1]));
            } 
            if(pageReqVO.getEndDate() != null) {
                predicates.add(cb.between(root.get("endDate"), pageReqVO.getEndDate()[0], pageReqVO.getEndDate()[1]));
            } 

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectDevice> page = projectDeviceRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectDevice> getProjectDeviceList(ProjectDeviceExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectDevice> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getDeviceId() != null) {
                predicates.add(cb.equal(root.get("deviceId"), exportReqVO.getDeviceId()));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), exportReqVO.getStartDate()[0], exportReqVO.getStartDate()[1]));
            } 
            if(exportReqVO.getEndDate() != null) {
                predicates.add(cb.between(root.get("endDate"), exportReqVO.getEndDate()[0], exportReqVO.getEndDate()[1]));
            } 

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectDeviceRepository.findAll(spec);
    }

    private Sort createSort(ProjectDevicePageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getDeviceId() != null) {
            orders.add(new Sort.Order(order.getDeviceId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "deviceId"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getStartDate() != null) {
            orders.add(new Sort.Order(order.getStartDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "startDate"));
        }

        if (order.getEndDate() != null) {
            orders.add(new Sort.Order(order.getEndDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "endDate"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}