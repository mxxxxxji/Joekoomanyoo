package com.project.common.mapper.Group;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.project.common.dto.Group.GroupDailyMemoDto;
import com.project.common.dto.Group.GroupMemberDto;
import com.project.common.entity.Group.GroupDailyMemoEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.mapper.StructMapper;

@Mapper
public interface GroupDailyMemoMapper extends StructMapper<GroupDailyMemoDto,GroupDailyMemoEntity>{
	GroupDailyMemoMapper MAPPER = Mappers.getMapper(GroupDailyMemoMapper.class);
	
	@Override
	GroupDailyMemoEntity toEntity(final GroupDailyMemoDto dto);
	
	@Override
	GroupDailyMemoDto toDto(final GroupDailyMemoEntity entity);
	
	@Override
	List<GroupDailyMemoDto> toDtoList(List<GroupDailyMemoEntity> entityList);
	
	
	
}