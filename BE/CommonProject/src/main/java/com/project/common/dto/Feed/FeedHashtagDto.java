package com.project.common.dto.Feed;

import java.time.LocalDateTime;

import com.project.common.entity.Feed.FeedEntity;
import com.project.common.entity.Feed.FeedHashtagEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FeedHashtagDto {
    private int fhSeq;
    private String fhTag;
    private LocalDateTime createdTime;
    private FeedEntity feed;
    
    @Builder
    public FeedHashtagDto(int fhSeq, String fhTag, LocalDateTime createdTime, FeedEntity feed) {
    	super();
    	this.fhSeq = fhSeq;
    	this.fhTag = fhTag;
    	this.createdTime = createdTime;
    	this.feed = feed;
    }
   
    public FeedHashtagEntity toEntity(){
        return FeedHashtagEntity.builder()
                .fhSeq(fhSeq)
                .fhTag(fhTag)
                .feed(feed)
                .createdTime(createdTime)
                .build();
    }

}

