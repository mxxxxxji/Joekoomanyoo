package com.project.common.dto;

import com.project.common.dto.HeritageScrapDto.HeritageScrapDtoBuilder;
import com.project.common.entity.HeritageScrapEntity;
import com.project.common.entity.HeritageScrapEntity.HeritageScrapEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-03T22:36:04+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16 (Amazon.com Inc.)"
)
public class HeritageScrapMapperImpl implements HeritageScrapMapper {

    @Override
    public List<HeritageScrapDto> toDtoList(List<HeritageScrapEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<HeritageScrapDto> list = new ArrayList<HeritageScrapDto>( entityList.size() );
        for ( HeritageScrapEntity heritageScrapEntity : entityList ) {
            list.add( toDto( heritageScrapEntity ) );
        }

        return list;
    }

    @Override
    public List<HeritageScrapEntity> toEntityList(List<HeritageScrapDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<HeritageScrapEntity> list = new ArrayList<HeritageScrapEntity>( dtoList.size() );
        for ( HeritageScrapDto heritageScrapDto : dtoList ) {
            list.add( toEntity( heritageScrapDto ) );
        }

        return list;
    }

    @Override
    public HeritageScrapEntity toEntity(HeritageScrapDto heritageScrapDto) {
        if ( heritageScrapDto == null ) {
            return null;
        }

        HeritageScrapEntityBuilder heritageScrapEntity = HeritageScrapEntity.builder();

        heritageScrapEntity.heritageScrapSeq( heritageScrapDto.getHeritageScrapSeq() );
        heritageScrapEntity.userSeq( heritageScrapDto.getUserSeq() );
        heritageScrapEntity.heritageSeq( heritageScrapDto.getHeritageSeq() );
        heritageScrapEntity.heritageScrapRegistedAt( heritageScrapDto.getHeritageScrapRegistedAt() );

        return heritageScrapEntity.build();
    }

    @Override
    public HeritageScrapDto toDto(HeritageScrapEntity heritageScrapEntity) {
        if ( heritageScrapEntity == null ) {
            return null;
        }

        HeritageScrapDtoBuilder heritageScrapDto = HeritageScrapDto.builder();

        heritageScrapDto.heritageScrapSeq( heritageScrapEntity.getHeritageScrapSeq() );
        heritageScrapDto.userSeq( heritageScrapEntity.getUserSeq() );
        heritageScrapDto.heritageSeq( heritageScrapEntity.getHeritageSeq() );
        heritageScrapDto.heritageScrapRegistedAt( heritageScrapEntity.getHeritageScrapRegistedAt() );

        return heritageScrapDto.build();
    }
}
