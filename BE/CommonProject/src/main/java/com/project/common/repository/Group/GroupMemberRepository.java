package com.project.common.repository.Group;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.common.entity.Group.GroupMemberEntity;


public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {

	GroupMemberEntity findByUserSeq(long userSeq);

	void deleteByUserSeq(long userSeq);  
}
