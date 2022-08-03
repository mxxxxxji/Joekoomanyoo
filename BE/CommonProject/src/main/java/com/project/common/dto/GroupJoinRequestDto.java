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
public class GroupJoinRequestDto {

	private long userId;
	private String memberAppeal;

	@Builder
	public GroupJoinRequestDto(long userId, String memberAppeal) {
		super();
		this.userId = userId;
		this.memberAppeal = memberAppeal;
	}

	

}
