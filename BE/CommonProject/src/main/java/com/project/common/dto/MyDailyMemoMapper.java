package com.project.common.dto;

import com.project.common.entity.MyDailyMemoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MyDailyMemoMapper extends StructMapper<MyDailyMemoDto, MyDailyMemoEntity> {
    MyDailyMemoMapper MAPPER = Mappers.getMapper(MyDailyMemoMapper.class);

    @Override
    MyDailyMemoEntity toEntity(final MyDailyMemoDto myDailyMemoDto);

    @Override
    MyDailyMemoDto toDto(final MyDailyMemoEntity myDailyMemoEntity);
}
