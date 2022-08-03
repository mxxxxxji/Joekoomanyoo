package com.project.common.dto;

import java.util.Date;

import com.project.common.entity.GroupMemberEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupLeaveRequestDto {

	private long userSeq;
	private long groupSeq;
	
	@Builder
	public GroupLeaveRequestDto(long userSeq, long groupSeq) {
		super();
		this.userSeq = userSeq;
		this.groupSeq = groupSeq;
	}
	

	

	

}
