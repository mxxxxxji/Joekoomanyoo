package com.project.common.dto;

import com.project.common.dto.GroupDto.GroupDtoBuilder;
import com.project.common.dto.GroupMemberDto.GroupMemberDtoBuilder;
import com.project.common.entity.GroupEntity;
import com.project.common.entity.GroupEntity.GroupEntityBuilder;
import com.project.common.entity.GroupMemberEntity;
import com.project.common.entity.GroupMemberEntity.GroupMemberEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-03T10:14:43+0900",
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
        groupMemberEntity.userSeq( dto.getUserSeq() );
        groupMemberEntity.group( groupDtoToGroupEntity( dto.getGroup() ) );
        groupMemberEntity.memberStatus( dto.getMemberStatus() );
        groupMemberEntity.memberAppeal( dto.getMemberAppeal() );
        groupMemberEntity.memberIsEvaluated( dto.getMemberIsEvaluated() );
        groupMemberEntity.memberInAt( dto.getMemberInAt() );
        groupMemberEntity.memberCreatedAt( dto.getMemberCreatedAt() );
        groupMemberEntity.memberUpdatedAt( dto.getMemberUpdatedAt() );

        return groupMemberEntity.build();
    }

    @Override
    public GroupMemberDto toDto(GroupMemberEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GroupMemberDtoBuilder groupMemberDto = GroupMemberDto.builder();

        groupMemberDto.memberSeq( entity.getMemberSeq() );
        groupMemberDto.userSeq( entity.getUserSeq() );
        groupMemberDto.memberStatus( entity.getMemberStatus() );
        groupMemberDto.memberAppeal( entity.getMemberAppeal() );
        groupMemberDto.memberIsEvaluated( entity.getMemberIsEvaluated() );
        groupMemberDto.memberInAt( entity.getMemberInAt() );
        groupMemberDto.memberCreatedAt( entity.getMemberCreatedAt() );
        groupMemberDto.memberUpdatedAt( entity.getMemberUpdatedAt() );
        groupMemberDto.group( groupEntityToGroupDto( entity.getGroup() ) );

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

    protected GroupEntity groupDtoToGroupEntity(GroupDto groupDto) {
        if ( groupDto == null ) {
            return null;
        }

        GroupEntityBuilder groupEntity = GroupEntity.builder();

        groupEntity.groupSeq( groupDto.getGroupSeq() );
        groupEntity.attachSeq( groupDto.getAttachSeq() );
        groupEntity.groupMaker( groupDto.getGroupMaker() );
        groupEntity.groupName( groupDto.getGroupName() );
        groupEntity.groupDescription( groupDto.getGroupDescription() );
        groupEntity.groupAccessType( groupDto.getGroupAccessType() );
        groupEntity.groupPassword( groupDto.getGroupPassword() );
        groupEntity.groupStatus( groupDto.getGroupStatus() );
        groupEntity.groupIsActive( groupDto.getGroupIsActive() );
        groupEntity.groupTotalCount( groupDto.getGroupTotalCount() );
        groupEntity.groupRegion( groupDto.getGroupRegion() );
        groupEntity.groupStartDate( groupDto.getGroupStartDate() );
        groupEntity.groupEndDate( groupDto.getGroupEndDate() );
        groupEntity.groupChild( groupDto.getGroupChild() );
        groupEntity.groupGlobal( groupDto.getGroupGlobal() );
        groupEntity.groupAgeRange( groupDto.getGroupAgeRange() );
        groupEntity.groupCreatedAt( groupDto.getGroupCreatedAt() );
        groupEntity.groupUpdatedAt( groupDto.getGroupUpdatedAt() );

        return groupEntity.build();
    }

    protected GroupDto groupEntityToGroupDto(GroupEntity groupEntity) {
        if ( groupEntity == null ) {
            return null;
        }

        GroupDtoBuilder groupDto = GroupDto.builder();

        if ( groupEntity.getGroupSeq() != null ) {
            groupDto.groupSeq( groupEntity.getGroupSeq() );
        }
        groupDto.attachSeq( groupEntity.getAttachSeq() );
        groupDto.groupMaker( groupEntity.getGroupMaker() );
        groupDto.groupName( groupEntity.getGroupName() );
        groupDto.groupDescription( groupEntity.getGroupDescription() );
        groupDto.groupAccessType( groupEntity.getGroupAccessType() );
        groupDto.groupPassword( groupEntity.getGroupPassword() );
        groupDto.groupStatus( groupEntity.getGroupStatus() );
        groupDto.groupIsActive( groupEntity.getGroupIsActive() );
        groupDto.groupTotalCount( groupEntity.getGroupTotalCount() );
        groupDto.groupRegion( groupEntity.getGroupRegion() );
        groupDto.groupStartDate( groupEntity.getGroupStartDate() );
        groupDto.groupEndDate( groupEntity.getGroupEndDate() );
        groupDto.groupChild( groupEntity.getGroupChild() );
        groupDto.groupGlobal( groupEntity.getGroupGlobal() );
        groupDto.groupAgeRange( groupEntity.getGroupAgeRange() );
        groupDto.groupCreatedAt( groupEntity.getGroupCreatedAt() );
        groupDto.groupUpdatedAt( groupEntity.getGroupUpdatedAt() );

        return groupDto.build();
    }
}
