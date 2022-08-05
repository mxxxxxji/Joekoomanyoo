package com.project.common.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.project.common.dto.Group.GroupDestinationDto;
import com.project.common.entity.Group.GroupDestinationEntity;

@Mapper
public interface GroupDestinationMapper extends StructMapper<GroupDestinationDto,GroupDestinationEntity>{
	GroupDestinationMapper MAPPER = Mappers.getMapper(GroupDestinationMapper.class);
	
	@Override
	GroupDestinationEntity toEntity(final GroupDestinationDto dto);
	
	@Override
	GroupDestinationDto toDto(final GroupDestinationEntity entity);
	
	@Override
	List<GroupDestinationDto> toDtoList(List<GroupDestinationEntity> entityList);
	
	
	
}