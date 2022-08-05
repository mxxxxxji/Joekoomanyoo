package com.project.common.mapper;

import com.project.common.dto.Group.GroupMemberDto;
import com.project.common.dto.Group.GroupMemberDto.GroupMemberDtoBuilder;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.entity.Group.GroupMemberEntity.GroupMemberEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-05T14:08:55+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class GroupMemberMapperImpl implements GroupMemberMapper {

    @Override
    public List<GroupMemberEntity> toEntityList(List<GroupMemberDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<GroupMemberEntity> list = new ArrayList<GroupMemberEntity>( dtoList.size() );
        for ( GroupMemberDto groupMemberDto : dtoList ) {
            list.add( toEntity( groupMemberDto ) );
        }

        return list;
    }

    @Override
    public GroupMemberEntity toEntity(GroupMemberDto dto) {
        if ( dto == null ) {
            return null;
        }

        GroupMemberEntityBuilder groupMemberEntity = GroupMemberEntity.builder();

        groupMemberEntity.memberSeq( dto.getMemberSeq() );
        groupMemberEntity.memberStatus( dto.getMemberStatus() );
        groupMemberEntity.memberAppeal( dto.getMemberAppeal() );
        groupMemberEntity.memberIsEvaluated( dto.getMemberIsEvaluated() );
        groupMemberEntity.memberInAt( dto.getMemberInAt() );
        groupMemberEntity.createdTime( dto.getCreatedTime() );
        groupMemberEntity.updatedTime( dto.getUpdatedTime() );
        groupMemberEntity.userSeq( dto.getUserSeq() );
        groupMemberEntity.group( dto.getGroup() );

        return groupMemberEntity.build();
    }

    @Override
    public GroupMemberDto toDto(GroupMemberEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GroupMemberDtoBuilder groupMemberDto = GroupMemberDto.builder();

        groupMemberDto.memberSeq( entity.getMemberSeq() );
        groupMemberDto.memberStatus( entity.getMemberStatus() );
        groupMemberDto.memberAppeal( entity.getMemberAppeal() );
        groupMemberDto.memberIsEvaluated( entity.getMemberIsEvaluated() );
        groupMemberDto.memberInAt( entity.getMemberInAt() );
        groupMemberDto.createdTime( entity.getCreatedTime() );
        groupMemberDto.updatedTime( entity.getUpdatedTime() );
        groupMemberDto.group( entity.getGroup() );
        groupMemberDto.userSeq( entity.getUserSeq() );

        return groupMemberDto.build();
    }

    @Override
    public List<GroupMemberDto> toDtoList(List<GroupMemberEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<GroupMemberDto> list = new ArrayList<GroupMemberDto>( entityList.size() );
        for ( GroupMemberEntity groupMemberEntity : entityList ) {
            list.add( toDto( groupMemberEntity ) );
        }

        return list;
    }
}
