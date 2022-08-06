package com.project.common.mapper;

import com.project.common.dto.Feed.FeedDto;
import com.project.common.dto.Feed.FeedDto.FeedDtoBuilder;
import com.project.common.entity.Feed.FeedEntity;
import com.project.common.entity.Feed.FeedEntity.FeedEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-07T01:51:20+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class FeedMapperImpl implements FeedMapper {

    @Override
    public List<FeedEntity> toEntityList(List<FeedDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FeedEntity> list = new ArrayList<FeedEntity>( dtoList.size() );
        for ( FeedDto feedDto : dtoList ) {
            list.add( toEntity( feedDto ) );
        }

        return list;
    }

    @Override
    public FeedEntity toEntity(FeedDto dto) {
        if ( dto == null ) {
            return null;
        }

        FeedEntityBuilder feedEntity = FeedEntity.builder();

        feedEntity.feedSeq( dto.getFeedSeq() );
        feedEntity.feedTitle( dto.getFeedTitle() );
        feedEntity.feedImgUrl( dto.getFeedImgUrl() );
        feedEntity.feedContent( dto.getFeedContent() );
        feedEntity.feedOpen( dto.getFeedOpen() );
        feedEntity.createdTime( dto.getCreatedTime() );
        feedEntity.updatedTime( dto.getUpdatedTime() );

        return feedEntity.build();
    }

    @Override
    public FeedDto toDto(FeedEntity entity) {
        if ( entity == null ) {
            return null;
        }

        FeedDtoBuilder feedDto = FeedDto.builder();

        feedDto.feedSeq( entity.getFeedSeq() );
        feedDto.feedImgUrl( entity.getFeedImgUrl() );
        feedDto.feedTitle( entity.getFeedTitle() );
        feedDto.feedContent( entity.getFeedContent() );
        feedDto.feedOpen( entity.getFeedOpen() );
        feedDto.createdTime( entity.getCreatedTime() );
        feedDto.updatedTime( entity.getUpdatedTime() );
        feedDto.user( entity.getUser() );

        return feedDto.build();
    }

    @Override
    public List<FeedDto> toDtoList(List<FeedEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FeedDto> list = new ArrayList<FeedDto>( entityList.size() );
        for ( FeedEntity feedEntity : entityList ) {
            list.add( toDto( feedEntity ) );
        }

        return list;
    }
}
