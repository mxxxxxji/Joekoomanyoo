package com.project.common.dto.Feed;

import com.project.common.entity.Feed.FeedEntity;
import com.project.common.entity.Feed.FeedLikeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class FeedLikeDto {
    private int feedLikeSeq;
    private FeedEntity feed;
    private int userSeq;
    
    @Builder
    public FeedLikeDto(int feedLikeSeq, FeedEntity feed, int userSeq) {
		super();
		this.feedLikeSeq = feedLikeSeq;
		this.feed = feed;
		this.userSeq = userSeq;
	}
	
    public FeedLikeEntity toEntity(){
        return FeedLikeEntity.builder()
                .feedLikeSeq(feedLikeSeq)
                .feed(feed)
                .userSeq(userSeq)
                .build();
    }
}

