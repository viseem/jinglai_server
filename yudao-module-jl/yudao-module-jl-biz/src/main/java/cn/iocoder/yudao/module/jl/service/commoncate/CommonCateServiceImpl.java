package cn.iocoder.yudao.module.jl.service.commoncate;

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
import cn.iocoder.yudao.module.jl.controller.admin.commoncate.vo.*;
import cn.iocoder.yudao.module.jl.entity.commoncate.CommonCate;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.commoncate.CommonCateMapper;
import cn.iocoder.yudao.module.jl.repository.commoncate.CommonCateRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 通用分类 Service 实现类
 *
 */
@Service
@Validated
public class CommonCateServiceImpl implements CommonCateService {

    @Resource
    private CommonCateRepository commonCateRepository;

    @Resource
    private CommonCateMapper commonCateMapper;

    @Override
    public Long createCommonCate(CommonCateCreateReqVO createReqVO) {
        // 插入
        CommonCate commonCate = commonCateMapper.toEntity(createReqVO);
        commonCateRepository.save(commonCate);
        // 返回
        return commonCate.getId();
    }

    @Override
    public void updateCommonCate(CommonCateUpdateReqVO updateReqVO) {
        // 校验存在
        validateCommonCateExists(updateReqVO.getId());
        // 更新
        CommonCate updateObj = commonCateMapper.toEntity(updateReqVO);
        commonCateRepository.save(updateObj);
    }

    @Override
    public void deleteCommonCate(Long id) {
        // 校验存在
        validateCommonCateExists(id);
        // 删除
        commonCateRepository.deleteById(id);
    }

    private void validateCommonCateExists(Long id) {
        commonCateRepository.findById(id).orElseThrow(() -> exception(COMMON_CATE_NOT_EXISTS));
    }

    @Override
    public Optional<CommonCate> getCommonCate(Long id) {
        return commonCateRepository.findById(id);
    }

    @Override
    public List<CommonCate> getCommonCateList(Collection<Long> ids) {
        return StreamSupport.stream(commonCateRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CommonCate> getCommonCatePage(CommonCatePageReqVO pageReqVO, CommonCatePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CommonCate> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
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


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CommonCate> page = commonCateRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CommonCate> getCommonCateList(CommonCateExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CommonCate> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getParentId() != null) {
                predicates.add(cb.equal(root.get("parentId"), exportReqVO.getParentId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return commonCateRepository.findAll(spec);
    }

    private Sort createSort(CommonCatePageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
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


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}