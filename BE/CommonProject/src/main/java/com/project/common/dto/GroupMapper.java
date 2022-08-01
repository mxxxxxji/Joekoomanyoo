package com.project.common.dto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.project.common.entity.GroupEntity;

@Mapper(componentModel = "spring")
public interface GroupMapper extends StructMapper<GroupDto,GroupEntity>{
	GroupMapper MAPPER = Mappers.getMapper(GroupMapper.class);
	
	@Override
	GroupDto toDto(final GroupEntity entity);
	
	@Override
	List<GroupDto> toDtoList(List<GroupEntity> entityList);
	
	
	
}