package com.project.common.dto;

import com.project.common.dto.UserDto.UserDtoBuilder;
import com.project.common.entity.UserEntity;
import com.project.common.entity.UserEntity.UserEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-03T22:35:31+0900",
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
        userEntity.userGender( userDto.getUserGender() );

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

        return userDto.build();
    }
}
