package com.project.common.dto;

import com.project.common.dto.UserDto.UserDtoBuilder;
import com.project.common.entity.UserEntity;
import com.project.common.entity.UserEntity.UserEntityBuilder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-03T22:36:04+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16 (Amazon.com Inc.)"
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
        if ( userDto.getUserRegistedAt() != null ) {
            userEntity.userRegistedAt( LocalDateTime.ofInstant( userDto.getUserRegistedAt().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( userDto.getUserUpdatedAt() != null ) {
            userEntity.userUpdatedAt( LocalDateTime.ofInstant( userDto.getUserUpdatedAt().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        userEntity.isDeleted( userDto.getIsDeleted() );

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
        if ( userEntity.getUserRegistedAt() != null ) {
            userDto.userRegistedAt( Date.from( userEntity.getUserRegistedAt().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( userEntity.getUserUpdatedAt() != null ) {
            userDto.userUpdatedAt( Date.from( userEntity.getUserUpdatedAt().toInstant( ZoneOffset.UTC ) ) );
        }
        userDto.isDeleted( userEntity.getIsDeleted() );

        return userDto.build();
    }
}
