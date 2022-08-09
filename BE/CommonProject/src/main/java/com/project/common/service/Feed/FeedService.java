package com.project.common.service.Feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Feed.FeedDto;
import com.project.common.dto.Feed.ReqFeedDto;
import com.project.common.entity.Feed.FeedEntity;
import com.project.common.entity.Feed.FeedHashtagEntity;
import com.project.common.entity.Feed.FeedLikeEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.mapper.Feed.FeedMapper;
import com.project.common.repository.Feed.FeedHashtagRepository;
import com.project.common.repository.Feed.FeedLikeRepository;
import com.project.common.repository.Feed.FeedRepository;
import com.project.common.repository.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService{
	private final FeedRepository feedRepository;
	private final FeedHashtagRepository feedHashtagRepository;
	private final FeedLikeRepository feedLikeRepository;
	private final UserRepository userRepository;
	
	//피드 등록
	@Transactional
	public FeedDto addFeed(String userId,ReqFeedDto feedDto) {
		FeedEntity feed =new FeedEntity();
		feed.setFeedContent(feedDto.getFeedContent());
		feed.setFeedImgUrl(feedDto.getFeedImgUrl());
		feed.setFeedTitle(feedDto.getFeedTitle());
		feed.setFeedOpen(feedDto.getFeedOpen());
		feed.setCreatedTime(new Date());
		feed.setUpdatedTime(new Date());
		
		for(String tag : feedDto.getHashtags()) {
			feed.addFeedHashtag(
					FeedHashtagEntity.builder()
					.fhTag(tag)
					.createdTime(new Date()).build());
			feedRepository.save(feed);
		}
		
		UserEntity user = userRepository.findByUserId(userId);
		
		//upload
		feedRepository.save(feed);
		user.addFeed(feed);
	 	userRepository.save(user);
		return FeedMapper.MAPPER.toDto(feed);
	}
	
	//피드 전체 조회
	public List<FeedDto> getFeedList(){
		List<FeedEntity> feedList=feedRepository.findAll();
		return FeedMapper.MAPPER.toDtoList(feedList);
	}
	
	//피드 전체 조회 (By 해쉬태그)
	public List<FeedDto> getFeedListByTag(String fhTag){
		List<FeedEntity> feedList=new ArrayList<>();
		for(FeedHashtagEntity entity : feedHashtagRepository.findAll()) {
			if(entity.getFhTag().equals(fhTag)) {
				feedList.add(findFeed(entity.getFeed().getFeedSeq()));
			}
		}
		return FeedMapper.MAPPER.toDtoList(feedList);
	}
	
	//내 피드 조회
	public List<FeedDto> getMyFeedList(String userId){
		List<FeedDto> feedList=new ArrayList<>();
		for(FeedEntity entity : feedRepository.findAll()) {
			if(entity.getUser().getUserId().equals(userId))
				feedList.add(FeedMapper.MAPPER.toDto(entity));
		}
		return feedList;
	}
	
	//피드 보기
	public FeedDto getFeedInfo(int feedSeq) {
		FeedEntity feedInfo=feedRepository.findById(feedSeq).orElse(null);
		return FeedMapper.MAPPER.toDto(feedInfo);
	}
	
	//피드 삭제
	public String deleteFeed(String userId,int feedSeq){
		FeedEntity feed =feedRepository.findById(feedSeq).orElse(null);
		
		//해쉬태그 삭제
		for(FeedHashtagEntity entity : feed.getHashtags())  
			feedHashtagRepository.deleteById(entity.getFhSeq());
		
		//좋아요 삭제
		for(FeedLikeEntity entity : feed.getFeedLikes())
			feedLikeRepository.deleteById(entity.getFeedLikeSeq());
	
//		//userId, feedSeq로 삭제
//		feedRepository.deleteByUserIdAndFeedSeq(userId,feedSeq);
		
		return "Success";
	}
	

	
	//피드 수정
	public String updateFeed(int feedSeq,FeedDto feedDto) {
		FeedEntity feed =feedRepository.findById(feedSeq).orElse(null);

		feed.setFeedContent(feedDto.getFeedContent());
		feed.setFeedImgUrl(feedDto.getFeedImgUrl());
		feed.setFeedTitle(feedDto.getFeedTitle());
		feed.setFeedOpen(feedDto.getFeedOpen());
		feed.setUpdatedTime(new Date());
		feedRepository.save(feed);
		
		return "Success";
	}
	
	//피드 공개/비공개
	public String openFeed(int feedSeq,char feedOpen) {
		FeedEntity feed =feedRepository.findById(feedSeq).orElse(null);
		feed.setFeedOpen(feedOpen);
		feed.setUpdatedTime(new Date());
		feedRepository.save(feed);
		return "Success";
	}

	//피드 찾기
	public FeedEntity findFeed(int feedSeq) {
		FeedEntity findFeed = feedRepository.findById(feedSeq).orElse(null);
	    if (findFeed == null) {
	    	throw new IllegalArgumentException("해당하는 피드가 없습니다.");
		}
		 return findFeed;
	}


}
