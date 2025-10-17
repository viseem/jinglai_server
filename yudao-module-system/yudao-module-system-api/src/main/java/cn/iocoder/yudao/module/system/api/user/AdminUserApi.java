package cn.iocoder.yudao.module.system.api.user;

import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Admin 用户 API 接口
 *
 * @author 芋道源码
 */
public interface AdminUserApi {

    /**
     * 通过用户 ID 查询用户
     *
     * @param id 用户ID
     * @return 用户对象信息
     */
    AdminUserRespDTO getUser(Long id);

    /**
     * 通过用户 ID 查询用户们
     *
     * @param ids 用户 ID 们
     * @return 用户对象信息
     */
    List<AdminUserRespDTO> getUserList(Collection<Long> ids);

    /**
     * 获得指定部门的用户数组
     *
     * @param deptIds 部门数组
     * @return 用户数组
     */
    List<AdminUserRespDTO> getUserListByDeptIds(Collection<Long> deptIds);

    /**
     * 获得指定岗位的用户数组
     *
     * @param postIds 岗位数组
     * @return 用户数组
     */
    List<AdminUserRespDTO> getUsersByPostIds(Collection<Long> postIds);

    /**
     * 获得拥有指定角色的用户数组
     *
     * @param roleCode 角色标识
     * @return 用户数组
     */
    List<AdminUserRespDTO> getUserListByRoleCode(String roleCode);

    /**
     * 获得用户 Map
     *
     * @param ids 用户编号数组
     * @return 用户 Map
     */
    default Map<Long, AdminUserRespDTO> getUserMap(Collection<Long> ids) {
        List<AdminUserRespDTO> users = getUserList(ids);
        return CollectionUtils.convertMap(users, AdminUserRespDTO::getId);
    }

    /**
     * 校验用户们是否有效。如下情况，视为无效：
     * 1. 用户编号不存在
     * 2. 用户被禁用
     *
     * @param ids 用户编号数组
     */
    void validateUserList(Collection<Long> ids);

    /**
     * 根据角色代码获取用户列表（带权限控制）
     * 权限规则：
     * - 如果当前用户拥有 finance 或 manager 角色，返回所有拥有指定角色的用户
     * - 否则只返回当前用户下属部门中拥有指定角色的用户
     *
     * @param roleCode 角色代码，支持逗号分隔多个角色，如 "sales,sale_manager"
     * @param loginUserId 当前登录用户ID
     * @return 用户列表
     */
    List<AdminUserRespDTO> getUserListByRoleCodeWithPermission(String roleCode, Long loginUserId);

}
