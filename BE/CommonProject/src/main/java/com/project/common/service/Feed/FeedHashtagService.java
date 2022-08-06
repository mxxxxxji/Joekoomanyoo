package com.project.common.service.Feed;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Feed.FeedHashtagDto;
import com.project.common.entity.Feed.FeedEntity;
import com.project.common.entity.Feed.FeedHashtagEntity;
import com.project.common.mapper.FeedHashtagMapper;
import com.project.common.repository.Feed.FeedHashtagRepository;
import com.project.common.repository.Feed.FeedRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedHashtagService{
	private final FeedRepository feedRepository;
	private final FeedHashtagRepository feedHashtagRepository;

	//피드 전체 조회
	public List<FeedHashtagDto> getFeedHashtagList(int feedSeq){
		List<FeedHashtagEntity> feedList=new ArrayList<>();
		for(FeedHashtagEntity entity: feedHashtagRepository.findAll()) {
			if(entity.getFeed().getFeedSeq()==feedSeq)
				feedList.add(entity);
		}
		if(feedList.size()==0)
			throw new IllegalArgumentException("등록한 해쉬태그가 없습니다");
		return FeedHashtagMapper.MAPPER.toDtoList(feedList);
	}
	
	
	//피드 해쉬태그 등록
	@Transactional
	public String addFeedHashtag(int feedSeq, List<FeedHashtagDto> fhList) {
		
		FeedEntity feed = feedRepository.findById(feedSeq).orElse(null);
		for(FeedHashtagDto hashtag : fhList) {
			
			//해쉬태그 하나씩 등록
			feed.addFeedHashtag(FeedHashtagEntity.builder()
					.fhTag(hashtag.getFhTag()).build());
			feedRepository.save(feed);
		}	
		return "Success";
	}
	
//	public String addHashtag(FeedHashtagDto hashtag) {
//		List<FeedHashtagEntity> hashList = feedHashtagRepository.findAll();
//		for(FeedHashtagEntity entity : hashList) {
//			if(entity.getFhTag().equals(hashtag.getFhTag())
//					con
//		}
//		
//		
//	}
//	
//	//피드 해쉬태그 삭제
//	@Transactional
//	public String deleteFeedHashtag(int feedSeq, String fhTag) {
//		List<FeedHashtagEntity> hashtags= findHashtag(feedSeq);
//		if(hashtags==null) 
//			throw new IllegalArgumentException("등록된 해쉬태그가 없습니다");
//		FeedEntity feed = feedRepository.findById(feedSeq).orElse(null);
//		for(FeedHashtagEntity entity : hashtags) {
//			if(entity.getFhTag().equals(fhTag)) {
//				feedHashtagRepository.deleteByFhTag(fhTag);
//				feed.removeFeedHashTag(fhTag);
//			}
//		}
//		return "Success";
//	}

	
	
	
	//해쉬태그 찾기
	public List<FeedHashtagEntity> findHashtag(int feedSeq) {
		List<FeedHashtagEntity> findHashtag = new ArrayList<>();
		for(FeedHashtagEntity entity: feedHashtagRepository.findAll()) {
			if(entity.getFeed().getFeedSeq()==feedSeq) {
				findHashtag.add(entity);
			}
		}
		return findHashtag;
	}

}
