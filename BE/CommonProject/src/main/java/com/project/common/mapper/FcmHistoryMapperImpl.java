package com.project.common.mapper;

import com.project.common.dto.Push.FcmHistoryDto;
import com.project.common.dto.Push.FcmHistoryDto.FcmHistoryDtoBuilder;
import com.project.common.entity.Push.FcmHistoryEntity;
import com.project.common.entity.Push.FcmHistoryEntity.FcmHistoryEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-09T20:44:51+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class FcmHistoryMapperImpl implements FcmHistoryMapper {

    @Override
    public List<FcmHistoryDto> toDtoList(List<FcmHistoryEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FcmHistoryDto> list = new ArrayList<FcmHistoryDto>( entityList.size() );
        for ( FcmHistoryEntity fcmHistoryEntity : entityList ) {
            list.add( toDto( fcmHistoryEntity ) );
        }

        return list;
    }

    @Override
    public List<FcmHistoryEntity> toEntityList(List<FcmHistoryDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FcmHistoryEntity> list = new ArrayList<FcmHistoryEntity>( dtoList.size() );
        for ( FcmHistoryDto fcmHistoryDto : dtoList ) {
            list.add( toEntity( fcmHistoryDto ) );
        }

        return list;
    }

    @Override
    public FcmHistoryEntity toEntity(FcmHistoryDto fcmHistoryDto) {
        if ( fcmHistoryDto == null ) {
            return null;
        }

        FcmHistoryEntityBuilder fcmHistoryEntity = FcmHistoryEntity.builder();

        fcmHistoryEntity.pushSeq( fcmHistoryDto.getPushSeq() );
        fcmHistoryEntity.userSeq( fcmHistoryDto.getUserSeq() );
        fcmHistoryEntity.pushTitle( fcmHistoryDto.getPushTitle() );
        fcmHistoryEntity.pushContent( fcmHistoryDto.getPushContent() );
        fcmHistoryEntity.pushCreatedAt( fcmHistoryDto.getPushCreatedAt() );

        return fcmHistoryEntity.build();
    }

    @Override
    public FcmHistoryDto toDto(FcmHistoryEntity fcmHistoryEntity) {
        if ( fcmHistoryEntity == null ) {
            return null;
        }

        FcmHistoryDtoBuilder fcmHistoryDto = FcmHistoryDto.builder();

        fcmHistoryDto.pushSeq( fcmHistoryEntity.getPushSeq() );
        fcmHistoryDto.userSeq( fcmHistoryEntity.getUserSeq() );
        fcmHistoryDto.pushTitle( fcmHistoryEntity.getPushTitle() );
        fcmHistoryDto.pushContent( fcmHistoryEntity.getPushContent() );
        fcmHistoryDto.pushCreatedAt( fcmHistoryEntity.getPushCreatedAt() );

        return fcmHistoryDto.build();
    }
}
