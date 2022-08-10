package com.project.common.service.Feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Feed.FeedDto;
import com.project.common.dto.Feed.ReqFeedDto;
import com.project.common.dto.Feed.ResFeedDto;
import com.project.common.entity.Feed.FeedEntity;
import com.project.common.entity.Feed.FeedHashtagEntity;
import com.project.common.entity.Feed.FeedLikeEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.mapper.Feed.FeedMapper;
import com.project.common.repository.Feed.FeedLikeRepository;
import com.project.common.repository.Feed.FeedRepository;
import com.project.common.repository.User.UserRepository;
import com.project.common.temp.FeedHashtagRepository;

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
		feedRepository.save(feed);
		user.addFeed(feed);
	 	userRepository.save(user);
		return FeedMapper.MAPPER.toDto(feed);
	}
	
	//피드 전체 조회
	public List<ResFeedDto> getFeedList(){
		List<ResFeedDto> feedList =new ArrayList<>();
		for(FeedEntity entity : feedRepository.findAll()) {
			feedList.add(new ResFeedDto(entity,entity.getUser()));
		}
		return feedList;
	}
	
	//피드 전체 조회 (By 해쉬태그)
	public List<ResFeedDto> getFeedListByTag(String fhTag){
		List<ResFeedDto> feedList=new ArrayList<>();
		for(FeedHashtagEntity entity : feedHashtagRepository.findAll()) {
			if(entity.getFhTag().equals(fhTag)) {
				feedList.add(new ResFeedDto(entity.getFeed(),entity.getFeed().getUser()));
			}
		}
		return feedList;
	}
	
	//내 피드 조회
	public List<ResFeedDto> getMyFeedList(String userId){
		List<ResFeedDto> feedList=new ArrayList<>();
		UserEntity user = userRepository.findByUserId(userId);
		for(FeedEntity entity : user.getFeeds()) {
			feedList.add(new ResFeedDto(entity,user));
		}
		return feedList;
	}
	
	//피드 보기
	public ResFeedDto getFeedDetail(int feedSeq) {
		FeedEntity feedInfo=feedRepository.findByFeedSeq(feedSeq);
		ResFeedDto feed=new ResFeedDto();
		feed.setCreatedTime(feedInfo.getCreatedTime());
		feed.setFeedSeq(feedSeq);
		feed.setFeedImgUrl(feedInfo.getFeedImgUrl());
		feed.setFeedContent(feedInfo.getFeedContent());
		feed.setFeedTitle(feedInfo.getFeedTitle());
		feed.setFeedOpen(feedInfo.getFeedOpen());
		feed.setUserImgUrl(feedInfo.getUser().getProfileImgUrl());
		feed.setUserNickname(feedInfo.getUser().getUserNickname());
		return feed;
	}
	
	//피드 삭제
	public String deleteFeed(String userId,int feedSeq){
		FeedEntity feed =feedRepository.findByFeedSeq(feedSeq);
		UserEntity user = userRepository.findByUserId(userId);
		
		//해쉬태그 삭제
		for(FeedHashtagEntity entity : feed.getHashtags())  
			feedHashtagRepository.deleteByFhSeq(entity.getFhSeq());
		
		//좋아요 삭제
		for(FeedLikeEntity entity : feed.getFeedLikes())
			feedLikeRepository.deleteByFeedLikeSeq(entity.getFeedLikeSeq());
	
		for(FeedEntity entity: user.getFeeds()) {
			feedRepository.deleteByFeedSeq(entity.getFeedSeq());
		}
		
		return "Success";
	}
	
	//피드 수정
	public String modifyFeed(int feedSeq,ReqFeedDto feedDto) {
		FeedEntity feed =feedRepository.findByFeedSeq(feedSeq);
		feed.setFeedContent(feedDto.getFeedContent());
		feed.setFeedImgUrl(feedDto.getFeedImgUrl());
		feed.setFeedTitle(feedDto.getFeedTitle());
		feed.setFeedOpen(feedDto.getFeedOpen());
		feed.setUpdatedTime(new Date());
		feedRepository.save(feed);
		return "Success";
	}
	
	//피드 공개/비공개
	public String setFeedOpen(int feedSeq,char feedOpen) {
		FeedEntity feed =feedRepository.findByFeedSeq(feedSeq);
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
