package com.project.common.mapper;

import com.project.common.dto.Group.GroupScheduleDto;
import com.project.common.dto.Group.GroupScheduleDto.GroupScheduleDtoBuilder;
import com.project.common.entity.Group.GroupScheduleEntity;
import com.project.common.entity.Group.GroupScheduleEntity.GroupScheduleEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-07T01:51:20+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class GroupScheduleMapperImpl implements GroupScheduleMapper {

    @Override
    public List<GroupScheduleEntity> toEntityList(List<GroupScheduleDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<GroupScheduleEntity> list = new ArrayList<GroupScheduleEntity>( dtoList.size() );
        for ( GroupScheduleDto groupScheduleDto : dtoList ) {
            list.add( toEntity( groupScheduleDto ) );
        }

        return list;
    }

    @Override
    public GroupScheduleEntity toEntity(GroupScheduleDto dto) {
        if ( dto == null ) {
            return null;
        }

        GroupScheduleEntityBuilder groupScheduleEntity = GroupScheduleEntity.builder();

        groupScheduleEntity.gsSeq( dto.getGsSeq() );
        groupScheduleEntity.gsContent( dto.getGsContent() );
        groupScheduleEntity.gsDateTime( dto.getGsDateTime() );
        groupScheduleEntity.gsRegisteredAt( dto.getGsRegisteredAt() );
        groupScheduleEntity.gsUpdatedAt( dto.getGsUpdatedAt() );
        groupScheduleEntity.group( dto.getGroup() );

        return groupScheduleEntity.build();
    }

    @Override
    public GroupScheduleDto toDto(GroupScheduleEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GroupScheduleDtoBuilder groupScheduleDto = GroupScheduleDto.builder();

        groupScheduleDto.gsSeq( entity.getGsSeq() );
        groupScheduleDto.gsContent( entity.getGsContent() );
        groupScheduleDto.gsDateTime( entity.getGsDateTime() );
        groupScheduleDto.gsRegisteredAt( entity.getGsRegisteredAt() );
        groupScheduleDto.gsUpdatedAt( entity.getGsUpdatedAt() );
        groupScheduleDto.group( entity.getGroup() );

        return groupScheduleDto.build();
    }

    @Override
    public List<GroupScheduleDto> toDtoList(List<GroupScheduleEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<GroupScheduleDto> list = new ArrayList<GroupScheduleDto>( entityList.size() );
        for ( GroupScheduleEntity groupScheduleEntity : entityList ) {
            list.add( toDto( groupScheduleEntity ) );
        }

        return list;
    }
}
