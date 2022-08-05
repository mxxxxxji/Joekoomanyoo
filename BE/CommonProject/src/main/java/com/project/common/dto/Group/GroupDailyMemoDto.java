package com.project.common.dto.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import com.project.common.entity.Group.GroupDailyMemoEntity;
import com.project.common.entity.Group.GroupEntity;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class GroupDailyMemoDto {
    private int gdmSeq;
    private String gdmContent;
    private int gdmDate;
    private LocalDateTime gdmCreatedAt;
    private LocalDateTime gdmUpdatedAt;
    private GroupEntity group;
    
    @Builder
	public GroupDailyMemoDto(GroupDailyMemoEntity memo) {
		this.gdmContent = memo.getGdmContent();
		this.gdmDate = memo.getGdmDate();
		this.gdmCreatedAt = memo.getGdmCreatedAt();
	}
    
    
	
    public GroupDailyMemoEntity toEntity(){
        return GroupDailyMemoEntity.builder()
                .gdmSeq(gdmSeq)
                .gdmContent(gdmContent)
                .gdmDate(gdmDate)
                .gdmCreatedAt(gdmCreatedAt)
                .gdmUpdatedAt(gdmUpdatedAt)
                .group(group)
                .build();

    }



}

