package com.project.common.dto.Group;

import com.project.common.entity.Group.GroupMemberEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupMemberListDto {

	private long memberSeq;
	private long groupSeq;
	private int memberStatus;
	private String memberAppeal;
	private char memberIsEvaluated;
	
	@Builder
	public GroupMemberListDto(GroupMemberEntity member) {
		this.memberSeq = member.getMemberSeq();
		this.groupSeq = member.getGroup().getGroupSeq();
		this.memberStatus = member.getMemberStatus();
		this.memberAppeal = member.getMemberAppeal();
		this.memberIsEvaluated = member.getMemberIsEvaluated();
	}
	
}
