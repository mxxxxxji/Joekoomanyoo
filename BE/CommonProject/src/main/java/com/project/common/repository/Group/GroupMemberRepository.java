package com.project.common.repository.Group;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.common.entity.Group.GroupMemberEntity;


public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Integer> {

	GroupMemberEntity findByUserSeq(int userSeq);

	void deleteByUserSeq(int userSeq);

	void deleteByMemberSeq(int memberSeq);

//	void deleteByGroupSeqAndUserSeq(int groupSeq, int userSeq);  
}
