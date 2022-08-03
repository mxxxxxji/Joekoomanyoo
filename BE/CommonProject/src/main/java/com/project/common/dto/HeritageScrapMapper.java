package com.project.common.dto;

import com.project.common.entity.HeritageScrapEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HeritageScrapMapper extends StructMapper<HeritageScrapDto, HeritageScrapEntity>{
    HeritageScrapMapper MAPPER = Mappers.getMapper(HeritageScrapMapper.class);

    @Override
    HeritageScrapEntity toEntity(final HeritageScrapDto heritageScrapDto);

    @Override
    HeritageScrapDto toDto(final HeritageScrapEntity heritageScrapEntity);
}
