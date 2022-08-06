package com.project.common.dto.User;

import com.project.common.dto.User.UserDto.UserDtoBuilder;
import com.project.common.entity.User.UserEntity;
import com.project.common.entity.User.UserEntity.UserEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-07T01:51:21+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public List<UserDto> toDtoList(List<UserEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( entityList.size() );
        for ( UserEntity userEntity : entityList ) {
            list.add( toDto( userEntity ) );
        }

        return list;
    }

    @Override
    public List<UserEntity> toEntityList(List<UserDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserEntity> list = new ArrayList<UserEntity>( dtoList.size() );
        for ( UserDto userDto : dtoList ) {
            list.add( toEntity( userDto ) );
        }

        return list;
    }

    @Override
    public UserEntity toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.userSeq( userDto.getUserSeq() );
        userEntity.userId( userDto.getUserId() );
        userEntity.userNickname( userDto.getUserNickname() );
        userEntity.userPassword( userDto.getUserPassword() );
        userEntity.userBirth( userDto.getUserBirth() );
        userEntity.socialLoginType( userDto.getSocialLoginType() );
        userEntity.userGender( userDto.getUserGender() );
        userEntity.profileImgUrl( userDto.getProfileImgUrl() );
        userEntity.fcmToken( userDto.getFcmToken() );
        userEntity.userRegistedAt( userDto.getUserRegistedAt() );
        userEntity.userUpdatedAt( userDto.getUserUpdatedAt() );
        userEntity.isDeleted( userDto.getIsDeleted() );
        userEntity.evalCnt( userDto.getEvalCnt() );
        userEntity.evalList1( userDto.getEvalList1() );
        userEntity.evalList2( userDto.getEvalList2() );
        userEntity.evalList3( userDto.getEvalList3() );
        userEntity.evalList4( userDto.getEvalList4() );
        userEntity.evalList5( userDto.getEvalList5() );
        userEntity.evalUpdatedAt( userDto.getEvalUpdatedAt() );

        return userEntity.build();
    }

    @Override
    public UserDto toDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.userSeq( userEntity.getUserSeq() );
        userDto.userId( userEntity.getUserId() );
        userDto.userNickname( userEntity.getUserNickname() );
        userDto.userPassword( userEntity.getUserPassword() );
        userDto.userBirth( userEntity.getUserBirth() );
        userDto.userGender( userEntity.getUserGender() );
        userDto.socialLoginType( userEntity.getSocialLoginType() );
        userDto.profileImgUrl( userEntity.getProfileImgUrl() );
        userDto.fcmToken( userEntity.getFcmToken() );
        userDto.userRegistedAt( userEntity.getUserRegistedAt() );
        userDto.userUpdatedAt( userEntity.getUserUpdatedAt() );
        userDto.isDeleted( userEntity.getIsDeleted() );
        userDto.evalCnt( userEntity.getEvalCnt() );
        userDto.evalList1( userEntity.getEvalList1() );
        userDto.evalList2( userEntity.getEvalList2() );
        userDto.evalList3( userEntity.getEvalList3() );
        userDto.evalList4( userEntity.getEvalList4() );
        userDto.evalList5( userEntity.getEvalList5() );
        userDto.evalUpdatedAt( userEntity.getEvalUpdatedAt() );

        return userDto.build();
    }
}
