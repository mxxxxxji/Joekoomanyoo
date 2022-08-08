package com.project.common.dto.Group;

import com.project.common.entity.Group.GroupMemberEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupMemberListDto {

	private int memberSeq;
	private int groupSeq;
	private int memberStatus;
	private String memberAppeal;
	private char memberIsEvaluated;
	private long userSeq;
	private String memberNickname;
	
	@Builder
	public GroupMemberListDto(GroupMemberEntity member) {
		this.memberSeq = member.getMemberSeq();
		this.groupSeq = member.getGroup().getGroupSeq();
		this.memberStatus = member.getMemberStatus();
		this.memberAppeal = member.getMemberAppeal();
		this.memberIsEvaluated = member.getMemberIsEvaluated();
		this.userSeq = member.getUserSeq();
		this.memberNickname=member.getGroup().getUser().getUserNickname();
	}
	
}
