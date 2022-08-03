package com.project.common.repository;


import com.project.common.entity.GroupEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

	GroupEntity findByGroupSeq(long groupSeq);

	//List<GroupEntity> findAllByName(String groupName);
	//List<GroupEntity> findAllById(String groupSeq);
	
  
    
}
