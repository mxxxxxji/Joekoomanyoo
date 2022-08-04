package com.project.common.dto.Group;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupBasicReqDto {

	private long userSeq;
	private long groupSeq;	
	@Builder
	public GroupBasicReqDto(long userSeq, long groupSeq) {
		super();
		this.userSeq = userSeq;
		this.groupSeq = groupSeq;
	}
}
