package com.project.common.dto.Group;

import com.project.common.entity.Group.GroupMemberEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupMyListDto {


	private int groupSeq;
    private String groupName;
    private String groupMaster;
    private String groupDescription;
    private char groupAccessType;
    private String groupPassword;
    private int groupMaxCount;
   	private String groupRegion;
   	private int groupStartDate;
   	private int groupEndDate;
   	private int groupAgeRange;
   	private char groupWithChild;
   	private char groupWithGlobal;
   	private char groupActive;
   	private char groupStatus;
   	
   	private int memberStatus;
	private char memberIsEvaluated;
	
	@Builder
	public GroupMyListDto(GroupMemberEntity member) {
		this.groupSeq=member.getGroup().getGroupSeq();
		this.groupName = member.getGroup().getGroupName();
		this.groupMaster = member.getGroup().getGroupMaster();
		this.groupDescription = member.getGroup().getGroupDescription();
		this.groupAccessType = member.getGroup().getGroupAccessType();
		this.groupPassword = member.getGroup().getGroupPassword();
		this.groupMaxCount = member.getGroup().getGroupMaxCount();
		this.groupRegion = member.getGroup().getGroupRegion();
		this.groupStartDate=member.getGroup().getGroupStartDate();
		this.groupEndDate = member.getGroup().getGroupEndDate();
		this.groupAgeRange = member.getGroup().getGroupAgeRange();
		this.groupWithChild=member.getGroup().getGroupWithChild();
		this.groupWithGlobal=member.getGroup().getGroupWithGlobal();
		this.groupActive = member.getGroup().getGroupActive();
		this.groupStatus = member.getGroup().getGroupStatus();
		this.memberStatus=member.getMemberStatus();
		this.memberIsEvaluated = member.getMemberIsEvaluated();
	}
}
