package com.project.common.dto;

import com.project.common.dto.GroupAttributeDto.GroupAttributeDtoBuilder;
import com.project.common.entity.GroupAttributeEntity;
import com.project.common.entity.GroupAttributeEntity.GroupAttributeEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-02T04:21:55+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class GroupAttributeMapperImpl implements GroupAttributeMapper {

    @Override
    public List<GroupAttributeDto> toDtoList(List<GroupAttributeEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<GroupAttributeDto> list = new ArrayList<GroupAttributeDto>( entityList.size() );
        for ( GroupAttributeEntity groupAttributeEntity : entityList ) {
            list.add( toDto( groupAttributeEntity ) );
        }

        return list;
    }

    @Override
    public List<GroupAttributeEntity> toEntityList(List<GroupAttributeDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<GroupAttributeEntity> list = new ArrayList<GroupAttributeEntity>( dtoList.size() );
        for ( GroupAttributeDto groupAttributeDto : dtoList ) {
            list.add( toEntity( groupAttributeDto ) );
        }

        return list;
    }

    @Override
    public GroupAttributeEntity toEntity(GroupAttributeDto dto) {
        if ( dto == null ) {
            return null;
        }

        GroupAttributeEntityBuilder groupAttributeEntity = GroupAttributeEntity.builder();

        groupAttributeEntity.gaSeq( dto.getGaSeq() );
        groupAttributeEntity.groupSeq( dto.getGroupSeq() );
        groupAttributeEntity.gaRegion( dto.getGaRegion() );
        groupAttributeEntity.gaStartDate( dto.getGaStartDate() );
        groupAttributeEntity.gaEndDate( dto.getGaEndDate() );
        groupAttributeEntity.gaChildJoin( dto.getGaChildJoin() );
        groupAttributeEntity.gaGlobalJoin( dto.getGaGlobalJoin() );
        groupAttributeEntity.gaAge( dto.getGaAge() );
        groupAttributeEntity.gaCreatedAt( dto.getGaCreatedAt() );
        groupAttributeEntity.gaUpdatedAt( dto.getGaUpdatedAt() );

        return groupAttributeEntity.build();
    }

    @Override
    public GroupAttributeDto toDto(GroupAttributeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GroupAttributeDtoBuilder groupAttributeDto = GroupAttributeDto.builder();

        if ( entity.getGaSeq() != null ) {
            groupAttributeDto.gaSeq( entity.getGaSeq() );
        }
        if ( entity.getGroupSeq() != null ) {
            groupAttributeDto.groupSeq( entity.getGroupSeq() );
        }
        groupAttributeDto.gaRegion( entity.getGaRegion() );
        groupAttributeDto.gaStartDate( entity.getGaStartDate() );
        groupAttributeDto.gaEndDate( entity.getGaEndDate() );
        groupAttributeDto.gaChildJoin( entity.getGaChildJoin() );
        groupAttributeDto.gaGlobalJoin( entity.getGaGlobalJoin() );
        groupAttributeDto.gaAge( entity.getGaAge() );
        groupAttributeDto.gaCreatedAt( entity.getGaCreatedAt() );
        groupAttributeDto.gaUpdatedAt( entity.getGaUpdatedAt() );

        return groupAttributeDto.build();
    }
}
