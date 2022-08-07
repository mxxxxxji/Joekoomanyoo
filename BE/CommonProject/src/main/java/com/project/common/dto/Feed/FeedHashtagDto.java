package com.project.common.dto.Feed;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date createdTime;
	
    private FeedEntity feed;
    
    @Builder
    public FeedHashtagDto(int fhSeq, String fhTag, Date createdTime, FeedEntity feed) {
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

