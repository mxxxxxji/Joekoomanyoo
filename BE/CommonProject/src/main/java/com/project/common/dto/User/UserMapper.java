package com.project.common.dto.User;

import com.project.common.entity.User.UserEntity;
import com.project.common.mapper.StructMapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends StructMapper<UserDto, UserEntity> {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Override
    UserEntity toEntity(final UserDto userDto);

    @Override
    UserDto toDto(final UserEntity userEntity);

}