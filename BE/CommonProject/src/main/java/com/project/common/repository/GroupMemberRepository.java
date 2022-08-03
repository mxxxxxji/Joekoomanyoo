package com.project.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.project.common.entity.GroupEntity;
import com.project.common.entity.GroupMemberEntity;
import com.project.common.entity.UserEntity;


public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {

	//void deleteByGroup(UserEntity user, GroupEntity group);

	

    
}
