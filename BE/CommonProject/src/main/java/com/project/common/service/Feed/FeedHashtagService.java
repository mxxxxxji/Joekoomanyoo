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
	public String addFeedHashtag(int feedSeq, List<FeedHashtagDto> fhList) {
		if(fhList.size()==0)
			throw new IllegalArgumentException("해쉬태그를 등록해주세요");

		FeedEntity feed = feedService.findFeed(feedSeq);
		int cnt =0;
		for(FeedHashtagDto dto : fhList) {
			boolean check=false;
			for(FeedHashtagEntity entity : findHashtag(feedSeq)) {
				if(dto.getFhTag().equals(entity.getFhTag())) {
					check=true; break;
				}
			}
			if(check==false) {
				feed.addFeedHashtag(FeedHashtagEntity.builder().fhTag(dto.getFhTag()).build());
				feedRepository.save(feed);
				cnt++;
			}
		}
		if(cnt>0)
			return "Success";
		throw new IllegalArgumentException("해쉬태그 등록에 실패했습니다");
	}
	
	//피드 해쉬태그 삭제
	@Transactional
	public String deleteFeedHashtag(int feedSeq, List<FeedHashtagDto> fhList) {
		if(fhList.size()==0)
			throw new IllegalArgumentException("삭제할 해쉬태그를 선택해주세요");
		FeedEntity feed = feedService.findFeed(feedSeq);
		int cnt =0;
		for(FeedHashtagDto dto : fhList) {
			for(FeedHashtagEntity entity : findHashtag(feedSeq)) {
				if(dto.getFhTag().equals(entity.getFhTag())) {
					feedHashtagRepository.deleteByFhTag(entity.getFhTag());
					feed.removeFeedHashTag(entity.getFhTag());
					cnt++;
					break;
				}
			}
		}
		if(cnt>0)
			return "Success";
		throw new IllegalArgumentException("삭제할 해쉬태그가 존재하지 않습니다");
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
