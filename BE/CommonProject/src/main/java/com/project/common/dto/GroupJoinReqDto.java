package com.project.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupJoinReqDto {
	private long userSeq;
	private String memberAppeal;

	@Builder
	public GroupJoinReqDto(long userSeq, String memberAppeal) {
		super();
		this.userSeq = userSeq;
		this.memberAppeal = memberAppeal;
	}
}
