package com.project.common.dto;

import com.project.common.dto.HeritageReivewDto.HeritageReivewDtoBuilder;
import com.project.common.entity.HeritageReviewEntity;
import com.project.common.entity.HeritageReviewEntity.HeritageReviewEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-04T10:21:26+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class HeritageReviewMapperImpl implements HeritageReviewMapper {

    @Override
    public List<HeritageReivewDto> toDtoList(List<HeritageReviewEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<HeritageReivewDto> list = new ArrayList<HeritageReivewDto>( entityList.size() );
        for ( HeritageReviewEntity heritageReviewEntity : entityList ) {
            list.add( toDto( heritageReviewEntity ) );
        }

        return list;
    }

    @Override
    public List<HeritageReviewEntity> toEntityList(List<HeritageReivewDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<HeritageReviewEntity> list = new ArrayList<HeritageReviewEntity>( dtoList.size() );
        for ( HeritageReivewDto heritageReivewDto : dtoList ) {
            list.add( toEntity( heritageReivewDto ) );
        }

        return list;
    }

    @Override
    public HeritageReviewEntity toEntity(HeritageReivewDto heritageReivewDto) {
        if ( heritageReivewDto == null ) {
            return null;
        }

        HeritageReviewEntityBuilder heritageReviewEntity = HeritageReviewEntity.builder();

        heritageReviewEntity.heritageReviewSeq( heritageReivewDto.getHeritageReviewSeq() );
        heritageReviewEntity.userSeq( heritageReivewDto.getUserSeq() );
        heritageReviewEntity.heritageSeq( heritageReivewDto.getHeritageSeq() );
        heritageReviewEntity.heritageReviewText( heritageReivewDto.getHeritageReviewText() );
        heritageReviewEntity.heritageReviewRegistedAt( heritageReivewDto.getHeritageReviewRegistedAt() );

        return heritageReviewEntity.build();
    }

    @Override
    public HeritageReivewDto toDto(HeritageReviewEntity heritageReviewEntity) {
        if ( heritageReviewEntity == null ) {
            return null;
        }

        HeritageReivewDtoBuilder heritageReivewDto = HeritageReivewDto.builder();

        heritageReivewDto.heritageReviewSeq( heritageReviewEntity.getHeritageReviewSeq() );
        heritageReivewDto.userSeq( heritageReviewEntity.getUserSeq() );
        heritageReivewDto.heritageSeq( heritageReviewEntity.getHeritageSeq() );
        heritageReivewDto.heritageReviewText( heritageReviewEntity.getHeritageReviewText() );
        heritageReivewDto.heritageReviewRegistedAt( heritageReviewEntity.getHeritageReviewRegistedAt() );

        return heritageReivewDto.build();
    }
}
