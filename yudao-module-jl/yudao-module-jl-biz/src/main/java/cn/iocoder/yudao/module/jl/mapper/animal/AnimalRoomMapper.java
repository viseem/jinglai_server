package cn.iocoder.yudao.module.jl.mapper.animal;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalRoom;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimalRoomMapper {
    AnimalRoom toEntity(AnimalRoomCreateReqVO dto);

    AnimalRoom toEntity(AnimalRoomUpdateReqVO dto);

    AnimalRoomRespVO toDto(AnimalRoom entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AnimalRoom partialUpdate(AnimalRoomUpdateReqVO dto, @MappingTarget AnimalRoom entity);

    List<AnimalRoomExcelVO> toExcelList(List<AnimalRoom> list);

    PageResult<AnimalRoomRespVO> toPage(PageResult<AnimalRoom> page);
}