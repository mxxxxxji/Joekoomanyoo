package com.project.common.dto.Group;

import java.util.Date;

import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupMemberEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupMemberDto {
	private int memberSeq;
	private int memberStatus;
	private String memberAppeal;
	private char memberIsEvaluated;
	private Date approveTime;
	private Date createdTime;
    private Date updatedTime;
	private GroupEntity group;
    private int userSeq;
    
	@Builder
	public GroupMemberDto(int memberSeq, int memberStatus, String memberAppeal, char memberIsEvaluated,
			Date approveTime, Date createdTime, Date updatedTime, GroupEntity group, int userSeq) {
		super();
		this.memberSeq = memberSeq;
		this.memberStatus = memberStatus;
		this.memberAppeal = memberAppeal;
		this.memberIsEvaluated = memberIsEvaluated;
		this.approveTime = approveTime;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.group = group;
		this.userSeq = userSeq;
	}
	
    public GroupMemberEntity toEntity(){
        return GroupMemberEntity.builder()
                .memberSeq(memberSeq)
                .userSeq(userSeq)
                .memberStatus(memberStatus)
                .memberAppeal(memberAppeal)
                .memberIsEvaluated(memberIsEvaluated)
                .approveTime(approveTime)
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .group(group).build();
    }

}
