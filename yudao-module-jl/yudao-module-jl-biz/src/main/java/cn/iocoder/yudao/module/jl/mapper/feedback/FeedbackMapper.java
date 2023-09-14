package cn.iocoder.yudao.module.jl.mapper.feedback;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.feedback.vo.*;
import cn.iocoder.yudao.module.jl.entity.feedback.Feedback;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FeedbackMapper {
    Feedback toEntity(FeedbackCreateReqVO dto);

    Feedback toEntity(FeedbackUpdateReqVO dto);

    FeedbackRespVO toDto(Feedback entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Feedback partialUpdate(FeedbackUpdateReqVO dto, @MappingTarget Feedback entity);

    List<FeedbackExcelVO> toExcelList(List<Feedback> list);

    PageResult<FeedbackRespVO> toPage(PageResult<Feedback> page);
}