package com.project.common.dto;

import com.project.common.dto.GroupDto.GroupDtoBuilder;
import com.project.common.dto.UserSignupDto.UserSignupDtoBuilder;
import com.project.common.entity.GroupEntity;
import com.project.common.entity.GroupEntity.GroupEntityBuilder;
import com.project.common.entity.UserEntity;
import com.project.common.entity.UserEntity.UserEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-02T04:21:55+0900",
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
        groupEntity.groupName( dto.getGroupName() );
        groupEntity.groupDescription( dto.getGroupDescription() );
        groupEntity.groupAccessType( dto.getGroupAccessType() );
        groupEntity.groupPassword( dto.getGroupPassword() );
        groupEntity.groupStatus( dto.getGroupStatus() );
        groupEntity.groupIsActive( dto.getGroupIsActive() );
        groupEntity.groupMaxCount( dto.getGroupMaxCount() );
        groupEntity.groupCreatedAt( dto.getGroupCreatedAt() );
        groupEntity.groupUpdatedAt( dto.getGroupUpdatedAt() );
        groupEntity.groupMaker( userSignupDtoToUserEntity( dto.getGroupMaker() ) );

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
        groupDto.groupMaker( userEntityToUserSignupDto( entity.getGroupMaker() ) );
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

    protected UserEntity userSignupDtoToUserEntity(UserSignupDto userSignupDto) {
        if ( userSignupDto == null ) {
            return null;
        }

        UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.userSeq( userSignupDto.getUserSeq() );
        userEntity.userId( userSignupDto.getUserId() );
        userEntity.userNickname( userSignupDto.getUserNickname() );
        userEntity.userPassword( userSignupDto.getUserPassword() );
        userEntity.userBirth( userSignupDto.getUserBirth() );
        userEntity.socialLoginType( userSignupDto.getSocialLoginType() );
        userEntity.userGender( userSignupDto.getUserGender() );
        userEntity.profileImgUrl( userSignupDto.getProfileImgUrl() );
        userEntity.jwtToken( userSignupDto.getJwtToken() );
        userEntity.fcmToken( userSignupDto.getFcmToken() );
        userEntity.userRegistedAt( userSignupDto.getUserRegistedAt() );
        userEntity.userUpdatedAt( userSignupDto.getUserUpdatedAt() );
        userEntity.isDeleted( userSignupDto.getIsDeleted() );

        return userEntity.build();
    }

    protected UserSignupDto userEntityToUserSignupDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserSignupDtoBuilder userSignupDto = UserSignupDto.builder();

        userSignupDto.userSeq( userEntity.getUserSeq() );
        userSignupDto.userId( userEntity.getUserId() );
        userSignupDto.userNickname( userEntity.getUserNickname() );
        userSignupDto.userPassword( userEntity.getUserPassword() );
        userSignupDto.userBirth( userEntity.getUserBirth() );
        userSignupDto.socialLoginType( userEntity.getSocialLoginType() );
        userSignupDto.userGender( userEntity.getUserGender() );
        userSignupDto.profileImgUrl( userEntity.getProfileImgUrl() );
        userSignupDto.jwtToken( userEntity.getJwtToken() );
        userSignupDto.fcmToken( userEntity.getFcmToken() );
        userSignupDto.userRegistedAt( userEntity.getUserRegistedAt() );
        userSignupDto.userUpdatedAt( userEntity.getUserUpdatedAt() );
        userSignupDto.isDeleted( userEntity.getIsDeleted() );

        return userSignupDto.build();
    }
}
