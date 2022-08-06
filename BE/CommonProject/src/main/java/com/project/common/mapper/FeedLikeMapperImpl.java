package com.project.common.mapper;

import com.project.common.dto.Feed.FeedLikeDto;
import com.project.common.dto.Feed.FeedLikeDto.FeedLikeDtoBuilder;
import com.project.common.entity.Feed.FeedLikeEntity;
import com.project.common.entity.Feed.FeedLikeEntity.FeedLikeEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-07T01:51:20+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class FeedLikeMapperImpl implements FeedLikeMapper {

    @Override
    public List<FeedLikeEntity> toEntityList(List<FeedLikeDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FeedLikeEntity> list = new ArrayList<FeedLikeEntity>( dtoList.size() );
        for ( FeedLikeDto feedLikeDto : dtoList ) {
            list.add( toEntity( feedLikeDto ) );
        }

        return list;
    }

    @Override
    public FeedLikeEntity toEntity(FeedLikeDto dto) {
        if ( dto == null ) {
            return null;
        }

        FeedLikeEntityBuilder feedLikeEntity = FeedLikeEntity.builder();

        feedLikeEntity.feedLikeSeq( dto.getFeedLikeSeq() );
        feedLikeEntity.feed( dto.getFeed() );
        feedLikeEntity.userSeq( dto.getUserSeq() );

        return feedLikeEntity.build();
    }

    @Override
    public FeedLikeDto toDto(FeedLikeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        FeedLikeDtoBuilder feedLikeDto = FeedLikeDto.builder();

        feedLikeDto.feedLikeSeq( entity.getFeedLikeSeq() );
        feedLikeDto.feed( entity.getFeed() );
        feedLikeDto.userSeq( entity.getUserSeq() );

        return feedLikeDto.build();
    }

    @Override
    public List<FeedLikeDto> toDtoList(List<FeedLikeEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FeedLikeDto> list = new ArrayList<FeedLikeDto>( entityList.size() );
        for ( FeedLikeEntity feedLikeEntity : entityList ) {
            list.add( toDto( feedLikeEntity ) );
        }

        return list;
    }
}
