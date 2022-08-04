package com.project.common.dto;

import com.project.common.dto.UserKeywordDto.UserKeywordDtoBuilder;
import com.project.common.entity.UserKeywordEntity;
import com.project.common.entity.UserKeywordEntity.UserKeywordEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-04T15:38:18+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class UserKeywordMapperImpl implements UserKeywordMapper {

    @Override
    public List<UserKeywordDto> toDtoList(List<UserKeywordEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserKeywordDto> list = new ArrayList<UserKeywordDto>( entityList.size() );
        for ( UserKeywordEntity userKeywordEntity : entityList ) {
            list.add( toDto( userKeywordEntity ) );
        }

        return list;
    }

    @Override
    public List<UserKeywordEntity> toEntityList(List<UserKeywordDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserKeywordEntity> list = new ArrayList<UserKeywordEntity>( dtoList.size() );
        for ( UserKeywordDto userKeywordDto : dtoList ) {
            list.add( toEntity( userKeywordDto ) );
        }

        return list;
    }

    @Override
    public UserKeywordEntity toEntity(UserKeywordDto userKeywordDto) {
        if ( userKeywordDto == null ) {
            return null;
        }

        UserKeywordEntityBuilder userKeywordEntity = UserKeywordEntity.builder();

        userKeywordEntity.myKeywordSeq( userKeywordDto.getMyKeywordSeq() );
        userKeywordEntity.myKeywordName( userKeywordDto.getMyKeywordName() );
        userKeywordEntity.userSeq( userKeywordDto.getUserSeq() );
        userKeywordEntity.myKeywordRegistedAt( userKeywordDto.getMyKeywordRegistedAt() );

        return userKeywordEntity.build();
    }

    @Override
    public UserKeywordDto toDto(UserKeywordEntity userKeywordEntity) {
        if ( userKeywordEntity == null ) {
            return null;
        }

        UserKeywordDtoBuilder userKeywordDto = UserKeywordDto.builder();

        userKeywordDto.myKeywordSeq( userKeywordEntity.getMyKeywordSeq() );
        userKeywordDto.userSeq( userKeywordEntity.getUserSeq() );
        userKeywordDto.myKeywordName( userKeywordEntity.getMyKeywordName() );
        userKeywordDto.myKeywordRegistedAt( userKeywordEntity.getMyKeywordRegistedAt() );

        return userKeywordDto.build();
    }
}
