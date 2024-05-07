package cn.iocoder.yudao.module.jl.service.crm;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.jl.entity.crm.Customer;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerSimple;
import cn.iocoder.yudao.module.jl.service.commonattachment.CommonAttachmentServiceImpl;
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

import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.Institution;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.crm.InstitutionMapper;
import cn.iocoder.yudao.module.jl.repository.crm.InstitutionRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.USER_IMPORT_LIST_IS_EMPTY;

/**
 * 机构/公司 Service 实现类
 */
@Service
@Validated
public class InstitutionServiceImpl implements InstitutionService {

    @Resource
    private InstitutionRepository institutionRepository;

    @Resource
    private InstitutionMapper institutionMapper;

    @Resource
    private CommonAttachmentServiceImpl commonAttachmentService;

    @Override
    @Transactional
    public Long createInstitution(InstitutionCreateReqVO createReqVO) {

        Institution byName = institutionRepository.findByName(createReqVO.getName());
        if (byName != null) {
            return 0L;
        }

        // 插入
        Institution institution = institutionMapper.toEntity(createReqVO);
        institutionRepository.save(institution);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(institution.getId(), "INSTITUTION", createReqVO.getAttachmentList());

        // 返回
        return institution.getId();
    }

    @Override
    @Transactional
    public void updateInstitution(InstitutionUpdateReqVO updateReqVO) {
        // 校验存在
        validateInstitutionExists(updateReqVO.getId());
        // 更新
        Institution updateObj = institutionMapper.toEntity(updateReqVO);
        institutionRepository.save(updateObj);

        // 把attachmentList批量插入到附件表CommonAttachment中,使用saveAll方法
        commonAttachmentService.saveAttachmentList(updateReqVO.getId(), "INSTITUTION", updateReqVO.getAttachmentList());
    }

    @Override
    public void deleteInstitution(Long id) {
        // 校验存在
        validateInstitutionExists(id);
        // 删除
        institutionRepository.deleteById(id);
    }

    private void validateInstitutionExists(Long id) {
        institutionRepository.findById(id).orElseThrow(() -> exception(INSTITUTION_NOT_EXISTS));
    }

    @Override
    public Optional<Institution> getInstitution(Long id) {
        return institutionRepository.findById(id);
    }

    @Override
    public List<Institution> getInstitutionList(Collection<Long> ids) {
        return StreamSupport.stream(institutionRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Institution> getInstitutionPage(InstitutionPageReqVO pageReqVO, InstitutionPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Institution> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (pageReqVO.getProvince() != null) {
                predicates.add(cb.equal(root.get("province"), pageReqVO.getProvince()));
            }

            if (pageReqVO.getCity() != null) {
                predicates.add(cb.equal(root.get("city"), pageReqVO.getCity()));
            }

            if (pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if (pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }

            if (pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if (pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<Institution> page = institutionRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<Institution> getInstitutionList(InstitutionExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Institution> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (exportReqVO.getProvince() != null) {
                predicates.add(cb.equal(root.get("province"), exportReqVO.getProvince()));
            }

            if (exportReqVO.getCity() != null) {
                predicates.add(cb.equal(root.get("city"), exportReqVO.getCity()));
            }

            if (exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if (exportReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), exportReqVO.getAddress()));
            }

            if (exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if (exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return institutionRepository.findAll(spec);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public InstitutionImportRespVO importList(List<InstitutionImportVO> importUsers, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importUsers)) {
            throw exception(USER_IMPORT_LIST_IS_EMPTY);
        }
        InstitutionImportRespVO respVO = InstitutionImportRespVO.builder().createNames(new ArrayList<>())
                .updateNames(new ArrayList<>()).failureNames(new ArrayList<>()).build();

        importUsers.forEach(item -> {
            if (item.getName() != null) {
                item.setName(item.getName().trim());
            }
            Institution institution = institutionRepository.findByName(item.getName());
            if (institution == null && item.getName() != null) {
                Institution entity = institutionMapper.toEntity(item);
                if (item.getType() != null) {
                    switch (item.getType()) {
                        case "医院":
                            entity.setType("hospital");
                            break;
                        case "学校":
                            entity.setType("school");
                            break;
                        case "研究所":
                            entity.setType("research");
                            break;
                        default:
                            entity.setType("academy");
                    }
                } else {
                    entity.setType("academy");
                }

                institutionRepository.save(entity);

                respVO.getCreateNames().add(item.getName());
            } else {
                respVO.getFailureNames().add(item.getName() == null ? "企业名字为空" : item.getName());
            }

        });

        return respVO;
    }

    private Sort createSort(InstitutionPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProvince() != null) {
            orders.add(new Sort.Order(order.getProvince().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "province"));
        }

        if (order.getCity() != null) {
            orders.add(new Sort.Order(order.getCity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "city"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getAddress() != null) {
            orders.add(new Sort.Order(order.getAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}