package com.project.common.dto;



import com.project.common.entity.GroupEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RespGroupDto {
	private String groupName;
	private String attachSeq;
	private String groupMaker;
	private String groupDescription;
	private int groupMaxCount;
	private char groupAccessType;
	private String groupPassword;
	private String groupStatus;
	private char groupIsActive;
	
	private String Region;
	private int StartDate;
	private int EndDate;
	private char ChildJoin;
	private char GlobalJoin;
	private int Age;

	@Builder
	public RespGroupDto(GroupEntity group) {
		this.groupName = group.getGroupName();
		this.attachSeq = group.getAttachSeq();
		this.groupMaker = group.getGroupMaker().getUserNickname();
		this.groupDescription = group.getGroupDescription();
		this.groupMaxCount = group.getGroupMaxCount();
		this.groupAccessType = group.getGroupAccessType();
		this.groupPassword = group.getGroupPassword();
		this.groupStatus = group.getGroupStatus();
		this.groupIsActive = group.getGroupIsActive();
		this.Region = group.getGroupAttributeEntity().getGaRegion();
		this.StartDate = group.getGroupAttributeEntity().getGaStartDate();
		this.EndDate =group.getGroupAttributeEntity().getGaEndDate();
		this.ChildJoin = group.getGroupAttributeEntity().getGaChildJoin();
		this.GlobalJoin = group.getGroupAttributeEntity().getGaGlobalJoin();
		this.Age = group.getGroupAttributeEntity().getGaAge();
	}
	




}
