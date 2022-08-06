package com.project.common.mapper;

import com.project.common.dto.Group.GroupDailyMemoDto;
import com.project.common.dto.Group.GroupDailyMemoDto.GroupDailyMemoDtoBuilder;
import com.project.common.entity.Group.GroupDailyMemoEntity;
import com.project.common.entity.Group.GroupDailyMemoEntity.GroupDailyMemoEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-07T01:51:21+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11 (Oracle Corporation)"
)
public class GroupDailyMemoMapperImpl implements GroupDailyMemoMapper {

    @Override
    public List<GroupDailyMemoEntity> toEntityList(List<GroupDailyMemoDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<GroupDailyMemoEntity> list = new ArrayList<GroupDailyMemoEntity>( dtoList.size() );
        for ( GroupDailyMemoDto groupDailyMemoDto : dtoList ) {
            list.add( toEntity( groupDailyMemoDto ) );
        }

        return list;
    }

    @Override
    public GroupDailyMemoEntity toEntity(GroupDailyMemoDto dto) {
        if ( dto == null ) {
            return null;
        }

        GroupDailyMemoEntityBuilder groupDailyMemoEntity = GroupDailyMemoEntity.builder();

        groupDailyMemoEntity.gdmSeq( dto.getGdmSeq() );
        groupDailyMemoEntity.gdmContent( dto.getGdmContent() );
        groupDailyMemoEntity.gdmDate( dto.getGdmDate() );
        groupDailyMemoEntity.gdmCreatedAt( dto.getGdmCreatedAt() );
        groupDailyMemoEntity.gdmUpdatedAt( dto.getGdmUpdatedAt() );
        groupDailyMemoEntity.group( dto.getGroup() );

        return groupDailyMemoEntity.build();
    }

    @Override
    public GroupDailyMemoDto toDto(GroupDailyMemoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GroupDailyMemoDtoBuilder groupDailyMemoDto = GroupDailyMemoDto.builder();

        return groupDailyMemoDto.build();
    }

    @Override
    public List<GroupDailyMemoDto> toDtoList(List<GroupDailyMemoEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<GroupDailyMemoDto> list = new ArrayList<GroupDailyMemoDto>( entityList.size() );
        for ( GroupDailyMemoEntity groupDailyMemoEntity : entityList ) {
            list.add( toDto( groupDailyMemoEntity ) );
        }

        return list;
    }
}
