package com.project.common.dto;

import lombok.*;

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
