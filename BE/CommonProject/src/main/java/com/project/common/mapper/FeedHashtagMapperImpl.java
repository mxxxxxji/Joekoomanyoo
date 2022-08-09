package com.project.common.mapper;

import com.project.common.dto.Feed.FeedHashtagDto;
import com.project.common.dto.Feed.FeedHashtagDto.FeedHashtagDtoBuilder;
import com.project.common.entity.Feed.FeedHashtagEntity;
import com.project.common.entity.Feed.FeedHashtagEntity.FeedHashtagEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-07T11:02:47+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class FeedHashtagMapperImpl implements FeedHashtagMapper {

    @Override
    public List<FeedHashtagEntity> toEntityList(List<FeedHashtagDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FeedHashtagEntity> list = new ArrayList<FeedHashtagEntity>( dtoList.size() );
        for ( FeedHashtagDto feedHashtagDto : dtoList ) {
            list.add( toEntity( feedHashtagDto ) );
        }

        return list;
    }

    @Override
    public FeedHashtagEntity toEntity(FeedHashtagDto dto) {
        if ( dto == null ) {
            return null;
        }

        FeedHashtagEntityBuilder feedHashtagEntity = FeedHashtagEntity.builder();

        feedHashtagEntity.fhSeq( dto.getFhSeq() );
        feedHashtagEntity.fhTag( dto.getFhTag() );
        feedHashtagEntity.feed( dto.getFeed() );
        feedHashtagEntity.createdTime( dto.getCreatedTime() );

        return feedHashtagEntity.build();
    }

    @Override
    public FeedHashtagDto toDto(FeedHashtagEntity entity) {
        if ( entity == null ) {
            return null;
        }

        FeedHashtagDtoBuilder feedHashtagDto = FeedHashtagDto.builder();

        feedHashtagDto.fhSeq( entity.getFhSeq() );
        feedHashtagDto.fhTag( entity.getFhTag() );
        feedHashtagDto.createdTime( entity.getCreatedTime() );
        feedHashtagDto.feed( entity.getFeed() );

        return feedHashtagDto.build();
    }

    @Override
    public List<FeedHashtagDto> toDtoList(List<FeedHashtagEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FeedHashtagDto> list = new ArrayList<FeedHashtagDto>( entityList.size() );
        for ( FeedHashtagEntity feedHashtagEntity : entityList ) {
            list.add( toDto( feedHashtagEntity ) );
        }

        return list;
    }
}
