package cn.iocoder.yudao.module.jl.service.asset;

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
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.*;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDeviceLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.asset.AssetDeviceLogMapper;
import cn.iocoder.yudao.module.jl.repository.asset.AssetDeviceLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 公司资产（设备）预约 Service 实现类
 *
 */
@Service
@Validated
public class AssetDeviceLogServiceImpl implements AssetDeviceLogService {

    @Resource
    private AssetDeviceLogRepository assetDeviceLogRepository;

    @Resource
    private AssetDeviceLogMapper assetDeviceLogMapper;

    @Override
    public Long createAssetDeviceLog(AssetDeviceLogCreateReqVO createReqVO) {
        // 插入
        AssetDeviceLog assetDeviceLog = assetDeviceLogMapper.toEntity(createReqVO);
        assetDeviceLogRepository.save(assetDeviceLog);
        // 返回
        return assetDeviceLog.getId();
    }

    @Override
    public void updateAssetDeviceLog(AssetDeviceLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateAssetDeviceLogExists(updateReqVO.getId());
        // 更新
        AssetDeviceLog updateObj = assetDeviceLogMapper.toEntity(updateReqVO);
        assetDeviceLogRepository.save(updateObj);
    }

    @Override
    public void deleteAssetDeviceLog(Long id) {
        // 校验存在
        validateAssetDeviceLogExists(id);
        // 删除
        assetDeviceLogRepository.deleteById(id);
    }

    private void validateAssetDeviceLogExists(Long id) {
        assetDeviceLogRepository.findById(id).orElseThrow(() -> exception(ASSET_DEVICE_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<AssetDeviceLog> getAssetDeviceLog(Long id) {
        return assetDeviceLogRepository.findById(id);
    }

    @Override
    public List<AssetDeviceLog> getAssetDeviceLogList(Collection<Long> ids) {
        return StreamSupport.stream(assetDeviceLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<AssetDeviceLog> getAssetDeviceLogPage(AssetDeviceLogPageReqVO pageReqVO, AssetDeviceLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<AssetDeviceLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getDeviceId() != null) {
                predicates.add(cb.equal(root.get("deviceId"), pageReqVO.getDeviceId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getStartData() != null) {
                predicates.add(cb.equal(root.get("startData"), pageReqVO.getStartData()));
            }

            if(pageReqVO.getEndData() != null) {
                predicates.add(cb.equal(root.get("endData"), pageReqVO.getEndData()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getUseType() != null) {
                predicates.add(cb.equal(root.get("useType"), pageReqVO.getUseType()));
            }

            if(pageReqVO.getProjectDeviceId() != null) {
                predicates.add(cb.equal(root.get("projectDeviceId"), pageReqVO.getProjectDeviceId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<AssetDeviceLog> page = assetDeviceLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<AssetDeviceLog> getAssetDeviceLogList(AssetDeviceLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<AssetDeviceLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getDeviceId() != null) {
                predicates.add(cb.equal(root.get("deviceId"), exportReqVO.getDeviceId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getStartData() != null) {
                predicates.add(cb.equal(root.get("startData"), exportReqVO.getStartData()));
            }

            if(exportReqVO.getEndData() != null) {
                predicates.add(cb.equal(root.get("endData"), exportReqVO.getEndData()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getUseType() != null) {
                predicates.add(cb.equal(root.get("useType"), exportReqVO.getUseType()));
            }

            if(exportReqVO.getProjectDeviceId() != null) {
                predicates.add(cb.equal(root.get("projectDeviceId"), exportReqVO.getProjectDeviceId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return assetDeviceLogRepository.findAll(spec);
    }

    private Sort createSort(AssetDeviceLogPageOrder order) {
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

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getStartData() != null) {
            orders.add(new Sort.Order(order.getStartData().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "startData"));
        }

        if (order.getEndData() != null) {
            orders.add(new Sort.Order(order.getEndData().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "endData"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getUseType() != null) {
            orders.add(new Sort.Order(order.getUseType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "useType"));
        }

        if (order.getProjectDeviceId() != null) {
            orders.add(new Sort.Order(order.getProjectDeviceId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectDeviceId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}