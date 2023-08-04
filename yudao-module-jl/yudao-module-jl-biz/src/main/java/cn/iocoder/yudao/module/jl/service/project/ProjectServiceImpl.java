package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSchedule;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectScheduleMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectScheduleRepository;
import cn.iocoder.yudao.module.jl.repository.user.UserRepository;
import cn.iocoder.yudao.module.jl.utils.UniqCodeGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Predicate;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.*;

/**
 * 项目管理 Service 实现类
 *
 */
@Service
@Validated
public class ProjectServiceImpl implements ProjectService {
    private final String uniqCodeKey = AUTO_INCREMENT_KEY_PROJECT_CODE.getKeyTemplate();
    private final String uniqCodePrefixKey = PREFIX_PROJECT_CODE.getKeyTemplate();


    @Resource
    private UniqCodeGenerator uniqCodeGenerator;

    @Resource
    private ProjectRepository projectRepository;
    @PostConstruct
    public void ProjectServiceImpl(){
        Project firstByOrderByIdDesc = projectRepository.findFirstByOrderByIdDesc();
        uniqCodeGenerator.setInitUniqUid(firstByOrderByIdDesc != null ? firstByOrderByIdDesc.getId() : 0L,uniqCodeKey,uniqCodePrefixKey, PROJECT_CODE_DEFAULT_PREFIX);
    }


    public String generateCode() {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return  String.format("%s%s%07d",uniqCodeGenerator.getUniqCodePrefix(),dateStr, uniqCodeGenerator.generateUniqUid());
    }



    public ProjectServiceImpl(UserRepository userRepository,
                              ProjectConstractRepository projectConstractRepository) {
        this.userRepository = userRepository;
        this.projectConstractRepository = projectConstractRepository;
    }



    @Resource
    private ProjectScheduleRepository projectScheduleRepository;

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ProjectScheduleMapper projectScheduleMapper;
    private final UserRepository userRepository;
    private final ProjectConstractRepository projectConstractRepository;


    @Override
    public Long createProject(ProjectCreateReqVO createReqVO) {
        // 插入
        createReqVO.setCode(generateCode());
        Project project = projectMapper.toEntity(createReqVO);
        projectRepository.save(project);

        Long projectId = project.getId();

        // 创建默认的安排单
        ProjectScheduleSaveReqVO saveScheduleReqVO = new ProjectScheduleSaveReqVO();
        saveScheduleReqVO.setProjectId(projectId);
        saveScheduleReqVO.setName(project.getName() + "的默认安排单");
        ProjectSchedule projectSchedule = projectScheduleMapper.toEntity(saveScheduleReqVO);
        projectScheduleRepository.save(projectSchedule);

        // 设置项目当前安排单
        setProjectCurrentSchedule(projectId, projectSchedule.getId());

        // 返回
        return project.getId();
    }

    @Override
    public void updateProject(ProjectUpdateReqVO updateReqVO) {
        // 校验存在
        Project project = validateProjectExists(updateReqVO.getId());
/*        if(project.getCode()==null|| project.getCode().equals("")){
            updateReqVO.setCode(generateCode());
        }*/
        // 更新
        Project updateObj = projectMapper.toEntity(updateReqVO);
        projectRepository.save(updateObj);
    }

    @Override
    public void setProjectCurrentSchedule(Long projectId, Long scheduleId) {
        // 校验存在
        validateProjectExists(projectId);

        projectRepository.updateCurrentScheduleIdById(projectId, scheduleId);
    }

    @Override
    public void deleteProject(Long id) {
        // 校验存在
        validateProjectExists(id);
        // 删除
        projectRepository.deleteById(id);
    }

    private Project validateProjectExists(Long id) {
        Optional<Project> byId = projectRepository.findById(id);
        if(byId.isEmpty()){
            throw exception(PROJECT_NOT_EXISTS);
        }
        return byId.orElse(null);
    }

    @Override
    public Optional<Project> getProject(Long id) {
        Optional<Project> byId = projectRepository.findById(id);
        byId.ifPresent(this::processProjectItem);
        return byId;
    }

    @Override
    public List<Project> getProjectList(Collection<Long> ids) {
        return StreamSupport.stream(projectRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Project> getProjectPage(ProjectPageReqVO pageReqVO, ProjectPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Project> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), getLoginUserId()));
            }

            if(pageReqVO.getStageArr() != null&&pageReqVO.getStageArr().size()>0) {
                predicates.add(root.get("stage").in(pageReqVO.getStageArr()));
            }

            if(pageReqVO.getSalesleadId() != null) {
                predicates.add(cb.equal(root.get("salesleadId"), pageReqVO.getSalesleadId()));
            }


            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), pageReqVO.getStage()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), pageReqVO.getStartDate()[0], pageReqVO.getStartDate()[1]));
            } 
            if(pageReqVO.getEndDate() != null) {
                predicates.add(cb.between(root.get("endDate"), pageReqVO.getEndDate()[0], pageReqVO.getEndDate()[1]));
            } 
            if(pageReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), pageReqVO.getManagerId()));
            }

            if(pageReqVO.getParticipants() != null) {
                predicates.add(cb.equal(root.get("participants"), pageReqVO.getParticipants()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getProcurementerId() != null) {
                predicates.add(cb.equal(root.get("procurementerId"), pageReqVO.getProcurementerId()));
            }
            if(pageReqVO.getInventorierId() != null) {
                predicates.add(cb.equal(root.get("inventorierId"), pageReqVO.getInventorierId()));
            }
            if(pageReqVO.getExperId() != null) {
                predicates.add(cb.equal(root.get("experId"), pageReqVO.getExperId()));
            }
/*            if(pageReqVO.getExpersId() != null) {
                predicates.add(cb.in(root.get("experIds"), pageReqVO.getExpersId()));
            }*/
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<Project> page = projectRepository.findAll(spec, pageable);
        page.forEach(this::processProjectItem);


        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    private void processProjectItem(Project project) {
        if (project.getApprovals()!=null&&project.getApprovals().size()>0){
            project.setLatestApproval(project.getApprovals().get(0));
        }
    }

    @Override
    public List<Project> getProjectList(ProjectExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Project> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getSalesleadId() != null) {
                predicates.add(cb.equal(root.get("salesleadId"), exportReqVO.getSalesleadId()));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), exportReqVO.getStage()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), exportReqVO.getStartDate()[0], exportReqVO.getStartDate()[1]));
            } 
            if(exportReqVO.getEndDate() != null) {
                predicates.add(cb.between(root.get("endDate"), exportReqVO.getEndDate()[0], exportReqVO.getEndDate()[1]));
            } 
            if(exportReqVO.getManagerId() != null) {
                predicates.add(cb.equal(root.get("managerId"), exportReqVO.getManagerId()));
            }

            if(exportReqVO.getParticipants() != null) {
                predicates.add(cb.equal(root.get("participants"), exportReqVO.getParticipants()));
            }

            if(exportReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), exportReqVO.getSalesId()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectRepository.findAll(spec);
    }

    private Sort createSort(ProjectPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getSalesleadId() != null) {
            orders.add(new Sort.Order(order.getSalesleadId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salesleadId"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getStage() != null) {
            orders.add(new Sort.Order(order.getStage().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "stage"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getStartDate() != null) {
            orders.add(new Sort.Order(order.getStartDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "startDate"));
        }

        if (order.getEndDate() != null) {
            orders.add(new Sort.Order(order.getEndDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "endDate"));
        }

        if (order.getManagerId() != null) {
            orders.add(new Sort.Order(order.getManagerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "managerId"));
        }

        if (order.getParticipants() != null) {
            orders.add(new Sort.Order(order.getParticipants().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "participants"));
        }

        if (order.getSalesId() != null) {
            orders.add(new Sort.Order(order.getSalesId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salesId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}