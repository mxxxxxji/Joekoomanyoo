package com.project.common.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.project.common.entity.GroupEntity;
import com.project.common.entity.GroupMemberEntity;
import com.project.common.entity.UserEntity;

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
	private long memberSeq;
	private int memberStatus;
	private String memberAppeal;
	private char memberIsEvaluated;
	private Date memberInAt;
	private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
	private GroupEntity group;
    private UserEntity user;
	
	@Builder
	public GroupMemberDto(long memberSeq, int memberStatus, String memberAppeal, char memberIsEvaluated,
			Date memberInAt, LocalDateTime createdTime, LocalDateTime updatedTime, GroupEntity group, UserEntity user) {
		super();
		this.memberSeq = memberSeq;
		this.memberStatus = memberStatus;
		this.memberAppeal = memberAppeal;
		this.memberIsEvaluated = memberIsEvaluated;
		this.memberInAt = memberInAt;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.group = group;
		this.user = user;
	}
	
    public GroupMemberEntity toEntity(){
        return GroupMemberEntity.builder()
                .memberSeq(memberSeq)
                .user(user)
                .memberStatus(memberStatus)
                .memberAppeal(memberAppeal)
                .memberIsEvaluated(memberIsEvaluated)
                .memberInAt(memberInAt)
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .group(group).build();

    }



	

}
