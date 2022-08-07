package com.project.common.service.Feed;

import java.util.ArrayList;
import java.util.Date;
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
	private final FeedService feedService;

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
	public String addFeedHashtag(String userId,int feedSeq, List<FeedHashtagDto> fhList) {
		FeedEntity feed = feedService.findFeed(feedSeq);
		if(!feed.getUser().getUserId().equals(userId))
			return "Fail";
		int cnt =0;
		for(FeedHashtagDto dto : fhList) {
			boolean check=false;
			for(FeedHashtagEntity entity : findHashtag(feedSeq)) {
				if(dto.getFhTag().equals(entity.getFhTag())) {
					check=true; break;
				}
			}
			if(check==false) {
				feed.addFeedHashtag(
						FeedHashtagEntity.builder()
						.fhTag(dto.getFhTag())
						.createdTime(new Date()).build());
				feedRepository.save(feed);
				cnt++;
			}
		}
		if(cnt>0)
			return "Success";
		return "Fail";
	}
	
	//피드 해쉬태그 삭제
	@Transactional
	public String deleteFeedHashtag(String userId,int feedSeq, List<FeedHashtagDto> fhList) {
		FeedEntity feed = feedService.findFeed(feedSeq);
		if(!feed.getUser().getUserId().equals(userId))
			return "Fail";

		int cnt =0;
		for(FeedHashtagDto dto : fhList) {
			for(FeedHashtagEntity entity : findHashtag(feedSeq)) {
				if(dto.getFhTag().equals(entity.getFhTag())) {
					feedHashtagRepository.deleteByFhTag(entity.getFhTag());
					feed.removeFeedHashTag(entity.getFhTag());
					cnt++;break;
				}
			}
		}
		if(cnt>0)
			return "Success";
		return "Fail";
	}

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
