package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedStoreIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 动物饲养日志更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AnimalFeedLogSaveReqVO extends AnimalFeedLogBaseVO {

    private Long id;

    private List<AnimalFeedStoreIn> storeList;

    private String location;
    private String locationCode;


}
