package cn.iocoder.yudao.module.jl.service.devicecate;

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
import cn.iocoder.yudao.module.jl.controller.admin.devicecate.vo.*;
import cn.iocoder.yudao.module.jl.entity.devicecate.DeviceCate;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.devicecate.DeviceCateMapper;
import cn.iocoder.yudao.module.jl.repository.devicecate.DeviceCateRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 设备分类 Service 实现类
 *
 */
@Service
@Validated
public class DeviceCateServiceImpl implements DeviceCateService {

    @Resource
    private DeviceCateRepository deviceCateRepository;

    @Resource
    private DeviceCateMapper deviceCateMapper;

    @Override
    public Long createDeviceCate(DeviceCateCreateReqVO createReqVO) {
        // 插入
        DeviceCate deviceCate = deviceCateMapper.toEntity(createReqVO);
        deviceCateRepository.save(deviceCate);
        // 返回
        return deviceCate.getId();
    }

    @Override
    public void updateDeviceCate(DeviceCateUpdateReqVO updateReqVO) {
        // 校验存在
        validateDeviceCateExists(updateReqVO.getId());
        // 更新
        DeviceCate updateObj = deviceCateMapper.toEntity(updateReqVO);
        deviceCateRepository.save(updateObj);
    }

    @Override
    public void deleteDeviceCate(Long id) {
        // 校验存在
        validateDeviceCateExists(id);
        // 删除
        deviceCateRepository.deleteById(id);
    }

    private void validateDeviceCateExists(Long id) {
        deviceCateRepository.findById(id).orElseThrow(() -> exception(DEVICE_CATE_NOT_EXISTS));
    }

    @Override
    public Optional<DeviceCate> getDeviceCate(Long id) {
        return deviceCateRepository.findById(id);
    }

    @Override
    public List<DeviceCate> getDeviceCateList(Collection<Long> ids) {
        return StreamSupport.stream(deviceCateRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<DeviceCate> getDeviceCatePage(DeviceCatePageReqVO pageReqVO, DeviceCatePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<DeviceCate> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getWithChild()){
                predicates.add(cb.equal(root.get("parentId"), 0));
            }


            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getParentId() != null) {
                predicates.add(cb.equal(root.get("parentId"), pageReqVO.getParentId()));
            }

            if(pageReqVO.getTagIds() != null) {
                predicates.add(cb.equal(root.get("tagIds"), pageReqVO.getTagIds()));
            }

            if(pageReqVO.getSort() != null) {
                predicates.add(cb.equal(root.get("sort"), pageReqVO.getSort()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<DeviceCate> page = deviceCateRepository.findAll(spec, pageable);

        // 查询子分类
        if(pageReqVO.getWithChild()){
            List<Long> ids = page.getContent().stream().map(DeviceCate::getId).collect(Collectors.toList());
            List<DeviceCate> list = deviceCateRepository.findByParentIdIn(ids);
            list.sort(Comparator.comparingInt(DeviceCate::getSort));
            if(list!=null&&!list.isEmpty()){
                for (DeviceCate deviceCate : page.getContent()) {
                    if(deviceCate.getChildList()==null){
                        deviceCate.setChildList(new ArrayList<>());
                    }
                    for (DeviceCate cate : list) {
                        if(Objects.equals(cate.getParentId(),deviceCate.getId())){
                            deviceCate.getChildList().add(cate);
                        }
                    }
                }
            }

        }

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<DeviceCate> getDeviceCateList(DeviceCateExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<DeviceCate> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getParentId() != null) {
                predicates.add(cb.equal(root.get("parentId"), exportReqVO.getParentId()));
            }

            if(exportReqVO.getTagIds() != null) {
                predicates.add(cb.equal(root.get("tagIds"), exportReqVO.getTagIds()));
            }

            if(exportReqVO.getSort() != null) {
                predicates.add(cb.equal(root.get("sort"), exportReqVO.getSort()));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return deviceCateRepository.findAll(spec);
    }

    private Sort createSort(DeviceCatePageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order(Sort.Direction.ASC, "sort"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getParentId() != null) {
            orders.add(new Sort.Order(order.getParentId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "parentId"));
        }

        if (order.getTagIds() != null) {
            orders.add(new Sort.Order(order.getTagIds().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "tagIds"));
        }

        if (order.getSort() != null) {
            orders.add(new Sort.Order(order.getSort().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sort"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}