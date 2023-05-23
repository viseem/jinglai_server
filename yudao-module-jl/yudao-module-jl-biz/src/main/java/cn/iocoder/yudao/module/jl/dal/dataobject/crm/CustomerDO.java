package cn.iocoder.yudao.module.jl.dal.dataobject.crm;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 客户 DO
 *
 * @author 惟象科技
 */
@TableName("jl_crm_customer")
@KeySequence("jl_crm_customer_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 客户来源
     *
     * 枚举 {@link TODO customer_source 对应的类}
     */
    private String source;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 备注
     */
    private String mark;
    /**
     * 微信号
     */
    private String wechat;
    /**
     * 医生职业级别
     */
    private String doctorProfessionalRank;
    /**
     * 医院科室
     */
    private String hospitalDepartment;
    /**
     * 学校职称
     */
    private String academicTitle;
    /**
     * 学历
     */
    private String academicCredential;
    /**
     * 医院
     */
    private Long hospitalId;
    /**
     * 学校机构
     */
    private Long universityId;
    /**
     * 公司
     */
    private Long companyId;

}