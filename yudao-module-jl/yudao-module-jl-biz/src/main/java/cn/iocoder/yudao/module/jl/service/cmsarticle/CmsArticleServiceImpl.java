package cn.iocoder.yudao.module.jl.service.cmsarticle;

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
import cn.iocoder.yudao.module.jl.controller.admin.cmsarticle.vo.*;
import cn.iocoder.yudao.module.jl.entity.cmsarticle.CmsArticle;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.cmsarticle.CmsArticleMapper;
import cn.iocoder.yudao.module.jl.repository.cmsarticle.CmsArticleRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 文章 Service 实现类
 *
 */
@Service
@Validated
public class CmsArticleServiceImpl implements CmsArticleService {

    @Resource
    private CmsArticleRepository cmsArticleRepository;

    @Resource
    private CmsArticleMapper cmsArticleMapper;

    @Override
    public Long createCmsArticle(CmsArticleCreateReqVO createReqVO) {

        //如果浏览次数小于0，则随机生成一个1000-90000的数
        if(createReqVO.getLookCount()==null||createReqVO.getLookCount() < 1){
            createReqVO.setLookCount((int)(Math.random()*90000+1000));
        }

        // 插入
        CmsArticle cmsArticle = cmsArticleMapper.toEntity(createReqVO);
        cmsArticleRepository.save(cmsArticle);
        // 返回
        return cmsArticle.getId();
    }

    @Override
    public void updateCmsArticle(CmsArticleUpdateReqVO updateReqVO) {
        // 校验存在
        validateCmsArticleExists(updateReqVO.getId());

        //如果浏览次数小于0，则随机生成一个1000-90000的数
        if(updateReqVO.getLookCount()==null||updateReqVO.getLookCount() < 1){
            updateReqVO.setLookCount((int)(Math.random()*90000+1000));
        }

        // 更新
        CmsArticle updateObj = cmsArticleMapper.toEntity(updateReqVO);
        cmsArticleRepository.save(updateObj);
    }

    @Override
    public void deleteCmsArticle(Long id) {
        // 校验存在
        validateCmsArticleExists(id);
        // 删除
        cmsArticleRepository.deleteById(id);
    }

    private void validateCmsArticleExists(Long id) {
        cmsArticleRepository.findById(id).orElseThrow(() -> exception(CMS_ARTICLE_NOT_EXISTS));
    }

    @Override
    public Optional<CmsArticle> getCmsArticle(Long id) {
        return cmsArticleRepository.findById(id);
    }

    @Override
    public List<CmsArticle> getCmsArticleList(Collection<Long> ids) {
        return StreamSupport.stream(cmsArticleRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CmsArticle> getCmsArticlePage(CmsArticlePageReqVO pageReqVO, CmsArticlePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CmsArticle> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getTitle() != null) {
                predicates.add(cb.equal(root.get("title"), pageReqVO.getTitle()));
            }

            if(pageReqVO.getSubTitle() != null) {
                predicates.add(cb.equal(root.get("subTitle"), pageReqVO.getSubTitle()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getLookCount() != null) {
                predicates.add(cb.equal(root.get("lookCount"), pageReqVO.getLookCount()));
            }

            if(pageReqVO.getSort() != null) {
                predicates.add(cb.equal(root.get("sort"), pageReqVO.getSort()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CmsArticle> page = cmsArticleRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CmsArticle> getCmsArticleList(CmsArticleExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CmsArticle> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getTitle() != null) {
                predicates.add(cb.equal(root.get("title"), exportReqVO.getTitle()));
            }

            if(exportReqVO.getSubTitle() != null) {
                predicates.add(cb.equal(root.get("subTitle"), exportReqVO.getSubTitle()));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getLookCount() != null) {
                predicates.add(cb.equal(root.get("lookCount"), exportReqVO.getLookCount()));
            }

            if(exportReqVO.getSort() != null) {
                predicates.add(cb.equal(root.get("sort"), exportReqVO.getSort()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return cmsArticleRepository.findAll(spec);
    }

    private Sort createSort(CmsArticlePageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("desc".equals(order.getCreateTime()) ? Sort.Direction.DESC : Sort.Direction.ASC, "sort"));
        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getTitle() != null) {
            orders.add(new Sort.Order(order.getTitle().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "title"));
        }

        if (order.getSubTitle() != null) {
            orders.add(new Sort.Order(order.getSubTitle().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "subTitle"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getLookCount() != null) {
            orders.add(new Sort.Order(order.getLookCount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "lookCount"));
        }

        if (order.getSort() != null) {
            orders.add(new Sort.Order(order.getSort().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sort"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}