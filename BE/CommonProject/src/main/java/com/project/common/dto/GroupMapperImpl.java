package com.project.common.dto;

import com.project.common.dto.GroupDto.GroupDtoBuilder;
import com.project.common.entity.GroupEntity;
import com.project.common.entity.GroupEntity.GroupEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-03T22:35:31+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class GroupMapperImpl implements GroupMapper {

    @Override
    public List<GroupEntity> toEntityList(List<GroupDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<GroupEntity> list = new ArrayList<GroupEntity>( dtoList.size() );
        for ( GroupDto groupDto : dtoList ) {
            list.add( toEntity( groupDto ) );
        }

        return list;
    }

    @Override
    public GroupEntity toEntity(GroupDto dto) {
        if ( dto == null ) {
            return null;
        }

        GroupEntityBuilder groupEntity = GroupEntity.builder();

        groupEntity.groupSeq( dto.getGroupSeq() );
        groupEntity.name( dto.getName() );
        groupEntity.themaImg( dto.getThemaImg() );
        groupEntity.master( dto.getMaster() );
        groupEntity.description( dto.getDescription() );
        groupEntity.accessType( dto.getAccessType() );
        groupEntity.password( dto.getPassword() );
        groupEntity.maxCount( dto.getMaxCount() );
        groupEntity.region( dto.getRegion() );
        groupEntity.startDate( dto.getStartDate() );
        groupEntity.endDate( dto.getEndDate() );
        groupEntity.ageRange( dto.getAgeRange() );
        groupEntity.withChild( dto.isWithChild() );
        groupEntity.withGlobal( dto.isWithGlobal() );
        groupEntity.active( dto.isActive() );
        groupEntity.status( dto.getStatus() );
        groupEntity.createdTime( dto.getCreatedTime() );
        groupEntity.updatedTime( dto.getUpdatedTime() );

        return groupEntity.build();
    }

    @Override
    public GroupDto toDto(GroupEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GroupDtoBuilder groupDto = GroupDto.builder();

        groupDto.groupSeq( entity.getGroupSeq() );
        groupDto.name( entity.getName() );
        groupDto.themaImg( entity.getThemaImg() );
        groupDto.master( entity.getMaster() );
        groupDto.description( entity.getDescription() );
        groupDto.accessType( entity.getAccessType() );
        groupDto.password( entity.getPassword() );
        groupDto.maxCount( entity.getMaxCount() );
        groupDto.region( entity.getRegion() );
        groupDto.startDate( entity.getStartDate() );
        groupDto.endDate( entity.getEndDate() );
        groupDto.ageRange( entity.getAgeRange() );
        groupDto.withChild( entity.isWithChild() );
        groupDto.withGlobal( entity.isWithGlobal() );
        groupDto.active( entity.isActive() );
        groupDto.status( entity.getStatus() );
        groupDto.createdTime( entity.getCreatedTime() );
        groupDto.updatedTime( entity.getUpdatedTime() );

        return groupDto.build();
    }

    @Override
    public List<GroupDto> toDtoList(List<GroupEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<GroupDto> list = new ArrayList<GroupDto>( entityList.size() );
        for ( GroupEntity groupEntity : entityList ) {
            list.add( toDto( groupEntity ) );
        }

        return list;
    }
}
