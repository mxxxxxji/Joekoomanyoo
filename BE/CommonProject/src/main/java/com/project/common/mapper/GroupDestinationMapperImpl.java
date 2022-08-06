package com.project.common.mapper;

import com.project.common.dto.Group.GroupDestinationDto;
import com.project.common.dto.Group.GroupDestinationDto.GroupDestinationDtoBuilder;
import com.project.common.entity.Group.GroupDestinationEntity;
import com.project.common.entity.Group.GroupDestinationEntity.GroupDestinationEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-07T01:51:21+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class GroupDestinationMapperImpl implements GroupDestinationMapper {

    @Override
    public List<GroupDestinationEntity> toEntityList(List<GroupDestinationDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<GroupDestinationEntity> list = new ArrayList<GroupDestinationEntity>( dtoList.size() );
        for ( GroupDestinationDto groupDestinationDto : dtoList ) {
            list.add( toEntity( groupDestinationDto ) );
        }

        return list;
    }

    @Override
    public GroupDestinationEntity toEntity(GroupDestinationDto dto) {
        if ( dto == null ) {
            return null;
        }

        GroupDestinationEntityBuilder groupDestinationEntity = GroupDestinationEntity.builder();

        groupDestinationEntity.gdSeq( dto.getGdSeq() );
        groupDestinationEntity.gdCompleted( dto.getGdCompleted() );
        groupDestinationEntity.group( dto.getGroup() );
        groupDestinationEntity.heritageSeq( dto.getHeritageSeq() );

        return groupDestinationEntity.build();
    }

    @Override
    public GroupDestinationDto toDto(GroupDestinationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GroupDestinationDtoBuilder groupDestinationDto = GroupDestinationDto.builder();

        groupDestinationDto.gdSeq( entity.getGdSeq() );
        groupDestinationDto.heritageSeq( entity.getHeritageSeq() );
        groupDestinationDto.gdCompleted( entity.getGdCompleted() );
        groupDestinationDto.group( entity.getGroup() );

        return groupDestinationDto.build();
    }

    @Override
    public List<GroupDestinationDto> toDtoList(List<GroupDestinationEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<GroupDestinationDto> list = new ArrayList<GroupDestinationDto>( entityList.size() );
        for ( GroupDestinationEntity groupDestinationEntity : entityList ) {
            list.add( toDto( groupDestinationEntity ) );
        }

        return list;
    }
}
