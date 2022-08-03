package com.project.common.repository;

import com.project.common.entity.HeritageScrapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeritageScrapRepository extends JpaRepository<HeritageScrapEntity, Integer> {

    List<HeritageScrapEntity> findAllByUserSeq(int userSeq);
}
