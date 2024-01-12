package cn.iocoder.yudao.module.jl.service.asset;

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

import javax.persistence.criteria.Predicate;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.*;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDevice;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.asset.AssetDeviceMapper;
import cn.iocoder.yudao.module.jl.repository.asset.AssetDeviceRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 公司资产（设备） Service 实现类
 *
 */
@Service
@Validated
public class AssetDeviceServiceImpl implements AssetDeviceService {

    @Resource
    private AssetDeviceRepository assetDeviceRepository;

    @Resource
    private AssetDeviceMapper assetDeviceMapper;

    private String generateCodeById(Long id){
        return "D"+id;
    }

    @Override
    @Transactional
    public Long createAssetDevice(AssetDeviceCreateReqVO createReqVO) {

        //校验一下code是否存在
        validateAssetDeviceExistsByCode(createReqVO.getCode());

        // 插入
        AssetDevice assetDevice = assetDeviceMapper.toEntity(createReqVO);
        assetDeviceRepository.save(assetDevice);

        //如果没有传入编码 更新一下
        Long id = assetDevice.getId();
        if(createReqVO.getCode()==null||createReqVO.getCode().equals("")){
            assetDeviceRepository.updateCodeById(generateCodeById(id),id);
        }

        // 返回
        return assetDevice.getId();
    }

    private void validateAssetDeviceExistsByCode(String code) {
        AssetDevice byCode = assetDeviceRepository.findByCode(code);
        if (byCode!=null){
            throw exception(UNIQUE_CODE_EXISTS);
        }
    }

    @Override
    public void updateAssetDevice(AssetDeviceUpdateReqVO updateReqVO) {
        // 校验存在
        AssetDevice assetDevice = validateAssetDeviceExists(updateReqVO.getId());

        // 如果是空 设置一个
        if(updateReqVO.getCode()==null||updateReqVO.getCode().equals("")){
            updateReqVO.setCode(generateCodeById(updateReqVO.getId()));
        } else if (!Objects.equals(assetDevice.getCode(), updateReqVO.getCode())) {
            //如果不空 并且跟原先的code不一样 校验一下存在
            validateAssetDeviceExistsByCode(updateReqVO.getCode());
        }

        // 更新
        AssetDevice updateObj = assetDeviceMapper.toEntity(updateReqVO);
        assetDeviceRepository.save(updateObj);
    }

    @Override
    public void deleteAssetDevice(Long id) {
        // 校验存在
        validateAssetDeviceExists(id);
        // 删除
        assetDeviceRepository.deleteById(id);
    }

    private AssetDevice validateAssetDeviceExists(Long id) {
        Optional<AssetDevice> byId = assetDeviceRepository.findById(id);
        if(byId.isEmpty()){
            throw exception(ASSET_DEVICE_NOT_EXISTS);
        }
        return byId.orElse(null);
    }

    @Override
    public Optional<AssetDevice> getAssetDevice(Long id) {
        return assetDeviceRepository.findById(id);
    }

    @Override
    public List<AssetDevice> getAssetDeviceList(Collection<Long> ids) {
        return StreamSupport.stream(assetDeviceRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<AssetDevice> getAssetDevicePage(AssetDevicePageReqVO pageReqVO, AssetDevicePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<AssetDevice> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getLabId() != null) {
                predicates.add(cb.equal(root.get("labId"), pageReqVO.getLabId()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getOwnerType() != null) {
                predicates.add(cb.equal(root.get("ownerType"), pageReqVO.getOwnerType()));
            }

            if(pageReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), pageReqVO.getManagerId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            if(pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getLocation() != null) {
                predicates.add(cb.equal(root.get("location"), pageReqVO.getLocation()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getSn() != null) {
                predicates.add(cb.equal(root.get("sn"), pageReqVO.getSn()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<AssetDevice> page = assetDeviceRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<AssetDevice> getAssetDeviceList(AssetDeviceExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<AssetDevice> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getOwnerType() != null) {
                predicates.add(cb.equal(root.get("ownerType"), exportReqVO.getOwnerType()));
            }

            if(exportReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), exportReqVO.getManagerId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
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

            if(exportReqVO.getLocation() != null) {
                predicates.add(cb.equal(root.get("location"), exportReqVO.getLocation()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getSn() != null) {
                predicates.add(cb.equal(root.get("sn"), exportReqVO.getSn()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return assetDeviceRepository.findAll(spec);
    }

    private Sort createSort(AssetDevicePageOrder order) {
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

        if (order.getOwnerType() != null) {
            orders.add(new Sort.Order(order.getOwnerType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "ownerType"));
        }

        if (order.getManagerId() != null) {
            orders.add(new Sort.Order(order.getManagerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "managerId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
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

        if (order.getLocation() != null) {
            orders.add(new Sort.Order(order.getLocation().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "location"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getSn() != null) {
            orders.add(new Sort.Order(order.getSn().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sn"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}