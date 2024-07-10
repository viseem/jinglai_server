package cn.iocoder.yudao.module.jl.service.dept;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.dept.vo.*;
import cn.iocoder.yudao.module.jl.entity.dept.Dept;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 部门 Service 接口
 *
 */
public interface XDeptService {

    /**
     * 创建部门
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDept(@Valid DeptCreateReqVO createReqVO);

    /**
     * 更新部门
     *
     * @param updateReqVO 更新信息
     */
    void updateDept(@Valid DeptUpdateReqVO updateReqVO);

    /**
     * 删除部门
     *
     * @param id 编号
     */
    void deleteDept(Long id);

    /**
     * 获得部门
     *
     * @param id 编号
     * @return 部门
     */
    Optional<Dept> getDept(Long id);

    /**
     * 获得部门列表
     *
     * @param ids 编号
     * @return 部门列表
     */
    List<Dept> getDeptList(Collection<Long> ids);

    /**
     * 获得部门分页
     *
     * @param pageReqVO 分页查询
     * @return 部门分页
     */
    PageResult<Dept> getDeptPage(DeptPageReqVO pageReqVO, DeptPageOrder orderV0);

    /**
     * 获得部门列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 部门列表
     */
    List<Dept> getDeptList(DeptExportReqVO exportReqVO);

}
