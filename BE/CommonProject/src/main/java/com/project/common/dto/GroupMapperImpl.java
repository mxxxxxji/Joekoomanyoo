package com.project.common.dto;

import com.project.common.dto.GroupDto.GroupDtoBuilder;
import com.project.common.entity.GroupEntity;
import com.project.common.entity.GroupEntity.GroupEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-28T12:09:51+0900",
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
        groupEntity.attachSeq( dto.getAttachSeq() );
        groupEntity.groupMaker( dto.getGroupMaker() );
        groupEntity.groupPassword( dto.getGroupPassword() );
        groupEntity.groupName( dto.getGroupName() );
        groupEntity.groupMaxCount( dto.getGroupMaxCount() );
        groupEntity.groupDescription( dto.getGroupDescription() );
        groupEntity.groupStatus( dto.getGroupStatus() );
        groupEntity.groupIsActive( dto.getGroupIsActive() );
        groupEntity.groupAccessType( dto.getGroupAccessType() );
        groupEntity.groupCreatedAt( dto.getGroupCreatedAt() );
        groupEntity.groupUpdatedAt( dto.getGroupUpdatedAt() );

        return groupEntity.build();
    }

    @Override
    public GroupDto toDto(GroupEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GroupDtoBuilder groupDto = GroupDto.builder();

        if ( entity.getGroupSeq() != null ) {
            groupDto.groupSeq( entity.getGroupSeq() );
        }
        groupDto.groupName( entity.getGroupName() );
        groupDto.attachSeq( entity.getAttachSeq() );
        if ( entity.getGroupMaker() != null ) {
            groupDto.groupMaker( entity.getGroupMaker() );
        }
        groupDto.groupDescription( entity.getGroupDescription() );
        groupDto.groupMaxCount( entity.getGroupMaxCount() );
        groupDto.groupAccessType( entity.getGroupAccessType() );
        groupDto.groupPassword( entity.getGroupPassword() );
        groupDto.groupStatus( entity.getGroupStatus() );
        groupDto.groupIsActive( entity.getGroupIsActive() );
        groupDto.groupCreatedAt( entity.getGroupCreatedAt() );
        groupDto.groupUpdatedAt( entity.getGroupUpdatedAt() );

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
