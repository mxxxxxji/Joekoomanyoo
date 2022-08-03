package com.project.common.dto;

import com.project.common.dto.HeritageDto.HeritageDtoBuilder;
import com.project.common.entity.HeritageEntity;
import com.project.common.entity.HeritageEntity.HeritageEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-03T22:35:31+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class HeritageMapperImpl implements HeritageMapper {

    @Override
    public List<HeritageDto> toDtoList(List<HeritageEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<HeritageDto> list = new ArrayList<HeritageDto>( entityList.size() );
        for ( HeritageEntity heritageEntity : entityList ) {
            list.add( toDto( heritageEntity ) );
        }

        return list;
    }

    @Override
    public List<HeritageEntity> toEntityList(List<HeritageDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<HeritageEntity> list = new ArrayList<HeritageEntity>( dtoList.size() );
        for ( HeritageDto heritageDto : dtoList ) {
            list.add( toEntity( heritageDto ) );
        }

        return list;
    }

    @Override
    public HeritageEntity toEntity(HeritageDto heritageDto) {
        if ( heritageDto == null ) {
            return null;
        }

        HeritageEntityBuilder heritageEntity = HeritageEntity.builder();

        heritageEntity.heritageSeq( heritageDto.getHeritageSeq() );
        heritageEntity.heritageName( heritageDto.getHeritageName() );
        heritageEntity.heritageEra( heritageDto.getHeritageEra() );
        heritageEntity.heritageAddress( heritageDto.getHeritageAddress() );
        heritageEntity.heritageCategory( heritageDto.getHeritageCategory() );
        heritageEntity.heritageLng( heritageDto.getHeritageLng() );
        heritageEntity.heritageLat( heritageDto.getHeritageLat() );
        heritageEntity.heritageImgUrl( heritageDto.getHeritageImgUrl() );
        heritageEntity.heritageMemo( heritageDto.getHeritageMemo() );
        heritageEntity.heritageVoice( heritageDto.getHeritageVoice() );
        heritageEntity.stampExist( heritageDto.getStampExist() );
        heritageEntity.heritageClass( heritageDto.getHeritageClass() );
        heritageEntity.heritageScrapCnt( heritageDto.getHeritageScrapCnt() );
        heritageEntity.heritageReviewCnt( heritageDto.getHeritageReviewCnt() );

        return heritageEntity.build();
    }

    @Override
    public HeritageDto toDto(HeritageEntity heritageEntity) {
        if ( heritageEntity == null ) {
            return null;
        }

        HeritageDtoBuilder heritageDto = HeritageDto.builder();

        heritageDto.heritageSeq( heritageEntity.getHeritageSeq() );
        heritageDto.heritageName( heritageEntity.getHeritageName() );
        heritageDto.heritageEra( heritageEntity.getHeritageEra() );
        heritageDto.heritageAddress( heritageEntity.getHeritageAddress() );
        heritageDto.heritageCategory( heritageEntity.getHeritageCategory() );
        heritageDto.heritageLng( heritageEntity.getHeritageLng() );
        heritageDto.heritageLat( heritageEntity.getHeritageLat() );
        heritageDto.heritageImgUrl( heritageEntity.getHeritageImgUrl() );
        heritageDto.heritageMemo( heritageEntity.getHeritageMemo() );
        heritageDto.heritageVoice( heritageEntity.getHeritageVoice() );
        heritageDto.stampExist( heritageEntity.getStampExist() );
        heritageDto.heritageClass( heritageEntity.getHeritageClass() );
        heritageDto.heritageScrapCnt( heritageEntity.getHeritageScrapCnt() );
        heritageDto.heritageReviewCnt( heritageEntity.getHeritageReviewCnt() );

        return heritageDto.build();
    }
}
