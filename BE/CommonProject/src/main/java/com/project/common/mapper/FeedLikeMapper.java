package com.project.common.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.project.common.dto.Feed.FeedLikeDto;
import com.project.common.entity.Feed.FeedLikeEntity;

@Mapper
public interface FeedLikeMapper extends StructMapper<FeedLikeDto,FeedLikeEntity>{
	FeedLikeMapper MAPPER = Mappers.getMapper(FeedLikeMapper.class);
	
	@Override
	FeedLikeEntity toEntity(final FeedLikeDto dto);
	
	@Override
	FeedLikeDto toDto(final FeedLikeEntity entity);
	
	@Override
	List<FeedLikeDto> toDtoList(List<FeedLikeEntity> entityList);
	
	
	
}