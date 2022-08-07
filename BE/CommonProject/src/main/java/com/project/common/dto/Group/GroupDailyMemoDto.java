package com.project.common.dto.Group;

import java.util.Date;

import com.project.common.entity.Group.GroupDailyMemoEntity;
import com.project.common.entity.Group.GroupEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class GroupDailyMemoDto {
    private int gdmSeq;
    private String gdmContent;
    private Date gdmDate;
    private Date gdmCreatedAt;
    private Date gdmUpdatedAt;
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

