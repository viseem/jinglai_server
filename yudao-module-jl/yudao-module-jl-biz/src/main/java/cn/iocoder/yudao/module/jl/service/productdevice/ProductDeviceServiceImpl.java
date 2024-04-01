package cn.iocoder.yudao.module.jl.service.productdevice;

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

import javax.persistence.criteria.Predicate;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.productdevice.vo.*;
import cn.iocoder.yudao.module.jl.entity.productdevice.ProductDevice;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.productdevice.ProductDeviceMapper;
import cn.iocoder.yudao.module.jl.repository.productdevice.ProductDeviceRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 产品库设备 Service 实现类
 *
 */
@Service
@Validated
public class ProductDeviceServiceImpl implements ProductDeviceService {

    @Resource
    private ProductDeviceRepository productDeviceRepository;

    @Resource
    private ProductDeviceMapper productDeviceMapper;

    @Override
    public Long createProductDevice(ProductDeviceCreateReqVO createReqVO) {
        // 插入
        ProductDevice productDevice = productDeviceMapper.toEntity(createReqVO);
        productDeviceRepository.save(productDevice);
        // 返回
        return productDevice.getId();
    }

    @Override
    public void updateProductDevice(ProductDeviceUpdateReqVO updateReqVO) {

        if(updateReqVO.getList()!=null){
            productDeviceRepository.deleteByProductId(updateReqVO.getProductId());
            productDeviceRepository.saveAll(updateReqVO.getList());
        }else{
            // 校验存在
            validateProductDeviceExists(updateReqVO.getId());
            // 更新
            ProductDevice updateObj = productDeviceMapper.toEntity(updateReqVO);
            productDeviceRepository.save(updateObj);
        }

    }

    @Override
    public void deleteProductDevice(Long id) {
        // 校验存在
        validateProductDeviceExists(id);
        // 删除
        productDeviceRepository.deleteById(id);
    }

    private void validateProductDeviceExists(Long id) {
        productDeviceRepository.findById(id).orElseThrow(() -> exception(PRODUCT_DEVICE_NOT_EXISTS));
    }

    @Override
    public Optional<ProductDevice> getProductDevice(Long id) {
        return productDeviceRepository.findById(id);
    }

    @Override
    public List<ProductDevice> getProductDeviceList(Collection<Long> ids) {
        return StreamSupport.stream(productDeviceRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProductDevice> getProductDevicePage(ProductDevicePageReqVO pageReqVO, ProductDevicePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProductDevice> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), pageReqVO.getProductId()));
            }

            if(pageReqVO.getDeviceId() != null) {
                predicates.add(cb.equal(root.get("deviceId"), pageReqVO.getDeviceId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProductDevice> page = productDeviceRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProductDevice> getProductDeviceList(ProductDeviceExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProductDevice> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProductId() != null) {
                predicates.add(cb.equal(root.get("productId"), exportReqVO.getProductId()));
            }

            if(exportReqVO.getDeviceId() != null) {
                predicates.add(cb.equal(root.get("deviceId"), exportReqVO.getDeviceId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return productDeviceRepository.findAll(spec);
    }

    private Sort createSort(ProductDevicePageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProductId() != null) {
            orders.add(new Sort.Order(order.getProductId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "productId"));
        }

        if (order.getDeviceId() != null) {
            orders.add(new Sort.Order(order.getDeviceId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "deviceId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}