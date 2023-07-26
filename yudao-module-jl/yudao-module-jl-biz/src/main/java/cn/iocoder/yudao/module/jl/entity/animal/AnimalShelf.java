package cn.iocoder.yudao.module.jl.entity.animal;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalRoomRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.*;
import javax.annotation.Resource;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 动物饲养笼架 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AnimalShelf")
@Table(name = "jl_animal_shelf")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AnimalShelf extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     *
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "shelf_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<AnimalBox> boxes = new ArrayList<>();

    /**
     * 名字
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 编号
     */
    @Column(name = "code")
    private String code;

/*    public String getCode(){
        if(this.code!=null&&this.code.length()>0){
            return this.code;
        }
        animalRoomRepository.updateCodeById("ST"+this.id,this.id);
        return "ST"+this.id;
    }*/

    /**
     * 缩略图名称
     */
    @Column(name = "file_name", nullable = false )
    private String fileName;

    /**
     * 缩略图地址
     */
    @Column(name = "file_url", nullable = false )
    private String fileUrl;

    /**
     * 位置
     */
    @Column(name = "location", nullable = false )
    private String location;

    /**
     * 管理人id
     */
    @Column(name = "manager_id")
    private Long managerId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "manager_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User manager;

    /**
     * 描述说明
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 饲养室id
     */
    @Column(name = "room_id", nullable = false )
    private Long roomId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "room_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AnimalRoom room;

    /**
     * 排序
     */
    @Column(name = "weight", nullable = false )
    private Integer weight;

    /**
     * 行个数
     */
    @Column(name = "row_count", nullable = false )
    private Integer rowCount;

    /**
     * 列个数
     */
    @Column(name = "col_count", nullable = false )
    private Integer colCount;

    /**
     * 笼位默认容量
     */
    @Column(name = "capacity", nullable = false )
    private Integer capacity;

}
