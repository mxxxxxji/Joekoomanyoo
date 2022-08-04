package com.project.common.dto.Heritage;

import com.project.common.entity.Heritage.HeritageReviewEntity;
import com.project.common.mapper.StructMapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface HeritageReviewMapper extends StructMapper<HeritageReivewDto, HeritageReviewEntity> {
    HeritageReviewMapper MAPPER = Mappers.getMapper(HeritageReviewMapper.class);

    @Override
    HeritageReviewEntity toEntity(final HeritageReivewDto heritageReivewDto);

    @Override
    HeritageReivewDto toDto(final HeritageReviewEntity heritageReviewEntity);
}
