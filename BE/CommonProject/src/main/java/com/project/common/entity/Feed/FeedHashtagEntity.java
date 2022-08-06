package com.project.common.entity.Feed;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter 
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name="tb_feed_hashtag")
public class FeedHashtagEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fh_seq")
	private int fhSeq;
	
	@Column(name = "fh_tag")
    private String fhTag;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="feed_seq")
	private FeedEntity feed;
	
	@Column(name="fh_created_at")
    private LocalDateTime createdTime;

}
