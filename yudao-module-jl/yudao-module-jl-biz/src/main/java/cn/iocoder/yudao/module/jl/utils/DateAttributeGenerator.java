package cn.iocoder.yudao.module.jl.utils;

import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.enums.DateAttributeTypeEnums;
import cn.iocoder.yudao.module.jl.repository.user.UserRepository;
import cn.iocoder.yudao.module.system.controller.admin.dept.vo.dept.DeptByReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Repository
public class DateAttributeGenerator {

    @Resource
    DeptService deptService;

    @Resource
    UserRepository userRepository;

    public Long[] processAttributeUsers(String attribute) {
        List<Long> creators = new ArrayList<>();
        if (Objects.equals(attribute, DateAttributeTypeEnums.SUB.getStatus()) || Objects.equals(attribute, DateAttributeTypeEnums.ALL.getStatus())) {
            DeptByReqVO dept = new DeptByReqVO();
            dept.setLeaderUserId(getLoginUserId());
            DeptDO deptBy = deptService.getDeptBy(dept);
            if (deptBy != null) {
                List<DeptDO> deptListByParentIdFromCache = deptService.getDeptListByParentIdFromCache(deptBy.getId(), true);
                if (deptListByParentIdFromCache != null) {
                    // Extract the department IDs from the list of DeptDO objects
                    Collection<Long> departmentIds = deptListByParentIdFromCache.stream()
                            .map(DeptDO::getId)
                            .collect(Collectors.toList());
                    departmentIds.add(deptBy.getId());
                    // Find users by department IDs
                    List<User> byDeptIdIn = userRepository.findByDeptIdIn(departmentIds);
                    creators.addAll(byDeptIdIn.stream().map(User::getId).collect(Collectors.toList()));
                }
            }
        }
        System.out.println(getLoginUserId());

        System.out.println("--------");
        System.out.println(creators);

        if (Objects.equals(attribute, DateAttributeTypeEnums.SUB.getStatus())) {
            creators.remove(getLoginUserId());
        }

        if (Objects.equals(attribute, DateAttributeTypeEnums.MY.getStatus()) || Objects.equals(attribute, DateAttributeTypeEnums.ALL.getStatus())) {
            creators.add(getLoginUserId());
        }

        // Set creators as Long[] type
        return creators.toArray(new Long[0]);
    }

}