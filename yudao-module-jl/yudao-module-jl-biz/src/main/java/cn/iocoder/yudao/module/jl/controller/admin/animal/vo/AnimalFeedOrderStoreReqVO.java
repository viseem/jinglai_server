package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedCard;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedStoreIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 动物饲养申请单更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AnimalFeedOrderStoreReqVO extends AnimalFeedOrderBaseVO {

    private Long id;
    private List<AnimalFeedStoreIn> stores;

    private List<AnimalFeedCard> cards;
}
