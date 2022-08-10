package com.project.common.temp;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.common.entity.Feed.FeedHashtagEntity;


public interface FeedHashtagRepository extends JpaRepository<FeedHashtagEntity, Integer> {


	void deleteByFhSeq(int fhSeq);


}
