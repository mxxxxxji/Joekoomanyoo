package com.project.common.dto.Group;

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
	private int userSeq;
	private String memberAppeal;

	@Builder
	public GroupJoinReqDto(int userSeq, String memberAppeal) {
		super();
		this.userSeq = userSeq;
		this.memberAppeal = memberAppeal;
	}
}
