package cn.iocoder.yudao.module.system.service.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.datapermission.core.util.DataPermissionUtils;
import cn.iocoder.yudao.module.infra.api.file.FileApi;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.*;
import cn.iocoder.yudao.module.system.convert.user.UserConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.UserPostDO;
import cn.iocoder.yudao.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.UserRoleDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.dept.UserPostMapper;
import cn.iocoder.yudao.module.system.dal.mysql.oauth2.OAuth2AccessTokenMapper;
import cn.iocoder.yudao.module.system.dal.mysql.permission.UserRoleMapper;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import cn.iocoder.yudao.module.system.service.dept.PostService;
import cn.iocoder.yudao.module.system.service.oauth2.OAuth2TokenService;
import cn.iocoder.yudao.module.system.service.permission.PermissionService;
import cn.iocoder.yudao.module.system.service.permission.RoleService;
import cn.iocoder.yudao.module.system.service.tenant.TenantService;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 后台用户 Service 实现类
 *
 * @author 芋道源码
 */
@Service("adminUserService")
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Value("${sys.user.init-password:yudaoyuanma}")
    private String userInitPassword;

    @Resource
    private AdminUserMapper userMapper;

    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    @Lazy // 延迟，避免循环依赖报错
    private TenantService tenantService;

    @Resource
    private UserPostMapper userPostMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private FileApi fileApi;

    @Resource
    private OAuth2TokenService oauth2TokenService;

    @Resource
    private OAuth2AccessTokenMapper oauth2AccessTokenMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "usersByRoleCode", allEntries = true)
    public Long createUser(UserCreateReqVO reqVO) {
        // 校验账户配合
        tenantService.handleTenantInfo(tenant -> {
            long count = userMapper.selectCount();
            if (count >= tenant.getAccountCount()) {
                throw exception(USER_COUNT_MAX, tenant.getAccountCount());
            }
        });
        // 校验正确性
        validateUserForCreateOrUpdate(null, reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(),
                reqVO.getDeptId(), reqVO.getPostIds());
        // 插入用户
        AdminUserDO user = UserConvert.INSTANCE.convert(reqVO);
        user.setStatus(CommonStatusEnum.ENABLE.getStatus()); // 默认开启
        user.setPassword(encodePassword(reqVO.getPassword())); // 加密密码
        userMapper.insert(user);
        // 插入关联岗位
        if (CollectionUtil.isNotEmpty(user.getPostIds())) {
            userPostMapper.insertBatch(convertList(user.getPostIds(),
                    postId -> new UserPostDO().setUserId(user.getId()).setPostId(postId)));
        }
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "usersByRoleCode", allEntries = true)
    public void updateUser(UserUpdateReqVO reqVO) {
        // 校验正确性
        validateUserForCreateOrUpdate(reqVO.getId(), reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(),
                reqVO.getDeptId(), reqVO.getPostIds());
        // 更新用户
        AdminUserDO updateObj = UserConvert.INSTANCE.convert(reqVO);
        userMapper.updateById(updateObj);
        // 更新岗位
        updateUserPost(reqVO, updateObj);
    }

    private void updateUserPost(UserUpdateReqVO reqVO, AdminUserDO updateObj) {
        Long userId = reqVO.getId();
        Set<Long> dbPostIds = convertSet(userPostMapper.selectListByUserId(userId), UserPostDO::getPostId);
        // 计算新增和删除的岗位编号
        Set<Long> postIds = updateObj.getPostIds();
        Collection<Long> createPostIds = CollUtil.subtract(postIds, dbPostIds);
        Collection<Long> deletePostIds = CollUtil.subtract(dbPostIds, postIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (!CollectionUtil.isEmpty(createPostIds)) {
            userPostMapper.insertBatch(convertList(createPostIds,
                    postId -> new UserPostDO().setUserId(userId).setPostId(postId)));
        }
        if (!CollectionUtil.isEmpty(deletePostIds)) {
            userPostMapper.deleteByUserIdAndPostId(userId, deletePostIds);
        }
    }

    @Override
    public void updateUserLogin(Long id, String loginIp) {
        userMapper.updateById(new AdminUserDO().setId(id).setLoginIp(loginIp).setLoginDate(LocalDateTime.now()));
    }

    @Override
    public void updateUserProfile(Long id, UserProfileUpdateReqVO reqVO) {
        // 校验正确性
        validateUserExists(id);
        validateEmailUnique(id, reqVO.getEmail());
        validateMobileUnique(id, reqVO.getMobile());
        // 执行更新
        userMapper.updateById(UserConvert.INSTANCE.convert(reqVO).setId(id));
    }

    @Override
    public void updateUserPassword(Long id, UserProfileUpdatePasswordReqVO reqVO) {
        // 校验旧密码密码
        validateOldPassword(id, reqVO.getOldPassword());
        // 执行更新
        updateUserPassword(id,reqVO.getNewPassword());
    }

    @Override
    public String updateUserAvatar(Long id, InputStream avatarFile) throws Exception {
        validateUserExists(id);
        // 存储文件
        String avatar = fileApi.createFile(IoUtil.readBytes(avatarFile));
        // 更新路径
        AdminUserDO sysUserDO = new AdminUserDO();
        sysUserDO.setId(id);
        sysUserDO.setAvatar(avatar);
        userMapper.updateById(sysUserDO);
        return avatar;
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        // 校验用户存在
        validateUserExists(id);
        // 更新密码
        AdminUserDO updateObj = new AdminUserDO();
        updateObj.setId(id);
        updateObj.setPassword(encodePassword(password)); // 加密密码
        userMapper.updateById(updateObj);
        // 失效token缓存
        List<OAuth2AccessTokenDO> oAuth2AccessTokenDOS = oauth2AccessTokenMapper.selectListByUserId(id);
        for (OAuth2AccessTokenDO accessTokenDO : oAuth2AccessTokenDOS) {
            oauth2TokenService.removeAccessToken(accessTokenDO.getAccessToken());
        }


    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        // 校验用户存在
        validateUserExists(id);
        // 更新状态
        AdminUserDO updateObj = new AdminUserDO();
        updateObj.setId(id);
        updateObj.setStatus(status);
        userMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "usersByRoleCode", allEntries = true)
    public void deleteUser(Long id) {
        // 校验用户存在
        validateUserExists(id);
        // 删除用户
        userMapper.deleteById(id);
        // 删除用户关联数据
        permissionService.processUserDeleted(id);
        // 删除用户岗位
        userPostMapper.deleteByUserId(id);
    }

    @Override
    public AdminUserDO getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public AdminUserDO getUserByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }

    @Override
    public PageResult<AdminUserDO> getUserPage(UserPageReqVO reqVO) {
        // 如果指定了角色Code，先通过角色查询用户ID列表，然后添加到查询条件
        // 但如果IDs已经被设置（说明在Controller层已经进行了权限过滤），则不应该覆盖
        if (StrUtil.isNotEmpty(reqVO.getRoleCode()) && CollUtil.isEmpty(reqVO.getIds())) {
            // 1. 通过角色code查询角色
            RoleDO role = roleService.getRoleByCode(reqVO.getRoleCode());
            if (role != null) {
                // 2. 通过角色ID查询用户角色关联表，获取用户ID列表
                Set<Long> userIds = convertSet(userRoleMapper.selectListByRoleId(role.getId()), UserRoleDO::getUserId);
                if (CollUtil.isNotEmpty(userIds)) {
                    // 3. 将用户ID列表添加到查询条件
                    reqVO.setIds(new ArrayList<>(userIds));
                } else {
                    // 如果没有用户，设置一个不存在的ID，确保返回空结果
                    reqVO.setIds(Collections.singletonList(-1L));
                }
            } else {
                // 角色不存在，返回空结果
                reqVO.setIds(Collections.singletonList(-1L));
            }
        }
        
        return userMapper.selectPage(reqVO, reqVO.getDeptIds()!=null?getDeptCondition(reqVO.getDeptIds()):getDeptCondition(reqVO.getDeptId()));
    }

    @Override
    public AdminUserDO getUser(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<AdminUserDO> getUserListByDeptIds(Collection<Long> deptIds) {
        if (CollUtil.isEmpty(deptIds)) {
            return Collections.emptyList();
        }
        return userMapper.selectListByDeptIds(deptIds);
    }

    @Override
    public List<AdminUserDO> getUserListByPostIds(Collection<Long> postIds) {
        if (CollUtil.isEmpty(postIds)) {
            return Collections.emptyList();
        }
        Set<Long> userIds = convertSet(userPostMapper.selectListByPostIds(postIds), UserPostDO::getUserId);
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(userIds);
    }

    @Override
    public List<AdminUserDO> getUserListByRoleCode(String roleCode) {
        if (StrUtil.isEmpty(roleCode)) {
            return Collections.emptyList();
        }
        // 1. 通过角色code查询角色
        RoleDO role = roleService.getRoleByCode(roleCode);
        if (role == null) {
            return Collections.emptyList();
        }
        // 2. 通过角色ID查询用户角色关联表，获取用户ID列表
        Set<Long> userIds = convertSet(userRoleMapper.selectListByRoleId(role.getId()), UserRoleDO::getUserId);
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        // 3. 通过用户ID查询用户列表
        return userMapper.selectBatchIds(userIds);
    }

    @Override
    public List<AdminUserDO> getUserList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(ids);
    }

    @Override
    public void validateUserList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得岗位信息
        List<AdminUserDO> users = userMapper.selectBatchIds(ids);
        Map<Long, AdminUserDO> userMap = CollectionUtils.convertMap(users, AdminUserDO::getId);
        // 校验
        ids.forEach(id -> {
            AdminUserDO user = userMap.get(id);
            if (user == null) {
                throw exception(USER_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(user.getStatus())) {
                throw exception(USER_IS_DISABLE, user.getNickname());
            }
        });
    }

    @Override
    public List<AdminUserDO> getUserList(UserExportReqVO reqVO) {
        return userMapper.selectList(reqVO, getDeptCondition(reqVO.getDeptId()));
    }

    @Override
    public List<AdminUserDO> getUserListByNickname(String nickname) {
        return userMapper.selectListByNickname(nickname);
    }

    /**
     * 获得部门条件：查询指定部门的子部门编号们，包括自身
     * @param deptId 部门编号
     * @return 部门编号集合
     */
    private Set<Long> getDeptCondition(Long deptId) {
        if (deptId == null) {
            return Collections.emptySet();
        }
        Set<Long> deptIds = convertSet(deptService.getDeptListByParentIdFromCache(
                deptId, true), DeptDO::getId);
        deptIds.add(deptId); // 包括自身
        return deptIds;
    }

    /**
     * 获得部门条件：查询指定部门的子部门编号们，包括自身
     * @param deptIds 部门编号
     * @return 部门编号集合
     */
    private Set<Long> getDeptCondition(Collection<Long> deptIds) {
        if (deptIds == null) {
            return Collections.emptySet();
        }
        Set<Long> longs = convertSet(deptService.getDeptList(deptIds), DeptDO::getId);
//        longs.add(longs); // 包括自身
        return longs;
    }

    private void validateUserForCreateOrUpdate(Long id, String username, String mobile, String email,
                                              Long deptId, Set<Long> postIds) {
        // 关闭数据权限，避免因为没有数据权限，查询不到数据，进而导致唯一校验不正确
        DataPermissionUtils.executeIgnore(() -> {
            // 校验用户存在
            validateUserExists(id);
            // 校验用户名唯一
            validateUsernameUnique(id, username);
            // 校验手机号唯一
            validateMobileUnique(id, mobile);
            // 校验邮箱唯一
            validateEmailUnique(id, email);
            // 校验部门处于开启状态
            deptService.validateDeptList(CollectionUtils.singleton(deptId));
            // 校验岗位处于开启状态
            postService.validatePostList(postIds);
        });
    }

    @VisibleForTesting
    void validateUserExists(Long id) {
        if (id == null) {
            return;
        }
        AdminUserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    void validateUsernameUnique(Long id, String username) {
        if (StrUtil.isBlank(username)) {
            return;
        }
        AdminUserDO user = userMapper.selectByUsername(username);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_USERNAME_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_USERNAME_EXISTS);
        }
    }

    @VisibleForTesting
    void validateEmailUnique(Long id, String email) {
        if (StrUtil.isBlank(email)) {
            return;
        }
        AdminUserDO user = userMapper.selectByEmail(email);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_EMAIL_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_EMAIL_EXISTS);
        }
    }

    @VisibleForTesting
    void validateMobileUnique(Long id, String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return;
        }
        AdminUserDO user = userMapper.selectByMobile(mobile);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_MOBILE_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_MOBILE_EXISTS);
        }
    }

    /**
     * 校验旧密码
     * @param id          用户 id
     * @param oldPassword 旧密码
     */
    @VisibleForTesting
    void validateOldPassword(Long id, String oldPassword) {
        AdminUserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        if (!isPasswordMatch(oldPassword, user.getPassword())) {
            throw exception(USER_PASSWORD_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public UserImportRespVO importUserList(List<UserImportExcelVO> importUsers, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importUsers)) {
            throw exception(USER_IMPORT_LIST_IS_EMPTY);
        }
        UserImportRespVO respVO = UserImportRespVO.builder().createUsernames(new ArrayList<>())
                .updateUsernames(new ArrayList<>()).failureUsernames(new LinkedHashMap<>()).build();
        importUsers.forEach(importUser -> {
            // 校验，判断是否有不符合的原因
            try {
                validateUserForCreateOrUpdate(null, null, importUser.getMobile(), importUser.getEmail(),
                        importUser.getDeptId(), null);
            } catch (ServiceException ex) {
                respVO.getFailureUsernames().put(importUser.getUsername(), ex.getMessage());
                return;
            }
            // 判断如果不存在，在进行插入
            AdminUserDO existUser = userMapper.selectByUsername(importUser.getUsername());
            if (existUser == null) {
                userMapper.insert(UserConvert.INSTANCE.convert(importUser)
                        .setPassword(encodePassword(userInitPassword)).setPostIds(new HashSet<>())); // 设置默认密码及空岗位编号数组
                respVO.getCreateUsernames().add(importUser.getUsername());
                return;
            }
            // 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureUsernames().put(importUser.getUsername(), USER_USERNAME_EXISTS.getMsg());
                return;
            }
            AdminUserDO updateUser = UserConvert.INSTANCE.convert(importUser);
            updateUser.setId(existUser.getId());
            userMapper.updateById(updateUser);
            respVO.getUpdateUsernames().add(importUser.getUsername());
        });
        return respVO;
    }

    @Override
    public List<AdminUserDO> getUserListByStatus(Integer status) {
        return userMapper.selectListByStatus(status);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 对密码进行加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 获取用户列表（根据角色代码和权限控制）
     * 使用 Redis 缓存，永久缓存，仅在用户变更或角色分配时主动清除
     * 
     * @param roleCode 角色代码（支持逗号分隔的多个角色，如 "sales,sale_manager"）
     * @param loginUserId 当前登录用户ID（null 表示不进行权限控制）
     * @return 用户列表
     */
    @Override
    @Cacheable(cacheNames = "usersByRoleCode", key = "#roleCode + '_' + (#loginUserId != null ? #loginUserId : 'null')")
    public List<AdminUserDO> getUserListByRoleCodeWithPermission(String roleCode, Long loginUserId) {
        if (StrUtil.isEmpty(roleCode)) {
            return Collections.emptyList();
        }
        
        log.debug("从数据库查询销售人员列表，roleCode: {}, loginUserId: {}", roleCode, loginUserId);
        
        // 支持逗号分隔的多个角色代码
        String[] roleCodes = roleCode.split(",");
        Map<Long, AdminUserDO> allRoleUsersMap = new HashMap<>();
        
        // 查询所有角色的用户并去重
        for (String code : roleCodes) {
            String trimmedCode = code.trim();
            if (StrUtil.isNotEmpty(trimmedCode)) {
                List<AdminUserDO> users = getUserListByRoleCode(trimmedCode);
                if (CollUtil.isNotEmpty(users)) {
                    users.forEach(user -> allRoleUsersMap.put(user.getId(), user));
                }
            }
        }
        
        if (allRoleUsersMap.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<AdminUserDO> allRoleUsers = new ArrayList<>(allRoleUsersMap.values());
        
        // 如果没有登录用户ID，直接返回所有用户
        if (loginUserId == null) {
            log.debug("无权限控制，返回所有 {} 角色用户，共 {} 人", roleCode, allRoleUsers.size());
            return allRoleUsers;
        }
        
        // 检查当前用户是否有 finance 或 manager 角色
        boolean hasFullAccess = permissionService.hasAnyRoles(loginUserId, "finance", "manager");
        
        if (hasFullAccess) {
            // 有完整权限，返回所有拥有该角色的用户
            log.debug("用户 {} 拥有完整权限，返回所有 {} 角色用户，共 {} 人", 
                loginUserId, roleCode, allRoleUsers.size());
            return allRoleUsers;
        }
        
        // 没有完整权限，处理普通用户的权限
        List<AdminUserDO> result = new ArrayList<>();
        
        // 1. 首先检查当前用户自己是否拥有该角色，如果是，至少返回自己
        boolean isSelfInRole = allRoleUsers.stream()
                .anyMatch(user -> user.getId().equals(loginUserId));
        
        if (isSelfInRole) {
            // 当前用户自己就拥有该角色，添加自己
            allRoleUsers.stream()
                    .filter(user -> user.getId().equals(loginUserId))
                    .findFirst()
                    .ifPresent(result::add);
            log.debug("用户 {} 自己拥有 {} 角色，添加自己到结果中", loginUserId, roleCode);
        }
        
        // 2. 如果用户是部门负责人，还要添加下属部门的用户
        DeptDO userDept = deptService.getDept(getUserDeptId(loginUserId));
        if (userDept != null && loginUserId.equals(userDept.getLeaderUserId())) {
            // 是部门负责人，获取该部门及其子部门
            List<DeptDO> deptList = deptService.getDeptListByParentIdFromCache(userDept.getId(), true);
            final Set<Long> deptIds = new HashSet<>();
            if (CollUtil.isNotEmpty(deptList)) {
                deptIds.addAll(convertSet(deptList, DeptDO::getId));
            }
            deptIds.add(userDept.getId()); // 包含自己负责的部门
            
            // 过滤出下属部门的用户（排除已经添加的自己）
            List<AdminUserDO> subordinateUsers = allRoleUsers.stream()
                    .filter(user -> user.getDeptId() != null && deptIds.contains(user.getDeptId()))
                    .filter(user -> !user.getId().equals(loginUserId)) // 排除自己，避免重复
                    .collect(java.util.stream.Collectors.toList());
            
            result.addAll(subordinateUsers);
            log.debug("用户 {} 是部门负责人，添加下属部门用户 {} 人", loginUserId, subordinateUsers.size());
        }
        
        log.debug("用户 {} 最终可查看的 {} 角色用户，共 {} 人", 
            loginUserId, roleCode, result.size());
        
        return result;
    }
    
    /**
     * 获取用户的部门ID
     */
    private Long getUserDeptId(Long userId) {
        AdminUserDO user = getUser(userId);
        return user != null ? user.getDeptId() : null;
    }

}
