package com.project.common.service.Feed;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.entity.Feed.FeedEntity;
import com.project.common.entity.Feed.FeedLikeEntity;
import com.project.common.repository.Feed.FeedLikeRepository;
import com.project.common.repository.Feed.FeedRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedLikeService {
	private final FeedRepository feedRepository;
	private final FeedLikeRepository feedLikeRepository;
	private final FeedService feedService;

	// 좋아요 갯수 조회
	public int getFeedLikeCount(int feedSeq) {
		List<FeedLikeEntity> list = new ArrayList<>();
		for (FeedLikeEntity entity : feedLikeRepository.findAll()) {
			if (entity.getFeed() != null && entity.getFeed().getFeedSeq() == feedSeq) {
				list.add(entity);
			}
		}
		return list.size();
	}

	// 피드 좋아요 등록
	@Transactional
	public String addFeedLike(int feedSeq, int userSeq) {
		FeedEntity feed = feedService.findFeed(feedSeq);
		for (FeedLikeEntity entity : feed.getFeedLikes()) {
			if (entity.getUserSeq() == userSeq) {
				throw new IllegalArgumentException("이미 좋아요를 등록했습니다");
			}
		}
		feed.addFeedLike(FeedLikeEntity.builder().userSeq(userSeq).build());
		feedRepository.save(feed);
		return "Success";
	}

	// 피드 좋아요 해제
	@Transactional
	public String deleteFeedLike(int feedSeq, int userSeq) {
		List<FeedLikeEntity> feedLikes = findFeedLike(feedSeq);
		if (feedLikes.size() == 0)
			throw new IllegalArgumentException("등록된 좋아요가 없습니다");
		FeedEntity feed = feedService.findFeed(feedSeq);
		for (FeedLikeEntity entity : feedLikes) {
			if (entity.getUserSeq() == userSeq) {
				feedLikeRepository.deleteByUserSeq(userSeq);
				feed.removeFeedLike(userSeq);
			}
		}
		return "Success";
	}

	// 해당 모임 메모 찾기
	public List<FeedLikeEntity> findFeedLike(int feedSeq) {
		List<FeedLikeEntity> findFeedLike = new ArrayList<>();
		for (FeedLikeEntity entity : feedLikeRepository.findAll()) {
			if (entity.getFeed().getFeedSeq() == feedSeq) {
				findFeedLike.add(entity);
			}
		}
		return findFeedLike;
	}

}
