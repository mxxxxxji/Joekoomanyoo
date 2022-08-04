package com.project.common.dto.Group;

import com.project.common.entity.Group.GroupMemberEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupMyListDto {

    private String groupName;
    private String master;
    private String descriaption;
    private char accessType;
    private String password;
    private int maxCount;
   	private String region;
   	private int startDate;
   	private int endDate;
   	private int ageRange;
   	private char withChild;
   	private char withGlobal;
   	private char active;
   	private char status;
	
   	private int memberStatus;
	private char memberIsEvaluated;
	
	@Builder
	public GroupMyListDto(GroupMemberEntity member) {
		this.groupName = member.getGroup().getName();
		this.master = member.getGroup().getMaster();
		this.descriaption = member.getGroup().getDescription();
		this.accessType = member.getGroup().getAccessType();
		this.password = member.getGroup().getPassword();
		this.maxCount = member.getGroup().getMaxCount();
		this.region = member.getGroup().getRegion();
		this.startDate=member.getGroup().getStartDate();
		this.endDate = member.getGroup().getEndDate();
		this.ageRange = member.getGroup().getAgeRange();
		this.withChild=member.getGroup().getWithChild();
		this.withGlobal=member.getGroup().getWithGlobal();
		this.active = member.getGroup().getActive();
		this.status = member.getGroup().getStatus();
		this.memberStatus=member.getMemberStatus();
		this.memberIsEvaluated = member.getMemberIsEvaluated();
	}
}
